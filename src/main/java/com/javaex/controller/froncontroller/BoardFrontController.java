package com.javaex.controller.froncontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.controller.boardcontroller.*;
import com.javaex.manager.BoardManager;
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


@WebServlet(name = "boardFrontControllerServlet", urlPatterns = "/board/*")
public class BoardFrontController extends HttpServlet {

    private final Map<String, Controller<BoardManager>> controllerMap = new HashMap<>();

    public BoardFrontController() {

        controllerMap.put("/board/writeform", new BoardWriteFormController());
        controllerMap.put("/board/modifyform", new BoardModifyFormController());

        controllerMap.put("/board/list", new BoardListController());
        controllerMap.put("/board/read", new BoardReadController());
        controllerMap.put("/board/modify", new BoardModifyController());
        controllerMap.put("/board/write", new BoardWriteController());
        controllerMap.put("/board/delete", new BoardDeleteController());

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getParameter("a");
        Controller<BoardManager> userController = controllerMap.get(requestURI);
        response.setContentType("text/html; charset=UTF-8");
        if (controllerMap == null) {
            WebUtil.redirect(request, response, "/list");
            return;
        }

        BoardManager boardManager = BoardManager.getInstance();

        ModelView boardModelView = userController.process(boardManager, request, response);

        renderView(request, response, boardModelView);
    }

    private static void renderView(HttpServletRequest request, HttpServletResponse response, ModelView userModelView) throws ServletException, IOException {
        MySiteView.of(userModelView.getViewName())
                .render(userModelView.getModel(), request, response);
    }


}
