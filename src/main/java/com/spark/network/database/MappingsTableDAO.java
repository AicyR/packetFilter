package com.spark.network.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spark.network.database.DatabaseManager;
import com.spark.network.database.vo.MappingsVO;
import com.spark.network.constants.DatabaseConstants;

/**
 * The Class MappingsTableDAO.The class contains methods to perform CRUD
 * operations on mappings table.
 * 
 * 
 */
public class MappingsTableDAO {

	 /** Static containing Logger for the class. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MappingsTableDAO.class);
    
    /** The databaseManager object for accessing database. */
    private DatabaseManager databaseManager = new DatabaseManager();
    
    /** The callable statement. */
    private CallableStatement callablestatement = null;
    
    /** The Mappings table VO. */
    private MappingsVO mappingsVO = null;
    
    

    /**
     * Method retrieves list of MAPPINGSVO from Mappings table.
     * 
     * @version 1.0
     * @return the list of MappingsVO
     * @throws Exception
     *             the exception
     */
    
    public List<MappingsVO> retriveMappingsTableData() throws Exception{
    	Connection connection = null;
    	ResultSet resultSet = null;
    	List<MappingsVO> mappingsVOList = new ArrayList<MappingsVO>();
    	
    	try{
    		connection = databaseManager.getConnection();
    		callablestatement = connection.prepareCall("{ call getMappingsTableData() }");
    		
    		if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Retrieving all Mappings table entries");
            }
    		
    		
    		resultSet = callablestatement.executeQuery();
    		
    		while (resultSet.next()){
    			
    			mappingsVO = new MappingsVO();
    			mappingsVO.setMappingId(resultSet.getInt(DatabaseConstants.MAPPING_ID));
    			mappingsVO.setPacketId(resultSet.getInt(DatabaseConstants.PACKET_ID));
    			mappingsVO.setTransformationHandler(resultSet.getString(DatabaseConstants.TRANSFORMATION_HANDLER));
    			
    			mappingsVO.setInputParameters(resultSet.getString(DatabaseConstants.INPUT_PARAMETERS));
    			mappingsVO.setTransformationLevel(resultSet.getInt(DatabaseConstants.TRANSFORMATION_LEVEL));

    			
    			mappingsVOList.add(mappingsVO);
    			
    		}
    	}catch(SQLException e){
    		StringBuffer exMsg = new StringBuffer(
                    "Exception while retriving data from Mappings table - ").append(e
                    .getMessage());
            throw new Exception(exMsg.toString(), e);
    	}finally {
    		databaseManager.close(resultSet);
    		databaseManager.close(callablestatement);
    		databaseManager.close(connection);
    	}
    	return mappingsVOList;
    	
    }
    
           
    /**
     * Method retrieves a MAPPINGSVO from Mappings table for given packet.
     * 
     * @version 1.0
     * @return a MappingsVO
     * @throws Exception
     *             the exception
     */
    
    public MappingsVO retriveMappingDetails(final int packerID) throws Exception{
    	Connection connection = null;
    	ResultSet resultSet = null;
    	MappingsVO mappingsVO = new MappingsVO();
    	
    	try{
    		connection = databaseManager.getConnection();
    		callablestatement = connection.prepareCall("{ call getPacketMappingDetails(?) }");
    		databaseManager.prepareStatement(callablestatement, 1, packerID);
    		
    		if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Retrieving all Mappings table entries");
            }
    		
    		
    		resultSet = callablestatement.executeQuery();
    		
    		while (resultSet.next()){
    			
    			mappingsVO.setMappingId(resultSet.getInt(DatabaseConstants.MAPPING_ID));
    			mappingsVO.setPacketId(resultSet.getInt(DatabaseConstants.PACKET_ID));
    			mappingsVO.setTransformationHandler(resultSet.getString(DatabaseConstants.TRANSFORMATION_HANDLER));
    			
    			mappingsVO.setInputParameters(resultSet.getString(DatabaseConstants.INPUT_PARAMETERS));
    			mappingsVO.setTransformationLevel(resultSet.getInt(DatabaseConstants.TRANSFORMATION_LEVEL));
   			
    		}
    	}catch(SQLException e){
    		StringBuffer exMsg = new StringBuffer(
                    "Exception while retriving data from Mappings table - ").append(e
                    .getMessage());
            throw new Exception(exMsg.toString(), e);
    	}finally {
    		databaseManager.close(resultSet);
    		databaseManager.close(callablestatement);
    		databaseManager.close(connection);
    	}
    	return mappingsVO;
    	
    }
}
