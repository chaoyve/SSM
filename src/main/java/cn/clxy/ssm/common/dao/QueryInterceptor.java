package cn.clxy.ssm.common.dao;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import cn.clxy.ssm.common.dao.db.LimitOffsetDialect;

/**
 * 自动分页拦截器。
 * <ul>
 * <li>是否分页取决于查询参数是否是PaginationCondition类。</li>
 * <li>先查询总件数，如果总件数为0，不再继续查询下去。</li>
 * <li>完全无视MyBatis的RowBounds。不可并用！</li>
 * </ul>
 * 参考自https://github.com/yfyang/mybatis-pagination
 * @author clxy
 */
@Intercepts({ @Signature(
		type = Executor.class,
		method = "query",
		args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class QueryInterceptor implements Interceptor {

	protected Dialect dialect;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		return DaoUtil.handle(invocation);
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

		/**
		 * @TODO move to PaginationHandler
		 */
		String dialectName = properties.getProperty("dialectName").toLowerCase();
		switch (dialectName) {
		case "mysql":
			dialect = new LimitOffsetDialect();
			break;
		default:
			throw new RuntimeException("Wrong dialect name: " + dialectName);
		}
	}
}
