package com.navi.interact.tools.factory;

import com.navi.interact.tools.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Created by mevan on 25/05/2016.
 */
public class ConnectionFactory {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionFactory.class);

    private Properties prop;
    private Connection connection;

    private static ConnectionFactory singleton = null;

    private ConnectionFactory() throws Exception {
        prop = DBUtils.loadDynamicDBProperties(new Properties());
        Class.forName(prop.getProperty(PropertyKey.DB_DRIVER_KEY));
        connection = DriverManager.getConnection(
                prop.getProperty(PropertyKey.DB_CONNECTION_PREFIX_KEY)+
                prop.getProperty(PropertyKey.DB_SERVER_KEY)+":"+
                prop.getProperty(PropertyKey.DB_PORT_KEY)+"/"+
                prop.getProperty(PropertyKey.DB_NAME_KEY),
                prop.getProperty(PropertyKey.DB_USER_KEY),
                prop.getProperty(PropertyKey.DB_PASS_KEY));
        LOGGER.debug("Established db connection "+connection.getMetaData().toString());
    }

    public static ConnectionFactory getInstance() throws Exception {
        if (singleton == null) {
            singleton = new ConnectionFactory();
        }
        return singleton;
    }

    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = ConnectionFactory.getInstance();
    }

}
