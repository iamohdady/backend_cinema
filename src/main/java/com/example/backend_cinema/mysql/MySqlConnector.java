package com.example.backend_cinema.mysql;

import com.example.backend_cinema.utils.model.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class MySqlConnector {

    private final Logger logger = Logger.getLogger(getClass());

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public MySqlConnector(@Qualifier("mysql-jdbctemplate") NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public boolean insertOne(Pair<String, MapSqlParameterSource> pair) {
        try {
            int result = template.update(pair.key, pair.value);
            logger.debug("#insertOne: sql=" + pair.key + ", param=" + pair.value + ", result=" + result);
            return result > 0;
        } catch (Exception e) {
            logger.error("#insertOne: sql=" + pair.key + ", param=" + pair.value + ", error=" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public <T> T selectOne(Pair<String, MapSqlParameterSource> pair, Class<T> clazz) {
        try {
            T result = template.queryForObject(pair.key, pair.value, BeanPropertyRowMapper.newInstance(clazz));
            logger.debug("#selectOne: sql=" + pair.key + ", param=" + pair.value.toString() + ", result=" + result);
            return result;
        } catch (Exception e) {
            logger.error("#selectOne: sql=" + pair.key + ", param=" + pair.value.toString() + ", error=" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T> selectList(Pair<String, MapSqlParameterSource> pair, Class<T> clazz) {
        try {
            List<T> list = template.query(pair.key, pair.value, BeanPropertyRowMapper.newInstance(clazz));
            logger.debug("#selectList: sql=" + pair.key + ", param=" + pair.value.toString() + ", result=" + list);
            return list;
        } catch (Exception e) {
            logger.error("#selectList: sql=" + pair.key + ", param=" + pair.value.toString() + ", error=" + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean updateOne(Pair<String, MapSqlParameterSource> pair) {
        try {
            int result = template.update(pair.key, pair.value);
            logger.debug("#updateOne: sql=" + pair.key + ", param=" + pair.value.toString() + ", result=" + result);
            return result > 0;
        } catch (Exception e) {
            logger.error("#updateOne: sql=" + pair.key + ", param=" + pair.value.toString() + ", error=" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateList(Pair<String, List<MapSqlParameterSource>> pair) {
        try {
            int[] results = template.batchUpdate(pair.key, pair.value.toArray(new MapSqlParameterSource[]{}));
            logger.debug("#updateList: sql=" + pair.key + ", param=" + pair.value.toString() + ", results=" + Arrays.toString(results));
            return true;
        } catch (Exception e) {
            logger.error("#updateList: sql=" + pair.key + ", param=" + pair.value.toString() + ", error=" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOne(Pair<String, MapSqlParameterSource> pair) {
        try {
            int result = template.update(pair.key, pair.value);
            logger.debug("#deleteOne: sql=" + pair.key + ", param=" + pair.value + ", result=" + result);
            return result > 0;
        } catch (Exception e) {
            logger.error("#deleteOne: sql=" + pair.key + ", param=" + pair.value + ", error=" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

