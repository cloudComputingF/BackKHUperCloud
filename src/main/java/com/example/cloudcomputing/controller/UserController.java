package com.example.cloudcomputing.controller;

import com.example.cloudcomputing.service.UserService;
import com.example.cloudcomputing.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userList")
    public String getUserList(Model model) {
        List<UserVo> userList = userService.getUserList();
        model.addAttribute("list", userList);
        return "userList";
    }
    @GetMapping("/login")
    public String toLoginPage(HttpSession session) { // 로그인 페이지
        Long id = (Long) session.getAttribute("userId");
        if (id != null) { // 로그인된 상태
            return "redirect:/";
        }
        return "login"; // 로그인되지 않은 상태
    }

    @PostMapping("/login")
    public String login(String email, String password, HttpSession session) { // 로그인
        Long id = userService.login(email, password);
        if (id == null) { // 로그인 실패
            return "redirect:/login";
        }
        session.setAttribute("userId", id);
        return "redirect:/";
    }

}