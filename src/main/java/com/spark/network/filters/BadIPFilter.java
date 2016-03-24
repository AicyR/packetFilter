package com.spark.network.filters;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

import com.spark.network.database.vo.MappingsVO;
import com.spark.network.functions.Functions;
import com.spark.network.packets.UCI;

import java.sql.Timestamp;
import java.sql.Types;

public class BadIPFilter extends PacketHandler {

	

	public void process(JavaRDD<UCI> uciPackets, String destLocation,
			MappingsVO mappingsDetailVO, SparkManager sparkManager ) throws Exception {

		final String jsonString = ((MappingsVO) mappingsDetailVO)
				.getInputParameters();
		String badIPList = jsonString;


		if (badIPList == null) {
			System.out.println("Must specify BadIP file.");
			System.exit(-1);
		}

		// Edit to add file path to file name
		String badIPFilePath = badIPList;

		final JavaPairRDD<String, String> badIPLines = sparkManager.getSparkContext().textFile(
				badIPFilePath).mapToPair(Functions.GET_SOURCE_IP_KEY);

		

				JavaRDD<String> sourcePort = uciPackets.map(
						Functions.GET_SOURCE_PORT).cache();

				// if (sourcePort.equals("8080"))
				// System.out.println("SOURCE IP of Port: " +
				// sourcePort.toString() + " is =" +sourceIP.toString());

				// RDD is immutable, let's create a new RDD which doesn't
				// contain bad IPs
				// the function needs to return true for the records to be kept
				// List<String> badIPLines1 =
				// uciPackets.mapToPair(Functions.GET_SOURCE_DESTINATION_IP).filter(Functions.FILTER_BADIP).map(Functions.GET_TUPLE_FIRST).take(10);

				JavaPairRDD<String, String> badIPLines1 = uciPackets
						.mapToPair(Functions.GET_SOURCE_DESTINATION_IP);
				JavaPairRDD<String, Tuple2<String, String>> badIPLines2 = badIPLines1
						.join(badIPLines);

				System.out
						.println("Bad IP detected ===================>>>>>>>>>>>>>>>>>> : "
								+ badIPLines2.first());

				// List<String> badIPLines1 =badIPLines2.take(10);

				// .join(badIPLines).map(Functions.GET_TUPLE_FIRST).take(10);

				// List<String> badIPLines2 =
				// uciPackets.mapToPair(Functions.GET_SOURCE_DESTINATION_IP).joi.filter(Functions.FILTER_BADIP).map(Functions.GET_TUPLE_FIRST).take(10);

				Timestamp ts = new Timestamp(3);
				
				
				System.out.println("Handler //////////////// "+ ts.getTime() );
				
				try{
				Configuration config = new Configuration();
				   config.addResource(new Path("/etc/hadoop/conf/core-site.xml"));
				   config.addResource(new Path("/etc/hadoop/conf/hdfs-site.xml"));

				   config.set("fs.hdfs.impl", 
				            org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()
				        );
				       config.set("fs.file.impl",
				            org.apache.hadoop.fs.LocalFileSystem.class.getName()
				        );
				  FileSystem dfs = FileSystem.get(config);
				  String dirName = "TestDirectory";
				  System.out.println(dfs.getWorkingDirectory() +" this is from /n/n");
				  Path src = new Path(dfs.getWorkingDirectory()+"/"+dirName);

			//	   dfs.mkdirs(src); 
				

 
				
				   
			//	badIPLines2.saveAsTextFile("/tmp/spark-test/output");
				badIPLines2.saveAsTextFile(dfs.getUri() +"/TestDirectory/output"); 
			//	badIPLines2.saveAsTextFile("file:////tmp/spark-test/output");
				
				System.out.println(" HDFS path for wrire 000000000000000>>>>>>>> "+dfs.getUri() +"/TestDirectory/output"); 
			//			.saveAsTextFile("/user/cloudera/projects/data/output/"+Types.TIMESTAMP);

				
				   }catch(Exception e) {
				         throw new Exception(e);}
		
	}

}
