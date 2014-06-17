package com.misino.controller;

import com.misino.data.domain.User;
import com.misino.service.UserService;
import com.misino.utils.exceptions.UniqueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractBaseController
{
    @Autowired
    UserService userService;

    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String addUserPage(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute( "user", new User() );
        redirectAttributes.getFlashAttributes();
        return "user/userAdd";
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("user") User user, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors())
            return new ModelAndView("user/userAdd");

        try {
            userService.create( user );
            redirectAttributes.addFlashAttribute( "success", "funguje to" );
            ModelAndView mv = new ModelAndView(  );
            mv.setViewName( "redirect:/user/add" );
            return mv;
        } catch(UniqueException e) {
            result.addError( new ObjectError( "email", "User with this email address already exist" ) );
        }
        return new ModelAndView("user/userAdd");
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.POST)
    public ModelAndView addUser(@PathVariable Long id, BindingResult result, RedirectAttributes redirectAttributes) {
        User user = userService.findById( id );
        if(user!=null) {
            userService.delete( id );
        }

        //redirectAttributes.addFlashAttribute( "message", "funguje to" );

        ModelAndView mv = new ModelAndView(  );
        mv.setViewName( "redirect:/user/userList" );
        return mv;
    }

    @RequestMapping(value="/list")
    public String listUser(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute( "users", users );
        return "user/userList";
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus( value = HttpStatus.NOT_FOUND )
    public String handleException(Exception e) {
        return "sukaaaaaaaa";
    }


    @RequestMapping(value="/profile", method = RequestMethod.GET)
    public String showProfile(Model model, RedirectAttributes redirectAttributes) {
        Object user = getUser();
        //model.addAttribute( "user", );
        redirectAttributes.getFlashAttributes();
        return "user/profile";
    }

}
