package com.spark.network.database.vo;

import java.io.Serializable;

public class PacketVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Packet id. */
	private int packetId;

	/** The Packet type. */
	private String packetType;

	/** The target path. */
	private String targetPath;

	/**
	 * Gets the table id.
	 * 
	 * @return the packet id
	 */
	public int getPacketId() {
		return packetId;
	}

	/**
	 * Sets the packet id.
	 * 
	 * @param packetId
	 *            the new packet id
	 */
	public void setPacketId(int packetId) {
		this.packetId = packetId;
	}

	/**
	 * Gets the packet type.
	 * 
	 * @return the packet type
	 */
	public String getPacketType() {
		return packetType;
	}

	/**
	 * Sets the packet type.
	 * 
	 * @param packetType
	 *            the new packet type.
	 */
	public void setPacketType(String packetType) {
		this.packetType = packetType;
	}

	/**
	 * Gets the target Path
	 * 
	 * @return the target Path
	 */
	public String getTargetPath() {
		return targetPath;
	}

	/**
	 * Sets the target Path
	 * 
	 * @param targetPath
	 *            the new target Path
	 */
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

};
