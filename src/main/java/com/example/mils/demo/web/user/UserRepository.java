package com.example.mils.demo.web.user;

import java.util.List;

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

    @Select("select name from groups where id in (select group_id from group_user where username=#{userName})")
    List<String> getGroupListByUserName(String userName);

    @Select("select username from group_user where group_id = (select id from groups where name=#{group})")
    List<String> getUserListByGroup(String group);
}
