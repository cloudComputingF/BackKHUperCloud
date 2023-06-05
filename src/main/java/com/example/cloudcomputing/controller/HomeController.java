package com.example.cloudcomputing.controller;


import com.example.cloudcomputing.service.UserService;
import com.example.cloudcomputing.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Long id = (Long) session.getAttribute("userId");
        if (id != null) { // 로그인된 상태
            UserVo userVo = userService.getUserById(id);
            model.addAttribute("user", userVo);
            return "home";
        }
        return "redirect:/login";
    }
}