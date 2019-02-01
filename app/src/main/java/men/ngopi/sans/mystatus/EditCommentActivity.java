package men.ngopi.sans.mystatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import men.ngopi.sans.mystatus.helpers.CommentHelper;
import men.ngopi.sans.mystatus.helpers.PostHelper;
import men.ngopi.sans.mystatus.models.CommentModel;
import men.ngopi.sans.mystatus.models.PostModel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class EditCommentActivity extends AppCompatActivity implements View.OnClickListener {
    Button editBtn;
    TextInputEditText nameTxt;
    TextInputEditText commentTxt;
    CommentModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment);

        editBtn = findViewById(R.id.edit_btn);
        nameTxt = findViewById(R.id.name_txt);
        commentTxt = findViewById(R.id.comment_txt);

        data = new CommentModel();
        data.setId(getIntent().getIntExtra("id", 0));
        data.setPostId(getIntent().getIntExtra("postId", 0));
        data.setName(getIntent().getStringExtra("name"));
        data.setComment(getIntent().getStringExtra("comment"));

        nameTxt.setText(data.getName());
        commentTxt.setText(data.getComment());

        editBtn.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_btn: {
                String name = nameTxt.getText().toString();
                String comment = commentTxt.getText().toString();
                CommentModel commentModel = new CommentModel();

                Log.d("post id", "" + data.getPostId());
                commentModel.setId(data.getId());
                commentModel.setPostId(data.getPostId());
                commentModel.setName(name);
                commentModel.setComment(comment);

                CommentHelper.getInstance().open();
                CommentHelper.getInstance().update(commentModel);
                CommentHelper.getInstance().close();
                finish();
            }
        }
    }
}
