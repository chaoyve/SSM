package cn.clxy.ssm.wow.data;

import cn.clxy.ssm.common.data.BaseData;
import java.io.Serializable;

/**
 * MyBatis Generatorより自動生成。<br>
 * テーブル： wow_equipment
 */
public class WowEquipment extends BaseData implements Serializable {
    private String boss;

    private String name;

    private static final long serialVersionUID = 1L;

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}