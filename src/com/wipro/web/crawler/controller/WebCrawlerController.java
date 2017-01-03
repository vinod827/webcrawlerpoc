/***************************************************************************************************
FILE		:	WebCrawlerController
Author		:	Vinod Kumar Nair (WIPRO)
Purpose		:	This class will be used to controller to population of home screen and also to show the crawling result to end user
Date Created:	03-01-2017
****************************************************************************************************/
/**
 * This class will be used to controller to population of home screen and also to show the crawling result to end user
 *
 * @history <<DD>> <<MON>> <<YYYY>> << DEVELOPER >> - << CHANGES DONE >>.
 */
package com.wipro.web.crawler.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.wipro.web.crawler.db.MongoDBConnection;
import com.wipro.web.crawler.form.WiproWebCrawlerForm;



/**
 * @author vinod827
 *
 */
@Controller
public class WebCrawlerController {
	private static Logger logger = Logger.getLogger(WebCrawlerController.class);
	private static final String SEARCH_CONDITION = "wiprodigital.com";
   
	@RequestMapping("/")
	public String loadHomePage(ModelMap model){
		System.out.println("Entering loadHomePage@WebCrawlerController");
		logger.info("Entering loadHomePage@WebCrawlerController");
		model.addAttribute("message", "Wipro Web Crawler");
		
		WiproWebCrawlerForm crawlerForm = new WiproWebCrawlerForm();
		model.addAttribute("wiproWebCrawlerForm", crawlerForm); //form binding
		
		logger.info("Exiting loadHomePage@WebCrawlerController");
		System.out.println("Exiting loadHomePage@WebCrawlerController");
		return "index";		
	}

	@RequestMapping("/submit")
	public String getWebCrawlerList(@ModelAttribute("wiproWebCrawlerForm") WiproWebCrawlerForm crawlerForm, ModelMap model){
		System.out.println("Entering getWebCrawlerList@WebCrawlerController");
		logger.info("Entering getWebCrawlerList@WebCrawlerController");
		
		String inputURL = crawlerForm.getUrl(); //get the URL input from screen
		System.out.println("URL entered by user:"+inputURL);
		List<String> result = null;
		
		//Do the search only if the given Use Case statisfied, i.e. wiprodigital.com (irrespective if it is upper or lower case)
		if (null != inputURL && inputURL.equalsIgnoreCase(SEARCH_CONDITION)) { 			                                                                    
			MongoClient mongoClient = null;
			MongoDatabase mongoDatabase = null;
			MongoCollection<Document> mongoCollection = null;
			
			try {
				mongoClient = MongoDBConnection.getDBConnection(); // get MongoDB connection
				mongoDatabase = mongoClient.getDatabase("wiprowebcrawler"); // database name
				System.out.println("database:" + mongoDatabase);
				mongoCollection = mongoDatabase.getCollection("webcrawler"); // table name
				result = getResult(inputURL, mongoDatabase, mongoCollection);
			} catch (Exception e) {
				logger.error("Exception caught, " + e.getLocalizedMessage());
			} finally {
				if (null != mongoClient) {
					MongoDBConnection.closeDBConnection(mongoClient);
					logger.info("MongoDB connection closed");
				}
			}
		}
		
		model.addAttribute("message", "Wipro Web Crawler");  //showing display message
		model.addAttribute("searchedTitle", "Web Crawler Result"); //showing display message
		model.addAttribute("colorCd", "FF8333"); //show message as no result found
		if(null!=result && !result.isEmpty()){
			model.addAttribute("urls", result); //showing searched result
		}else{
			model.addAttribute("urls", "No Result Found!"); //show message as no result found
		}
		
		logger.info("Exiting getWebCrawlerList@WebCrawlerController");
		System.out.println("Exiting getWebCrawlerList@WebCrawlerController");
		return "index";		
	}
	
	
	/**
	 * 
	 * @param inputURL
	 * @param mongoDatabase
	 * @param mongoCollection
	 * @return
	 */
	private List<String> getResult(String inputURL, MongoDatabase mongoDatabase, MongoCollection<Document> mongoCollection){
		BasicDBObject query = new BasicDBObject();
		//query.put("url",  java.util.regex.Pattern.compile(inputURL)); //TODO to fix case insenstive issue
		query.put("url",  Pattern.compile(inputURL , Pattern.CASE_INSENSITIVE));
		FindIterable<Document> findIterable =  mongoCollection.find(query);
		List<String> result = null;
		if (null != findIterable) {
			result = new ArrayList<String>();
			for (Document obj : findIterable) {
				System.out.println("Obj:"+obj);
				//result.add(obj.toString());
				result.add(obj.getString("url")); //get only URL and ignore its _id
			}
		}
		return result;
	}
	
}
