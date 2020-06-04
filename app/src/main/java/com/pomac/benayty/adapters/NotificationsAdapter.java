package com.pomac.benayty.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.benayty.R;
import com.pomac.benayty.view.interfaces.OnNotificationsItemSelected;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    private Context context;
    private List<Map<String, String>> notificationsData;
    private OnNotificationsItemSelected onNotificationsItemSelected;

    public NotificationsAdapter
            (Context context,
             List<Map<String, String>> notificationsData,
             OnNotificationsItemSelected onNotificationsItemSelected) {
        this.context = context;
        this.notificationsData = notificationsData;
        this.onNotificationsItemSelected = onNotificationsItemSelected;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notifications_item_layout, parent, false);
        return new NotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {
        holder.notificationTextView.setText(notificationsData.get(position).get("message"));

        Calendar calendar = Calendar.getInstance();

        if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
            holder.notificationTimeTextView.setText
                    (String.format(Locale.US,
                            "%d:%d PM",
                            calendar.get(Calendar.HOUR),
                            calendar.get(Calendar.MINUTE)));
        } else {
            holder.notificationTimeTextView.setText
                    (String.format(Locale.US,
                            "%d:%d AM",
                            calendar.get(Calendar.HOUR),
                            calendar.get(Calendar.MINUTE)));
        }

        holder.notificationsItemRelativeLayout.setOnClickListener
                (v -> onNotificationsItemSelected.onNotificationsItemSelected(notificationsData.get(position)));

    }

    @Override
    public int getItemCount() {
        return notificationsData.size();
    }

    static class NotificationsViewHolder extends RecyclerView.ViewHolder {

        TextView notificationTextView;
        TextView notificationTimeTextView;
        RelativeLayout notificationsItemRelativeLayout;

        public NotificationsViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTextView = itemView.findViewById(R.id.notificationTextView);
            notificationTimeTextView = itemView.findViewById(R.id.notificationTimeTextView);
            notificationsItemRelativeLayout =
                    itemView.findViewById(R.id.notificationsItemRelativeLayout);
        }
    }
}
