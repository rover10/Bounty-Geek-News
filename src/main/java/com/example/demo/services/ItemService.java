package com.example.demo.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import com.example.demo.models.Configuration;
import com.example.demo.models.Item;
import com.example.demo.models.ItemBrief;
import com.example.demo.models.Search;
import com.example.demo.util.DateUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
	Calendar calendar = Calendar.getInstance();
	Calendar cal = Calendar.getInstance();
	int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	int dayOfYear =  cal.get(Calendar.DAY_OF_YEAR);
	int year = cal.get(Calendar.YEAR);
*/

@Service
public class ItemService {
	
	private Map<String, List<Integer>> itemCategorization;
	private Map<Integer, List<ItemBrief>> allFilteredItems;
	private Configuration config;
	
	public ItemService(Map<String, List<Integer>> itemCategorization, Map<Integer,List<ItemBrief>> allFilteredItems, Configuration config) {
		this.itemCategorization = itemCategorization;
		this.allFilteredItems = allFilteredItems;
		this.config = config;
	}
	
	public ItemService() {
		this.itemCategorization = new ConcurrentHashMap<String, List<Integer>>();
		this.allFilteredItems = new ConcurrentHashMap<Integer,List<ItemBrief>>();
		this.config = new Configuration();
		buildIndex();
	}
	
	Callable<String> readAndIndexItem(String url) {
	    return () -> {
	    	URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "");

			int responseCode = con.getResponseCode();
			System.out.println("'Get ' " + url);
			System.out.println("Status: " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			in.close();
			Item item = ItemService.generateItem(response);
			//addItem(item);
			addToBriefItem(item);
			return response.toString();
	    };
	}
	
	private void addToBriefItem(Item item) {
		//System.out.println("adding to briefItem Item Type = ");
		//System.out.print(item.getType());
		
		//System.out.println("FILTERING ITEMS 0");
		if(item.getType() !=null  && (item.getType().equals(config.STORY) || item.getType().equals(config.COMMENT)|| item.getType().equals(config.JOB)) && item.getScore() > config.MIN_SCORE ) {
			//System.out.println("FILTERING ITEMS 1");
			ItemBrief itemBrief = new ItemBrief();
			itemBrief.setBy(item.getBy());
			itemBrief.setId(item.getId());
			itemBrief.setScore(item.getScore());
			itemBrief.setTime(item.getTime());
			itemBrief.setTitle(item.getTitle());
			itemBrief.setType(item.getType());
			itemBrief.setUrl(item.getUrl());
			
			/*Insertion logic in the list*/
			
			// Map Time -> days which act as --> index in the list
			// if index exit  -> add the briefItem to list @that index
			// else create a new list at that index and add the briefItem to the List
			
			
			/*Fetch Logic from the list */
			// getTime range(Start && end ) -> day
			// Iterate the list starting from start to end range
			
			
			
			int itemPostDay =(int) convertTimeToDay(item.getTime());
			if(this.allFilteredItems.get(itemPostDay) == null) {
				insertInFilteredItem(itemPostDay, this.allFilteredItems, itemBrief);
				// System.out.println("Adding => ");
				// System.out.print(item.getType());
				
			}else {
				//System.out.print(" FILTERING ITEMS BEFORE  " + this.allFilteredItems.get(itemPostDay).size());
				this.allFilteredItems.get(itemPostDay).add(itemBrief);
				
				// System.out.println("Adding ===> ");
				// System.out.print(item.getType());
				// System.out.print(" FILTERING ITEMS AFTER  " + this.allFilteredItems.get(itemPostDay).size());
				
				
				
			}
		}
			
		
	}
	
	private void insertInFilteredItem(int itemPostDay, Map<Integer,List<ItemBrief>> allFilteredItems, ItemBrief itemBrief) {
		List<ItemBrief> briefItemList = new ArrayList<ItemBrief>();
		briefItemList.add(itemBrief);
		allFilteredItems.put(itemPostDay, briefItemList);
	}
	
	private long convertTimeToDay(long time) {
		// EPOC Converter https://www.epochconverter.com/
		
		// Sample test https://hacker-news.firebaseio.com/v0/item/1734.json
		
		// {"by":"jwecker","descendants":0,"id":1734,"score":2,"time":1172767536,"title":
		// "Starting a Company is a Crap Sandwich","type":"story","url":
		// "http://www.gobignetwork.com/wil/2007/2/21/starting-a-company-is-a-crap-sandwich/10108/view.aspx"}
		
		// Search on hackernews
		// https://hn.algolia.com/?query=Starting%20a%20Company%20is%20a%20Crap%20Sandwich&sort=byPopularity&prefix&page=0&dateRange=all&type=story
		
		
		//baseTime = The timestamp of the first item created
		// first item -> https://hacker-news.firebaseio.com/v0/item/1.json
		// Make a call to first item and set baseTime
		// total seconds in one day SEC_IN_A_DAY= 3600 
		// day--> index--> (ITEM_EPOC - BASE_EPOC)/SEC_IN_A_DAY
		//long bucketIndex =  (time - BASE_EPOC)/SEC_IN_A_DAY;
		
		return (time - config.INDEX_BASE_EPOC)/DateUtil.SEC_IN_A_DAY;
		
	}

	private static Item generateItem(StringBuffer response) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Item item = mapper.readValue(response.toString(), Item.class);
		//System.out.println("Item created!");
		return item;
	}

	private void addItem(Item item) {
		
		if(item == null)
			return;
		
		List<Integer> type = this.itemCategorization.get(item.getType());
		if(type == null) {
			this.itemCategorization.put(item.getType(), new ArrayList<Integer>());
			this.itemCategorization.get(item.getType()).add(item.getId());
		}else {
			this.itemCategorization.get(item.getType()).add(item.getId());
		}
		
		//System.out.println("Adding item -> Id -> "+ item.getId() + " Title -> " + item.getTitle());
	}

	public Map<String, List<Integer>> getItemCategorization() {
		return itemCategorization;
	}

	public void setItemCategorization(Map<String, List<Integer>> itemCategorization) {
		this.itemCategorization = itemCategorization;
	}


	
	public void buildIndex() {
		if(this.config == null) {
			System.out.println("No configuration found. Please set Configuration before indexing.");
			return;
		}
		
		ExecutorService executor = Executors.newFixedThreadPool(config.NO_OF_WORKERS_THREAD);
		LongStream.range(config.FIRST_ITEM_ID, config.FIRST_ITEM_ID + config.NO_OF_ITEMS_TO_PROCESS)
	    		  .forEach(i -> { executor.submit(readAndIndexItem(config.ITEM_URI + i + config.FORMAT));
	    });
		
		try {
			executor.shutdown();
			executor.awaitTermination(500, TimeUnit.MINUTES);
			System.out.println(" Printing map ");
			System.out.println(" Printing map size = " + this.itemCategorization.size());
			printMap(this.itemCategorization);
			
			System.out.println(" Printing map ");
			System.out.println(" Printing filter map size = " + this.allFilteredItems);
			printFilteredItems();
		} catch (InterruptedException e) {
			System.out.println("Error occured while processing..");
			e.printStackTrace();
		}
		
	}
	
	void printMap(Map<String, List<Integer>> itemCategorization){
		//itemCategorization.forEach((k,v)->{System.out.println("Key = "+ k + " Value "+v);});
	} 
	
	public void printFilteredItems() {
		
		System.out.println("Printing all indexed items: Size = " + this.allFilteredItems.size()); 
		try {
		this.allFilteredItems.forEach((key, bItemList)->{
			bItemList.stream()
					 .forEach(bItem->{
						 System.out.println("Day = " + key + " Id: " + bItem.getId() + " Title: " + bItem.getTitle() + " type"); System.out.println(bItem.getType());
					 }
			);
		});
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void printItems(Map<Integer, List<ItemBrief>> items) {
	/*	items.forEach((key, bItemList)->{
			bItemList.stream().forEach(bItem->{
				System.out.println("Day = " + key + " Id: " + bItem.getId() + " Title: " + bItem.getTitle());}
			);
		});*/
	}
	
	
	public Map<Integer, List<ItemBrief>> getTopItems(long start, long end, String itemType){
		int dayRangeStart = (int) convertTimeToDay(start);
		int dayRangeEnd = (int) convertTimeToDay(end);
		Map<Integer, List<ItemBrief>> topItems = new HashMap<Integer, List<ItemBrief>>();
		//System.out.println("Finding Top Items: Start  " + dayRangeStart + " End: " + dayRangeEnd + " type = " );
		//System.out.println(itemType);
		
		IntStream.range(dayRangeStart, dayRangeEnd+1)
				 .forEach(day ->{
						if(this.allFilteredItems.get(day) != null && (itemType ==  null || itemType.equals(config.ALL))) {
							topItems.put(day, this.allFilteredItems.get(day));
						}else if(this.allFilteredItems.get(day) != null){
							List<ItemBrief> typeSpecificItems = this.allFilteredItems.get(day)
					    											.stream()
					    											.filter(e-> {return (e.getType()!=null && e.getType().equals(itemType));})
					    											.collect(Collectors.toCollection(ArrayList::new));
							if( !typeSpecificItems.isEmpty() )
								topItems.put(day, typeSpecificItems);
							
						}
		});
		return topItems;
	}
	
	public List<ItemBrief> getTopItems(Search searchParameter){
		System.out.println("getTopItems() --> startDate = " + searchParameter.getStartTime() + "endTime = " + searchParameter.getEndTime() + " type = " );
		System.out.print(searchParameter.getType());
		
		Map<Integer, List<ItemBrief>> result = getTopItems(searchParameter.getStartTime(), searchParameter.getEndTime(), searchParameter.getType());
		List<ItemBrief> sortedList = new ArrayList<ItemBrief>();
		result.forEach((k,v)->{
			sortedList.addAll(v);
		});
		
		Collections.sort(sortedList, (obj1, obj2)->{return obj2.getScore()-obj1.getScore();});
		List<ItemBrief> topItems  = sortedList.stream().limit(config.TOP).collect(Collectors.toCollection(ArrayList::new));
		return topItems;
		
		
	}
	
	public boolean save(int id, long time ) {
		return store(id, time,true);				
	}

	public boolean remove(int id, long time) {
		return store(id, time,false);
	}
	
	private boolean store(int id, long time, boolean storeOperation) {
		int day = (int) convertTimeToDay(time);
		//System.out.println("DAY === " + day);
		List<ItemBrief> topItemsOfDay = this.allFilteredItems.get(day);
		if(topItemsOfDay == null)
			return false;
		
		try {
			topItemsOfDay.stream().filter(e->e!=null && e.getId() ==  id).findFirst().get().setSaved(storeOperation);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	 	
	}
}
