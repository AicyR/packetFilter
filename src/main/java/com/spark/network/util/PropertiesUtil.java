package com.spark.network.util;

import java.io.IOException;
import java.util.Properties;









import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.spark.network.constants.ConfigurationConstants;


public final class PropertiesUtil {
	
	private static Properties properties = new Properties();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);
	
	
	private PropertiesUtil(){
		
	}
	
	static {
		try{
			properties.load(ClassLoader.getSystemResourceAsStream(ConfigurationConstants.CONFIG_FILE));
		} catch (IOException e){
			LOGGER.error("Exception while loading properties file - {}", e.getMessage());
		}
	}
	
	
	/**
     * Gets the any property from global-env.properties file
     * 
     * @param property
     *            name for which the value need to be fetched from
     *            global-env.properties file
     * @return the hadoop core site path
     * @see
     * @version 1.0
     * @since 1.0
     */
    public static String getProperty(String property) {
        Object propValue = properties.get(property);
        if (propValue == null) {
            LOGGER.error(
                    "Failed to get the {} from global-env.properties file",
                    property);
            return null;
        }
        return propValue.toString();
    }
    
    /**
     * Gets the Database Vendor name from property file.
     * 
     * @return the Database Vendor name
     * @see
     * @version 1.0
     * @since 1.0
     */
    public static String getDatabaseVendor() {
        Object dbVendor = properties.get(ConfigurationConstants.DB_VENDOR);

        if (dbVendor == null) {
            LOGGER.error("Failed to get database vendor name");
            return null;
        }
        return dbVendor.toString();
    }
	
	/**
     * Gets the Database connection URL.
     * 
     * @return the Database connection URL
     * @see
     * @version 1.0
     * @since 1.0
     */
    public static String getDatabaseConnectionUrl() {
        StringBuilder dbConnUrl = new StringBuilder(getDatabaseVendor())
                .append(ConfigurationConstants.DOT_CHARACTER).append(
                        ConfigurationConstants.DB_CONN_URL);
        Object connectionUrl = properties.get(dbConnUrl.toString());

        if (connectionUrl == null) {
            LOGGER.error("Failed to get database connection url");
            return null;
        }
        return connectionUrl.toString();
    }
    
    /**
     * Gets the Database JDBC driver.
     * 
     * @return the Database JDBC driver
     * @see
     * @version 1.0
     * @since 1.0
     */
    public static String getDatabaseJdbcDriver() {
        Object jdbcDriver = properties.get(getDatabaseVendor() + "."
                + ConfigurationConstants.DB_CONN_DRIVER);

        if (jdbcDriver == null) {
            LOGGER.error("Failed to get database jdbc driver");
            return null;
        }
        return jdbcDriver.toString();
    }


    /**
     * Gets the Database connection user name.
     * 
     * @return the Database connection user name
     * @see
     * @version 1.0
     * @since 1.0
     */
    public static String getDatabaseConnUserName() {
        StringBuilder dbConnUser = new StringBuilder(getDatabaseVendor())
                .append(ConfigurationConstants.DOT_CHARACTER).append(
                        ConfigurationConstants.DB_CONN_USER);
        Object connUserName = properties.get(dbConnUser.toString());

        if (connUserName == null) {
            LOGGER.error("Failed to get database connection user name");
            return null;
        }
        return connUserName.toString();
    }

    /**
     * Gets the Database connection password.
     * 
     * @return the Database connection password
     * @see
     * @version 1.0
     * @since 1.0
     */
    public static String getDatabaseConnPassword() {
        StringBuilder dbConnPass = new StringBuilder(getDatabaseVendor())
                .append(ConfigurationConstants.DOT_CHARACTER).append(
                        ConfigurationConstants.DB_CONN_PASSWORD);
        Object connPassword = properties.get(dbConnPass.toString());

        if (connPassword == null) {
            LOGGER.error("Failed to get database connection password");
            return null;
        }
        return connPassword.toString();
    }

}
