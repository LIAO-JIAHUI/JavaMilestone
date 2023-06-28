package com.example.mils.demo.domain.milestone;

import java.util.List;
import java.sql.*;

import org.apache.ibatis.annotations.*;
// import org.springframework.data.jpa.repository.JpaRepository;

@Mapper
public interface MilestoneRepository {
    @Select("select * from milestones where title like #{title} and author like #{author} and status like #{status} order by ${orderBy} ${order}")
    List<MilestoneEntity> search(String title, String author, String status, @Param("orderBy") String orderBy,
            @Param("order") String order);

    @Select("select * from milestones where id=#{id}")
    MilestoneEntity getById(long id);

    @Select("select title from milestones where id=#{id}")
    String getTitleById(long id);

    @Select("select name from groups where id=#{id}")
    String getGroupNameByGroupId(long id);

    @Update("update milestones set title=#{title}, description=#{description}, status=#{status}, schedule_at=#{scheduleAt}, deadline_at=#{deadlineAt} where id=#{id}")
    void update(long id, String title, String description, String status, Date scheduleAt, Date deadlineAt);

    @Delete("delete from milestones where id=#{id}")
    void deleteById(long id);

    // @Insert("insert into milestones (title,description) values
    // (#{title},#{description})")
    // void insert(String title, String description);
    // FIXME : group_idが固定値
    @Insert("insert into milestones (author, title, description, status, schedule_at, deadline_at,group_id) values (#{author},#{title},#{description},#{status},#{scheduleAt},#{deadlineAt},1)")
    // @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(String author, String title, String description, String status, Date scheduleAt, Date deadlineAt);
}
