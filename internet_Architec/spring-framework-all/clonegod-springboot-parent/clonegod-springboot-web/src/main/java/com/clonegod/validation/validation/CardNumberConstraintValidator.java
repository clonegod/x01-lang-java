package com.clonegod.validation.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.clonegod.validation.constraints.ValidCardNumber;

public class CardNumberConstraintValidator implements ConstraintValidator<ValidCardNumber, String> {

	@Override
	public void initialize(ValidCardNumber constraintAnnotation) {
		System.out.println("使用@ValidCardNumber进行参数校验");
	}

	/**
	 * 校验规则：
	 * 	前缀必须是：clonegod
	 * 	后缀必须是：数字
	 * 
	 * @param value
	 * @param context
	 * @return
	 */
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		String[] parts = StringUtils.split(value, "-");
		
		if(ArrayUtils.getLength(parts) != 2) {
			return false;
		}
		
		String prefix = parts[0];
		
		String suffix = parts[1];
		
		boolean isValidPrefix = Objects.equals(prefix, "clonegod");
		
		boolean isValidSuffix =  StringUtils.isNumeric(suffix);
		
		return isValidPrefix &&  isValidSuffix ;
	}

}
