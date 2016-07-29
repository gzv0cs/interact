package com.navi.interact.tools.factory;

/**
 * Created by mevan on 14/06/2016.
 */
public interface PropertyKey {

    String PROJECT_NAME = "";

    String DYNAMICDB_PROPERTIES_FILE = PROJECT_NAME+"DynamicDB.property";

    String SRC_FOLDER_KEY = "source_folder";
    String ENTITY_NAMES_XML_KEY = "entity_names_xml";
    String ENTITY_BUILD_PKG_KEY = "entity_build_pkg";
    String WRAPPER_BUILD_PKG_KEY = "wrapper_build_pkg";
    String SCRIPT_SRC_FOLDER_KEY = "script_src_folder";

    String CREATE_TABLE_SCRIPT = "create_table_script";
    String DELETE_TABLE_SCRIPT = "delete_table_script";

    String BEANS_XML_KEY="beans_xml";

    String DB_SERVER_KEY = "db.server";
    String DB_DRIVER_KEY = "db.driver";
    String DB_CONNECTION_PREFIX_KEY = "db.connection.prefix";
    String DB_PORT_KEY = "db.port";
    String DB_NAME_KEY = "db.name";
    String DB_USER_KEY = "db.user";
    String DB_PASS_KEY = "db.pass";

    String LOG_PROPERTIES_FILE = "log4j2.filename";
}
