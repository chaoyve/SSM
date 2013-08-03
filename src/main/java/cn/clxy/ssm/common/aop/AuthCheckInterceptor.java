package cn.clxy.ssm.common.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.clxy.ssm.common.action.NoAuth;
import cn.clxy.ssm.common.data.User;
import cn.clxy.ssm.common.util.AppUtil;
import cn.clxy.ssm.common.util.WebUtil;

/**
 * ログインか否かをチェックする。<br>
 * @author clxy
 */
public class AuthCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {

		User user = WebUtil.getUser();
		if (user != null && user.isLogined()) {
			// ログイン済み＝OK。
			return true;
		}

		HandlerMethod method = (HandlerMethod) handler;
		if (AppUtil.find(method, NoAuth.class) != null) {
			// ログイン必要ない場合OK。
			return true;
		}

		boolean isAjax = WebUtil.isAjax(request);
		if (isAjax) {// Ajax
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		} else {
			response.sendRedirect(request.getContextPath() + "/auth/");
		}

		return false;
	}
}
