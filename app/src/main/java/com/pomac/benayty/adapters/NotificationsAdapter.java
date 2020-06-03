package com.pomac.benayty.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.benayty.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    private Context context;
    private List<String> notificationsData;

    public NotificationsAdapter(Context context, List<String> notificationsData) {
        this.context = context;
        this.notificationsData = notificationsData;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notifications_item_layout, parent, false);
        return new NotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {
        try {
            JSONObject jsonObject = new JSONObject(notificationsData.get(position));
            holder.notificationTextView.setText(jsonObject.get("message").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return notificationsData.size();
    }

    static class NotificationsViewHolder extends RecyclerView.ViewHolder {

        TextView notificationTextView;

        public NotificationsViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTextView = itemView.findViewById(R.id.notificationTextView);
        }
    }
}
