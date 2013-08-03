package cn.clxy.ssm.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 起動が全部終了後関連処理。
 * @author clxy
 */
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(StartupListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("============-----------------------After initialized.");
	}
}
