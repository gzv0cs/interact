package com.navi.interact.tools.util;

import com.navi.interact.tools.entity.EntityProperty;

import com.navi.interact.tools.factory.PropertyKey;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by mevan on 31/05/2016.
 */
public class DBUtils {

    public static final String SELECT = " SELECT ";
    public static final String FROM = " FROM ";
    public static final String ON = " ON ";

    public static final String INNER_JOIN = "INNER JOIN ";
    public static final String OUTER_JOIN = " OUTER JOIN ";
    public static final String WHERE = " WHERE ";

    public static final String AND = " and ";
    public static final String OR = " or ";
    public static final String EQUAL = "=";
    public static final String NOTEQUAAL = "!=";
    public static final String LIKE = " like ";
    public static final String IN = " in ";

    private static final String METHOD_TYPE_GET = "get";
    private static final String METHOD_TYPE_IS = "is";
    private static final String METHOD_TYPE_SET = "set";

    static final String LOG_PROPERTIES_FILE = "Log4j2.xml";

    public static String getMethodName(boolean isGet, EntityProperty eProp) {
        String camelCasePropertyNAme = eProp.getName().substring(0,1).toUpperCase()+eProp.getName().substring(1);
        if (isGet) {
            if (eProp.getType().toLowerCase().equals("boolean")) {
                return METHOD_TYPE_IS + camelCasePropertyNAme;
            } else {
                return METHOD_TYPE_GET + camelCasePropertyNAme;
            }
        } else {
            return METHOD_TYPE_SET+camelCasePropertyNAme;
        }
    }

    public static String getCamelCaseWord(String propertyName) {
        return propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
    }

    public static void loadLog4j2Configuration() throws Exception {
        Properties prop =  DBUtils.loadDynamicDBProperties(new Properties());
        DOMConfigurator.configure(prop.getProperty(PropertyKey.LOG_PROPERTIES_FILE));
    }

    public static Properties loadDynamicDBProperties(Properties prop) throws Exception {
        prop.load(new FileInputStream(PropertyKey.DYNAMICDB_PROPERTIES_FILE));
        return prop;
    }

}
