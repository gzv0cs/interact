package com.navi.interact.tools.wrapper;

import com.navi.interact.tools.factory.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;

/**
 * Created by mevan on 30/05/2016.
 */
public class Wrapper extends BaseWrapper {

    protected Connection connection;
    private static final Logger LOGGER = LogManager.getLogger(Wrapper.class);

    public Wrapper() throws Exception {
        connection = ConnectionFactory.getInstance().getConnection();
    }

}
