package com.navi.interact.tools.wrapper;

import com.navi.interact.tools.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.*;

public class DynamicSQLWrapper extends Wrapper {

    private static final Logger LOGGER = LogManager.getLogger(DynamicSQLWrapper.class);

    private Statement stmt;

    // Default constructor
    public DynamicSQLWrapper() throws Exception {
        stmt = connection.createStatement();
    }

    public DataRecord[] executeDynamicQuery(ArrayList<String> columns, Enum findUsingEnumValue, String dynamicClause) throws Exception {

        HashMap<String, String> tables = new HashMap<String, String>();
        StringBuffer columnNames = new StringBuffer();
        for (int i=0; i<columns.size(); i++) {
            String column = columns.get(i);
            StringTokenizer st = new StringTokenizer(column,".");
            String tableName = st.nextToken();
            tables.put(tableName, tableName);
            columnNames.append(column);
            if (i+1<columns.size()) {
                columnNames.append(",");
            }
        }

        Iterator<String> itr = tables.keySet().iterator();
        StringBuffer tableNames = new StringBuffer();
        while (itr.hasNext()) {
            tableNames.append(itr.next());
            if (itr.hasNext()) {
                tableNames.append(",");
            }
        }

        String findUsing = null;
        if (findUsingEnumValue.equals(FindUsing.WHERE)) {
            findUsing = DBUtils.WHERE;
        } else if (findUsingEnumValue.equals(FindUsing.INNER_JOIN)) {
            findUsing = DBUtils.INNER_JOIN;
        } else if (findUsingEnumValue.equals(FindUsing.OUTER_JOIN)) {
            findUsing = DBUtils.OUTER_JOIN;
        }
        String sql = "SELECT "+columnNames.toString()+" FROM "+tableNames.toString()+" "+findUsing+" "+dynamicClause+";";
        LOGGER.debug(sql);
        ResultSet rset = stmt.executeQuery(sql);
        return getResults(columns, rset);
    }

    private DataRecord[] getResults(ArrayList<String> columns, ResultSet rset) throws Exception {

        ArrayList<DataRecord> dataRecordList = new ArrayList<DataRecord>();
        while(rset.next()) {
            DataRecord dr = new DataRecord(rset.getRow());
            ResultSetMetaData metadata = rset.getMetaData();
            LOGGER.debug("Number of Columns: "+metadata.getColumnCount());
            for (int i=1; i<= metadata.getColumnCount(); i++) {
                LOGGER.debug(metadata.getColumnClassName(i));
                if (metadata.getColumnClassName(i).equals("java.lang.String")) {
                    dr.add(columns.get(i-1),rset.getString(i));
                } else if (metadata.getColumnClassName(i).equals("java.lang.Integer")) {
                    dr.add(columns.get(i-1),new Integer(rset.getInt(i)));
                } else if (metadata.getColumnClassName(i).equals("java.lang.Boolean")) {
                    dr.add(columns.get(i-1),rset.getBoolean(i));
                } else if (metadata.getColumnClassName(i).equals("java.sql.Date")) {
                    dr.add(columns.get(i-1),rset.getDate(i));
                } else if (metadata.getColumnClassName(i).equals("java.math.BigDecimal")) {
                    dr.add(columns.get(i-1),rset.getBigDecimal(i));
                }
            }
            dataRecordList.add(dr);
        }

        DataRecord[] records = new DataRecord[dataRecordList.size()];
        for (int i=0; i<dataRecordList.size(); i++) {
            records[i] = dataRecordList.get(i);
        }
        return records;
    }

}
