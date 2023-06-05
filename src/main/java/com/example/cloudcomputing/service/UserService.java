package com.example.cloudcomputing.service;

import com.example.cloudcomputing.mapper.UserMapper;
import com.example.cloudcomputing.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<UserVo> getUserList(){
        return userMapper.getUserList();
    }

    public Long login(String email, String password){
        UserVo userVo = userMapper.getUserByEmail(email);
        if(UserVo.getPassword().equals(password))
            return userVo.getId();
        return null;
    }
}
