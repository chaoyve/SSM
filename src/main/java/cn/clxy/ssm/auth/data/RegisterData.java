package cn.clxy.ssm.auth.data;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@FieldMatch.List({ @FieldMatch(first = "password", second = "confirmPassword"
		/* , message = "{auth.confirmPassword.error}"可以指定message */) })
public class RegisterData extends LoginData {

	private String confirmPassword;

	@NotBlank()
	private String firstName;
	private String lastName;

	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date birthday;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
