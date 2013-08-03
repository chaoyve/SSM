package cn.clxy.ssm.auth.action;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.clxy.ssm.auth.data.LoginData;
import cn.clxy.ssm.auth.service.UserService;
import cn.clxy.ssm.common.action.Input;
import cn.clxy.ssm.common.action.NoAuth;
import cn.clxy.ssm.common.data.ExcelModel;
import cn.clxy.ssm.common.data.User;
import cn.clxy.ssm.common.util.WebUtil;

/**
 * ログイン画面。
 * @author clxy
 */
@NoAuth
public class LoginAction {

	@Resource
	private UserService userService;

	@RequestMapping(value = "/")
	public ModelAndView index() {
		return new ModelAndView(input, formName, new LoginData());
	}

	@Input(input)
	@RequestMapping(value = "login*")
	public String submit(HttpServletRequest request,
			@ModelAttribute(value = formName) @Valid final LoginData data) {

		User user = userService.login(data);
		WebUtil.setUser(user);
		return "/";
	}

	@RequestMapping(value = "download*")
	public ModelAndView download() {

		ModelAndView mav = new ExcelModel("入庫通知");
		mav.addObject("user", new User(1, "a"));
		List<User> users = Arrays.asList(new User[] { new User(2, "あ"), new User(3, "い") });
		mav.addObject("users", users);

		return mav;
	}

	private static final String formName = "auth";
	private static final String input = "index";
}
