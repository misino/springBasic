package com.misino.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main/index page
 */
@Controller
@RequestMapping("/")
public class SiteController extends AbstractBaseController
{
    static final Logger log = LoggerFactory.getLogger(SiteController.class);
    @RequestMapping("")
    public String start(Model model) {
        return "site/site";
    }

    @RequestMapping("/admin")
    public String admin(Model model) {
        return "site/site";
    }
}
