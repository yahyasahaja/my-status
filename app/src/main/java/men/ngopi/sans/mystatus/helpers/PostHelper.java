package men.ngopi.sans.mystatus.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import men.ngopi.sans.mystatus.MainActivity;
import men.ngopi.sans.mystatus.models.PostModel;
import men.ngopi.sans.mystatus.models.CommentModel;

import static android.provider.BaseColumns._ID;
import static men.ngopi.sans.mystatus.helpers.DatabaseContract.POST_TABLE;
import static men.ngopi.sans.mystatus.helpers.DatabaseContract.PostColumns.*;

public class PostHelper {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private static PostHelper instance;

    public static PostHelper getInstance() {
        return instance;
    }

    public PostHelper() {
        instance = this;
        context = MainActivity.getInstance();
    }

    public PostHelper(Context context) {
        instance = this;
        this.context = context;
    }

    public PostHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public PostHelper close() {
        databaseHelper.close();
        return this;
    }

    public PostModel fetchPostsById(int id) {
        Cursor cursor = database.query(
                POST_TABLE,
                null,
                _ID + " = ?",
                new String[]{ "" + id },
                null,
                null,
                _ID + " DESC",
                null
        );
        return fetchPost(cursor).get(0);
    }

    public ArrayList<PostModel> fetchAllPosts() {
        return this.fetchAllPosts(null);
    }

    public ArrayList<PostModel> fetchAllPosts(String search) {
        String selection = null;
//        String[] selectionArgs;

        if (search != null) {
            if (search.length() > 0) {
                selection = NAME + " LIKE '%" + search + "%' OR " + POST + " LIKE '%" + search + "%'";
            }
        }

        Cursor cursor = null;
        try {
            database.beginTransaction();
            cursor = database.query(
                POST_TABLE,
                null,
                selection,
                null,
                null,
                null,
                _ID + " DESC",
                null
            );
            database.setTransactionSuccessful();
        } catch (SQLiteException err) {
            Log.e("Fetching Post", err.toString());
        }
        database.endTransaction();

        return fetchPost(cursor);
    }

    public ArrayList<PostModel> fetchPost(Cursor cursor) {
        ArrayList<PostModel> result = new ArrayList<PostModel>();
        PostModel postModel;

        if (cursor.getCount() > 0) {
//            CommentHelper.getInstance().open();
            cursor.moveToFirst();
            do {
                postModel = new PostModel();
                postModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                postModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                postModel.setPost(cursor.getString(cursor.getColumnIndexOrThrow(POST)));
//                postModel.fetchComments();
                result.add(postModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
//            CommentHelper.getInstance().close();
        }

        return result;
    }

    public long insert(PostModel postModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME, postModel.getName());
        initialValues.put(POST, postModel.getPost());
        return database.insert(POST_TABLE, null, initialValues);
    }

    public int update(PostModel postModel) {
        ContentValues args = new ContentValues();
        args.put(NAME, postModel.getName());
        args.put(POST, postModel.getPost());
//        Log.d("ciduk?", "" + postModel.getId());
        return database.update(
                POST_TABLE,
                args,
                _ID + " = '" + postModel.getId() + "'",
                null
        );
    }


    public int delete(int id) {
        return database.delete(POST_TABLE, _ID + " = '" + id + "'", null);
    }

}
