package com.javaex.controller.UserController;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.dao.DaoResult;
import com.javaex.manager.UserManager;
import com.javaex.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyFormController implements Controller<UserManager> {
    @Override
    public ModelView process(UserManager userManager, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        UserVo authUser = (UserVo) session.getAttribute("authUser");

        DaoResult daoResult = userManager.readByUserNumber(authUser);

        request.setAttribute("userVo", daoResult.getResultValue().get("userVo"));

        return new ModelView("modifyform");
    }
}
