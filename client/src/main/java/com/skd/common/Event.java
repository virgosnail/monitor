package com.skd.common;

public enum Event
{
	FILE_CREATE("1"),FILE_DELETE("0"),FILE_CHANGE("2");
	
	String type;
	Event(String type){
		this.type = type;
	}
	
	public String getValue(){
		return this.type;
	}

}
