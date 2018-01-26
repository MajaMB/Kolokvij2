package hr.math.kolokvij2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mabaruk on 1/26/18.
 */

public class DBAdapter {
    static final String KEY_ROWID = "ID";
    static final String KEY_DATE = "DATUM";
    static final String KEY_TERMIN = "TERMIN";
    static final String KEY_DOGADJAJ = "DOGADJAJ";
    static final String KEY_idvrste = "ID_VRSTE";
    static final String KEY_KATEGORIJA = "KATEGORIJA";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "ROKOVNIK";
    static final String DATABASE_TABLE2 = "VRSTA";

    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
            "create table ROKOVNIK (ID integer primary key autoincrement, "
                    + "DATUM text not null, TERMIN text not null, DOGADJAJ text not null);";


    static final String DATABASE_CREATE2 =
            "create table VRSTA (ID_VRSTE integer primary key autoincrement, "
                    + "KATEGORIJA integer not null, ID integer not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
                db.execSQL(DATABASE_CREATE2);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading db from" + oldVersion + "to"
                    + newVersion );
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a ZAPIS into the database---
    public long insertZapis(String datum, String termin,String dogadaj)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATE, datum);
        initialValues.put(KEY_TERMIN, termin);
        initialValues.put(KEY_DOGADJAJ, dogadaj);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---insert a vrsta into the database---
    public long insertVrsta(int kategorija,int _id)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_KATEGORIJA, kategorija);
        initialValues.put(KEY_ROWID, _id);
        return db.insert(DATABASE_TABLE2, null, initialValues);
    }


    //---deletes a particular zapis---
    public boolean deleteZapis(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---deletes a particular vrsta---
    public boolean deleteVrsta(long rowId)
    {
        return db.delete(DATABASE_TABLE2, KEY_idvrste + "=" + rowId, null) > 0;
    }

    //---retrieves all the Zapise---
    public Cursor getAllZapis()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_DATE,
                KEY_TERMIN,KEY_DOGADJAJ}, null, null, null, null, null);
    }

    public Cursor getAllVrsta()
    {
        return db.query(DATABASE_TABLE2, new String[] {KEY_idvrste, KEY_KATEGORIJA,
                KEY_ROWID}, null, null, null, null, null);
    }


    //---retrieves a particular Zapis---
    public Cursor getZapis(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_DATE, KEY_TERMIN,KEY_DOGADJAJ}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //---retrieves a particular Vrsta---
    public Cursor getVrsta(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {KEY_idvrste,
                                KEY_KATEGORIJA, KEY_ROWID}, KEY_idvrste + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //---updates a Zapis---
    public boolean updateZapis(long rowId, String datum, String termin,String dogadaj)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_DATE, datum);
        args.put(KEY_TERMIN, termin);
        args.put(KEY_DOGADJAJ, dogadaj);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---updates a Vrsta---
    public boolean updateVrsta(long rowId, String kategorija,int _id)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_KATEGORIJA, kategorija);
        args.put(KEY_ROWID, _id);
        return db.update(DATABASE_TABLE2, args, KEY_idvrste + "=" + rowId, null) > 0;
    }

    //---retrieves a particular Zapis by date---
    public Cursor getZapisByDate(String datum) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_DATE, KEY_TERMIN,KEY_DOGADJAJ}, KEY_DATE + "=" + datum, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}
