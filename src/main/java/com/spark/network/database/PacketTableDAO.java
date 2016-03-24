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
import com.spark.network.database.vo.PacketVO;
import com.spark.network.constants.DatabaseConstants;

/**
 * The Class PacketTableDAO.The class contains methods to perform CRUD
 * operations on mappings table.
 * 
 * 
 */
public class PacketTableDAO {

	/** Static containing Logger for the class. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PacketTableDAO.class);

	/** The databaseManager object for accessing database. */
	private DatabaseManager databaseManager = new DatabaseManager();

	/** The callable statement. */
	private CallableStatement callablestatement = null;

	/** The Packet table VO. */
	private PacketVO packetVO = null;

	/**
	 * Method retrieves list of PacketVO from Packet table.
	 * 
	 * @version 1.0
	 * @return the list of PacketVO
	 * @throws Exception
	 *             the exception
	 */

	public List<PacketVO> retrivePacketTableData() throws Exception {
		Connection connection = null;
		ResultSet resultSet = null;
		List<PacketVO> packetVOList = new ArrayList<PacketVO>();

		try {
			connection = databaseManager.getConnection();
			callablestatement = connection
					.prepareCall("{ call getPacketTableData() }");

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving all Packet table entries");
			}

			resultSet = callablestatement.executeQuery();

			while (resultSet.next()) {

				packetVO = new PacketVO();
				packetVO.setPacketId(resultSet
						.getInt(DatabaseConstants.PACKET_ID));
				packetVO.setPacketType(resultSet
						.getString(DatabaseConstants.PACKET_TYPE));

				packetVO.setTargetPath(resultSet
						.getString(DatabaseConstants.TARGET_PATH));
				
				packetVOList.add(packetVO);

			}
		} catch (SQLException e) {
			StringBuffer exMsg = new StringBuffer(
					"Exception while retriving data from Packet table - ")
					.append(e.getMessage());
			throw new Exception(exMsg.toString(), e);
		} finally {
			databaseManager.close(resultSet);
			databaseManager.close(callablestatement);
			databaseManager.close(connection);
		}
		return packetVOList;

	}
	
	/**
	 * Method retrieves a PacketVO for given Packet ID from packet table.
	 * 
	 * @version 1.0
	 * @return a PacketVO
	 * @throws Exception
	 *             the exception
	 */

	public PacketVO retrivePacketDetails(final int packetID) throws Exception {
		Connection connection = null;
		ResultSet resultSet = null;
		PacketVO packetVO = new PacketVO();

		try {
			connection = databaseManager.getConnection();
			final String procSQL = "{ call getPacketDetails(?) }";
			callablestatement = connection.prepareCall(procSQL);
			databaseManager.prepareStatement(callablestatement,1, packetID);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving all Packet table entries");
			}

			resultSet = callablestatement.executeQuery();

			while (resultSet.next()) {

				packetVO.setPacketId(resultSet
						.getInt(DatabaseConstants.PACKET_ID));
				packetVO.setPacketType(resultSet
						.getString(DatabaseConstants.PACKET_TYPE));

				packetVO.setTargetPath(resultSet
						.getString(DatabaseConstants.TARGET_PATH));
				
			}
		} catch (SQLException e) {
			StringBuffer exMsg = new StringBuffer(
					"Exception while retriving data from Packet table - ")
					.append(e.getMessage());
			throw new Exception(exMsg.toString(), e);
		} finally {
			databaseManager.close(resultSet);
			databaseManager.close(callablestatement);
			databaseManager.close(connection);
		}
		return packetVO;

	}

}
