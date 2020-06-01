package com.pomac.benayty.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QuerySnapshot;
import com.pomac.benayty.R;

import java.util.Objects;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.ChattingViewHolder> {

    private Context context;
    private QuerySnapshot messages;
    private String from;

    public ChattingAdapter(Context context, QuerySnapshot messages, String from) {
        this.context = context;
        this.messages = messages;
        this.from = from;
    }

    @NonNull
    @Override
    public ChattingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatting_item_layout, parent, false);
        return new ChattingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChattingViewHolder holder, int position) {
        holder.chattingTextView.setText(Objects.requireNonNull(messages.getDocuments().get(position).get("message")).toString());

        String from = Objects.requireNonNull(messages.getDocuments().get(position).get("from")).toString();

        if (this.from.equals(from)) {
            holder.chattingItemLayout.setGravity(Gravity.START);
        } else {
            holder.chattingItemLayout.setGravity(Gravity.END);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ChattingViewHolder extends RecyclerView.ViewHolder {

        TextView chattingTextView;
        LinearLayout chattingItemLayout;

        public ChattingViewHolder(@NonNull View itemView) {
            super(itemView);
            chattingTextView = itemView.findViewById(R.id.chattingTextView);
            chattingItemLayout = itemView.findViewById(R.id.chattingItemLayout);
        }
    }
}
