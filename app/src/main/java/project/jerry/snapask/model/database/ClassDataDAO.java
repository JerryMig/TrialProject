package project.jerry.snapask.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import project.jerry.snapask.model.data.ClassData;
import project.jerry.snapask.model.data.Person;
import project.jerry.snapask.model.data.Region;
import project.jerry.snapask.model.data.Role;
import project.jerry.snapask.model.data.Subject;

/**
 * Created by Migme_Jerry on 2017/5/7.
 */

public class ClassDataDAO extends BaseDAO {

    private static final String TABLE_CLASS = "class";
    private static final String TABLE_PERSON = "person";
    private static final String TABLE_SUBJECT = "subject";
    private static final String TABLE_ROLE = "role";
    private static final String TABLE_REGION = "region";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_ASKING_ID = "user_id";
    private static final String COLUMN_ANSWERING_ID = "answer_tutor_id";
    private static final String COLUMN_TIME = "created_at";
    private static final String COLUMN_RATING = "user_rating";
    private static final String COLUMN_PIC = "picture_url";
    private static final String COLUMN_PROFILE_PIC = "profile_pic_url";

    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USER_NAME = "username";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_ROLE_ID = "role_id";
    private static final String COLUMN_SUBJECT_ID = "subject_id";
    private static final String COLUMN_ABBREV = "abbr";
    private static final String COLUMN_REGION_ID = "region_id";

    public ClassDataDAO(Context context) {
        super(context);
    }

    @Override
    protected void createTable() {
        final String CREATE_TABLE_CLASS = "create table if not exists "
                + TABLE_CLASS
                + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_STATUS + " TEXT, "
                + COLUMN_ASKING_ID + " INTEGER, "
                + COLUMN_ANSWERING_ID + " INTEGER, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_SUBJECT_ID + " INTEGER, "
                + COLUMN_PIC + " TEXT, "
                + COLUMN_RATING + " INTEGER "
                + ");";

        final String CREATE_TABLE_PERSON = "create table if not exists "
                + TABLE_PERSON
                + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_PROFILE_PIC + " TEXT, "
                + COLUMN_ROLE_ID + " INTEGER "
                + ");";

        final String CREATE_TABLE_SUBJECT = "create table if not exists "
                + TABLE_SUBJECT
                + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_ABBREV + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_REGION_ID + " INTEGER "
                + ");";

        final String CREATE_TABLE_ROLE = "create table if not exists "
                + TABLE_ROLE
                + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME + " TEXT "
                + ");";

        final String CREATE_TABLE_REGION = "create table if not exists "
                + TABLE_REGION
                + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_FULL_NAME + " TEXT "
                + ");";

        execSQLWithCmd(CREATE_TABLE_CLASS);
        execSQLWithCmd(CREATE_TABLE_PERSON);
        execSQLWithCmd(CREATE_TABLE_SUBJECT);
        execSQLWithCmd(CREATE_TABLE_ROLE);
        execSQLWithCmd(CREATE_TABLE_REGION);
    }

    @Override
    protected String[] getTableName() {
        return new String[] {
                TABLE_CLASS,
                TABLE_PERSON,
                TABLE_SUBJECT,
                TABLE_ROLE,
                TABLE_REGION
        };
    }

    /**
     * ****************************
     * Beginning of class methods
     * ****************************
     */

    public synchronized boolean saveClassDataToDb(final List<ClassData> dataList) {
        boolean result = false;
        if (dataList != null && !dataList.isEmpty()) {
            result = doTransaction(new TransactionRunnable() {
                @Override
                public void run(SQLiteDatabase db) {
                    for (ClassData data : dataList) {
                        ContentValues values = new ContentValues();
                        values.put(COLUMN_ID, data.getId());
                        values.put(COLUMN_DESCRIPTION, data.getDescription());
                        values.put(COLUMN_STATUS, data.getStatus());
                        values.put(COLUMN_ASKING_ID, data.getAskingUserId());
                        Person askingOne = data.getAskingPerson();
                        if (askingOne != null) {
                            savePersonToDb(askingOne);
                        }
                        values.put(COLUMN_ANSWERING_ID, data.getAnsweringUserId());
                        Person answeringOne = data.getAnsweringPerson();
                        if (answeringOne != null) {
                            savePersonToDb(answeringOne);
                        }
                        Subject subject = data.getSubject();
                        if (subject != null) {
                            values.put(COLUMN_SUBJECT_ID, subject.getId());
                            saveSubjectToDb(subject);
                        }
                        values.put(COLUMN_TIME, data.getTime());
                        values.put(COLUMN_PIC, data.getClassPic());
                        values.put(COLUMN_RATING, data.getRating());

                        if (db.update(TABLE_CLASS, values, COLUMN_ID + " = ?"
                                , new String[] {String.valueOf(data.getId())}) <= 0) {
                            db.insertOrThrow(TABLE_CLASS, null, values);
                        }

                    }
                }
            });
        }
        return result;
    }

    public synchronized List<ClassData> loadClassesFromDb() {
        final String query = "SELECT * FROM " + TABLE_CLASS;
        List<ClassData> dataList = new ArrayList<>();
        Cursor cursor;
        SQLiteDatabase db = getDb();
        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ClassData classData = new ClassData();
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));
                int askingId = cursor.getInt(cursor.getColumnIndex(COLUMN_ASKING_ID));
                Person askingOne = loadPersonFromDb(askingId);
                int answeringId = cursor.getInt(cursor.getColumnIndex(COLUMN_ANSWERING_ID));
                Person answeringOne = loadPersonFromDb(answeringId);
                String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                String pic = cursor.getString(cursor.getColumnIndex(COLUMN_PIC));
                int subjectId = cursor.getInt(cursor.getColumnIndex(COLUMN_SUBJECT_ID));
                Subject subject = loadSubjectFromDb(subjectId);
                int rating = cursor.getInt(cursor.getColumnIndex(COLUMN_RATING));

                classData.setId(id);
                classData.setAskingPerson(askingOne);
                classData.setDescription(description);
                classData.setStatus(status);
                classData.setAskingUserId(askingId);
                classData.setAnsweringUserId(answeringId);
                classData.setAnsweringPerson(answeringOne);
                classData.setTime(time);
                classData.setSubject(subject);
                classData.setClassPic(pic);
                classData.setRating(rating);

                dataList.add(classData);
                cursor.moveToNext();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return dataList;
    }

    /**
     * ****************************
     * End of class methods
     * ****************************
     */

    /**
     * ****************************
     * Beginning of person methods
     * ****************************
     */

    private synchronized Person loadPersonFromDb(int id) {
        Person person = null;
        final String query = "SELECT * FROM " + TABLE_PERSON
                + " WHERE " + COLUMN_ID + " = " + id;
        Cursor cursor;
        SQLiteDatabase db = getDb();
        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            person = new Person();
            String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
            String username = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
            int roleId = cursor.getInt(cursor.getColumnIndex(COLUMN_ROLE_ID));
            Role role = loadRoleFromDb(roleId);
            String url = cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_PIC));

            person.setId(id);
            person.setEmail(email);
            person.setUserName(username);
            person.setRole(role);
            person.setProfilePic(url);
        }
        if (cursor != null) {
            cursor.close();
        }
        return person;
    }

    private synchronized boolean savePersonToDb(final Person data) {
        boolean result = false;
        if (data != null) {
            result = doTransaction(new TransactionRunnable() {
                @Override
                public void run(SQLiteDatabase db) {
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_ID, data.getId());
                    values.put(COLUMN_EMAIL, data.getEmail());
                    values.put(COLUMN_USER_NAME, data.getUserName());

                    Role role = data.getRole();
                    if (role != null) {
                        values.put(COLUMN_ROLE_ID, role.getId());
                        saveRoleToDb(role);
                    }

                    values.put(COLUMN_PROFILE_PIC, data.getProfilePic());
                    if (db.update(TABLE_PERSON, values, COLUMN_ID + " = ?"
                            , new String[] {String.valueOf(data.getId())}) <= 0) {
                        db.insertOrThrow(TABLE_PERSON, null, values);
                    }
                }
            });
        }
        return result;
    }

    /**
     * ****************************
     * End of person methods
     * ****************************
     */

    /**
     * ****************************
     * Beginning of subject methods
     * ****************************
     */

    private boolean saveSubjectToDb(final Subject data) {
        boolean result = false;
        if (data != null) {
            result = doTransaction(new TransactionRunnable() {
                @Override
                public void run(SQLiteDatabase db) {
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_ID, data.getId());
                    values.put(COLUMN_ABBREV, data.getAbbreviation());
                    values.put(COLUMN_DESCRIPTION, data.getDescription());
                    Region region = data.getRegion();
                    if (region != null) {
                        values.put(COLUMN_REGION_ID, region.getName());
                        saveRegionToDb(region);
                    }
                    if (db.update(TABLE_SUBJECT, values, COLUMN_ID + " = ?"
                            , new String[] {String.valueOf(data.getId())}) <= 0) {
                        db.insertOrThrow(TABLE_SUBJECT, null, values);
                    }
                }
            });
        }
        return result;
    }

    private Subject loadSubjectFromDb(int id) {
        Subject subject = null;
        final String query = "SELECT * FROM " + TABLE_SUBJECT
                + " WHERE " + COLUMN_ID + " = \'" + id + "\'";
        Cursor cursor;
        SQLiteDatabase db = getDb();
        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            subject = new Subject();
            String abbrev = cursor.getString(cursor.getColumnIndex(COLUMN_ABBREV));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            int regionId = cursor.getInt(cursor.getColumnIndex(COLUMN_REGION_ID));
            Region region = loadRegionFromDb(regionId);
            subject.setmId(id);
            subject.setAbbreviation(abbrev);
            subject.setDescription(description);
            subject.setRegion(region);
        }
        if (cursor != null) {
            cursor.close();
        }
        return subject;
    }

    /**
     * ****************************
     * End of subject methods
     * ****************************
     */

    /**
     * ***************************
     * Beginning of region methods
     * ***************************
     */

    private boolean saveRegionToDb(final Region data) {
        boolean result = false;
        if (data != null) {
            result = doTransaction(new TransactionRunnable() {
                @Override
                public void run(SQLiteDatabase db) {
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_ID, data.getId());
                    values.put(COLUMN_NAME, data.getName());
                    values.put(COLUMN_FULL_NAME, data.getFullName());
                    if (db.update(TABLE_REGION, values, COLUMN_ID + " = ?"
                            , new String[] {String.valueOf(data.getId())}) <= 0) {
                        db.insertOrThrow(TABLE_REGION, null, values);
                    }
                }
            });
        }
        return result;
    }

    private Region loadRegionFromDb(int id) {
        Region region = null;
        final String query = "SELECT * FROM " + TABLE_REGION
                + " WHERE " + COLUMN_ID + " = \'" + id + "\'";
        Cursor cursor;
        SQLiteDatabase db = getDb();
        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            region = new Region();
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String fullName = cursor.getString(cursor.getColumnIndex(COLUMN_FULL_NAME));
            region.setId(id);
            region.setName(name);
            region.setFullName(fullName);
        }
        if (cursor != null) {
            cursor.close();
        }
        return region;
    }

    /**
     * ***************************
     * End of region methods
     * ***************************
     */

    /**
     * *************************
     * Beginning of role methods
     * *************************
     */

    private boolean saveRoleToDb(final Role data) {
        boolean result = false;
        if (data != null) {
            result = doTransaction(new TransactionRunnable() {
                @Override
                public void run(SQLiteDatabase db) {
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_ID, data.getId());
                    values.put(COLUMN_NAME, data.getName());
                    if (db.update(TABLE_ROLE, values, COLUMN_ID + " = ?"
                            , new String[] {String.valueOf(data.getId())}) <= 0) {
                        db.insertOrThrow(TABLE_ROLE, null, values);
                    }
                }
            });
        }
        return result;
    }

    private Role loadRoleFromDb(int id) {
        Role role = null;
        final String query = "SELECT * FROM " + TABLE_ROLE
                + " WHERE " + COLUMN_ID + " = \'" + id + "\'";
        Cursor cursor;
        SQLiteDatabase db = getDb();
        cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            role = new Role();
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            role.setId(id);
            role.setName(name);
        }
        if (cursor != null) {
            cursor.close();
        }
        return role;
    }

    /**
     * *************************
     * End of role methods
     * *************************
     */

}
