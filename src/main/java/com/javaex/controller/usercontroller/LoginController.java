package com.javaex.controller.usercontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.dao.DaoResult;
import com.javaex.manager.UserManager;
import com.javaex.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController implements Controller<UserManager> {
    @Override
    public ModelView process(UserManager userManager, HttpServletRequest request, HttpServletResponse response) {

        UserVo userVo = fetchUserVo(userManager, request);

        handleNotFound(response, userVo);

        HttpSession session = request.getSession(true);
        session.setAttribute("authUser", userVo);

        return new ModelView("");
    }

    private static void handleNotFound(HttpServletResponse response, UserVo userVo) {
        if (userVo == null) {
            new ModelView("loginform");
            try {
                response.sendRedirect("/user?a=loginform&result=fail");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static UserVo fetchUserVo(UserManager userManager, HttpServletRequest request) {
        DaoResult daoResult = userManager.readByEmailAndPassword(
                new UserVo(
                        request.getParameter("email"),
                        request.getParameter("password")));

        UserVo userFound = (UserVo) daoResult.getResultValue().get("userVo");
        return userFound;
    }
}