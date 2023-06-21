package com.example.mils.demo.domain.milestone;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;

    public List<MilestoneEntity> findAll() {
        return milestoneRepository.findAll();
    }

    @Transactional
    public void create(String title, String description) {
        milestoneRepository.insert(title, description, "todo");
    }

    public MilestoneEntity getById(long id) {
        return milestoneRepository.getById(id);
    }
}
