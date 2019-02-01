package men.ngopi.sans.mystatus.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import men.ngopi.sans.mystatus.EditPostActivity;
import men.ngopi.sans.mystatus.MainActivity;
import men.ngopi.sans.mystatus.PostDetailActivity;
import men.ngopi.sans.mystatus.R;
import men.ngopi.sans.mystatus.helpers.PostHelper;
import men.ngopi.sans.mystatus.models.PostModel;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    ArrayList<PostModel> data;

    public ArrayList<PostModel> getData() {
        return data;
    }

    public void setData(ArrayList<PostModel> data) {
        this.data = data;
        Log.d("update hrsnya", data.toString());
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.post_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostModel dt = this.data.get(position);
        String name = dt.getName();
        String post = dt.getPost();

        Log.d("update ga", "nih");
        holder.nameTxt.setText(name);
        holder.postTxt.setText(post);
        holder.data = dt;
        holder.adapter = this;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTxt;
        public TextView postTxt;
        public ImageButton commentBtn;
        public ImageButton deleteBtn;
        public ImageButton editBtn;
        public PostModel data;
        public PostAdapter adapter;
        public AlertDialog.Builder builder;
        public AlertDialog alert;
        public LinearLayout postLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameTxt = itemView.findViewById(R.id.name_txt);
            this.postTxt = itemView.findViewById(R.id.post_txt);
            this.editBtn = itemView.findViewById(R.id.edit_btn);
            this.deleteBtn = itemView.findViewById(R.id.delete_btn);
            this.commentBtn = itemView.findViewById(R.id.comment_btn);
            this.postLinearLayout = itemView.findViewById(R.id.post_linear_layout);

            deleteBtn.setOnClickListener(this);
            editBtn.setOnClickListener(this);
            postLinearLayout.setOnClickListener(this);

            builder = new AlertDialog.Builder(MainActivity.getInstance());

            //Setting message manually and performing action on button click
            //This will not allow to close dialogbox until user selects an option
//                builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    PostHelper.getInstance().open();
                    PostHelper.getInstance().delete(data.getId());
                    PostHelper.getInstance().close();

                    MainActivity.getInstance().fetchAllPosts();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.getInstance(), data.getName() + "'s post deleted", Toast.LENGTH_SHORT).show();
                    //builder.finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //  Action for 'NO' Button
                    dialog.cancel();
                }
            });

            //Creating dialog box
            //Setting the title manually
            //alert.setTitle("AlertDialogExample");
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.delete_btn) {
                builder.setMessage("Do you delete " + this.data.getName() + "'s post ?");
                alert = builder.create();
                alert.show();
            } else if (v.getId() == R.id.edit_btn) {
                Intent editPostActivityIntent = new Intent(MainActivity.getInstance(), EditPostActivity.class);
                editPostActivityIntent.putExtra("id", data.getId());
                editPostActivityIntent.putExtra("name", data.getName());
                editPostActivityIntent.putExtra("post", data.getPost());
                MainActivity.getInstance().startActivity(editPostActivityIntent);
            } else if (v.getId() == R.id.post_linear_layout) {
                Intent detailPostActivityIntent = new Intent(MainActivity.getInstance(), PostDetailActivity.class);
                detailPostActivityIntent.putExtra("id", data.getId());
                detailPostActivityIntent.putExtra("name", data.getName());
                detailPostActivityIntent.putExtra("post", data.getPost());
                MainActivity.getInstance().startActivity(detailPostActivityIntent);
            }
        }
    }
}
