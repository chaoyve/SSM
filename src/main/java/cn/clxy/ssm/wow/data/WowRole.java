package cn.clxy.ssm.wow.data;

import cn.clxy.ssm.common.data.BaseData;
import java.io.Serializable;

/**
 * MyBatis Generatorより自動生成。<br>
 * テーブル： wow_role
 */
public class WowRole extends BaseData implements Serializable {
    private String name;

    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}