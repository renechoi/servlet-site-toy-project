package com.javaex.controller;

import com.javaex.manager.Manager;

import java.util.Map;

public interface Controller<T> {
    ModelView<T> process(Manager manager, Map<String, String> parameterMap);

}
