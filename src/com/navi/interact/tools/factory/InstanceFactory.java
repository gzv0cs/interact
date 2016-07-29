package com.navi.interact.tools.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by mevan on 25/05/2016.
 */
public class InstanceFactory {

    private ApplicationContext context;

    private static InstanceFactory singleton = null;

    private InstanceFactory() throws Exception {
        context = new ClassPathXmlApplicationContext("Beans.xml");
    }

    public static InstanceFactory getInstance() throws Exception {
        if (singleton == null) {
            singleton = new InstanceFactory();
        }
        return singleton;
    }

    public Object getEntity(String beanId) throws Exception {
        return context.getBean(beanId);
    }

    public static void main(String[] args) throws Exception {
        InstanceFactory factory = InstanceFactory.getInstance();
        Object object = factory.getEntity("Person");

        System.out.println(object.getClass().getName());
    }

}
