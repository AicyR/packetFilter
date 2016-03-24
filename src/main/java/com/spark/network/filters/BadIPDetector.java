package com.spark.network.filters;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple1;
import scala.Tuple2;

import com.spark.network.packets.UCI;
import com.spark.network.functions.Functions;

public class BadIPDetector {
	// Stats will be computed for the last window length of time.
	  private static final Duration WINDOW_LENGTH = new Duration(30 * 1000);
	  // Stats will be computed every slide interval time.
	  private static final Duration SLIDE_INTERVAL = new Duration(10 * 1000);

	  public static void main(String[] args) {
		    SparkConf conf = new SparkConf().setAppName("UCI Filter Streaming");
		    JavaSparkContext sc = new JavaSparkContext(conf);

		    JavaStreamingContext jssc = new JavaStreamingContext(sc,
		        SLIDE_INTERVAL);  // This sets the update window to be every 10 seconds.

		    JavaReceiverInputDStream<String> packetDataDStream =
		        jssc.socketTextStream("localhost", 9997);

		    // A DStream of UCI Packet.
		    JavaDStream<UCI> uciDStream =
		    		packetDataDStream.map(Functions.PARSE_PACKET_LINE).cache();

		    // Splits the uciDStream into a dstream of time windowed rdd's of uci packets.
		    JavaDStream<UCI> windowDStream =
		        uciDStream.window(WINDOW_LENGTH, SLIDE_INTERVAL);
		    
		    
		    if (args.length == 0) {
                System.out.println("Must specify BadIP file.");
                System.exit(-1);
              }
              String badIPFilePath = args[0];
              
              final JavaPairRDD<String, String> badIPLines = sc.textFile(badIPFilePath).mapToPair(Functions.GET_SOURCE_IP_KEY);
		    
            
		    windowDStream.foreachRDD(
		        new Function<JavaRDD<UCI>, Void>() {
		          @Override
		          public Void call(JavaRDD<UCI> uciPackets) {
		            if (uciPackets.count() == 0) {
		              System.out.println("No access logs in this time interval");
		              return null;
		            }

		            // *** Note that this is code copied verbatim from LogAnalyzer.java.

		            // Calculate statistics based on the content size.
		            JavaRDD<String> sourceIP =
		            		uciPackets.map(Functions.GET_SOURCE_IP).cache();
		    		System.out.println("Source IP in Stream ++++++++++++++++++++++++||||||||||| "+Functions.GET_SOURCE_IP);
		            
		            JavaRDD<String> sourcePort =
		            		uciPackets.map(Functions.GET_SOURCE_PORT).cache();
		            
		      //      if (sourcePort.equals("8080")) 
		       //     	System.out.println("SOURCE IP of Port: " + sourcePort.toString() + " is =" +sourceIP.toString());
		            
		            
		         // RDD is immutable, let's create a new RDD which doesn't contain bad IPs
		            // the function needs to return true for the records to be kept
		          //  List<String> badIPLines1 = uciPackets.mapToPair(Functions.GET_SOURCE_DESTINATION_IP).filter(Functions.FILTER_BADIP).map(Functions.GET_TUPLE_FIRST).take(10);
		            
		            JavaPairRDD<String, String> badIPLines1 = uciPackets.mapToPair(Functions.GET_SOURCE_DESTINATION_IP);
		            JavaPairRDD<String, Tuple2<String, String>> badIPLines2=badIPLines1.join(badIPLines);
		          
		            System.out.println("Bad IP detected ===================>>>>>>>>>>>>>>>>>> : " + badIPLines2.first());
		            
		            
		            //  List<String> badIPLines1 =badIPLines2.take(10);

		        //    .join(badIPLines).map(Functions.GET_TUPLE_FIRST).take(10);

		            
		 //           List<String> badIPLines2 = uciPackets.mapToPair(Functions.GET_SOURCE_DESTINATION_IP).joi.filter(Functions.FILTER_BADIP).map(Functions.GET_TUPLE_FIRST).take(10);
		            
		            badIPLines2.saveAsTextFile("/user/cloudera/projects/data/output");

		            		    

		            return null;
		          }
		        });

		      // Start the streaming server.
		      jssc.start();              // Start the computation
		      jssc.awaitTermination();   // Wait for the computation to terminate
		    }

}
