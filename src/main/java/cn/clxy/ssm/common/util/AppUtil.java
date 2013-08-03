package cn.clxy.ssm.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;

import cn.clxy.ssm.common.exception.SystemException;

/**
 * アプリケーション共通ツール。
 * @author clxy
 */
public class AppUtil {

	/**
	 * MD5ハッシュを取得する。
	 * @param string
	 * @return
	 */
	public static String getMD5(String string) {

		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(string.getBytes());
			byte[] digest = md.digest();
			for (int i = 0; i < digest.length; i++) {
				if ((0xff & digest[i]) < 0x10) {
					sb.append("0" + Integer.toHexString((0xFF & digest[i])));
				} else {
					sb.append(Integer.toHexString(0xFF & digest[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			throw new SystemException(e);
		}

		return sb.toString();
	}

	/**
	 * パッケージと親を沿って、Annotationを探す。
	 * @param p
	 * @param clazz
	 * @return
	 */
	public static <T extends Annotation> T find(Package p, Class<T> clazz) {

		if (p == null) {
			return null;
		}

		T result = p.getAnnotation(clazz);
		if (result != null) {
			return result;
		}

		return find(PackageUtil.getParent(p), clazz);
	}

	/**
	 * メソッドのAnnotationを探す。ない場合、定義クラスも探す。<br>
	 * クラス自分だけ親など捜さない。
	 * @see AnnotationUtils#findAnnotation(Method, Class)
	 * @param m
	 * @param clazz
	 * @return
	 */
	public static <T extends Annotation> T find(Method m, Class<T> clazz) {

		T result = m.getAnnotation(clazz);
		if (result != null) {
			return result;
		}
		return m.getDeclaringClass().getAnnotation(clazz);
	}

	public static <T extends Annotation> T find(HandlerMethod hm, Class<T> clazz) {
		return find(hm.getMethod(), clazz);
	}

}
