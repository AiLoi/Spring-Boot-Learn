package com.webflux.config;

import com.webflux.service.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


/**
 * @program: webflux
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-23 14:05
 **/

@Configuration
public class RouterConfig {


    @Autowired
    private UserHandler userHandler;


    private static final String HEADER_NAME = "header_user";

    private static final String HEADER_VALUE = "header_password";

    @Bean
    public RouterFunction<ServerResponse> userRouter(){

        RouterFunction<ServerResponse> router =
                route(
                        GET("/router/user?id={id}")
                        .and(accept(MediaType.APPLICATION_STREAM_JSON)),
                        userHandler::getUser)
                        .andRoute(
                        GET("/router/user?userName={userName}&note={note}")
                        .and(accept(MediaType.APPLICATION_STREAM_JSON)),
                        userHandler::findUsers)
                        .andRoute(
                        POST("/router/user")
                        .and(contentType(MediaType.APPLICATION_STREAM_JSON))
                        .and(accept(MediaType.APPLICATION_STREAM_JSON)),
                        userHandler::insertUser)
                        .andRoute(
                        PUT("/router/user")
                        .and(contentType(MediaType.APPLICATION_STREAM_JSON))
                        .and(accept(MediaType.APPLICATION_STREAM_JSON)),
                        userHandler::updateUser)
                        .andRoute(
                        DELETE("/router/user?id={id}")
                        .and(accept(MediaType.APPLICATION_STREAM_JSON)),
                        userHandler::deleteUser)
                        .andRoute(
                        PUT("/router/user/name")
                        .and(accept(MediaType.APPLICATION_STREAM_JSON)),
                        userHandler::updateUserName
                );

        return router;

    }


    @Bean
    public RouterFunction<ServerResponse> securityRouter(){
        RouterFunction<ServerResponse> router =
                route(
                        GET("/security/user?id={id}")
                        .and(accept(MediaType.APPLICATION_STREAM_JSON)),
                        userHandler::getUser
                ).filter(this::filterLogic);
        return router;
    }

    private Mono<ServerResponse> filterLogic(ServerRequest request, HandlerFunction<ServerResponse> next){

        String userName = request.headers().header(HEADER_NAME).get(0);

        String password = request.headers().header(HEADER_VALUE).get(0);

        if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password) && !userName.equals(password)){
            //接受请求
            return next.handle(request);
        }
        //请求头不匹配，则不允许请求，返回未签名错误
        return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
    }

}

