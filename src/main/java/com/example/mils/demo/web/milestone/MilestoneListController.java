package com.example.mils.demo.web.milestone;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mils.demo.domain.milestone.MilestoneEntity;
import com.example.mils.demo.domain.milestone.MilestoneRepository;
import com.example.mils.demo.domain.milestone.MilestoneService;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneListController {
    @Autowired
    private MilestoneService milestoneService;

    @GetMapping("") // ("/{status}") @PathVariable("status") String status
    public Map<String, String> getCompletionRate() {
        Map<String, String> rates = new HashMap<>();
        rates.put("todo", milestoneService.getCompletionRate("todo") * 100 + " %");
        rates.put("in-progress", milestoneService.getCompletionRate("in-progress") * 100 + " %");
        rates.put("done", milestoneService.getCompletionRate("done") * 100 + " %");
        return rates;
    }
}
