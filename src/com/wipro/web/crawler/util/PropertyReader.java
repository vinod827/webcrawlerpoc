/***************************************************************************************************
FILE		:	PropertyReader
Author		:	Vinod Kumar Nair (WIPRO)
Purpose		:	This class will be used as property file
Date Created:	03-01-2017
****************************************************************************************************/
/**
 * This class will be used as property file
 *
 * @history <<DD>> <<MON>> <<YYYY>> << DEVELOPER >> - << CHANGES DONE >>.
 */

package com.wipro.web.crawler.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author vinod827
 *
 */
public class PropertyReader {

	public static Properties dbProperties = null;
	
	static {
		try{
			if (dbProperties == null) {
				dbProperties = PropertyReader.getFileProperties("config/properties/db.properties");
				System.out.println("DB Properties: " + dbProperties); 
			}			
		}
		catch(Exception e){
			System.out.println("Exception while loading properties file : "+e);
			System.exit(0);
		}
	}
	
	public static Properties getFileProperties(String fileName) throws IOException {
		InputStream inputStream = getInputStream(fileName);
		Properties properties = new Properties();
		properties.load(inputStream);
		inputStream.close();
		return properties;
	}
	
	public static InputStream getInputStream(String fileName) throws FileNotFoundException {
		String appRoot = System.getProperty("WIPRO");
		InputStream is = null;
		if (appRoot!= null){
			is = getInputStream(appRoot,fileName);
			if(is==null)is=getInputStream(appRoot+"WEB-INF/",fileName);
		}

		if(is==null){
			ClassLoader tcl;
			tcl=Thread.currentThread().getContextClassLoader();
			is=tcl.getResourceAsStream(fileName);
		}
		if(is==null)is=getInputStream("",fileName);
		if(is==null)throw new FileNotFoundException("File:'"+fileName+" not found in either system property:"+ appRoot+" or classpaths");
		return is;
	}
	
	public static InputStream getInputStream(String dir,String fileName){
		String absPath=null;
		if(dir !=null && dir.length()>0){
			String sep=(dir.endsWith("/")||dir.endsWith("\\"))?"":"/";
			absPath=dir+sep+fileName;
		}else{
			absPath=fileName;
		}

		File file=new File(absPath);
		if(!file.exists()){
			return null;
		}
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			return null;
		}
	}		
}