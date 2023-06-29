package com.example.mils.demo.domain.milestone;

import java.util.List;
import java.sql.*;

import org.apache.ibatis.annotations.*;
// import org.springframework.data.jpa.repository.JpaRepository;

@Mapper
public interface MilestoneRepository {
    @Select("select m.ID,m.AUTHOR,m.group_id,g.name,m.TITLE,m.DESCRIPTION,m.STATUS,m.SCHEDULE_AT,m.DEADLINE_AT,m.CREATED_AT,m.UPDATED_AT from milestones as m join groups as g on m.group_id=g.id join group_user as gu on gu.group_id = m.group_id where gu.username = #{username} and m.title like #{title} and m.author like #{author} and m.status like #{status} order by m.${orderBy} ${order}")
    List<MilestoneEntity> search(String title, String author, String status, @Param("orderBy") String orderBy,
            @Param("order") String order, String username);

    // TODO: 一個しか取らないからjoinじゃなくてsub queryのほうがいいかも
    @Select("select m.ID,m.AUTHOR,m.group_id,g.name,m.TITLE,m.DESCRIPTION,m.STATUS,m.SCHEDULE_AT,m.DEADLINE_AT,m.CREATED_AT,m.UPDATED_AT from milestones as m join groups as g where m.group_id=g.id and m.id=#{id}")
    MilestoneEntity getById(long id);

    @Select("select title from milestones where id=#{id}")
    String getTitleById(long id);

    @Update("update milestones set title=#{title}, description=#{description}, status=#{status}, schedule_at=#{scheduleAt}, deadline_at=#{deadlineAt} where id=#{id}")
    void update(long id, String title, String description, String status, Date scheduleAt, Date deadlineAt);

    @Delete("delete from milestones where id=#{id}")
    void deleteById(long id);

    // @Insert("insert into milestones (title,description) values
    // (#{title},#{description})")
    // void insert(String title, String description);
    // FIXME : group_idが固定値
    @Insert("insert into milestones (author, title, description, status, schedule_at, deadline_at,group_id) values (#{author},#{title},#{description},#{status},#{scheduleAt},#{deadlineAt},#{groupId})")
    // @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(String author, String title, String description, String status, Date scheduleAt, Date deadlineAt,
            long groupId);

    @Insert("insert into milestones (author, title, description, status, schedule_at, deadline_at,group_id) values (#{author},#{title},#{description},#{status},#{scheduleAt},#{deadlineAt},#{group_id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertByEntity(MilestoneEntity milestoneEntity);
}
