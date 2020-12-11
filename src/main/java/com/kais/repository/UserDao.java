package com.kais.repository;

import com.kais.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDao extends BaseDao<User> {

    public void init() {

        if (!validate("SELECT COUNT(*) FROM T_USER")) {

            template.execute("CREATE TABLE T_USER(ID TEXT PRIMARY KEY, NAME TEXT)");
        }
    }

    @Override
    public List<User> getAll(String sql) {

        return template.queryForList(sql, User.class);
    }

    @Override
    public Optional<User> get(String query, Object[] args) {

        return Optional.ofNullable(template.queryForObject(query, (rs, i) ->{

            User user = new User();
            user.setId(rs.getString(0));
            user.setName(rs.getString(1));
            return user;
        }, args));
    }

    public void insert(User user) {

        String insert = "INSERT INTO T_USER (ID, NAME) VALUES (?, ?)";
        template.update(insert, user.getId(), user.getName());
    }
}
