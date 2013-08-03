package cn.clxy.ssm.common.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.util.StringUtils;

@Intercepts({ @Signature(
		type = Executor.class,
		method = "update",
		args = { MappedStatement.class, Object.class }) })
public class UpdateInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		return DaoUtil.handle(invocation);
	}

	private void preHandle(Invocation invocation) throws SQLException {

		Object[] args = invocation.getArgs();
		final MappedStatement statement = (MappedStatement) args[0];
		String id = statement.getId();
		if (!methods.contains(id)) {
			// 如果该方法不处理则stop。
			return;
		}

		Object argParam = args[1];
		BoundSql originalBound = statement.getBoundSql(argParam);
		if (!StringUtils.isEmpty(originalBound.getSql().trim())) {
			// 如果子类用指定SQL覆盖该方法则Stop。
			return;
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
	}

	protected static final Set<String> methods = new HashSet<>();
	protected static final Map<Class, Map<String, String>> caches = new HashMap<>();

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		String className = BaseDao.class.getName() + ".";
		methods.add(className + "insert");
		methods.add(className + "update");
		methods.add(className + "updateNotNull");
		methods.add(className + "deleteBy");
		methods.add(className + "findAll");
	}

	static class DataInfo {

		public String tableName;
		public String insert;
		public String update;
		public String findAll;
		public String deleteBy;

		public DataInfo(Class clazz) {

		}
	}
}
