package com.example.demo.models;

import org.springframework.stereotype.Service;

@Service
public class Configuration {
	
	public final String ITEM_URI;
	public final String FORMAT;
	public final int NO_OF_WORKERS_THREAD;
	public final long NO_OF_ITEMS_TO_PROCESS;
	public final long FIRST_ITEM_ID; 
	public final String STORY;
	public final String COMMENT;
	public final String JOB;
	public final String ALL;
	public final int MIN_SCORE;
	public final int TOP;
	public final long INDEX_BASE_EPOC;

	// Default configuration
	public Configuration(){
		MIN_SCORE = 250;
		ALL = "all";
		JOB = "job";
		COMMENT = "comment";
		STORY = "story";
		FIRST_ITEM_ID = 16016181L - 100000L;
		NO_OF_ITEMS_TO_PROCESS = 100000L; // Total itmes as off now(12/27/2017) from https://hacker-news.firebaseio.com/v0/maxitem.json
		NO_OF_WORKERS_THREAD = 400;
		FORMAT = ".json";
		ITEM_URI = "https://hacker-news.firebaseio.com/v0/item/";
		TOP = 50;
		/*
		 * First Item-> https://hacker-news.firebaseio.com/v0/item/1.json <- Give us base EPOC {"by":"pg",
		 * "descendants":15,"id":1,"kids":[487171,15,234509,454410,82729],
		 	"score":61,"time":1160418111,"title":"Y Combinator","type":"story","url":"http://ycombinator.com"}
		*/
		INDEX_BASE_EPOC = 1160418111;
		
	}
	
	public Configuration(
			String ITEM_URI, 
			String FORMAT, 
			int NO_OF_WORKERS_THREAD, 
			long NO_OF_ITEMS_TO_PROCESS, 
			long FIRST_ITEM_ID, 
			String STORY, 
			String COMMENT,
			String JOB,
			String ALL, 
			int MIN_SCORE,
			int TOP,
			long INDEX_BASE_EPOC){
		
		
			this.MIN_SCORE = MIN_SCORE;
			this.ALL = ALL;
			this.JOB = JOB;
			this.COMMENT = COMMENT;
			this.STORY = STORY;
			this.FIRST_ITEM_ID = FIRST_ITEM_ID;
			this.NO_OF_ITEMS_TO_PROCESS = NO_OF_ITEMS_TO_PROCESS;
			this.NO_OF_WORKERS_THREAD = NO_OF_WORKERS_THREAD;
			this.FORMAT = FORMAT;
			this.ITEM_URI = ITEM_URI;
			this.TOP = TOP;
			this.INDEX_BASE_EPOC = INDEX_BASE_EPOC;
	}
		
}
