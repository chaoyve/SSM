package cn.clxy.ssm.entry.action;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 入口。
 * @author clxy
 */
public class IndexAction {

	@RequestMapping()
	public String index() {
		return "/entry/index";
	}
}
