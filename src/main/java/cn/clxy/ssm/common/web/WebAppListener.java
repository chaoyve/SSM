package cn.clxy.ssm.common.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebAppListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		String rootPath = sce.getServletContext().getRealPath("/");
		System.setProperty("webroot", rootPath);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
