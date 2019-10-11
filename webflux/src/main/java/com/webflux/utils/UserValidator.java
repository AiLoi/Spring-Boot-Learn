package com.webflux.utils;

import com.webflux.pojo.User;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @program: webflux
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-22 14:48
 **/
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(User.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if(StringUtils.isEmpty(user.getUserName())) {
            errors.rejectValue("userName",null,"用户名不能为空");
        }
    }
}

