package cn.clxy.ssm.common.exception;

public class ServiceException extends BaseException {

	public ServiceException(String code, Object[] params) {
		super(code, params);
	}

	public ServiceException(String code, Object[] params, Throwable cause) {
		super(code, params, cause);
	}

	private static final long serialVersionUID = 1L;
}
