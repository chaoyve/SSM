package cn.clxy.ssm.common.util;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import cn.clxy.ssm.common.exception.SystemException;

/**
 * Commons beanutilとSpringなどラッパーする。
 * @author clxy
 */
public final class BeanUtil {

	/**
	 * 封装commons。主要为了异常。
	 * @param obj
	 * @param name
	 * @return
	 */
	public static Object getProperty(Object obj, String name) {
		try {
			return PropertyUtils.getProperty(obj, name);
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Hash値を生成する。
	 * @param obj
	 * @return
	 */
	public static int hash(Object obj) {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((obj == null) ? 0 : obj.hashCode());
		return result;
	}

	/**
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean equal(Object src, Object dest) {

		if (src == dest) {
			return true;
		}

		if (src == null || dest == null) {
			return false;
		}

		return src.equals(dest);
	}

	/**
	 * 指定ObjectをCopyする。Deepではない。
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copy(T t) {

		T result = null;
		try {
			result = (T) t.getClass().newInstance();
		} catch (Exception e) {
			throw new SystemException(e);
		}

		copy(t, result);
		return result;
	}

	/**
	 * Object間をCopyする。Deepではない。
	 * @param from
	 * @param to
	 */
	@SuppressWarnings("unchecked")
	public static void copy(Object from, Object to) {

		try {
			if (to instanceof Map) {
				((Map<String, Object>) to).putAll(toMap(from));
			} else {
				BeanUtils.copyProperties(to, from);
			}
		} catch (Exception e) {
			throw new SystemException(e);
		}

	}

	/**
	 * Mapへ変換する。
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(Object bean) {

		if (bean instanceof Map) {
			return (Map<String, Object>) bean;
		}

		try {
			return (Map<String, Object>) PropertyUtils.describe(bean);
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private BeanUtil() {
	}
}
