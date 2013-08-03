package cn.clxy.ssm.wow.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class WowWeekRow implements Serializable {

	private WowRole role;

	private Set<Integer> equips = new HashSet<>();

	private Set<String> bosses = new HashSet<>();

	public WowWeekRow() {
	}

	public WowWeekRow(WowRole role) {
		this.role = role;
	}

	public void addEquip(WowRoleEquipment equip) {
		equips.add(equip.getEquipmentId());
	}

	public boolean hasEquip(Integer equipId) {
		return equips.contains(equipId);
	}

	public void addBoss(String boss) {
		bosses.add(boss);
	}

	public boolean hasBoss(String boss) {
		return bosses.contains(boss);
	}

	public WowRole getRole() {
		return role;
	}

	public void setRole(WowRole role) {
		this.role = role;
	}

	private static final long serialVersionUID = 1L;
}
