package com.spark.network.packets;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The UCI packet class
 * 
 */
public class UCI implements Serializable {
  private static final Logger logger = Logger.getLogger("Access");

  private String packetID;
  private String srcIP;
  private String dstIP;
  private String srcPort;
  private String dstPort;
  private String data;

	private UCI(String packetID, String srcIP, String dstIP, String srcPort, String dstPort,
			String data) {
		this.packetID=packetID;
		this.srcIP = srcIP;
		this.dstIP = dstIP;
		this.srcPort = srcPort;
		this.dstPort = dstPort;
		this.data = data;

	}

	
	
	
	public String getPacketID() {
		System.out.println("Packet ID in UCI class------->>>>>>>>>>"+packetID);
		return packetID;
	}
	
	public String getSourceIP() {
		System.out.println("Source IP in UCI class------->>>>>>>>>>"+srcIP);
		return srcIP;
	}

	public String getDestinationIP() {
		return dstIP;
	}

	public String getSourcePort() {
		return srcPort;
	}

	public String getDestinationPort() {
		return dstPort;
	}

	public String getData() {
		return data;
	}

	public void setPacketID(String packetID) {
		this.packetID = packetID;
	}
	
	
	public void setSourceIP(String srcIP) {
		this.srcIP = srcIP;
	}

	public void setDestinationIP(String dstIP) {
		this.dstIP = dstIP;
	}

	public void setSourcePort(String srcPort) {
		this.srcPort = srcPort;
	}

	public void setDestinationPort(String dstPort) {
		this.dstPort = dstPort;
	}

	public void setData(String data) {
		this.data = data;
	}
	  
	  
// Example Apache log line:
  //   127.0.0.1 127.0.0.1 8081 8080 data
  private static final String UCI_ENTRY_PATTERN =
     // 1:SourceIP  2:DestinationIP 3:SourcePort 4:DestinationPort  5:data
     "^(\\S+) (\\S+) (\\S+) (\\d{4}) (\\d{4}) (\\S+)";
	

  private static final Pattern PATTERN = Pattern.compile(UCI_ENTRY_PATTERN);

  public static UCI parseFromLogLine(String packetline) {
    Matcher m = PATTERN.matcher(packetline);
    if (!m.find()) {
      logger.log(Level.ALL, "Cannot parse packet" + packetline);
      throw new RuntimeException("Error parsing packetline");
    }

    return new UCI(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6));
  }
}