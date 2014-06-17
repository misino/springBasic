package com.misino.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorHandlerController extends AbstractBaseController
{
    @RequestMapping("/WEB-INF/views/error/notFound.html")
    public String error(HttpServletRequest request, Model model) {
        return "error/notFound";
    }

    @RequestMapping("/WEB-INF/views/error/general.html")
    public String errorGeneral(HttpServletRequest request, Model model) {
        model.addAttribute("errorMessage", request.getAttribute("javax.servlet.error.message"));
        return "error/general";
    }

}
