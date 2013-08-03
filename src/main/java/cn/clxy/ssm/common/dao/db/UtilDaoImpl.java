package cn.clxy.ssm.common.dao.db;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.formula.functions.T;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.clxy.ssm.common.dao.UtilDao;
import cn.clxy.ssm.common.data.IDData;
import cn.clxy.ssm.common.util.BeanUtil;

public class UtilDaoImpl extends SqlSessionDaoSupport implements UtilDao {

	/**
	 * Insert。注意，Null值也会被insert。
	 * @param object
	 * @return
	 */
	public int insert(Object object) {
		String sql = getSqlInfo(object.getClass()).insert;
		return getSqlSession().insert(sql, object);
	}

	/**
	 * Update。条件是id和update时间（如果存在的话）。注意，Null值也会被更新。
	 * @param object
	 * @return
	 */
	public int update(IDData object) {

		String sql = getSqlInfo(object.getClass()).update;
		sql += getUpdateAt(object);
		log.debug(sql);
		return getSqlSession().update(sql, object);
	}

	/**
	 * Update。条件是id和update时间（如果存在的话）。注意，Null值不会被更新。
	 * @param object
	 * @return
	 */
	public int updateNotNull(IDData object) {

		SqlInfo si = getSqlInfo(object.getClass());
		String sql = "update " + si.tableName + " set ";

		List<String> updates = new ArrayList<>();
		for (Entry<String, String> e : si.updateMap.entrySet()) {
			if (BeanUtil.getProperty(object, e.getKey()) != null) {
				updates.add(e.getValue());
			}
		}

		sql += StringUtils.collectionToCommaDelimitedString(updates);
		sql = sql + idCondition + getUpdateAt(object);
		log.debug(sql);
		return getSqlSession().update(sql, object);
	}

	/**
	 * Delete。条件是id和update时间（如果存在的话）。
	 * @param object
	 * @return
	 */
	public int deleteById(IDData object) {
		String sql = getSqlInfo(object.getClass()).deleteById;
		return getSqlSession().delete(sql, object);
	}

	/**
	 * 无条件选择所有。
	 * @param clazz
	 * @return
	 */
	public List<T> findAll(Class<T> clazz) {
		String sql = getSqlInfo(clazz).findAll;
		return getSqlSession().selectList(sql);
	}

	/**
	 * 取得SQL信息。<br>
	 * 无需synchronized，因为不会造成错误。
	 * @param clazz
	 * @return
	 */
	private SqlInfo getSqlInfo(Class<?> clazz) {

		SqlInfo result = caches.get(clazz);
		if (result == null) {
			result = createSqlInfo(clazz);
			caches.put(clazz, result);
		}
		return result;
	}

	/**
	 * SQL文数据类。
	 * @author clxy
	 */
	static class SqlInfo {
		public String tableName;
		public String findAll;
		public String deleteById;
		public String insert;
		public String update;
		public Map<String, String> updateMap;
	}

	/**
	 * 创建SQL文。
	 * @param clazz
	 * @return
	 */
	private SqlInfo createSqlInfo(Class<?> clazz) {

		SqlInfo si = new SqlInfo();
		String className = clazz.getSimpleName();
		String tableName = toDBString(className);

		si.tableName = tableName;
		si.findAll = "select * from " + tableName;
		si.deleteById = "delete from " + tableName + idCondition;

		boolean isIdData = clazz.isAssignableFrom(IDData.class);
		PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(clazz);
		List<String> cols = new ArrayList<>();
		List<String> vals = new ArrayList<>();
		Map<String, String> updateMap = new HashMap<>();

		for (PropertyDescriptor prop : props) {

			if (prop.getReadMethod() == null) {
				continue;
			}

			String name = prop.getName();
			if ("class".equals(name) || ("id".equals(name) && isIdData)) {
				continue;
			}
			String col = toDBString(name);
			String val = toValueString(name);
			cols.add(col);
			vals.add(val);
			updateMap.put(name, col + " = " + val);
		}

		si.insert = "insert into "
				+ tableName + " (" + StringUtils.collectionToCommaDelimitedString(cols) + ") "
				+ "values (" + StringUtils.collectionToCommaDelimitedString(vals) + ")";

		si.update = "update " + tableName + " set "
				+ StringUtils.collectionToCommaDelimitedString(updateMap.values()) + idCondition;
		si.updateMap = updateMap;

		return si;
	}

	/**
	 * 追加更新时间。
	 * @param obj
	 * @return
	 */
	private String getUpdateAt(Object obj) {
		if (BeanUtil.getProperty(obj, "updateAt") == null) {
			return "";
		}
		return " and updateAt = #{updateAt}";
	}

	/**
	 * 追加下划线。注意，大小写没有整理。
	 * @param str
	 * @return
	 */
	private String toDBString(String str) {
		boolean hasUnderScore = getSqlSession().getConfiguration().isMapUnderscoreToCamelCase();
		return hasUnderScore ? str.replaceAll("([a-z])([A-Z])", "$1_$2") : str;
	}

	/**
	 * 追加MyBatis格式。
	 * @param str
	 * @return
	 */
	private String toValueString(String str) {
		return "#{" + str + "}";
	}

	private static final String idCondition = " where id=#{id}";
	private static final Map<Class<?>, SqlInfo> caches = new HashMap<>();
	private static final Logger log = LoggerFactory.getLogger(UtilDao.class);
}
