package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Save;
import com.example.demo.services.ItemService;

@RestController
public class StorageController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageController.class);
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping(path = "/save/",produces=MediaType.APPLICATION_JSON_VALUE)
	public boolean save(@ModelAttribute Save saveItemInfo) {
		System.out.println(" Id "+ saveItemInfo.getId());
		System.out.println(" Time " + saveItemInfo.getTime());
		
		return itemService.save(saveItemInfo.getId(), saveItemInfo.getTime());
	}
	

	@RequestMapping(path = "/remove/",produces=MediaType.APPLICATION_JSON_VALUE)
	public boolean remove(@ModelAttribute Save saveItemInfo) {
		System.out.println(" Id "+ saveItemInfo.getId());
		System.out.println(" Time " + saveItemInfo.getTime());
		
		return itemService.remove(saveItemInfo.getId(), saveItemInfo.getTime());
	}
	
}
