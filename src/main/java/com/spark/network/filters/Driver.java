package com.spark.network.filters;

import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;

import com.spark.network.util.SparkManager;
import com.spark.network.functions.Functions;
import com.spark.network.packets.UCI;
import com.spark.network.database.vo.PacketVO;
import com.spark.network.database.vo.MappingsVO;
import com.spark.network.database.PacketTableDAO;
import com.spark.network.database.MappingsTableDAO;

/*Main Driver class which receives the packets and triggers their handlers based on entries in metadata */
public class Driver {

	public static void main(String[] args) throws Exception {

		final PacketTableDAO packetTableDAO = new PacketTableDAO();
		final MappingsTableDAO mappingsTableDAO = new MappingsTableDAO();

		final SparkManager sparkManager = new SparkManager();
		sparkManager.openStreamConnection();

		// start steaming window
		sparkManager.getSparkstreamWindow().foreachRDD(
				new Function<JavaRDD<UCI>, Void>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					/* TODO change to general packet not only UCI */
					@Override
					public Void call(JavaRDD<UCI> uciPackets) throws Exception {
						if (uciPackets.count() == 0) {
							System.out
									.println("No access logs in this time interval");
							return null;
						}

						String sourceIP = uciPackets
								.map(Functions.GET_SOURCE_IP).take(4).get(0);
						System.out
								.println("Source IP in Stream in Driver ++++++++++++++++++++++++||||||||||| "
										+ sourceIP);

						// Get Next Packet ID

						JavaRDD<String> packetIDrdd = uciPackets
								.map(Functions.GET_Packet_ID);

						List<String> packetIDlist = packetIDrdd.take(1);

						String packetID = packetIDlist.get(0);

						int packetId = Integer.parseInt(packetID);

						System.out
								.println("Packet ID Stream in Driver ++++++++++++++++++++++++||||||||||| "
										+ packetId);

						try {

							PacketVO packetDetails = packetTableDAO
									.retrivePacketDetails(packetId);
							MappingsVO mappingDetails = mappingsTableDAO
									.retriveMappingDetails(packetId);

							/*
							 * look at transformationComplexHandler to see how
							 * Retrieve each handler parameters from cache
							 */

							String handlerClass = mappingDetails
									.getTransformationHandler();

							PacketHandler packetHandler = (PacketHandler) Class
									.forName(handlerClass).newInstance();

							System.out.println("Handler Name ========> "
									+ mappingDetails.getTransformationHandler());

							packetHandler.process(uciPackets,
									packetDetails.getTargetPath(),
									mappingDetails, sparkManager);

						} catch (Exception e) {
							StringBuffer exMsg = new StringBuffer(
									"Exception while calling DAOs in Driver - ")
									.append(e.getMessage());
							throw new Exception(exMsg.toString(), e);
						}
						return null;
					}
				});

		// Start the streaming server.
		// Start the computation
		sparkManager.getJavaStreamingContext().start(); 
		// Wait for the computation to terminate

		sparkManager.getJavaStreamingContext().awaitTermination(); 
	}

}
