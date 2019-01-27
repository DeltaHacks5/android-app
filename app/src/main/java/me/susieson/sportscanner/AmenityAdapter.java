package me.susieson.sportscanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

class AmenityAdapter extends android.support.v7.widget.RecyclerView.Adapter<AmenityAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Group> groups;

    AmenityAdapter(Context context, ArrayList<Group> groups) {
        this.context = context;
        this.groups = groups;
    }

    @NonNull
    @Override
    public AmenityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView view = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.amenity_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AmenityAdapter.ViewHolder viewHolder, int i) {
        final Group group = groups.get(i);
        viewHolder.name.setText(group.getName());
        viewHolder.time.setText(group.getTime());
        viewHolder.signUp.setText("Sign Up");
        viewHolder.members.setText(group.getCount() + " members");
        viewHolder.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (group.isRegistered()) {
                    viewHolder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                    viewHolder.name.setTextColor(context.getResources().getColor(android.R.color.black));
                    viewHolder.time.setTextColor(context.getResources().getColor(android.R.color.black));
                    viewHolder.signUp.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                    viewHolder.signUp.setText("Sign Up");
                    viewHolder.members.setTextColor(context.getResources().getColor(android.R.color.black));
                    viewHolder.members.setText(group.getCount() + " members");
                    group.setRegistered(false);
                } else {
                    viewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    viewHolder.name.setTextColor(context.getResources().getColor(android.R.color.white));
                    viewHolder.time.setTextColor(context.getResources().getColor(android.R.color.white));
                    viewHolder.signUp.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    viewHolder.signUp.setText("Registered");
                    viewHolder.members.setTextColor(context.getResources().getColor(android.R.color.white));
                    viewHolder.members.setText(group.getCount() + 1 + " members");
                    group.setRegistered(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView time;
        Button signUp;
        TextView members;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.amenity_name);
            time = view.findViewById(R.id.amenity_time);
            signUp = view.findViewById(R.id.sign_up_button);
            members = view.findViewById(R.id.amenity_members);
        }
    }
}
