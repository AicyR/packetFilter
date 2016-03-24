package com.spark.network.database.vo;

import java.io.Serializable;

public class MappingsVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
		   /** The Mapping id. */
		private int mappingId;
	
	/** The Packet id. */
	private int packetId;

	/** The Transformation Handler. */
	private String transformationHandler;

	/** The input parameters. */
	private String inputParameters;
	
	/** The transformation level. */
	private int transformationLevel;

	
	
	/**
	 * Gets the Mapping id.
	 * 
	 * @return the Mapping id
	 */
	public int getMappingId() {
		return mappingId;
	}

	/**
	 * Sets the Mapping id.
	 * 
	 * @param mappingId
	 *            the new packet id
	 */
	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}

	
	

	/**
	 * Gets the packet id.
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
	 * Gets the transformation Handler.
	 * 
	 * @return the transformation Handler
	 */
	public String getTransformationHandler() {
		return transformationHandler;
	}

	/**
	 * Sets the transformation Handler
	 * 
	 * @param transformationHandler
	 *            the new transformation Handler
	 */
	public void setTransformationHandler(String transformationHandler) {
		this.transformationHandler = transformationHandler;
	}

	/**
	 * Gets the input Parameters
	 * 
	 * @return the input Parameters
	 */
	public String getInputParameters() {
		return inputParameters;
	}

	/**
	 * Sets the input Parameters
	 * 
	 * @param inputParameters
	 *            the new input Parameters
	 */
	public void setInputParameters(String inputParameters) {
		this.inputParameters = inputParameters;
	}
	
	/**
	 * Gets the transformation Level.
	 * 
	 * @return the transformation Level
	 */
	public int getTransformationLevel() {
		return transformationLevel;
	}

	/**
	 * Sets the transformation Level
	 * 
	 * @param transformationLevel
	 *            the new transformation Level
	 */
	public void setTransformationLevel(int transformationLevel) {
		this.transformationLevel = transformationLevel;
	}

};
