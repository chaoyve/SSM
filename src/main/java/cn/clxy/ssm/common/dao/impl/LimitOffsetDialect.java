package cn.clxy.ssm.common.dao.impl;

import cn.clxy.ssm.common.dao.Dialect;

public class LimitOffsetDialect implements Dialect {

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		return sql + " limit " + offset + ", " + limit;
	}
}
