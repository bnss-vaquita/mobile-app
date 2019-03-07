package bnss.mobile.a2famobile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    List<VaquitaUser> values;
    UserListView root;
    VerifiedActiviy root_activity;
    public UserListAdapter(List<VaquitaUser> values, UserListView root, VerifiedActiviy root_activity) {
        this.values = values;
        this.root = root;
        this.root_activity = root_activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView_list_item);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_view, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        TextView textView = viewHolder.textView;
        final VaquitaUser user = values.get(i);
        String name = user.firstName + user.lastName;
        textView.setText(name);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(), UserFileTransferActivity.class);
                intent.putExtra("vaquitaUser", user );
                Log.e("UserListAdapter", "Starting activity for user: " + user);
                root_activity.launchActivity(intent);
            }
        });



    }





    @Override
    public int getItemCount() {
        return values.size();
    }
}
