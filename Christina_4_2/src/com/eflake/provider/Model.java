package com.eflake.provider;

import android.net.Uri;

public class Model {
	//content provider常量
	public static final String AUTHORITY = "com.eflake.provider.items";
	public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/item");
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.eflake.provider.items";
	public static final String CONTENT_ITEM_TYPE ="vnd/android.cursor.item/vnd.eflake.provider.items";
	//数据库中字段常量
	public static final String _ID = "_id";
	public static final String DIARY_ID = "diary_id";
	public static final String DIARY_TITLE = "diary_title";
	public static final String DIARY_DESCRIPTION = "diary_description";
	public static final String DIARY_PIC_URL = "diary_pic_url";
	public static final String DIARY_CONTENT = "diary_content";

	public Model() {
	}
	
}
