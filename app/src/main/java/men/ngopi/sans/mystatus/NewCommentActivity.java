package men.ngopi.sans.mystatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import men.ngopi.sans.mystatus.helpers.CommentHelper;
import men.ngopi.sans.mystatus.helpers.PostHelper;
import men.ngopi.sans.mystatus.models.CommentModel;
import men.ngopi.sans.mystatus.models.PostModel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class NewCommentActivity extends AppCompatActivity implements View.OnClickListener {
    Button addBtn;
    TextInputEditText nameTxt;
    TextInputEditText commentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);

        addBtn = findViewById(R.id.add_btn);
        nameTxt = findViewById(R.id.name_txt);
        commentTxt = findViewById(R.id.comment_txt);

        addBtn.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn: {
                String name = nameTxt.getText().toString();
                String comment = commentTxt.getText().toString();
                CommentModel commentModel = new CommentModel();

                commentModel.setPostId(getIntent().getIntExtra("postId", 0));
                commentModel.setName(name);
                commentModel.setComment(comment);

                CommentHelper.getInstance().open();
                CommentHelper.getInstance().insert(commentModel);
                CommentHelper.getInstance().close();
                finish();
            }
        }
    }
}
