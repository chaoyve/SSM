package cn.clxy.ssm.wow.data;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class WowBoss implements Serializable {

	private String name;
	private Set<WowEquipment> equips = new LinkedHashSet<>();

	public WowBoss() {
	}

	public WowBoss(String name) {
		this.name = name;
	}

	public int getEquipCount() {
		return equips.size();
	}

	public void add(WowEquipment equip) {
		equips.add(equip);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<WowEquipment> getEquips() {
		return equips;
	}

	private static final long serialVersionUID = 1L;
}
