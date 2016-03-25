package com.spark.network.filters;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import scala.Tuple2;

import com.spark.network.database.vo.MappingsVO;
import com.spark.network.functions.Functions;
import com.spark.network.packets.UCI;
import com.spark.network.util.SparkManager;

import java.sql.Timestamp;

public class BadIPFilter extends PacketHandler {

	public void process(JavaRDD<UCI> uciPackets, String destLocation,
			MappingsVO mappingsDetailVO, SparkManager sparkManager)
			throws Exception {

		final String jsonString = ((MappingsVO) mappingsDetailVO)
				.getInputParameters();
		String badIPList = jsonString;

		if (badIPList == null) {
			System.out.println("Must specify BadIP file.");
			System.exit(-1);
		}

		
		// TODO Edit to add file path to file name
		String badIPFilePath = badIPList;

		final JavaPairRDD<String, String> badIPLines = sparkManager
				.getSparkContext().textFile(badIPFilePath)
				.mapToPair(Functions.GET_SOURCE_IP_KEY);

		JavaPairRDD<String, String> inputPacketIPs = uciPackets
				.mapToPair(Functions.GET_SOURCE_DESTINATION_IP);
		JavaPairRDD<String, Tuple2<String, String>> badIpList = inputPacketIPs
				.join(badIPLines);

		System.out
				.println("Bad IP detected ===================>>>>>>>>>>>>>>>>>> : "
						+ badIpList.first());

		Timestamp ts = new Timestamp(3);

		System.out.println("Handler //////////////// " + ts.getTime());

		try {
			Configuration config = new Configuration();
			config.addResource(new Path("/etc/hadoop/conf/core-site.xml"));
			config.addResource(new Path("/etc/hadoop/conf/hdfs-site.xml"));

			config.set("fs.hdfs.impl",
					org.apache.hadoop.hdfs.DistributedFileSystem.class
							.getName());
			config.set("fs.file.impl",
					org.apache.hadoop.fs.LocalFileSystem.class.getName());
			FileSystem dfs = FileSystem.get(config);

			badIpList.saveAsTextFile(dfs.getUri() + "/TestDirectory/output");

			System.out.println(" HDFS path for wrire 000000000000000>>>>>>>> "
					+ dfs.getUri() + "/TestDirectory/output");
			// .saveAsTextFile("/user/cloudera/projects/data/output/"+Types.TIMESTAMP);

		} catch (Exception e) {
			throw new Exception(e);
		}

	}

}
