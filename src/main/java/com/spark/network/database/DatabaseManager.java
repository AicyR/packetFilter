package com.spark.network.database;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

import com.spark.network.util.PropertiesUtil;


public class DatabaseManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseManager.class);
	
	private Connection connection = null;
	
	/**
     * Creates the database connection.
     * 
     * @return the connection
     * @throws Exception
     *             the generic exception
     */
    private Connection createConnection() throws Exception {

        try {

            String dbDriver = PropertiesUtil.getDatabaseJdbcDriver();
            String connectionString = PropertiesUtil
                    .getDatabaseConnectionUrl();
            String userName = PropertiesUtil.getDatabaseConnUserName();
            String password = PropertiesUtil.getDatabaseConnPassword();

            Class.forName(dbDriver);
            connection = DriverManager.getConnection(connectionString,
                    userName, password);
        } catch (Exception e) {
            StringBuffer exMsg = new StringBuffer(
                    "Exception while creating DB connection - ").append(e
                    .getMessage());
            throw new Exception(exMsg.toString(), e);
        }
        return connection;
    }

    /**
     * Creates the database connection with arguments.
     * 
     * @param dbDriver
     *            the database driver
     * @param connectionString
     *            the connection string
     * @param userName
     *            the user name
     * @param password
     *            the password
     * @return the connection
     * @throws Exception
     *             the generic exception
     */
    public Connection createConnection(String dbDriver,
            String connectionString, String userName, String password)
            throws Exception {
        try {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(connectionString,
                    userName, password);
        } catch (Exception e) {
            StringBuffer exMsg = new StringBuffer(
                    "Exception while creating DB connection - ").append(e
                    .getMessage());

            throw new Exception(exMsg.toString(), e);
        }
        return connection;
    }

    /**
     * Gets the connection.A new connection is created only if the connection
     * object is null or closed.
     * 
     * @version 1.0
     * @return the connection
     * @throws Exception
     *             the generic exception
     * @see
     * @since 1.0
     */
    public Connection getConnection() throws Exception {

        try {
            if (connection == null || connection.isClosed()) {
                connection = createConnection();
            }
        } catch (SQLException e) {
            StringBuffer exMsg = new StringBuffer(
                    "Exception while creating DB connection - ").append(e
                    .getMessage());

            throw new Exception(exMsg.toString(), e);
        }
        return connection;
    }

    /**
     * Close the database connection.
     * 
     * @version 1.0
     * @param connection
     *            the connection
     * @see
     * @since 1.0
     */
    public void close(Connection connection) {

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            StringBuffer exMsg = new StringBuffer(
                    "Exception while creating DB connection - ").append(e
                    .getMessage());
            LOGGER.error(exMsg.toString());
        }
    }

    /**
     * Close the callable statement.
     * 
     * @version 1.0
     * @param callableStatement
     *            the callable statement
     * @see
     * @since 1.0
     */
    public void close(CallableStatement callableStatement) {
        try {
            if (callableStatement != null) {
                callableStatement.close();
            }
        } catch (SQLException e) {
            StringBuffer exMsg = new StringBuffer(
                    "Exception while closing callable statement - ").append(e
                    .getMessage());
            LOGGER.error(exMsg.toString());
        }
    }

    /**
     * Populate statement with param index and int param value.
     * 
     * @version 1.0
     * @param callableStatement
     *            the callable statement
     * @param paramIndex
     *            the param index
     * @param paramValue
     *            the param value
     * @throws SQLException
     *             the SQL exception
     * @see
     * @since 1.0
     */
    public void prepareStatement(CallableStatement callableStatement,
            int paramIndex, int paramValue) throws SQLException {
        if (paramValue != 0) {
            callableStatement.setInt(paramIndex, paramValue);
        } else {
            callableStatement.setNull(paramIndex, Types.INTEGER);
        }
    }

    /**
     * Populate statement with parameter index and Time parameter value.
     * 
     * @version 1.0
     * @param callableStatement
     *            the callable statement
     * @param paramIndex
     *            the parameter index
     * @param paramValue
     *            the parameter value
     * @throws SQLException
     *             the SQL exception
     * @see
     * @since 1.0
     */
    public void prepareStatement(CallableStatement callableStatement,
            int paramIndex, Time paramValue) throws SQLException {
        if (paramValue != null) {
            callableStatement.setTime(paramIndex, paramValue);
        } else {
            callableStatement.setNull(paramIndex, Types.TIME);
        }
    }

    /**
     * Populate statement with parameter index and string parameter value.
     * 
     * @version 1.0
     * @param callableStatement
     *            the callable statement
     * @param paramIndex
     *            the parameter index
     * @param paramValue
     *            the parameter value
     * @throws SQLException
     *             the SQL exception
     * @see
     * @since 1.0
     */
    public void prepareStatement(CallableStatement callableStatement,
            int paramIndex, String paramValue) throws SQLException {
        if (StringUtils.isNotBlank(paramValue)) {
            callableStatement.setString(paramIndex, paramValue);
        } else {
            callableStatement.setNull(paramIndex, Types.VARCHAR);
        }
    }

    /**
     * Populate statement with parameter index and date parameter value.
     * 
     * @version 1.0
     * @param callableStatement
     *            the callable statement
     * @param paramIndex
     *            the parameter index
     * @param paramValue
     *            the parameter value
     * @throws SQLException
     *             the SQL exception
     * @see
     * @since 1.0
     */
    public void prepareStatement(CallableStatement callableStatement,
            int paramIndex, Date paramValue) throws SQLException {
        if (paramValue != null) {
            callableStatement.setDate(paramIndex, paramValue);
        } else {
            callableStatement.setNull(paramIndex, Types.DATE);
        }
    }

    /**
     * Populate statement with parameter index and time stamp parameter value.
     * 
     * @version 1.0
     * @param callableStatement
     *            the callable statement
     * @param paramIndex
     *            the parameter index
     * @param paramValue
     *            the parameter value
     * @throws SQLException
     *             the SQL exception
     * @see
     * @since 1.0
     */
    public void prepareStatement(CallableStatement callableStatement,
            int paramIndex, Timestamp paramValue) throws SQLException {
        if (paramValue != null) {
            callableStatement.setTimestamp(paramIndex, paramValue);
        } else {
            callableStatement.setNull(paramIndex, Types.TIMESTAMP);
        }
    }

    /**
     * Prepare statement.
     * 
     * @param callableStatement
     *            the callable statement
     * @param paramIndex
     *            the param index
     * @param paramValue
     *            the param value
     * @throws SQLException
     *             the SQL exception
     */
    public void prepareStatement(CallableStatement callableStatement,
            int paramIndex, Blob paramValue) throws SQLException {
        if (paramValue != null) {
            callableStatement.setBlob(paramIndex, paramValue);
        } else {
            callableStatement.setNull(paramIndex, Types.BLOB);
        }
    }

    /**
     * Close the result set.
     * 
     * @version 1.0
     * @param resultSet
     *            the result set instance passed as argument
     * @see
     * @since 1.0
     */
    public void close(ResultSet resultSet) {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while closing result set - '{}'",
                    e.getMessage());
        }
    }
	
}
