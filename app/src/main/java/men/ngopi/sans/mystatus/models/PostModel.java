package men.ngopi.sans.mystatus.models;
import java.util.ArrayList;

import men.ngopi.sans.mystatus.helpers.CommentHelper;
import men.ngopi.sans.mystatus.helpers.PostHelper;

public class PostModel {
    private int id;
    private String name;
    private String post;
    private ArrayList<CommentModel> comments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public ArrayList<CommentModel> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentModel> comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<CommentModel> fetchComments() {
        return this.comments = CommentHelper.getInstance().fetchCommentsByPostId(id);
    }
}
