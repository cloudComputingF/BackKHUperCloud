package com.example.cloudcomputing.controller;

import com.example.cloudcomputing.service.UserService;
import com.example.cloudcomputing.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

    @GetMapping("/signup")
    public String toSignupPage() {  //회원가입 페이지
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(UserVo userVo) { // 회원가입
        try {
            userService.signup(userVo);
        } catch (DuplicateKeyException e) {
            return "redirect:/signup?error_code=-1";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/signup?error_code=-99";
        }
        return "redirect:/login";
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
        Long id = Long.valueOf(userService.login(email, password));
        if (id == null) { // 로그인 실패
            return "redirect:/login";
        }
        session.setAttribute("userId", id);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) { // 로그아웃
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/update")
    public String toUpdatePage(HttpSession session, Model model) { // 회원 정보 수정 페이지
        Long id = (Long) session.getAttribute("userId");
        UserVo userVo = userService.getUserById(id);
        model.addAttribute("user", userVo);
        return "update";
    }

    @PostMapping("/update")
    public String modifyInfo(HttpSession session, UserVo userVo) { // 회원 정보 수정
        Long id = (Long) session.getAttribute("userId");
        userVo.setId(Math.toIntExact(id));
        userService.modifyInfo(userVo);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String withdraw(HttpSession session) { // 회원 탈퇴
        Long id = (Long) session.getAttribute("userId");
        if (id != null) {
            userService.withdraw(id);
        }
        session.invalidate();
        return "redirect:/";
    }

}