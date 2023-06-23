package com.example.mils.demo.web.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserRepository {
    @Select("select is_dark from users where username=#{userName}")
    boolean getDarkMode(String userName);

    @Update("update users set is_dark=#{isDark} where username=#{userName}")
    void changeDarkMode(String userName, Boolean isDark);
}
