package com.skd.client.common;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 17:06
 */
public enum EventType
{
	/**
	 * 删除文件
	 */
	FILE_DELETE("0"),
	/**
	 * 新增文件
	 */
	FILE_CREATE("1"),
	/**
	 * 修改文件
	 */
	FILE_CHANGE("2");

	String type;
	EventType(String type){
		this.type = type;
	}
	
	public String getValue(){
		return this.type;
	}

}
