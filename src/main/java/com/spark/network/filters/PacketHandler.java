package com.spark.network.filters;

import org.apache.spark.api.java.JavaRDD;

import com.spark.network.database.vo.MappingsVO;
import com.spark.network.packets.UCI;
import com.spark.network.util.SparkManager;


public class PacketHandler {
	
public void process(JavaRDD<UCI> uciPackets, String destLocation, MappingsVO mappingsDetailVO, SparkManager sparkManager )throws Exception{
		
		final String jsonString = ((MappingsVO) mappingsDetailVO).getInputParameters();
		
		
	            
	           String badIPList = jsonString;
	           /* run filter/transformation*/
	           
	           System.out.println (" PacketHandler =========> "+ badIPList);
	            		
	
		
	}	

}
