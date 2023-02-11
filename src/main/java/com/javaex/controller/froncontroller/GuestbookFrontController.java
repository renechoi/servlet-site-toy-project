package com.javaex.controller.froncontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.controller.guestbookcontroller.GuestbookAddController;
import com.javaex.controller.guestbookcontroller.GuestbookDeleteController;
import com.javaex.controller.guestbookcontroller.GuestbookDeleteFormController;
import com.javaex.controller.guestbookcontroller.GuestbookMainController;
import com.javaex.manager.GuestbookManager;
import com.javaex.util.WebUtil;
import com.javaex.view.MySiteView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "guestbookFrontControllerServlet", urlPatterns = "/gb/*")
public class GuestbookFrontController extends HttpServlet {

    private final Map<String, Controller<GuestbookManager>> controllerMap = new HashMap<>();

    public GuestbookFrontController() {

        controllerMap.put("/gb/add", new GuestbookAddController());
        controllerMap.put("/gb/deleteform", new GuestbookDeleteFormController());
        controllerMap.put("/gb/delete", new GuestbookDeleteController());
        controllerMap.put("/gb/", new GuestbookMainController());


    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getParameter("a");
        Controller<GuestbookManager> userController = controllerMap.get(requestURI);
        response.setContentType("text/html; charset=UTF-8");
        if (controllerMap == null) {
            WebUtil.redirect(request, response, "/list");
            return;
        }

        GuestbookManager guestbookManager = GuestbookManager.getInstance();

        ModelView guestbookModelView = userController.process(guestbookManager, request, response);

        renderView(request, response, guestbookModelView);
    }

    private static void renderView(HttpServletRequest request, HttpServletResponse response, ModelView userModelView) throws ServletException, IOException {
        MySiteView.of(userModelView.getViewName())
                .render(userModelView.getModel(), request, response);
    }


}
