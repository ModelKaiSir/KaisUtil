package com.kais.repository;

import com.kais.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class BaseDao<T> {

    @Autowired
    JdbcTemplate template;

    public abstract List<T> getAll(String query);

    public abstract Optional<T> get(String query, Object[] args);

    protected boolean validate(String sql) {

        try {

            return template.queryForObject(sql, Integer.class) > -1;
        } catch (DataAccessException e) {

            e.printStackTrace();
            return false;
        }
    }
}
