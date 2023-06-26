package com.example.mils.demo.web.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.mils.demo.domain.user.UserEntity;

@Mapper
public interface UserRepository {
    @Select("select * from users where username=#{userName}")
    UserEntity find(String userName);

    @Update("update users set is_dark=#{isDark} where username=#{userName}")
    void changeDarkMode(String userName, Boolean isDark);

    @Update("update users set icon=#{icon}, display_name=#{displayName} where username=#{userName}")
    void update(String userName, String icon, String displayName);
}
