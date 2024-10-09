package com.example.backend_cinema.utils.model;

public interface Constants {

    String EMPTY = "";
    String SEMICOLON = ";";
    String PIPE = "\\|";

    /**
     * These headers are enriched by VNPT-NET
     */
    String HEADER_MSISDN_MD5 = "msisdn-md5";
    String HEADER_MSISDN_ENC = "msisdn-enc";

    String PARAM_INFO = "info";

    String ALGORITHM_MD5 = "md5";
    String ALGORITHM_RC4 = "RC4";
    String ALGORITHM_AES = "AES";
    String ALGORITHM_AES_GCM = "AES/GCM/NoPadding";
    String ALGORITHM_SHA256 = "SHA-256";
    String ALGORITHM_HMAC_SHA256 = "HmacSHA256";

    String CONFIG = "{" +
        "\"protocol_name\":\"htwo\"," +

        // 256 bytes
        "\"transport_key\":\"4+H/LghRr3BxsofaIswdwi2m2lbdYmrvanx66KBimVZ0DwWGnlPdgpVqC0HTFuUL57TksY1kmo87bbY3hXPHil6ThunK7bfZxyzG+jJ15G3vxO0IUzekmrvNTozOw0NfmF1Mx1nuS1TOAbeDwwg4X+eGBhw4ArO2LG4ZVIuHtq8ivN7URoGzyxax+zQZJthGAR1jTjcZByG/iOBSwxoTsMUumvJH8FxnXOlGVCFu3KGS3nCP4Zo46MhOITPbPas3BgquckQ8gINORuIrE0ai7LL88SfE+ctB3RVJ9lyWMjG0ySLYJaIRso7EfU+4z7ZvA6n5XZoU7WQHvwbrS+8MWQ==\"," +
        "\"transport_key_version\":\"1\"," +
        "\"transport_key_hash_method\":\"sha256\"," +

        // 256 bytes
        "\"signature_key\":\"qbnnYyA4ci6p6QYPfHOo7ss0+7GzYlGW9lScK4yfEwh+fRKLAscazRMw4M/hDLcezbzqpYfRu0vvaGZcN7w0s0FVpaJKs2AEfNvtRlXYqqnOB1U2O/rQoaxmm0hqtxc6oT1BeJCGPpkDl9FK0OOUVRBAwoM0ab73yi/VF/n8dStpr9bFrFB46Cbj2pi9gKfSD181iY4y/yD5kiBA4ewQer/RBtaJYX0tnbwq4tMa1+TJ5RLp8s90QfU8B+Gf14GyHbZ60kSiXiNXtKW4kiUeEt11jzbVxqtdTWEhcgrRv2ijUq6abB/RTceQR1UFObXuPzv4MC7JzMp8EZZEAO4O3Q==\"," +

        // 256 bytes
        "\"info_signature_key\":\"A47jwuKp0plXI4mcvQX+7TxmDIu/sYa/9e4QX8wSkejHHdTKg0BnLj/k9srIs/veqK9IT9jiFE7g+BZOY5e1XkBe33PxzGsev+3pwcCAFHNK58Jj1fCToLvVfoFQXvGpPgb6fgsPrrIjNr0ZTNBGyf6tkYuCHpW6ixDsvGreuMPivVMpjHHc50iiLbwzoaHMoFYNY07369HdWQxbtV2tZlFqIwnIOw9pf6rjyvgRbacKJtjIqbv5GlH4zRxtVqhVDGlLb4G4MPs4Rsg5IBWQQvCdq7dUGT2vX0XWBfMw06rePlRrtUrYOtA1oJG5NCJJkF9cacZP6YTBF5Uc/h33cQ==\"," +

        "\"eacr_key\":\"d7e8e94655cc33ed2440\"" + // eacr_key is used in VNPT side. Not share to Meta
        "}";


    interface UserStatus {
        String INVITED = "INVITED";
        String ACTIVE = "ACTIVE";
        String INACTIVE = "INACTIVE";
    }

    interface NewPassword {
        String NEW_PASSWORD = "123456";
    }
}
