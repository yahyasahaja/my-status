package men.ngopi.sans.mystatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import men.ngopi.sans.mystatus.adapters.CommentAdapter;
import men.ngopi.sans.mystatus.helpers.CommentHelper;
import men.ngopi.sans.mystatus.models.CommentModel;
import men.ngopi.sans.mystatus.models.PostModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PostDetailActivity extends AppCompatActivity implements View.OnClickListener {
    TextView nameTxt;
    TextView postTxt;
    FloatingActionButton addBtn;
    PostModel data;
    ArrayList<CommentModel> comments;
    TextView noCommentTxt;
    static PostDetailActivity instance;
    private CommentAdapter commentAdapter;
    private RecyclerView commentRecyclerView;
    LinearLayout listContainer;

    public static PostDetailActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        instance = this;

        listContainer = findViewById(R.id.list_container);
        this.nameTxt = findViewById(R.id.name_txt);
        this.postTxt = findViewById(R.id.post_txt);
        this.addBtn = findViewById(R.id.add_btn);
        this.noCommentTxt = findViewById(R.id.no_comment_txt);
        commentAdapter = new CommentAdapter();
        commentRecyclerView = findViewById(R.id.comment_recycler_view);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        data = new PostModel();
        data.setId(getIntent().getIntExtra("id", 0));
        data.setName(getIntent().getStringExtra("name"));
        data.setPost(getIntent().getStringExtra("post"));

        nameTxt.setText(data.getName());
        postTxt.setText(data.getPost());

        addBtn.setOnClickListener(this);
//        this.fetchComments();
    }

    public void fetchComments() {
        CommentHelper.getInstance().open();
        this.comments = data.fetchComments();
        CommentHelper.getInstance().close();

        if (comments.size() == 0) {
            noCommentTxt.setVisibility(View.VISIBLE);
            listContainer.setVisibility(View.GONE);
        } else {
            noCommentTxt.setVisibility(View.GONE);
            listContainer.setVisibility(View.VISIBLE);
            commentAdapter.setData(this.comments);
            commentRecyclerView.setAdapter(commentAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        Intent newCommentActivity = new Intent(this, NewCommentActivity.class);
        newCommentActivity.putExtra("postId", data.getId());
        startActivity(newCommentActivity);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //refetch
        this.fetchComments();
    }
}
