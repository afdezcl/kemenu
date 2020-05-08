package com.kemenu.kemenu_backend.application.http;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Redirect to index.html after a refresh on a non-root page.
 *
 * https://stackoverflow.com/questions/48740997/whitelabel-error-page-404-spring-boot-angular-5
 * https://stackoverflow.com/questions/44692781/configure-spring-boot-to-redirect-404-to-a-single-page-app
 * https://stackoverflow.com/questions/43913753/spring-boot-with-redirecting-with-single-page-angular2
 */
@Controller
public class RedirectToIndexController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        return "forward:/index.html";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
