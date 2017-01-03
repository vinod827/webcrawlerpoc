/***************************************************************************************************
FILE		:	MongoDBConnection
Author		:	Vinod Kumar Nair (WIPRO)
Purpose		:	This class will be used to connect and disconnect with database, MongoDB
Date Created:	03-01-2017
****************************************************************************************************/
/**
 * This class will be used to connect and disconnect with database, MongoDB
 *
 * @history <<DD>> <<MON>> <<YYYY>> << DEVELOPER >> - << CHANGES DONE >>.
 */
package com.wipro.web.crawler.db;

import java.util.Properties;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.wipro.web.crawler.util.PropertyReader;

/**
 * @author vinod827
 *
 */
public class MongoDBConnection {
	private static Properties db_properties = null;
	static{
		try{
			//Load property file
			db_properties = PropertyReader.dbProperties;			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @author vinod827
	 * @description: To get database connection
	 */
	public static MongoClient getDBConnection() {
		
		  /*MongoClient mongoClient = new
		  MongoClient(db_properties.getProperty("HOSTNAME"),
		  db_properties.getProperty("PORT") );*/
		 
		MongoClient mongoClient = new MongoClient("localhost", 27017); // database connection
		return mongoClient;
	}
	
	/**
	 * @author vinod827
	 * @description: To close database connection
	 */
	public static void closeDBConnection(MongoClient client) {
		client.close();		
	}

}
