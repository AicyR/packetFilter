package com.spark.network.filters;

import com.spark.network.database.vo.MappingsVO;
import com.spark.network.packets.UCI;

import org.apache.spark.streaming.api.java.JavaDStream;
import org.codehaus.jettison.json.JSONException;

import java.util.Map;


public class Handler {

	public void process(String packet, String destLocation, MappingsVO mappingsDetailVO ){
		
		final String jsonString = ((MappingsVO) mappingsDetailVO).getInputParameters();
		
		// try {
	            
		
		
	            /* look at Transformation Util.getPosFromJSON to change to Jason input parameters, should change entry in metadata as well*/
	          /*
	           * Map<String, String> badIPList;
	           *   badIPList = TransformationUtil.getPosFromJSON(Arrays.asList(vars),jsonString);
	           */
	            
	           String badIPList = jsonString;
	           /* run filter/transformation*/
	           
	           System.out.println (" BadIP List File name=========> "+ badIPList);
	            		
	/*	 }catch (JSONException e) {
	            throw new Exception(
	                    "Exception in parsing input parameters in Handler", e);
		 }*/
		
	}	
	
	
}
