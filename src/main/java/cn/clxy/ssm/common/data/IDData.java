package cn.clxy.ssm.common.data;

import java.io.Serializable;

import cn.clxy.ssm.common.util.BeanUtil;

public class IDData implements Serializable {

	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return BeanUtil.hash(id);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		return BeanUtil.equal(((IDData) obj).getId(), id);
	}

	private static final long serialVersionUID = 1L;
}
