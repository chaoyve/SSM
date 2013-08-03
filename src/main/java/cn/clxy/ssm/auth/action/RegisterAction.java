package cn.clxy.ssm.auth.action;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.clxy.ssm.auth.data.RegisterData;
import cn.clxy.ssm.auth.service.UserService;
import cn.clxy.ssm.common.action.Input;
import cn.clxy.ssm.common.action.NoAuth;
import cn.clxy.ssm.common.data.User;
import cn.clxy.ssm.common.util.BeanUtil;

/**
 * 登録画面。
 * @author clxy
 */
@NoAuth
public class RegisterAction {

	@Resource
	private UserService userService;

	@RequestMapping(value = "register*")
	public ModelAndView index(ModelAndView view) {
		return new ModelAndView(input, formName, new RegisterData());
	}

	@Input("forward:" + input)
	@RequestMapping(value = "doRegister*")
	public String submit(@ModelAttribute(value = formName) @Valid final RegisterData form) {

		User user = new User();
		BeanUtil.copy(form, user);
		userService.create(user);

		return input;
	}

	private static final String formName = "auth";
	private static final String input = "register";
}
