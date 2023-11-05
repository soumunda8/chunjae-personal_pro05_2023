package com.chunjae.project05.domain;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class UserFailLogin extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorType = "";
        String errorMessage = "";

        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException || exception instanceof UsernameNotFoundException){

            errorType = "error01";
            errorMessage="아이디 또는 비밀번호가 맞지 않습니다.";

        } else {

            errorType = "error02";
            errorMessage="알 수 없는 이유로 로그인이 안되고 있습니다.";

        }

        //errorMessage= URLEncoder.encode(errorMessage,"UTF-8");
        setDefaultFailureUrl("/user/login.do?error=true&exception=" + errorType);
        super.onAuthenticationFailure(request, response, exception);

    }

}