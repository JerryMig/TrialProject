package project.jerry.snapask.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Migme_Jerry on 2017/5/7.
 */

class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DataBaseReader.db";

    private int mOldVersion = DATABASE_VERSION;
    private int mNewVersion = DATABASE_VERSION;

    private String[] mTableName;

    DbHelper(Context context, String[] tableName) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mTableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mNewVersion = newVersion;
        mOldVersion = oldVersion;
        for (String table : mTableName) {
            db.execSQL("DROP TABLE IF EXISTS " + table);
        }
        onCreate(db);
    }

    public void finalize() {
        this.close();
        try {
            super.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public SQLiteDatabase getDb() {
        return this.getWritableDatabase();
    }

    public boolean shouldUpgrade() {
        return (mNewVersion > mOldVersion);
    }
}
