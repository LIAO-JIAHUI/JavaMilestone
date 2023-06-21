package com.example.mils.demo.domain.milestone;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MilestoneRepository {
    @Select("select * from milestones")
    List<MilestoneEntity> findAll();

    @Select("select * from milestones where id=#{id}")
    MilestoneEntity getById(long id);

    @Insert("insert into milestones (title,description) values (#{title},#{description})")
    void insert(String title, String description);
}
