package com.javaex.controller.UserController;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.manager.UserManager;
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


@WebServlet(name = "userFrontControllerServlet", urlPatterns = "/user/*")
public class UserFrontController extends HttpServlet {

    private final Map<String, Controller<UserManager>> controllerMap = new HashMap<>();

    public UserFrontController() {

        controllerMap.put("/user/joinform", new JoinFormController());
        controllerMap.put("/user/modifyform", new ModifyFormController());
        controllerMap.put("/user/loginform", new LoginFormController());

        controllerMap.put("/user/join", new JoinController());
        controllerMap.put("/user/idcheck", new IdCheckController());
        controllerMap.put("/user/modify", new ModifyController());
        controllerMap.put("/user/login", new LoginController());
        controllerMap.put("/user/logout", new LogoutController());

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getParameter("a");
        Controller<UserManager> userController = controllerMap.get(requestURI);
        response.setContentType("text/html; charset=UTF-8");
        if (controllerMap == null) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            WebUtil.redirect(request, response, "/main");
            return;
        }

        UserManager userManager = UserManager.getInstance();

        ModelView userModelView = userController.process(userManager, request, response);

        renderView(request, response, userModelView);
    }

    private static void renderView(HttpServletRequest request, HttpServletResponse response, ModelView userModelView) throws ServletException, IOException {
        MySiteView.of(userModelView.getViewName())
                .render(userModelView.getModel(), request, response);
    }


}
