package com.spark.network.filters;


import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;

import com.spark.network.filters.SparkManager;
import com.spark.network.functions.Functions;
import com.spark.network.packets.UCI;
import com.spark.network.database.vo.PacketVO;
import com.spark.network.database.vo.MappingsVO;
import com.spark.network.database.PacketTableDAO;
import com.spark.network.database.MappingsTableDAO;

public class Driver {

	// Stats will be computed for the last window length of time.
		private static final Duration WINDOW_LENGTH = new Duration(30 * 1000);
		// Stats will be computed every slide interval time.
		private static final Duration SLIDE_INTERVAL = new Duration(10 * 1000);
		
	
	
	public static void main(String[] args) throws Exception{
		//int packetId = Integer.parseInt(args[0]);
		
		final PacketTableDAO packetTableDAO = new PacketTableDAO();
		final MappingsTableDAO mappingsTableDAO = new MappingsTableDAO();
		
		final SparkManager sparkManager = new SparkManager();
		sparkManager.openStreamConnection();
		
		
		//start steaming window
		sparkManager.getSparkstreamWindow().foreachRDD(new Function<JavaRDD<UCI>, Void>() {
			
			/*TODO change to general packet not only UCI*/
			@Override
			public Void call(JavaRDD<UCI> uciPackets) throws Exception{
				if (uciPackets.count() == 0) {
					System.out.println("No access logs in this time interval");
					return null;
				}

				// *** Note that this is code copied verbatim from
				// LogAnalyzer.java.

				
			    String sourceIP =
	            		uciPackets.map(Functions.GET_SOURCE_IP).take(4).get(0);
				System.out
						.println("Source IP in Stream in Driver ++++++++++++++++++++++++||||||||||| "
								+ sourceIP);
		
				// Get First Packet ID
				
				JavaRDD<String> packetIDrdd = uciPackets.map(Functions.GET_Packet_ID);
				
				List<String> packetIDlist = packetIDrdd.take(1);
				
				String packetID= packetIDlist.get(0);
					
								
				int packetId = Integer.parseInt(packetID);
				
				System.out
				.println("Packet ID Stream in Driver ++++++++++++++++++++++++||||||||||| "
						+ packetId);
				
				try{
				
			PacketVO packetDetails = packetTableDAO.retrivePacketDetails(packetId);
    		MappingsVO mappingDetails = mappingsTableDAO.retriveMappingDetails(packetId);
    		
    		/* look at transformationComplexHandler to see how retrive each handler parameters from cach*/
    		
    		String handlerClass = mappingDetails.getTransformationHandler();
    		
    		PacketHandler packetHandler = (PacketHandler) Class.forName(handlerClass).newInstance();
           
    		System.out.println("Handler Name ========> "+mappingDetails.getTransformationHandler());
    		
    	//	Handler handler = new Handler();
    		packetHandler.process(uciPackets, packetDetails.getTargetPath(), mappingDetails, sparkManager);
    		
    		
				}catch (Exception e) {
		            StringBuffer exMsg = new StringBuffer(
		                    "Exception while calling DAOs in Driver - ").append(e
		                    .getMessage());
		            throw new Exception(exMsg.toString(), e);
		        }
    		return null;
			}
		}); 	            
    		
    		
		// Start the streaming server.
				sparkManager.getJavaStreamingContext().start(); // Start the computation
				sparkManager.getJavaStreamingContext().awaitTermination(); // Wait for the computation to terminate
		
	}
	
	
		
	
}
