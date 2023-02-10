package com.javaex.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class MySiteView {

    private String viewPath;

    public MySiteView(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        modelToRequestAttribute(model, request);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath);
        requestDispatcher.forward(request,response);
    }
    //
    private static void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.forEach( (key, value) -> session.setAttribute(key, value));
//        checkModelInside(model);
    }
}
