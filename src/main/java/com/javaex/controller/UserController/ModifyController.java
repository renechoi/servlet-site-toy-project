package com.javaex.controller.UserController;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.manager.UserManager;
import com.javaex.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyController implements Controller<UserManager> {
    @Override
    public ModelView process(UserManager userManager, HttpServletRequest request, HttpServletResponse response) {

        UserVo userVo = new UserVo(
                request.getParameter("name"),
                request.getParameter("password"),
                request.getParameter("gender")
                );

        HttpSession session = request.getSession();
        UserVo authUser = (UserVo) session.getAttribute("authUser");

        int no = authUser.getUserNumber();
        userVo.setUserNumber(no);

        userManager.update(userVo);

        authUser.setName(userVo.getName());

        return new ModelView("");
    }
}
