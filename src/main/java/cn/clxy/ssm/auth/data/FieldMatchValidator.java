package cn.clxy.ssm.auth.data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderDefinedContext;

import org.apache.commons.beanutils.BeanUtils;

import cn.clxy.ssm.common.util.BeanUtil;

/**
 * 2つのフィルドの一致をチェックする。
 * @author clxy
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

	private String firstFieldName;
	private String secondFieldName;

	@Override
	public void initialize(final FieldMatch constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {

		try {
			final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
			final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

			boolean isValid = BeanUtil.equal(firstObj, secondObj);
			if (!isValid) {
				// 重新构建message。
				String message = context.getDefaultConstraintMessageTemplate();
				context.disableDefaultConstraintViolation();
				ConstraintViolationBuilder violationBuilder =
						context.buildConstraintViolationWithTemplate(message);
				// 追加属性名字。
				NodeBuilderDefinedContext nbdc = violationBuilder.addNode(secondFieldName);
				nbdc.addConstraintViolation();
			}

			return isValid;
		} catch (final Exception ignore) {
			// ignore
		}
		return true;
	}
}