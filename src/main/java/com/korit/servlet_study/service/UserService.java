package com.korit.servlet_study.service;

import com.korit.servlet_study.dao.UserDao;
import com.korit.servlet_study.dto.ResponseDto;
import com.korit.servlet_study.entity.User;

import java.util.List;
import java.util.Optional;

public class UserService {
    private UserDao userDao;
    private static UserService userService = null;

    private UserService() {
        userDao = UserDao.getInstance();
    }

    public static UserService getInstance() {
        if(userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public ResponseDto<?> getUser(int userid) {

        User foundUser = userDao.findById(userid);

        if(foundUser == null) {

            return ResponseDto.fail("유저 정보가 없습니다");
        }

        return ResponseDto.success(foundUser);
    }

    public List<User> getAllUsers(String searchValue) {
        if(searchValue == null || searchValue.isBlank()) {
            return userDao.findAll();
        }
        return userDao.findAllBySearchValue(searchValue);
    }

    public User addUser(User user) {
        Optional<User> userOptional = userDao.save(user);
        return userOptional.get();
    }


}