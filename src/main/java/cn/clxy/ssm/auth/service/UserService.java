package cn.clxy.ssm.auth.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.clxy.ssm.auth.dao.UserDao;
import cn.clxy.ssm.auth.data.LoginData;
import cn.clxy.ssm.common.dao.UtilDao;
import cn.clxy.ssm.common.data.PaginationCondition;
import cn.clxy.ssm.common.data.User;
import cn.clxy.ssm.common.exception.ServiceException;
import cn.clxy.ssm.common.util.AppUtil;
import cn.clxy.ssm.common.util.BeanUtil;

@Service
public class UserService {

	@Resource
	private UserDao userDao;

	@Resource(name = "utilDao")
	private UtilDao utilDao;

	/**
	 * ログイン情報でユーザーを検索する。<br>
	 * パスワードを先に暗号化する。
	 * @param condition
	 * @return
	 */
	public User login(LoginData data) {

		// 入力を退避する。
		LoginData condition = BeanUtil.copy(data);

		String password = condition.getPassword();
		condition.setPassword(AppUtil.getMD5(password));

		PaginationCondition pc = new PaginationCondition();
		pc.setLimit(10);
		userDao.find(pc);

		User user = userDao.find(condition);
		if (user == null) {
			throw new ServiceException("exception.service.loginfailed", null);
		}

		return user;
	}

	/**
	 * 新しいユーザーを生成する。
	 * @param user
	 */
	public void create(User user) {

		user.setPassword(AppUtil.getMD5(user.getPassword()));
		utilDao.insert(user);
		userDao.insert(user);
	}
}
