package com.example.mils.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mils.demo.domain.milestone.MilestoneEntity;
import com.example.mils.demo.domain.milestone.MilestoneService;

@SpringBootTest
class MilestoneServiceTest {
	@Autowired
	private MilestoneService milestoneService;

	@Test
	void contextLoads() {
	}

	@Test
	void test_all() {
		List<MilestoneEntity> milestoneList = milestoneService.findAll();
		assertEquals(3, milestoneList.size());
	}

    
}
