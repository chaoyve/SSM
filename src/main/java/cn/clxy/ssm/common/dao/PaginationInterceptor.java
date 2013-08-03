package cn.clxy.ssm.common.dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.clxy.ssm.common.dao.db.LimitOffsetDialect;
import cn.clxy.ssm.common.data.PaginationCondition;

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
@Deprecated
@Intercepts({ @Signature(
		type = Executor.class,
		method = "query",
		args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class PaginationInterceptor implements Interceptor {

	protected Dialect dialect;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		if (!preHandle(invocation)) {
			// 前处理失败时，中断。
			return Collections.EMPTY_LIST;
		}

		return invocation.proceed();
	}

	private boolean preHandle(Invocation invocation) throws SQLException {

		Object[] args = invocation.getArgs();
		final Object argParam = args[1];
		if (!(argParam instanceof PaginationCondition)) {
			// 如果参数不是分页条件，不处理。
			return true;
		}

		final MappedStatement statement = (MappedStatement) args[0];
		BoundSql originalBound = statement.getBoundSql(argParam);
		String originalSql = originalBound.getSql().trim();

		// 件数：select count。
		String countSql = "select count(1) from (" + originalSql + ") as auto_count";
		log.debug(countSql);
		int count = DaoUtil.getCount(statement, originalBound, countSql);
		if (count == 0) {
			// 结果为空时不再继续查询。
			return false;
		}
		PaginationCondition condition = (PaginationCondition) argParam;
		condition.setCount(count);

		// 分页：set limit and offset。
		String pageSql =
				dialect.getLimitString(originalSql, condition.getOffset(), condition.getLimit());
		log.debug(pageSql);
		args[0] = DaoUtil.createBy(statement, originalBound, pageSql);
		return true;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

		String dialectName = properties.getProperty("dialectName").toLowerCase();
		switch (dialectName) {
		case "mysql":
			dialect = new LimitOffsetDialect();
			break;
		default:
			throw new RuntimeException("Wrong dialect name: " + dialectName);
		}
	}

	private static final Logger log = LoggerFactory.getLogger(PaginationInterceptor.class);
}
