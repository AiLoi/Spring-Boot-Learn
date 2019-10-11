package com.websocket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: websocket
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-20 17:02
 **/
@RestController
@RequestMapping("/websocket")
public class WebSocketController {


    @GetMapping("/index")
    public ModelAndView websocket() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/websocket.html");
        return modelAndView;
    }

}

