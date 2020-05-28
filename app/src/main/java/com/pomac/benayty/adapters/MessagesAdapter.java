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

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;
import java.util.Objects;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private Context context;
    private QuerySnapshot messages;

    public MessagesAdapter(Context context, QuerySnapshot messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessagesAdapter.MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.messages_item_layout, parent, false);
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.MessagesViewHolder holder, int position) {
        holder.messageTextView.setText(Objects.requireNonNull(messages.getDocuments().get(position).get("message")).toString());

        String from = Objects.requireNonNull(messages.getDocuments().get(position).get("from")).toString();
        FirebaseFirestore.getInstance()
                .collection("users")
                .whereEqualTo("phone", from)
                .get()
                .addOnCompleteListener(task -> {
                    List<DocumentSnapshot> userData = Objects.requireNonNull(task.getResult()).getDocuments();
                    holder.userNameTextView.setText(Objects.requireNonNull(userData.get(0).get("name")).toString());

                    Transformation transformation = new RoundedTransformationBuilder()
                            .cornerRadiusDp(50)
                            .oval(false)
                            .build();
                    try {
                        Picasso.get()
                                .load(Objects.requireNonNull(userData.get(0).get("imagePath")).toString())
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
                    });
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
