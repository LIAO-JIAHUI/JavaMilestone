package com.example.mils.demo.web.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.mils.demo.domain.user.UserEntity;
import com.example.mils.demo.domain.user.UserGroupEntity;

@Mapper
public interface UserRepository {
    @Select("select * from users where username=#{userName}")
    UserEntity find(String userName);

    @Update("update users set is_dark=#{isDark} where username=#{userName}")
    void changeDarkMode(String userName, Boolean isDark);

    @Update("update users set icon=#{icon}, display_name=#{displayName} where username=#{userName}")
    void update(String userName, String icon, String displayName);

    @Select("select groups.id,groups.name,group_user.role from group_user join groups on groups.id = group_user.group_id where username = #{userName}")
    List<UserGroupEntity> getGroupListByUserName(String userName);

    @Select("select username from group_user where group_id = #{id}")
    List<String> getUserListByGroup(long id);
}
