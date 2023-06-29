package com.example.mils.demo.domain.user;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserGroupRepository {
    @Insert("INSERT INTO group_user (username, group_id,role) VALUES (#{username}, #{GroupId},'editor')")
    void insert(String username, long GroupId);
}
