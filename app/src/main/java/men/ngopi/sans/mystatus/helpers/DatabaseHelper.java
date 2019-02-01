package men.ngopi.sans.mystatus.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static men.ngopi.sans.mystatus.helpers.DatabaseContract.*;
import static men.ngopi.sans.mystatus.helpers.DatabaseContract.CommentColumns.*;
import static men.ngopi.sans.mystatus.helpers.DatabaseContract.PostColumns.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "mystatus";
    public final static String CREATE_COMMENT_TABLE = "CREATE TABLE " + COMMENT_TABLE + " ( "
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + POST_ID + " INTEGER NOT NULL, "
            + CommentColumns.NAME + " TEXT NOT NULL, "
            + COMMENT + " TEXT NOT NULL UNIQUE, "
            + "FOREIGN KEY (" + POST_ID + ") REFERENCES " + POST_TABLE + "(" + _ID + "));";
    public final static String CREATE_POST_TABLE = "CREATE TABLE " + POST_TABLE + " ( "
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PostColumns.NAME + " TEXT NOT NULL, "
            + POST + " TEXT NOT NULL);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_POST_TABLE);
        db.execSQL(CREATE_COMMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COMMENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE);
        onCreate(db);
    }
}
