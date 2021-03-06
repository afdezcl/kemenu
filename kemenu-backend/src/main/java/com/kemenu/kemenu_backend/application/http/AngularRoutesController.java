package com.kemenu.kemenu_backend.application.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AngularRoutesController {
    @RequestMapping(
            method = {RequestMethod.OPTIONS, RequestMethod.GET},
            path = {
                    "/register/**",
                    "/menu/**",
                    "/show",
                    "/demo",
                    "/forgotPassword",
                    "/changePassword",
                    "/aboutUs",
                    "/profile/**",
                    "/contact"
            })
    public String forwardAngularPaths() {
        return "forward:/index.html";
    }
}
