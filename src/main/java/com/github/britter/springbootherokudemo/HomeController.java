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

import javax.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

    private RecordRepository repository;
    private String clientId = "1955772338033020";
    private String redirectUri = "https://fb-login-flow.herokuapp.com/redirect";
    private String fbUrl = "https://www.facebook.com/v2.10/dialog/oauth?client_id=%s&redirect_uri=%s";

    @Autowired
    public HomeController(RecordRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(ModelMap model) {

        return "home";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView insertData(ModelMap model, @ModelAttribute("insertRecord") @Valid Record record, BindingResult result) {
        String redirectUrl = String.format(fbUrl, clientId, redirectUri);
        return new ModelAndView("redirect:" + fbUrl);
    }

    @RequestMapping(path = "/redirect", method = RequestMethod.POST)
    public ModelAndView redirect(ModelMap model, @ModelAttribute("insertRecord") @Valid Record record, BindingResult result) {

        return new ModelAndView();
    }
}
