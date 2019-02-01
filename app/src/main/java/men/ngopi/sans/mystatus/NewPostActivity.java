package men.ngopi.sans.mystatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import men.ngopi.sans.mystatus.helpers.PostHelper;
import men.ngopi.sans.mystatus.models.PostModel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {
    Button addBtn;
    TextInputEditText nameTxt;
    TextInputEditText postTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        addBtn = findViewById(R.id.add_btn);
        nameTxt = findViewById(R.id.name_txt);
        postTxt = findViewById(R.id.post_txt);

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
                String post = postTxt.getText().toString();
                PostModel postModel = new PostModel();

                postModel.setName(name);
                postModel.setPost(post);

                PostHelper.getInstance().open();
                PostHelper.getInstance().insert(postModel);
                PostHelper.getInstance().close();
                finish();
            }
        }
    }
}
