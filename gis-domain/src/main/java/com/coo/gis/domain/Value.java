package com.coo.gis.domain;

import lombok.Data;

@Data
public class Value {
	
	public Value() {
		
	}
	
	public Value(String lang, String val) {
		this.lang = lang;
		this.val = val;
	}
	
	private String lang;
	
	private String val;
}
