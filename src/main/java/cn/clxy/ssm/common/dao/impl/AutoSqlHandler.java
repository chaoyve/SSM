package cn.clxy.ssm.common.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.springframework.util.StringUtils;

import cn.clxy.ssm.common.dao.Handler;

public class AutoSqlHandler implements Handler {

	@Override
	public Object handle(Invocation invocation) throws Throwable {

		Object[] args = invocation.getArgs();
		final MappedStatement statement = (MappedStatement) args[0];
		String id = statement.getId();
		if (!methods.contains("auto." + id)) {
			// 如果该方法不处理则stop。
			return null;
		}

		Object argParam = args[1];
		BoundSql originalBound = statement.getBoundSql(argParam);
		if (!StringUtils.isEmpty(originalBound.getSql().trim())) {
			// 如果子类用指定SQL覆盖该方法则Stop。
			return null;
		}

		Class clazz = argParam.getClass();
		Map<String, String> sqls = null;
		synchronized (caches) {
			if (caches.containsKey(clazz)) {
				sqls = caches.get(clazz);
			} else {
				sqls = new HashMap<String, String>();
				caches.put(clazz, sqls);
			}
		}
		String sql = sqls.get(id);
		return null;
	}

	private static final Set<String> methods = new HashSet<String>() {
		{
			addAll(Arrays.asList("insert", "insertNotNull", "update"));
		}
	};
	private static final Map<Class, Map<String, String>> caches = new HashMap<>();
}