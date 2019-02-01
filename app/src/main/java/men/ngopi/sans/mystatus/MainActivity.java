package men.ngopi.sans.mystatus;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import men.ngopi.sans.mystatus.adapters.PostAdapter;
import men.ngopi.sans.mystatus.helpers.CommentHelper;
import men.ngopi.sans.mystatus.helpers.PostHelper;
import men.ngopi.sans.mystatus.models.PostModel;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;
    private PostHelper postHelper;
    private CommentHelper commentHelper;
    private ArrayList<PostModel> posts;
    private PostAdapter postAdapter;
    private RecyclerView postRecylerView;

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //config
        instance = this;
        postHelper = new PostHelper();
        commentHelper = new CommentHelper();
        postAdapter = new PostAdapter();
        postRecylerView = findViewById(R.id.post_recycler_view);
        postRecylerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//////                        .setAction("Action", null).show();
                Intent newPostActivityIntent = new Intent(MainActivity.this, NewPostActivity.class);
                startActivity(newPostActivityIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchAllPosts() {
        postHelper.open();
        this.posts = postHelper.fetchAllPosts();
        postAdapter.setData(this.posts);
        postRecylerView.setAdapter(postAdapter);
        postHelper.close();

        Log.d("coba lg", posts.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();

        //refetch
        this.fetchAllPosts();
    }
}
