package cn.clxy.ssm.wow.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.clxy.ssm.wow.dao.WowDao;
import cn.clxy.ssm.wow.data.WowBoss;
import cn.clxy.ssm.wow.data.WowEquipment;
import cn.clxy.ssm.wow.data.WowRole;
import cn.clxy.ssm.wow.data.WowRoleEquipment;
import cn.clxy.ssm.wow.data.WowRoleWeek;
import cn.clxy.ssm.wow.data.WowWeekRow;

public class WowWeekService {

	@Resource
	private WowDao wowDao;

	public List<WowBoss> getAllBoss() {

		Map<String, WowBoss> bosses = new LinkedHashMap<>();

		for (WowEquipment equip : wowDao.findAllEquipment()) {

			String name = equip.getBoss();
			WowBoss boss = bosses.get(name);
			if (boss == null) {
				boss = new WowBoss(name);
				bosses.put(name, boss);
			}
			boss.add(equip);
		}

		return new ArrayList<WowBoss>(bosses.values());
	}

	public List<WowWeekRow> getWeekRow(int week) {

		// すべてキャラクタでMapを組み立て。
		List<WowRole> roles = wowDao.findAllRole();
		Map<Integer, WowWeekRow> map = new LinkedHashMap<>();
		for (WowRole role : roles) {
			map.put(role.getId(), new WowWeekRow(role));
		}

		// 取得済みをセット。
		List<WowRoleEquipment> gotEquips = wowDao.findAllGot();
		for (WowRoleEquipment equip : gotEquips) {
			WowWeekRow row = map.get(equip.getRoleId());
			row.addEquip(equip);
		}

		// 週データをセット。
		List<WowRoleWeek> weeks = wowDao.findAllWeek(week);
		for (WowRoleWeek w : weeks) {
			WowWeekRow row = map.get(w.getRoleId());
			row.addBoss(w.getBoss());
		}

		return new ArrayList<WowWeekRow>(map.values());
	}

	public void updateEquip(int rid, int eid, boolean isDelete) {

		Integer i = null;
		if (isDelete) {
			i = wowDao.deleteEquip(rid, eid);
		} else {
			WowRoleEquipment equip = new WowRoleEquipment();
			equip.setRoleId(rid);
			equip.setEquipmentId(eid);
			i = wowDao.insertEquip(equip);
		}

		if (log.isDebugEnabled()) {
			log.debug("-------== updateEquip " + (isDelete ? "delete" : "update") + " " + i);
		}
	}

	public void updateWeek(WowRoleWeek week, boolean isDelete) {

		Integer i = null;
		if (isDelete) {
			i = wowDao.deleteWeek(week);
		} else {
			i = wowDao.insertWeek(week);
		}

		if (log.isDebugEnabled()) {
			log.debug("-------== updateWeek " + (isDelete ? "delete" : "update") + " " + i);
		}
	}

	private static final Logger log = LoggerFactory.getLogger(WowWeekService.class);
}
