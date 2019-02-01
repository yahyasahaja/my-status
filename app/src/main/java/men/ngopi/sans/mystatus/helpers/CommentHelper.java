package men.ngopi.sans.mystatus.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import men.ngopi.sans.mystatus.MainActivity;
import men.ngopi.sans.mystatus.models.CommentModel;
import men.ngopi.sans.mystatus.models.CommentModel;
import men.ngopi.sans.mystatus.models.PostModel;

import static android.provider.BaseColumns._ID;
import static men.ngopi.sans.mystatus.helpers.DatabaseContract.COMMENT_TABLE;
import static men.ngopi.sans.mystatus.helpers.DatabaseContract.CommentColumns.*;

public class CommentHelper {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private static CommentHelper instance;

    public static CommentHelper getInstance() {
        return instance;
    }

    public CommentHelper() {
        instance = this;
        context = MainActivity.getInstance();
    }

    public CommentHelper(Context context) {
        instance = this;
        this.context = context;
    }

    public CommentHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public CommentHelper close() {
        databaseHelper.close();
        return this;
    }

    public ArrayList<CommentModel> fetchCommentsByPostId(int postId) {
        Cursor cursor = database.query(
                COMMENT_TABLE,
                null,
                POST_ID + " = ?",
                new String[]{ "" + postId },
                null,
                null,
                _ID + " DESC",
                null
        );
        return fetchComment(cursor);
    }

    public CommentModel fetchCommentsById(int id) {
        Cursor cursor = database.query(
                COMMENT_TABLE,
                null,
                _ID + " = ?",
                new String[]{ "" + id },
                null,
                null,
                _ID + " DESC",
                null
        );
        return fetchComment(cursor).get(0);
    }

    public ArrayList<CommentModel> fetchAllComments() {
        Cursor cursor = database.query(
                COMMENT_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null
        );
        return fetchComment(cursor);
    }

    public ArrayList<CommentModel> fetchComment(Cursor cursor) {
        ArrayList<CommentModel> result = new ArrayList<CommentModel>();
        CommentModel CommentModel;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                CommentModel = new CommentModel();
                CommentModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                CommentModel.setPostId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                CommentModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                CommentModel.setComment(cursor.getString(cursor.getColumnIndexOrThrow(COMMENT)));
                result.add(CommentModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        return result;
    }

    public long insert(CommentModel commentModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(POST_ID, commentModel.getPostId());
        initialValues.put(NAME, commentModel.getName());
        initialValues.put(COMMENT, commentModel.getComment());
        Log.d("comment", commentModel.getPostId() + "");
        return database.insert(COMMENT_TABLE, null, initialValues);
    }

    public int update(CommentModel commentModel) {
        ContentValues args = new ContentValues();
        args.put(POST_ID, commentModel.getPostId());
        args.put(NAME, commentModel.getName());
        args.put(COMMENT, commentModel.getComment());
        Log.d("post id 2", "" + commentModel.getPostId());
        return database.update(
                COMMENT_TABLE,
                args,
                _ID + " = '" + commentModel.getId() + "'",
                null
        );
    }


    public int delete(int id) {
        return database.delete(COMMENT_TABLE, _ID + " = '" + id + "'", null);
    }

}
