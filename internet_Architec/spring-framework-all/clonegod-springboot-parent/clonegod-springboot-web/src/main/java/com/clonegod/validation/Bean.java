package com.clonegod.validation;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.clonegod.validation.constraints.ValidCardNumber;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bean {
	
	@Max(value = 100)
	private long id;
	
	@NotNull
	private String name;
	
	@NotNull
	@ValidCardNumber(message="卡号必须以 clonegod 开头，以数字结尾")
//	@ValidCardNumber
	private String cardNumber;
	
	
	
}
