package com.javaex.manager;

import com.javaex.dao.DaoResult;
import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

import java.sql.SQLException;

public class UserManager implements Manager{

    private final UserDao USER_DAO = new UserDao();

    private static final UserManager instance = new UserManager();

    public static UserManager getInstance() {
        return instance;
    }

    public DaoResult register(UserVo userVo) {
        try {
            return USER_DAO.insert(userVo);
        } catch (RuntimeException | SQLException e) {
            return new DaoResult("fail");
        }
    }
}
