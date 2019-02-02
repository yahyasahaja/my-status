package men.ngopi.sans.mystatus;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mancj.materialsearchbar.MaterialSearchBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import men.ngopi.sans.mystatus.adapters.PostAdapter;
import men.ngopi.sans.mystatus.helpers.CommentHelper;
import men.ngopi.sans.mystatus.helpers.PostHelper;
import men.ngopi.sans.mystatus.models.PostModel;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextWatcher {
    private static MainActivity instance;
    private PostHelper postHelper;
    private CommentHelper commentHelper;
    private ArrayList<PostModel> posts;
    private PostAdapter postAdapter;
    private RecyclerView postRecylerView;
    private MaterialSearchBar search;
    private String searchQuery = "";

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
        search = findViewById(R.id.search);
        postRecylerView = findViewById(R.id.post_recycler_view);
        postRecylerView.setLayoutManager(new LinearLayoutManager(this));

        search.addTextChangeListener(this);

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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchAllPosts() {
        postHelper.open();
        this.posts = postHelper.fetchAllPosts(searchQuery);
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchQuery = s.toString();
        fetchAllPosts();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
