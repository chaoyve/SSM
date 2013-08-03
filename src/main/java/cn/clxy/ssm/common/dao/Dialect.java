package cn.clxy.ssm.common.dao;

public interface Dialect {
	String getLimitString(String sql, int offset, int limit);
}
