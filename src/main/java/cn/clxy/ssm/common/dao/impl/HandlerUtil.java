package cn.clxy.ssm.common.dao.impl;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;

final class HandlerUtil {

	public static Object getParameter(Invocation invocation) {
		return invocation.getArgs()[1];
	}

	public static MappedStatement getStatement(Invocation invocation) {
		return (MappedStatement) invocation.getArgs()[0];
	}

	public static String getMethod(Invocation invocation) {
		String id = getStatement(invocation).getId();
		return id.substring(id.lastIndexOf('.'));
	}

	public static boolean isNotNullMethod(Invocation invocation) {
		String method = getMethod(invocation);
		return method.endsWith("NotNull");
	}

	private HandlerUtil() {
	}
}
