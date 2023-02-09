package com.javaex.dao;

import com.javaex.vo.UserVo;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

class UserDaoTest {
    @Test
    void createTest() throws SQLException {
        UserDao userDao = new UserDao();
        DaoResult daoResult = userDao.insert(new UserVo("name", "email", "password", "gender"));

        assertThat(daoResult.getMessage()).isEqualTo("success");
    }

    @Test
    void readByNumber() throws SQLException {
        UserDao userDao = new UserDao();
        DaoResult daoResult = userDao.readBy("userNumber", new UserVo(1));
        assertThat(((UserVo) daoResult.getResultValue().get("userVo")).getUserNumber())
                .isEqualTo(1);
    }


}