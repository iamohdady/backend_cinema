package com.example.backend_cinema.service;

import com.example.backend_cinema.CinemaSql;
import com.example.backend_cinema.mysql.MySqlConnector;
import com.example.backend_cinema.mysql.entity.UserEntity;
import com.example.backend_cinema.request.AddUserRequest;
import com.example.backend_cinema.request.UpdatePasswordRequest;
import com.example.backend_cinema.response.ForgetPasswordResponse;
import com.example.backend_cinema.response.TokenResponse;
import com.example.backend_cinema.response.UserResponse;
import com.example.backend_cinema.utils.crypt.CryptUtils;
import com.example.backend_cinema.utils.exception.BadRequestException;
import com.example.backend_cinema.utils.model.Constants;
import com.example.backend_cinema.utils.token.Jwt;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final MySqlConnector mysql;



    @Autowired
    public UserService(MySqlConnector mysql) {
        this.mysql = mysql;
    }

    public TokenResponse login(String username, String password) throws Exception {
        if (Strings.hasLength(username) && Strings.hasLength(password)) {
            UserEntity user = mysql.selectOne(
                CinemaSql.selectUser(username, CryptUtils.base64Encode(CryptUtils.hashSha256(password))),
                UserEntity.class
            );
            if (user != null && Strings.hasLength(user.status)) {
                switch (user.status) {
                    case Constants.UserStatus.INVITED:
                        throw new BadRequestException("Request changing password");
                    case Constants.UserStatus.ACTIVE:
                        mysql.updateOne(CinemaSql.updateUserLastTime(username));
                        String role = user.role;

                        String accessToken = Jwt.generateToken(username, role);
                        String refreshToken = generateRefreshToken();

                        saveTokenToDatabase(username, accessToken, refreshToken);

                        return new TokenResponse(accessToken, refreshToken);
                    case Constants.UserStatus.INACTIVE:
                    default:
                        break;
                }
            }
            throw new BadRequestException("User is inactive");
        }
        throw new BadRequestException("username or password is empty");
    }

    public List<UserEntity> list() {
        return mysql.selectList(CinemaSql.selectAllUser(), UserEntity.class);
    }

    public UserResponse add(AddUserRequest request) throws Exception {
        if (StringUtils.hasLength(request.username)) {
            if (checkExisted(request.username)) {
                throw new BadRequestException("Username already exists");
            }
            boolean success = mysql.insertOne(
                CinemaSql.insertUser(
                    request.username,
                    CryptUtils.base64Encode(CryptUtils.hashSha256(request.password)),
                    request.fullname,
                    request.address,
                    request.phone,
                    request.birthday,
                    request.email,
                    request.image
                )
            );
            return new UserResponse(success ? "SUCCESS" : "FAIL", request.username, request.password);
        }
        throw new BadRequestException("username is empty");
    }

    public boolean updateUserPassword(UpdatePasswordRequest request) throws Exception {
        if (request.isValid()) {
            if (!request.newPassword.equals(request.confirmPassword)) {
                throw new BadRequestException("New password and confirm password do not match");
            }
//            if (request.isWeakNewPassword()) {
//                throw new BadRequestException("Weak password");
//            }
            isValidUserPassword(request, false);
            return mysql.updateOne(CinemaSql.updateUserPassword(
                request.username,
                CryptUtils.base64Encode(CryptUtils.hashSha256(request.newPassword))));
        }
        throw new BadRequestException("Missing information for password update");
    }

    public boolean updateRole(String username, String role) throws Exception {
        if (StringUtils.hasLength(username) && StringUtils.hasLength(role)) {
            return mysql.updateOne(
                CinemaSql.updateRole(
                    username, role
                )
            );
        }
        throw new BadRequestException("username or role is empty");
    }

    public ForgetPasswordResponse forget(String username) throws Exception {
        if (StringUtils.hasLength(username)) {
            String newPassword = Constants.NewPassword.NEW_PASSWORD;
            boolean success = mysql.updateOne(
                CinemaSql.resetUserPassword(
                    username,
                    CryptUtils.base64Encode(CryptUtils.hashSha256(newPassword))
                )
            );
            return new ForgetPasswordResponse(success ? "SUCCESS" : "FAIL", newPassword);
        }
        throw new BadRequestException("username is empty");
    }
    public UserEntity getMemberDetails(String username) throws Exception {
        if (StringUtils.hasLength(username)) {
            return mysql.selectOne(CinemaSql.selectMemberByUsername(username), UserEntity.class);
        }
        throw new BadRequestException("Username is empty");
    }

    public boolean updateMember(AddUserRequest request) throws Exception {
        if (StringUtils.hasLength(request.username)) {
            // Cập nhật thông tin người dùng
            boolean success = mysql.updateOne(CinemaSql.updateMember(
                request.username,
                request.fullname,
                request.address,
                request.phone,
                request.birthday,
                request.email,
                request.image
            ));
            return success; // Trả về kết quả
        }
        throw new BadRequestException("Username is empty");
    }

    public boolean deleteUser(String username) throws Exception {
        if (StringUtils.hasLength(username)) {
            // Xóa người dùng trong cơ sở dữ liệu
            boolean success = mysql.deleteOne(CinemaSql.deleteUser(username));
            return success; // Trả về kết quả
        }
        throw new BadRequestException("Username is empty");
    }

    public boolean updateUserPasswordFirstTime(UpdatePasswordRequest request) throws Exception {
        if (request.isValid()) {
            if (!request.newPassword.equals(request.confirmPassword)) {
                throw new BadRequestException("New password and confirm password do not match");
            }
//            if (request.isWeakNewPassword()) {
//                throw new BadRequestException("Weak password");
//            }
            isValidUserPassword(request, true);
            return mysql.updateOne(CinemaSql.updateUserPassword(
                request.username,
                CryptUtils.base64Encode(CryptUtils.hashSha256(request.newPassword))));
        }
        throw new BadRequestException("Missing information for password update");
    }

    private void isValidUserPassword(UpdatePasswordRequest request, boolean isFirstTime) throws Exception {
        UserEntity user = mysql.selectOne(
            CinemaSql.selectUser(
                request.username,
                CryptUtils.base64Encode(CryptUtils.hashSha256(request.oldPassword))
            ),
            UserEntity.class
        );

        if (user == null || user.username == null || user.password == null ||
            !user.password.equals(CryptUtils.base64Encode(CryptUtils.hashSha256(request.oldPassword)))) {
            throw new BadRequestException("Old password is incorrect");
        }
        if (isFirstTime && !Constants.UserStatus.INVITED.equals(user.status)) {
            throw new BadRequestException("User already be active");
        }
        if (!isFirstTime && !Constants.UserStatus.ACTIVE.equals(user.status)) {
            throw new BadRequestException("User is not active");
        }
    }

    private boolean checkExisted(String username) throws Exception {
        if (Strings.hasLength(username)) {
            UserEntity user = mysql.selectOne(CinemaSql.selectByUsername(username), UserEntity.class);
            return user != null;
        }
        throw new BadRequestException("username is empty");
    }

    private boolean saveTokenToDatabase(String username, String accessToken, String refreshToken) throws Exception {
        if (Strings.hasLength(username) && Strings.hasLength(accessToken) && Strings.hasLength(refreshToken)) {
            return mysql.updateOne(CinemaSql.updateUserTokens(
                    username,
                    accessToken,
                    CryptUtils.base64Encode(CryptUtils.hashSha256(CryptUtils.byteArrayToHexString(CryptUtils.hashMd5(refreshToken))))
                )
            );
        }
        throw new BadRequestException("error saving token to database");
    }

    private String generateRefreshToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
