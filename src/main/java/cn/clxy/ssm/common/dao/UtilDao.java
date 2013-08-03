package cn.clxy.ssm.common.dao;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import cn.clxy.ssm.common.data.IDData;

/**
 * 工具Dao类。提供通常的insert，update，delete by id和select all 等功能。
 * @author clxy
 * @param <T>
 */
public interface UtilDao {

	/**
	 * Insert。注意，Null值也会被insert。
	 * @param object
	 * @return
	 */
	public int insert(Object object);

	/**
	 * Update。条件是id和update时间（如果存在的话）。注意，Null值也会被更新。
	 * @param object
	 * @return
	 */
	public int update(IDData object);

	/**
	 * Update。条件是id和update时间（如果存在的话）。注意，Null值不会被更新。
	 * @param object
	 * @return
	 */
	public int updateNotNull(IDData object);

	/**
	 * Delete。条件是id和update时间（如果存在的话）。
	 * @param object
	 * @return
	 */
	public int deleteById(IDData object);

	/**
	 * 无条件选择所有。
	 * @param clazz
	 * @return
	 */
	public List<T> findAll(Class<T> clazz);
}
