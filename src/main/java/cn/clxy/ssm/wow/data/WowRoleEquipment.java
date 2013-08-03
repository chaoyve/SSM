package cn.clxy.ssm.wow.data;

import cn.clxy.ssm.common.data.IDData;
import java.io.Serializable;

/**
 * MyBatis Generatorより自動生成。<br>
 * テーブル： wow_role_equipment
 */
public class WowRoleEquipment extends IDData implements Serializable {
    private Integer roleId;

    private Integer equipmentId;

    private static final long serialVersionUID = 1L;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }
}