package com.webflux.config;

import com.webflux.pojo.User;
import com.webflux.pojo.enums.SexEnum;
import com.webflux.utils.UserValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * @program: webflux
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-22 14:37
 **/

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {


    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(stringUserConverter());
    }



    /*
    转换器
     */
    public Converter<String,User> stringUserConverter(){


        Converter<String,User> converter = new Converter<String, User>() {
            @Override
            public User convert(String s) {
                String[] strArr = s.split("-");
                User user = new User();
                Long id = Long.valueOf(strArr[0]);
                user.setId(id);
                user.setUserName(strArr[1]);
                int sexCode = Integer.valueOf(strArr[2]);
                SexEnum sexEnum = SexEnum.getSexEnum(sexCode);
                user.setSexEnum(sexEnum);
                user.setNote(strArr[3]);

                return user;
            }
        };

        return converter;
    }


    /*
    验证器
     */
    @Override
    public Validator getValidator() {
        return new UserValidator();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //注册资源可以通过uri访问
        registry.addResourceHandler("/resources/static/**")
                //注册Spring资源，可以在Spring机制中访问
                .addResourceLocations("/public","classpath:/static/")
                //缓存一年
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }
}

