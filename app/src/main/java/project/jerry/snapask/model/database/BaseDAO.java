package project.jerry.snapask.model.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Migme_Jerry on 2017/5/7.
 */

abstract class BaseDAO {

    private static final String TAG = "BaseDAO";
    private static final Object mTransactionLock = new Object();

    // This may ne shared among DAOs
    static protected DbHelper mDbHelper = null;

    protected Context mContext;
    protected String[] mTableName;

    public interface TransactionRunnable {
        public void run(SQLiteDatabase db);
    }

    protected abstract void createTable();

    public BaseDAO(final Context appCtx) {
        mContext = appCtx;
        mTableName = getTableName();
        if (mDbHelper == null) {
            mDbHelper = new DbHelper(mContext, mTableName);
            mDbHelper.getWritableDatabase();
        }
        dropTables();
        createTable();
    }

    private void dropTables() {
        if (mDbHelper.shouldUpgrade()) {
            for (String tableName : mTableName) {
                final String query = "DROP TABLE " + tableName + ";";
                try {
                    execSQLWithCmd(query);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    protected abstract String[] getTableName();

    public boolean execSQLWithCmd(final String sqlCommand) {
        return doTransaction(new TransactionRunnable() {
            @Override
            public void run(SQLiteDatabase db) {
                db.execSQL(sqlCommand);
            }
        });
    }

    public boolean doTransaction(TransactionRunnable runnable) {
        boolean result = false;
        if (runnable == null) {
            throw new IllegalArgumentException("TransactionRunnable can't be null");
        }

        final SQLiteDatabase db = getDb();
        if (db != null) {
            synchronized (mTransactionLock) {
                try {
                    db.beginTransaction();
                    runnable.run(db);
                    db.setTransactionSuccessful();
                    result = true;
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                    Log.d(TAG, "doTransaction: SQLException occurred");
                }
                finally {
                    db.endTransaction();
                }
            }
        }
        return result;
    }

    protected SQLiteDatabase getDb() {
        return mDbHelper.getDb();
    }

}
