package com.example.demo.controller;

import java.util.Calendar;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ItemBrief;
import com.example.demo.models.Search;
import com.example.demo.models.SearchParameter;
import com.example.demo.services.ItemService;
import com.example.demo.util.DateUtil;

@RestController
public class SearchController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping(path = "/search/",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ItemBrief> search(@ModelAttribute SearchParameter searchParameters) {
		System.out.println(" Start "+ searchParameters.getStartDate());
		System.out.println(" End " + searchParameters.getEndDate());
		
		long startDayToSec = DateUtil.convertDateToSec(searchParameters.getStartDate());
		long endDayToSec = DateUtil.convertDateToSec(searchParameters.getEndDate());
		Search search = createSearchObject(startDayToSec, endDayToSec, searchParameters);
		return itemService.getTopItems(search);
	}
	
	@RequestMapping(path = "/search/today",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ItemBrief> searchToday(@ModelAttribute SearchParameter searchParameters) {
		System.out.println("Search in today's index");
		
		long startDayToSec = (System.currentTimeMillis()) / DateUtil.MIL_TO_SEC;
		System.out.println("Converted " +startDayToSec);
		Search search = createSearchObject(startDayToSec, startDayToSec, searchParameters);
		return itemService.getTopItems(search);
	}
	
	@RequestMapping(path = "/search/week",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ItemBrief> searchThisWeek(@ModelAttribute SearchParameter searchParameters) {
		System.out.println("Search in this week index");
		
		Calendar cal = Calendar.getInstance();
		long endDayToSec = ((System.currentTimeMillis()) / DateUtil.MIL_TO_SEC);
		long startDayToSec = endDayToSec - (cal.get(Calendar.DAY_OF_WEEK) * DateUtil.SEC_IN_A_DAY);
		
		Search search = createSearchObject(startDayToSec, endDayToSec, searchParameters);
		return itemService.getTopItems(search);
	}
	
	@RequestMapping(path = "/search/month",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ItemBrief> searchThisMonth(@ModelAttribute SearchParameter searchParameters) {
		System.out.println("Search in this month index");
		
		Calendar cal = Calendar.getInstance();
		long endDayToSec = ((System.currentTimeMillis()) / DateUtil.MIL_TO_SEC);
		long startDayToSec = endDayToSec - (cal.get(Calendar.DAY_OF_MONTH) * DateUtil.SEC_IN_A_DAY);
		
		Search search = createSearchObject(startDayToSec, endDayToSec, searchParameters);
		return itemService.getTopItems(search);
	}
	
	@RequestMapping(path = "/search/year",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ItemBrief> searchThisYear(@ModelAttribute SearchParameter searchParameters) {
		System.out.println("Search in this year index");
		
		Calendar cal = Calendar.getInstance();
		long endDayToSec = ((System.currentTimeMillis()) / DateUtil.MIL_TO_SEC);
		long startDayToSec = endDayToSec - (cal.get(Calendar.DAY_OF_YEAR) * DateUtil.SEC_IN_A_DAY);
		
		Search search = createSearchObject(startDayToSec, endDayToSec, searchParameters);
		return itemService.getTopItems(search);
	}
	
	@RequestMapping(path = "/search/last_year",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ItemBrief> searchLastYear(@ModelAttribute SearchParameter searchParameters) {
		System.out.println("Search in last year index");
		
		Calendar cal = Calendar.getInstance();
		long endDayToSec = ((System.currentTimeMillis()) / DateUtil.MIL_TO_SEC) - (cal.get(Calendar.DAY_OF_YEAR) * DateUtil.SEC_IN_A_DAY);
		//long startDayToSec = DateUtil.INDEX_BASE_EPOC/DateUtil.SEC_TO_MILLI;
		cal.add(Calendar.YEAR, -1);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		long startDayToSec  = cal.getTimeInMillis()/DateUtil.SEC_TO_MILLI;
		
		Search search = createSearchObject(startDayToSec, endDayToSec, searchParameters);
		return itemService.getTopItems(search);
	}
	
	
	
	private static Search createSearchObject(long startDayToSec, long endDayToSec, SearchParameter searchParameters) {
		Search search = new Search();
		search.setStartTime(startDayToSec);
		search.setEndTime(endDayToSec);
		search.setType(searchParameters.getType());
		search.setUser(searchParameters.getUser());
		search.setContains(searchParameters.getContains());
		return search;
	}
	
}
