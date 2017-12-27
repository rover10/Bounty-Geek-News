package com.example.demo;


import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.models.Configuration;
import com.example.demo.models.ItemBrief;
import com.example.demo.services.ItemService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println("------------>");
		System.out.println("------------>");
		System.out.println("------------>");
		System.out.println("Load test .. construct the news in memory.");
		//SpringApplication.run(DemoApplication.class, new String[5]);
		Configuration config = new Configuration();
		
		ItemService ic = new ItemService(new ConcurrentHashMap<String, List<Integer>>(), new ConcurrentHashMap<Integer,List<ItemBrief>>(), config);
		ic.buildIndex();
		ic.getItemCategorization();

		System.out.println("Map size = "+ic.getItemCategorization().size());
		System.out.println("--------------------------------------------------");
		ic.printFilteredItems();
		System.out.println("--------------------------------------------------");
		
		//ic.getTopItems(1160418111, 1172164181, "story");
		
		System.out.println("------------------Top Items-----------------------");
		ic.printItems(ic.getTopItems(1200607720, 1201119120, "story"));
		
	}
	
	@Test
	public void indexTest() {
		/*
		String ITEM_URI, 
			String FORMAT, 
			int NO_OF_WORKERS_THREAD, 
			long NO_OF_ITEMS_TO_PROCESS, 
			long FIRST_ITEM_ID, 
			String STORY, 
			String COMMENT,
			String JOB,
			String ALL, 
			int SEC_IN_A_DAY, 
			int TOP,
			long INDEX_BASE_EPOC){
		
		*/
		
		Configuration c = new Configuration();
		Configuration con = new Configuration(c.ITEM_URI,c.FORMAT,50,500L,1L,c.STORY,c.COMMENT, c.JOB, c.ALL,10,50,c.INDEX_BASE_EPOC);
		ItemService ic = new ItemService(new ConcurrentHashMap<String, List<Integer>>(), new ConcurrentHashMap<Integer,List<ItemBrief>>(), con);
		ic.buildIndex();
	}

}
