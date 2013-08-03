package cn.clxy.ssm.common.dao.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.plugin.Invocation;

public class Handlers implements Handler {

	private List<Handler> handlers = new ArrayList<>();

	public Handlers() {
		handlers.add(new PaginationHandler());
		handlers.add(new AutoSqlHandler());
	}

	@Override
	public Object handle(Invocation invocation) throws Throwable {

		for (Handler handler : handlers) {
			Object result = handler.handle(invocation);
			if (result != null) {
				return result;
			}
		}

		return invocation.proceed();
	}
}
