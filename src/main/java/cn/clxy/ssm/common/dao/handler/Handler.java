package cn.clxy.ssm.common.dao.handler;

import org.apache.ibatis.plugin.Invocation;

public interface Handler {

	Object handle(Invocation invocation) throws Throwable;
}
