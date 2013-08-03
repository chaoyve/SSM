package cn.clxy.ssm.common.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import cn.clxy.ssm.common.AppConfig;
import cn.clxy.ssm.common.action.ActionHandler;
import cn.clxy.ssm.common.action.Input;
import cn.clxy.ssm.common.action.Layout;
import cn.clxy.ssm.common.exception.SystemException;

/**
 * 処理メソッド関連ツール。<br>
 * 高速化するため、起動時すべてメソッドを登録する。<br>
 * @see ActionHandler#getMappingForMethod
 * @author clxy
 */
public final class ActionUtil {

	/**
	 * 追加package路径。
	 * <ul>
	 * <li>如果有指定forward:或redirect:，取后面的字符串。
	 * <li>如果是以/开头认定是绝对路径。不处理。
	 * <li>追加package路径。
	 * </ul>
	 * 转向jsp时，不可以加forward:或redirect:！！！
	 * @param method
	 * @param path
	 * @return
	 */
	public static String addPackageUrl(HandlerMethod method, String path) {
		return addPackageUrl(ActionUtil.getPackageUrl(method), path);
	}

	/**
	 * 追加package路径。
	 * <ul>
	 * <li>如果有指定forward:或redirect:，取后面的字符串。
	 * <li>如果是以/开头认定是绝对路径。不处理。
	 * <li>追加package路径。
	 * </ul>
	 * 转向jsp时，不可以加forward:或redirect:！！！
	 * @param packageUrl
	 * @param path
	 * @return
	 */
	static String addPackageUrl(String packageUrl, String path) {

		if (packageUrl == null) {
			return path;
		}

		String url = path;
		// 接頭辞を切り取る。
		String prefix = WebUtil.getPrefix(url);
		url = url.substring(prefix.length());

		// 绝对路径不处理。
		String slash = AppConfig.SLASH;
		if (url.startsWith(slash)) {
			return path;
		}

		url = packageUrl + slash + url;// 追加package路径。
		url = prefix + url;// 接頭辞を戻す。

		return url;
	}

	/**
	 * 保存Package信息。
	 * @param method
	 */
	public static void savePackageUrl(HandlerMethod method) {

		String packageUrl = ActionUtil.getPackageUrl(method);
		if (packageUrl == null) {
			return;
		}
		getAttributes().setAttribute(
				AppConfig.KEY_PACKAGE_URL, packageUrl, RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * 入力元をRequestに保存する。
	 * @param method
	 */
	public static void saveCurrentInput(HandlerMethod method) {
		String input = getInput(method);
		getAttributes().setAttribute(
				AppConfig.KEY_INPUT, input, RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * 入力元を取得する。
	 * @return
	 */
	public static String getCurrentInput() {
		return (String) getAttributes().getAttribute(
				AppConfig.KEY_INPUT, RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * 処理メソッドを登録する。
	 * @param object
	 */
	public static void register(Object object) {

		Method method = null;
		if (object instanceof Method) {
			method = (Method) object;
		} else if (object instanceof HandlerMethod) {
			method = ((HandlerMethod) object).getMethod();
		} else {// ありえない。
			throw new SystemException();
		}

		if (actionMappings.containsKey(method)) {
			return;
		}

		actionMappings.put(method, new ActionInfo(method));
	}

	/**
	 * 寻找RequestMapping的value值。如果为空则使用package的末段名。
	 * @param p
	 * @return
	 */
	public static String[] getRequestMappingValue(Package p) {

		RequestMapping rm = getRequestMapping(p);
		if (rm == null) {
			return null;
		}

		String[] value = rm.value();
		if (value != null && value.length > 0) {
			return value;
		}

		return new String[] { PackageUtil.getShortName(p) };
	}

	/**
	 * メソッドのパッケージURLを取得する。
	 * @param method
	 * @return
	 */
	public static String getPackageUrl(HandlerMethod method) {
		return actionMappings.get(method.getMethod()).packageUrl;
	}

	/**
	 * メソッドの入力元を取得する。
	 * @param method
	 * @return
	 */
	public static String getInput(HandlerMethod method) {
		return actionMappings.get(method.getMethod()).input;
	}

	/**
	 * メソッドのレイアウトを取得する。
	 * @param method
	 * @return
	 */
	public static Layout getLayout(HandlerMethod method) {
		return actionMappings.get(method.getMethod()).layout;
	}

	/**
	 * 沿着父包，寻找RequestMapping这个annotation。
	 * @param p
	 * @return
	 */
	public static RequestMapping getRequestMapping(Package p) {

		if (packageMappings.containsKey(p)) {
			return packageMappings.get(p);
		}

		RequestMapping rm = AppUtil.find(p, RequestMapping.class);
		packageMappings.put(p, rm);
		return rm;
	}

	/**
	 * 简化写法。
	 * @return
	 */
	private static ServletRequestAttributes getAttributes() {
		return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	}

	/**
	 * 処理メソッドの関連情報。
	 * @author clxy
	 */
	private static class ActionInfo {

		/**
		 * パッケージのURL。
		 */
		public String packageUrl;

		/**
		 * 入力元。
		 */
		public String input;

		/**
		 * レイアウト情報。
		 */
		public Layout layout;

		public ActionInfo(Method method) {
			initPackageUrl(method);
			initInput(method);
			initLayout(method);
		}

		private void initPackageUrl(Method method) {
			String[] values = getRequestMappingValue(method.getDeclaringClass().getPackage());
			if (values == null) {
				return;
			}
			packageUrl = values[0];
		}

		private void initInput(Method method) {
			Input a = AppUtil.find(method, Input.class);
			if (a == null) {
				return;
			}
			input = ActionUtil.addPackageUrl(packageUrl, a.value());
		}

		private void initLayout(Method method) {
			layout = AppUtil.find(method, Layout.class);
		}
	}

	/**
	 * パッケージ情報のケッシュ。起動時処理中心なのでMulti-Thread未対応。
	 */
	private static final Map<Package, RequestMapping> packageMappings = new HashMap<>();
	/**
	 * メソッド情報のケッシュ。起動時処理中心なのでMulti-Thread未対応。
	 */
	private static final Map<Method, ActionInfo> actionMappings = new HashMap<>();

	private ActionUtil() {
	}
}
