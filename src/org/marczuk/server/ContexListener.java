package org.marczuk.server;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;

public class ContexListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

        try {
        	String realPath = servletContextEvent.getServletContext().getRealPath("/");
			FileUtils.deleteDirectory(new File(realPath + "/uploads"));
			new File(realPath + "/uploads").mkdir();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
