package cn.clxy.ssm.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.poi.ss.formula.functions.T;

/**
 * Not this way , sir!
 * @author clxy
 */
@Deprecated
public interface BaseDao {

	@Insert("auto.insert into")
	void insert(Object object);

	@Select("auto.select all")
	List<T> findAll(Class<T> clazz);
}
