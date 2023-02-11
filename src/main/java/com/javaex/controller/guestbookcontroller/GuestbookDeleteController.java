package com.javaex.controller.guestbookcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.dao.GuestbookDao;
import com.javaex.manager.GuestbookManager;
import com.javaex.vo.GuestbookVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class GuestbookDeleteController implements Controller<GuestbookManager> {
    @Override
    public ModelView process(GuestbookManager guestbookManager, HttpServletRequest request, HttpServletResponse response) {
        GuestbookVo guestbookVo = new GuestbookVo(
                Integer.parseInt(request.getParameter("no")),
                request.getParameter("password")
        );

        guestbookManager.delete(guestbookVo);

        return new ModelView("gb");
    }
}
