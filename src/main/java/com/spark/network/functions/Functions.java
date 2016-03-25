package com.spark.network.functions;



import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import com.spark.network.packets.UCI;

public class Functions {
	

	@SuppressWarnings("serial")
	public static Function<String, UCI> PARSE_PACKET_LINE = new Function<String, UCI>() {
		@Override
		public UCI call(String ucipacket) throws Exception {
			return UCI.parseFromLogLine(ucipacket);
		}
	};

	@SuppressWarnings("serial")
	public static Function<UCI, String> GET_DATA = new Function<UCI, String>() {

		@Override
		public String call(UCI ucipacket) throws Exception {
			return ucipacket.getData();
		}
	};

	public static Function<UCI, String> GET_SOURCE_PORT = new Function<UCI, String>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String call(UCI ucipacket) throws Exception {
			return ucipacket.getSourcePort();
		}
	};

	

	  public static PairFunction<String, String, String> GET_SOURCE_IP_KEY
		 = new PairFunction<String, String, String>() {
				  /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

				public Tuple2<String, String> call(String x) {
				    return new Tuple2<String, String>(x.split(" ")[0], x);
				  }
				};

	
	public static PairFunction<UCI, String, String> GET_SOURCE_DESTINATION_IP
	 = new PairFunction<UCI, String, String>() {
	 
		 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override public Tuple2<String, String> call(UCI ucipacket) throws
	  Exception { return new Tuple2<String, String>(ucipacket.getSourceIP(), ucipacket.getDestinationIP()); } };
	  
	  public static Function<Tuple2<String, String>, Boolean> FILTER_BADIP =
		new Function<Tuple2<String, String>, Boolean>() {
		 
		/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		@Override public Boolean call(Tuple2<String, String> tuple) throws
		Exception { return tuple._2().equals("127.0.0.1"); } };
		
		public static Function<Tuple2<String, String>, String> GET_TUPLE_FIRST =
				new Function<Tuple2<String, String>, String>() {
				 
				/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

				@Override public String call(Tuple2<String, String> tuple) throws Exception
				{ return tuple._1(); } };

	public static Function<UCI, String> GET_SOURCE_IP = new Function<UCI, String>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String call(UCI ucipacket) throws Exception {
			System.out.println ("Inside Functions Source IP --------------------->>>>>>>");
			return ucipacket.getSourceIP();
		}
	};

	public static Function<UCI, String> GET_Packet_ID = new Function<UCI, String>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String call(UCI ucipacket) throws Exception {
			System.out.println ("Inside Functions Packet ID --------------------->>>>>>>");
			return ucipacket.getPacketID();
		}
	};
	
	public static Function<UCI, String> GET_DESTINATION_IP = new Function<UCI, String>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String call(UCI ucipacket) throws Exception {
			return ucipacket.getDestinationIP();
		}
	};

	
}
