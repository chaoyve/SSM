package cn.clxy.ssm.auth.dao;

import org.apache.ibatis.annotations.Select;

import cn.clxy.ssm.common.dao.BaseDao;
import cn.clxy.ssm.common.data.User;

public interface UserDao extends BaseDao {

	@Select("select * from user where user_id=#{userId} and password=#{password}")
	User find(Object condition);
}
