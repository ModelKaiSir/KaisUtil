package com.kais.services;

import com.kais.domain.User;
import com.kais.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserDao dao;

    @Autowired
    public UserService(UserDao dao) {

        dao.init();
        this.dao = dao;
    }

    public List<User> getUsers() {

        String sql = "SELECT ID, NAME FROM T_USER";
        return dao.getAll(sql);
    }

    public Optional<User> getUser(String userId) {

        String sql = "SELECT ID, NAME FROM T_USER WHERE ID = ?";
        return dao.get(sql, new Object[]{ userId });
    }

    public void add(User user) {

        dao.insert(user);
    }
}
