package com.javaex.controller;

import com.javaex.manager.Manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface Controller<T> {
    ModelView process(T manager, HttpServletRequest request, HttpServletResponse response);

}
