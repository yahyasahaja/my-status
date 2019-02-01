package men.ngopi.sans.mystatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import men.ngopi.sans.mystatus.helpers.PostHelper;
import men.ngopi.sans.mystatus.models.PostModel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class EditPostActivity extends AppCompatActivity implements View.OnClickListener {
    Button editBtn;
    TextInputEditText nameTxt;
    TextInputEditText postTxt;
    PostModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        editBtn = findViewById(R.id.add_btn);
        nameTxt = findViewById(R.id.name_txt);
        postTxt = findViewById(R.id.post_txt);

        data = new PostModel();
        data.setId(getIntent().getIntExtra("id", 0));
        data.setName(getIntent().getStringExtra("name"));
        data.setPost(getIntent().getStringExtra("post"));

        nameTxt.setText(data.getName());
        postTxt.setText(data.getPost());

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
            case R.id.add_btn: {
                String name = nameTxt.getText().toString();
                String post = postTxt.getText().toString();
                PostModel postModel = new PostModel();

                postModel.setId(this.data.getId());
                postModel.setName(name);
                postModel.setPost(post);

                PostHelper.getInstance().open();
                PostHelper.getInstance().update(postModel);
                PostHelper.getInstance().close();
                finish();
            }
        }
    }
}
