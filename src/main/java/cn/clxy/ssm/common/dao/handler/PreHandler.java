package cn.clxy.ssm.common.dao.handler;

import java.util.Date;

import org.apache.ibatis.plugin.Invocation;

import cn.clxy.ssm.common.data.BaseData;
import cn.clxy.ssm.common.data.User;
import cn.clxy.ssm.common.util.WebUtil;

public class PreHandler implements Handler {

	@Override
	public Object handle(Invocation invocation) throws Throwable {

		Object argParam = HandlerUtil.getParameter(invocation);
		if (!(argParam instanceof BaseData) || HandlerUtil.isNotNullMethod(invocation)) {
			return null;
		}

		BaseData data = (BaseData) argParam;
		Date now = new Date();
		if (data.getCreateAt() == null) {
			data.setCreateAt(now);
		}
		if (data.getUpdateAt() == null) {
			data.setUpdateAt(now);// TODO 做版本控制时该如何？
		}

		User user = WebUtil.getUser();
		if (user == null) {
			return null;
		}
		if (data.getCreateBy() == null) {
			data.setCreateBy(user.getId());
		}
		if (data.getUpdateBy() == null) {
			data.setUpdateBy(user.getId());
		}

		return null;
	}
}