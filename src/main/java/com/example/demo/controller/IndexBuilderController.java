package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.ItemService;

@RestController
public class IndexBuilderController {
	@Autowired
	ItemService itemService;
	
	@RequestMapping(path="build/start")
	void startIndexBuilder() {
		if(!itemService.isBuilding()) {
			itemService.setBuilding(true);
			new Thread(()-> {itemService.buildIndex();}).start();
		}
	}
	
	@RequestMapping("build/reset")
	void resetIndexBuilder() {
		// Interrupt any thread building the index;
		// Clear the index
		
		itemService.setBuilding(true);
		new Thread(()-> {itemService.buildIndex();}).start();
		
		
		
	}
	
	
	
}
