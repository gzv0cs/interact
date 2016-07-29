package com.navi.interact.tools.entity;

import org.w3c.dom.Node;

/**
 * Created by mevan on 30/05/2016.
 */
public class EntityProperty {

    private String name;
    private String type;
    private int length = 0;
    private String format;
    private boolean pkey;
    private boolean index;
    private boolean sequence;
    private boolean notnull;
    private String defaultValue;
    private String fkey;
    private String precision;
    private String scale;

    public EntityProperty(Node node) {
        if (node != null) {
            name = node.getAttributes().getNamedItem("name").getNodeValue();
            type = node.getAttributes().getNamedItem("type").getNodeValue();

            if (type.toLowerCase().equals("numeric")) {
                type = "double";
            }

            if (type.toLowerCase().equals("string") && (node.getAttributes().getNamedItem("length") != null)) {
                length = Integer.parseInt(node.getAttributes().getNamedItem("length").getNodeValue());
            }
            if (node.getAttributes().getNamedItem("pkey") != null ) {
                pkey = node.getAttributes().getNamedItem("pkey").getNodeValue().equals("true");
            }
            if (node.getAttributes().getNamedItem("index") != null ) {
                index = node.getAttributes().getNamedItem("index").getNodeValue().equals("true");
            }
            if (node.getAttributes().getNamedItem("sequence") != null ) {
                sequence = node.getAttributes().getNamedItem("sequence").getNodeValue().equals("true");
            }
            if (node.getAttributes().getNamedItem("notnull") != null ) {
                notnull = node.getAttributes().getNamedItem("notnull").getNodeValue().equals("true");
            }
            if (node.getAttributes().getNamedItem("default") != null ) {
                defaultValue = node.getAttributes().getNamedItem("default").getNodeValue();
            }
            if (node.getAttributes().getNamedItem("fkey") != null ) {
                fkey = node.getAttributes().getNamedItem("fkey").getNodeValue();
            }
            if (node.getAttributes().getNamedItem("precision") != null ) {
                precision = node.getAttributes().getNamedItem("precision").getNodeValue();
            }
            if (node.getAttributes().getNamedItem("scale") != null ) {
                scale = node.getAttributes().getNamedItem("scale").getNodeValue();
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public String getFormat() {
        return format;
    }

    public boolean isPkey() {
        return pkey;
    }

    public boolean isIndex() {
        return index;
    }

    public boolean isSequence() {
        return sequence;
    }

    public boolean isNotNull() {
        return notnull;
    }

    public String getDefauktValue() { return defaultValue; }

    public String getFKey() { return fkey; }

    public String getPrecision() { return precision; }

    public String getScale() { return scale; }

}
