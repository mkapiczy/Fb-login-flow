/*
 * Copyright 2015 Benedikt Ritter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.britter.springbootherokudemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;

@Controller
@RequestMapping("/")
public class FacebookController {

    private RecordRepository repository;
    private String clientId = "1955772338033020";
    private String appSecret = "3e5e6b3fc275af2d4303391ab7a03e58";
    private String firstRedirectUri = "https://fb-login-flow.herokuapp.com/firstRedirect";
    private String secondRedirectUri = "https://fb-login-flow.herokuapp.com/secondRedirect";
    private String fbUrl = "https://www.facebook.com/v2.10/dialog/oauth?client_id=%s&redirect_uri=%s";

    @Autowired
    public FacebookController(RecordRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(HttpServletRequest request, HttpServletResponse response) {
        return "home";
    }

    @RequestMapping(path = "/facebook/login", method = RequestMethod.POST)
    public ModelAndView insertData(HttpServletRequest request, HttpServletResponse response) {
        String redirectUrl = String.format(fbUrl, clientId, firstRedirectUri);
        System.out.println(redirectUrl);
        return new ModelAndView("redirect:" + redirectUrl);
    }

    @RequestMapping(path = "facebook/firstRedirect", method = RequestMethod.GET)
    public ModelAndView firstRedirect(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String redirect = "https://graph.facebook.com/oauth/access_token?client_id="
                + clientId + "&redirect_uri=" + secondRedirectUri
                + "&client_secret=" + appSecret
                + "&code=" + code;
        return new ModelAndView("redirect:" + redirect);

    }

    @RequestMapping(path = "facebook/secondRedirect", method = RequestMethod.GET)
    public String secondRedirect(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = request.getParameter("accessToken");
        System.out.println(accessToken);
        return "home";

    }
}
