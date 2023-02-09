package com.javaex.controller;

import com.javaex.dao.DaoResult;
import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String actionName = request.getParameter("a");
        System.out.println("user:" + actionName);

        if ("joinform".equals(actionName)) {

            WebUtil.forward(request, response, "/WEB-INF/views/user/joinform.jsp");
			
			/*
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/joinform.jsp");
			rd.forward(request, response);
			*/

        } else if ("join".equals(actionName)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String gender = request.getParameter("gender");
            UserVo vo = new UserVo(name, email, password, gender);

            UserDao userDao = new UserDao();
            try {
                userDao.insert(vo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/joinsuccess.jsp");
            rd.forward(request, response);

        } else if ("idcheck".equals(actionName)) {
            String email = request.getParameter("email");
            UserDao userDao = new UserDao();

            Enumeration e = request.getParameterNames();
            while (e.hasMoreElements()) {
                String name = (String) e.nextElement();
                String[] values = request.getParameterValues(name);
                for (String value : values) {
                    System.out.println("name=" + name + ",value=" + value);
                }
            }

            response.setContentType("text/html; charset=UTF-8");

            // "true", "false" 문자열이 반환되므로 ajax에서 결과값으로 받아서 처리
            try {
                response.getWriter().write(userDao.idCheck(email));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        } else if ("modify".equals(actionName)) {
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String gender = request.getParameter("gender");
            UserVo vo = new UserVo();
            vo.setName(name);
            vo.setPassword(password);
            vo.setGender(gender);

            HttpSession session = request.getSession();
            UserVo authUser = (UserVo) session.getAttribute("authUser");

            int no = authUser.getUserNumber();
            vo.setUserNumber(no);

            UserDao dao = new UserDao();
            try {
                dao.update(vo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            authUser.setName(name);

            WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");

        } else if ("modifyform".equals(actionName)) {

            HttpSession session = request.getSession();
            UserVo authUser = (UserVo) session.getAttribute("authUser");
            int no = authUser.getUserNumber(); // 회원번호

            UserDao userDao = new UserDao();
            DaoResult userVo = null;
            try {
                userVo = userDao.readBy("userNumber", authUser);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println(userVo.toString());

            request.setAttribute("userVo", userVo);
            WebUtil.forward(request, response, "/WEB-INF/views/user/modifyform.jsp");

        } else if ("loginform".equals(actionName)) {

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp");
            rd.forward(request, response);

        } else if ("login".equals(actionName)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            UserDao userDao = new UserDao();
            DaoResult vo = null;
            try { //TODO : null 값 처리
                vo = userDao.readBy("emailAndPassword", null);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (vo == null) {
                System.out.println("실패");
                response.sendRedirect("/user?a=loginform&result=fail");
            } else {
                System.out.println("성공");
                HttpSession session = request.getSession(true);
                session.setAttribute("authUser", vo);

                response.sendRedirect("/main");
                return;
            }

        } else if ("logout".equals(actionName)) {
            HttpSession session = request.getSession();
            session.removeAttribute("authUser");
            session.invalidate();
            response.sendRedirect("/main");

        } else {

            WebUtil.redirect(request, response, "/main");
            /*response.sendRedirect("/mysite/main");*/
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
