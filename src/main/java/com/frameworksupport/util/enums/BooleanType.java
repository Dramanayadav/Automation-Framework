package com.frameworksupport.util.enums;

public enum BooleanType {
	  TRUE("true"),
	  FALSE("false");
	  
	  String booleanType;
	  
	  BooleanType(String booleanType) {
	    this.booleanType = booleanType;
	  }
	  
	  public String toString() {
	    return this.booleanType;
	  }
	}