package com.pomac.benayty.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.pomac.benayty.Globals;
import com.pomac.benayty.MessageItem;
import com.pomac.benayty.R;
import com.pomac.benayty.view.interfaces.OnMessageItemSelected;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private Context context;
    private List<MessageItem> messages;
    private OnMessageItemSelected onMessageItemSelected;

    public MessagesAdapter(Context context, List<MessageItem> messages, OnMessageItemSelected onMessageItemSelected) {
        this.context = context;
        this.messages = messages;
        this.onMessageItemSelected = onMessageItemSelected;
    }

    @NonNull
    @Override
    public MessagesAdapter.MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.messages_item_layout, parent, false);
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.MessagesViewHolder holder, int position) {
        holder.messageTextView.setText(messages.get(position).getMessage());

        holder.userNameTextView.setText(messages.get(position).getUserName());

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(50)
                .oval(false)
                .build();
        try {
            Picasso.get()
                    .load(messages.get(position).getImagePath())
                    .fit()
                    .transform(transformation)
                    .into(holder.userImageView);
        } catch (Exception e) {
            Picasso.get()
                    .load(R.drawable.person)
                    .fit()
                    .transform(transformation)
                    .into(holder.userImageView);
        }

        holder.messageItem.setOnClickListener(v -> {
            Log.d(Globals.TAG, "Clicked");
            onMessageItemSelected
                    .onMessageItemSelected(messages.get(position).getUserName(),
                            messages.get(position).getUserPhone(),
                            Globals.phone
                    );
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessagesViewHolder extends RecyclerView.ViewHolder {

        TextView messageTextView;
        TextView userNameTextView;
        RoundedImageView userImageView;
        CardView messageItem;

        MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            userImageView = itemView.findViewById(R.id.userImageView);
            messageItem = itemView.findViewById(R.id.messageItem);
        }
    }
}
