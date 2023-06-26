package com.example.mils.demo.domain.milestone;

import java.util.List;
import java.sql.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;

    public List<MilestoneEntity> findAll(String orderBy, String order) {
        if (orderBy == null)
            orderBy = "id";
        if (order == null)
            order = "asc";
        return milestoneRepository.findAll(orderBy, order);
    }

    @Transactional
    public void create(String author, String title, String description, String status, Date scheduleAt,
            Date deadlineAt) {
        milestoneRepository.insert(author, title, description, status, scheduleAt, deadlineAt);
    }

    public void update(long id, String title, String description, String status, Date scheduleAt, Date deadlineAt) {
        milestoneRepository.update(id, title, description, status, scheduleAt, deadlineAt);
    }

    public MilestoneEntity getById(long id) {
        return milestoneRepository.getById(id);
    }

    public void deleteById(long id) {
        milestoneRepository.deleteById(id);
    }

}
