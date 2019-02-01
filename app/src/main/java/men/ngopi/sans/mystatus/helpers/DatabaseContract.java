package men.ngopi.sans.mystatus.helpers;

import android.provider.BaseColumns;

public class DatabaseContract {
    static final String COMMENT_TABLE = "comment_table";
    static final String POST_TABLE = "post_table";

    static final class CommentColumns implements BaseColumns {
        //CommentModel name
        static final String NAME = "name";
        //CommentModel comment
        static final String COMMENT = "comment";
        //CommentModel postId
        static final String POST_ID = "post_id";
    }

    static final class PostColumns implements BaseColumns {
        //PostModel name
        static final String NAME = "name";
        //PostModel post
        static final String POST = "post";
    }
}
