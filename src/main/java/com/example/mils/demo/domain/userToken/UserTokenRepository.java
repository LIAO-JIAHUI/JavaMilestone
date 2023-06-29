package com.example.mils.demo.domain.userToken;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserTokenRepository {
    @Select("SELECT * FROM user_tokens WHERE username != #{username}")
    List<UserTokenEntity> whereNotAll(String username);

    @Insert("INSERT INTO user_tokens (username, token) VALUES (#{username}, #{token})")
    void insert(String username, String token);
}
