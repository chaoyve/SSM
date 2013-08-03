package cn.clxy.ssm.common.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * システム例外。
 */
@NoAuth
@Layout(noNeed = true)
public class SystemExceptionAction {

	@RequestMapping(value = "systemException*")
	public Object index(HttpServletRequest request) {
		return "systemError";
	}
}
