package org.marczuk.server;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.io.FileUtils;

import com.google.gwt.user.client.Window;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {	}
 
    public void sessionDestroyed(HttpSessionEvent event) {

    	System.out.println(Window.getTitle());
        System.out.println("Session Destroyed: " + event.getSession().getId());
        
        try {
			FileUtils.deleteDirectory(new File("uploads/" + event.getSession().getId()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
