package com.example.mils.demo.domain.milestone;

import java.util.List;
import java.sql.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MilestoneRepository {
    @Select("select * from milestones")
    List<MilestoneEntity> findAll();

    @Select("select * from milestones where id=#{id}")
    MilestoneEntity getById(long id);

    // @Insert("insert into milestones (title,description) values
    // (#{title},#{description})")
    // void insert(String title, String description);
    @Insert("insert into milestones (title, description, status) values (#{title},#{description},#{status})")
    void insert(String title, String description, String status);
}
