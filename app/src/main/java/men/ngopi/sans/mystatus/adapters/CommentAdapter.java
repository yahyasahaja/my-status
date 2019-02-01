package men.ngopi.sans.mystatus.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import men.ngopi.sans.mystatus.EditCommentActivity;
import men.ngopi.sans.mystatus.EditPostActivity;
import men.ngopi.sans.mystatus.MainActivity;
import men.ngopi.sans.mystatus.PostDetailActivity;
import men.ngopi.sans.mystatus.R;
import men.ngopi.sans.mystatus.helpers.CommentHelper;
import men.ngopi.sans.mystatus.models.CommentModel;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    ArrayList<CommentModel> data;

    public ArrayList<CommentModel> getData() {
        return data;
    }

    public void setData(ArrayList<CommentModel> data) {
        this.data = data;
//        Log.d("update hrsnya", data.toString());
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentModel dt = this.data.get(position);
        String name = dt.getName();
        String comment = dt.getComment();

//        Log.d("update ga", "nih");
        holder.nameTxt.setText(name);
        holder.commentTxt.setText(comment);
        holder.data = dt;
        holder.adapter = this;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTxt;
        public TextView commentTxt;
        public ImageButton deleteBtn;
        public ImageButton editBtn;
        public CommentModel data;
        public CommentAdapter adapter;
        public AlertDialog.Builder builder;
        public AlertDialog alert;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameTxt = itemView.findViewById(R.id.name_txt);
            this.commentTxt = itemView.findViewById(R.id.commetnt_txt);
            this.editBtn = itemView.findViewById(R.id.edit_btn);
            this.deleteBtn = itemView.findViewById(R.id.delete_btn);

            deleteBtn.setOnClickListener(this);
            editBtn.setOnClickListener(this);

            builder = new AlertDialog.Builder(PostDetailActivity.getInstance());

            //Setting message manually and performing action on button click
            //This will not allow to close dialogbox until user selects an option
//                builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    CommentHelper.getInstance().open();
                    CommentHelper.getInstance().delete(data.getId());
                    CommentHelper.getInstance().close();

                    PostDetailActivity.getInstance().fetchComments();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.getInstance(), data.getName() + "'s comment deleted", Toast.LENGTH_SHORT).show();
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
                builder.setMessage("Do you delete " + this.data.getName() + "'s comment ?");
                alert = builder.create();
                alert.show();
                Log.d("triggered", "must show");
            } else if (v.getId() == R.id.edit_btn) {
                Intent editCommentActivityIntent = new Intent(PostDetailActivity.getInstance(), EditCommentActivity.class);
                editCommentActivityIntent.putExtra("id", data.getId());
                editCommentActivityIntent.putExtra("postId", data.getPostId());
                editCommentActivityIntent.putExtra("name", data.getName());
                editCommentActivityIntent.putExtra("comment", data.getComment());
                PostDetailActivity.getInstance().startActivity(editCommentActivityIntent);
            }
        }
    }
}
