package com.spark.network.filters;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import com.spark.network.database.vo.PacketVO;
import com.spark.network.database.vo.MappingsVO;
import com.spark.network.database.PacketTableDAO;
import com.spark.network.database.MappingsTableDAO;
import com.spark.network.functions.Functions;
import com.spark.network.packets.UCI;

public class SparkManager {

	/* TODO: extract these parameters into parameters file */

	// Stats will be computed for the last window length of time.
	private static final Duration WINDOW_LENGTH = new Duration(30 * 1000);
	// Stats will be computed every slide interval time.
	private static final Duration SLIDE_INTERVAL = new Duration(10 * 1000);

	
	SparkConf conf;
	JavaSparkContext sc;
	JavaStreamingContext jssc;
	JavaDStream<UCI> windowDStream;

	public SparkManager() throws Exception {

		conf = new SparkConf().setAppName("UCI Filter Streaming");
		sc = new JavaSparkContext(conf);

		jssc = new JavaStreamingContext(sc, SLIDE_INTERVAL);
	}

	public final void openStreamConnection() throws Exception {

		
		try {
			// This sets the update window to be every 10 seconds.

			JavaReceiverInputDStream<String> packetDataDStream = jssc
					.socketTextStream("localhost", 9997);

			// A DStream of UCI Packet.
			JavaDStream<UCI> uciDStream = packetDataDStream.map(
					Functions.PARSE_PACKET_LINE).cache();

			// Splits the uciDStream into a dstream of time windowed rdd's of
			// uci
			// packets.
			windowDStream = uciDStream.window(WINDOW_LENGTH, SLIDE_INTERVAL);

		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	public void closeConnection() {

	}

	public JavaSparkContext getSparkContext() {
		return sc;
	}

	public JavaDStream<UCI> getSparkstreamWindow() {
		return this.windowDStream;
	}

	public JavaStreamingContext getJavaStreamingContext() {
		return this.jssc;

	}

}
