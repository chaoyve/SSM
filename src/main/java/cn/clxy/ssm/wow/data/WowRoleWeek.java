package cn.clxy.ssm.wow.data;

import cn.clxy.ssm.common.data.IDData;
import java.io.Serializable;

/**
 * MyBatis Generatorより自動生成。<br>
 * テーブル： wow_role_week
 */
public class WowRoleWeek extends IDData implements Serializable {
    private Integer roleId;

    private String boss;

    private Integer killWeek;

    private static final long serialVersionUID = 1L;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public Integer getKillWeek() {
        return killWeek;
    }

    public void setKillWeek(Integer killWeek) {
        this.killWeek = killWeek;
    }
}