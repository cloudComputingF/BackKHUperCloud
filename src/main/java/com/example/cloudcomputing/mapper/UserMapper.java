package com.example.cloudcomputing.mapper;


import com.example.cloudcomputing.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<UserVo> getUserList();
    UserVo getUserByEmail(String email);  //회원정보
}
