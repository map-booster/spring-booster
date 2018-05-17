package com.coo.touchpoint.domain;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginRequest {
	
	@NotNull
	private String userId;

}
