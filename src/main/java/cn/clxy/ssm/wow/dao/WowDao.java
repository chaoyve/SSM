package cn.clxy.ssm.wow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.clxy.ssm.wow.data.WowEquipment;
import cn.clxy.ssm.wow.data.WowRole;
import cn.clxy.ssm.wow.data.WowRoleEquipment;
import cn.clxy.ssm.wow.data.WowRoleWeek;

public interface WowDao {

	/**
	 * 所有角色。
	 * @return
	 */
	@Select("select * from wow_role")
	List<WowRole> findAllRole();

	/**
	 * 所有装备。
	 * @return
	 */
	@Select("select * from wow_equipment")
	List<WowEquipment> findAllEquipment();

	/**
	 * 已获得装备。
	 * @return
	 */
	@Select("select * from wow_role_equipment")
	List<WowRoleEquipment> findAllGot();

	/**
	 * 指定周的击杀。
	 * @param week
	 * @return
	 */
	@Select("select * from wow_role_week where kill_week = #{week} ")
	List<WowRoleWeek> findAllWeek(@Param("week") Integer week);

	/**
	 * 删除一件已获得装备。
	 * @param rid
	 * @param eid
	 */
	int deleteEquip(int rid, int eid);

	/**
	 * 装备追加。
	 * @param equip
	 * @return
	 */
	int insertEquip(WowRoleEquipment equip);

	/**
	 * 删除击杀记录。
	 * @param week
	 * @return
	 */
	int deleteWeek(WowRoleWeek week);

	/**
	 * 追加击杀记录。
	 * @param week
	 * @return
	 */
	int insertWeek(WowRoleWeek week);
}
