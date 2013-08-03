package cn.clxy.ssm.auth.data;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class LoginData {

	@NotBlank()
	private String userId;

	@NotBlank
	@Size(min = 8, max = 25)
	private String password;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
