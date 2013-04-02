package com.eflake.christina;

import java.util.HashMap;

import com.eflake.provider.DbHelper;
import com.eflake.provider.Model;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class EflakeProvider extends ContentProvider {
	private DbHelper dbHelper;
	private static final UriMatcher sUriMatcher;
	private static final int ITEM = 1;
	private static final int ITEM_ID = 2;
	private static HashMap<String,String>qureyProjection;
	
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(Model.AUTHORITY, "item",	ITEM);
		sUriMatcher.addURI(Model.AUTHORITY, "item/#", ITEM_ID);
		qureyProjection = new HashMap<String, String>();
		qureyProjection.put(Model.DIARY_ID,Model.DIARY_ID);
		qureyProjection.put(Model.DIARY_TITLE,Model.DIARY_TITLE);
		qureyProjection.put(Model.DIARY_DESCRIPTION,Model.DIARY_DESCRIPTION);
		qureyProjection.put(Model.DIARY_PIC_URL,Model.DIARY_PIC_URL);
		qureyProjection.put(Model.DIARY_CONTENT,Model.DIARY_CONTENT);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count = 0;
		switch (sUriMatcher.match(uri)) {
		case  ITEM:
			count = db.delete(DbHelper.ITEMS_TABLE_NAME, selection, selectionArgs);
			break;
		case  ITEM_ID:
			String itemId = uri.getPathSegments().get(1);
			count = db.delete(DbHelper.ITEMS_TABLE_NAME, Model._ID + "=" + itemId + (!TextUtils.isEmpty(selection) ? "AND (" + selection +')' :"") , selectionArgs);
			break;
		default:
			break;
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
		
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insert(DbHelper.ITEMS_TABLE_NAME, Model.DIARY_TITLE,values);
		if (rowId >0) {
			Uri itemUri = ContentUris.withAppendedId(Model.CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(itemUri, null);
			Log.d("insert complete","insert complete");
			return itemUri;
		}
		return null;
	}
	

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (sUriMatcher.match(uri)) {
		case ITEM:	
			qb.setTables(DbHelper.ITEMS_TABLE_NAME);
			qb.setProjectionMap(qureyProjection);
			break;
		case ITEM_ID:	
			qb.setTables(DbHelper.ITEMS_TABLE_NAME);
			qb.setProjectionMap(qureyProjection);
			qb.appendWhere(Model._ID + "=" + uri.getPathSegments().get(1));
			break;
		default:
			throw new IllegalArgumentException("Uri´íÎó" + uri);
		}
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, null);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

}
