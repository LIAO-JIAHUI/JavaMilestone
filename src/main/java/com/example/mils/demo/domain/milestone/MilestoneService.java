package com.example.mils.demo.domain.milestone;

import java.util.List;
import java.util.Arrays;
import java.sql.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;

    public List<MilestoneEntity> search(String title, String author, String status, String orderBy, String order,
            String username) {
        List<String> expectedOrderBy = Arrays.asList("author", "title", "status", "schedule_at", "deadline_at");
        List<String> expectedOrder = Arrays.asList("asc", "desc");
        title = getLikeStatement(title);
        author = getLikeStatement(author);
        status = getLikeStatement(status);
        if (orderBy == null || !expectedOrderBy.contains(orderBy))
            orderBy = "id";
        if (order == null || !expectedOrder.contains(order))
            order = "asc";
        return milestoneRepository.search(title, author, status, orderBy, order, username);
    }

    public double getCompletionRate(String status) {
        List<MilestoneEntity> milestoneList = this.search(null, null, null, null, null, null);

        double statusCount = milestoneList.stream().filter(i -> i.getStatus().equals(status)).count();
        double completionRate = (double) (statusCount / milestoneList.size());
        return completionRate;
    }

    // @Transactional
    // public void create(String author, String title, String description, String
    // status, Date scheduleAt,
    // Date deadlineAt) {
    // milestoneRepository.insert(author, title, description, status, scheduleAt,
    // deadlineAt);
    // }

    @Transactional
    public MilestoneEntity createByEntity(MilestoneEntity milestoneEntity) {
        milestoneRepository.insertByEntity(milestoneEntity);
        return milestoneEntity;
    }

    public void update(long id, String title, String description, String status, Date scheduleAt, Date deadlineAt) {
        milestoneRepository.update(id, title, description, status, scheduleAt, deadlineAt);
    }

    public MilestoneEntity getById(long id) {
        return milestoneRepository.getById(id);
    }

    public String getTitleById(long id) {
        return milestoneRepository.getTitleById(id);
    }

    public void deleteById(long id) {
        milestoneRepository.deleteById(id);
    }

    private String getLikeStatement(String value) {
        return value == null ? "%%" : "%" + value + "%";
    }

}
