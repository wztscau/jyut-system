package com.jyut.system.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jyut.system.C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Sqlite数据库的管理工具
 * @author wztscau
 * @date 10/7/2016
 * @project 粤盟管理系统客户端
 */

public class DBHelper extends SQLiteOpenHelper {


    private static final String TAG = "DBHelper";
    private final String dbName;

    public DBHelper(Context context, String name) {
        this(context, name, null, 1);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i(TAG, "DBHelper: constructor");
        this.dbName = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate: ");
        String tableName = C.L.MESSAGE;

        //sqlite中的字段和字符串是严格区分大小写的
        String sql = "create table " + tableName + " (" + C.L._ID + " integer primary key autoincrement," + C.L.TITLE + " varchar(100)," + C.L.CONTENT + " text," + C.L.DATE + " date," + C.L.READED + " boolean )";
        Log.d(TAG, "onCreate: sql--"+sql);
        // 如果表不存在才创建表
        if (!isTableExist(tableName)) {
            db.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            onCreate(db);
        }
    }

    public List<Map<String, String>> queryAll(String tableName, String orderBy) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = null;
        List<Map<String, String>> list = new ArrayList<>();
        try {
            // 开启事务
            database.beginTransaction();
            cursor = database.query(tableName, null, null, null, null, null, orderBy);

            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<>();
                String content = cursor.getString(cursor.getColumnIndex(C.L.CONTENT));
                String title = cursor.getString(cursor.getColumnIndex(C.L.TITLE));
                String date = cursor.getString(cursor.getColumnIndex(C.L.DATE));
                String readed = cursor.getString(cursor.getColumnIndex(C.L.READED));

                map.put(C.L.TITLE, title);
                map.put(C.L.CONTENT, content);
                map.put(C.L.DATE, date);
                map.put(C.L.READED, readed);
                list.add(map);
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "queryAll: " + e.getMessage());
        } finally {
            // 最后要结束事务和关闭数据库连接
            database.endTransaction();
            database.close();
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public boolean insert(String tableName, Map<String, String> map) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            database.beginTransaction();
            // 插入用建筑队方式比较好
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                values.put(entry.getKey(),entry.getValue());
            }
            database.insert(tableName, null, values);
        } finally {
            database.endTransaction();
            database.close();
        }
        return true;
    }

    /**
     * 删除数据表里面表的数据,暂时不实现
     * @param tableName 表名
     * @return 是否删除成功
     */
    public boolean delete(String tableName){
        SQLiteDatabase database = getWritableDatabase();
        try{
            database.beginTransaction();
//            database.de


            database.endTransaction();
        }finally {
            database.close();
        }
        return true;
    }

    /**
     * 因为sqlite里面没有create table if not exist的方法,
     * 所以只能手动添加检测表是否存在的方法
     * @param tableName 表名
     * @return 表是否存在
     */
    private boolean isTableExist(String tableName) {
        if (tableName == null) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = getReadableDatabase();

            String sql = "select count(*) from " + tableName ;
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                // 有一条便可为true
                if (count > 0) {
                    return true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, "isTableExist: "+e.getMessage() );
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }
}
