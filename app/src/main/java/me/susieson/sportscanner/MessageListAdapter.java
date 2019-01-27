package me.susieson.sportscanner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_MESSAGE_ACHIEVED = 3;
    private List<Message> mMessageList;

    public MessageListAdapter(List<Message> messageList) {
        mMessageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_message, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.their_message, parent, false);
            return new ReceivedMessageHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.achievement_message, parent, false);
            return new AchievedMessageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_ACHIEVED:
                ((AchievedMessageHolder) holder).bind(message);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mMessageList.get(position);

        if (message.sender.nickname.equals("Susie")) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else if (message.sender.nickname.equals("Kyle")) {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_ACHIEVED;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
            nameText = itemView.findViewById(R.id.text_message_name);
        }

        void bind(Message message) {
            messageText.setText(message.message);

            // Format the stored timestamp into a readable String using method.
            timeText.setText(Utils.formatDateTime(message.createdAt));
            nameText.setText(message.sender.nickname);
        }
    }

    private class AchievedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        AchievedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
            nameText = itemView.findViewById(R.id.text_message_name);
        }

        void bind(Message message) {
            messageText.setText(message.message);

            // Format the stored timestamp into a readable String using method.
            timeText.setText(Utils.formatDateTime(message.createdAt));
            nameText.setText(message.sender.nickname);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
        }

        void bind(Message message) {
            messageText.setText(message.message);

            timeText.setText(Utils.formatDateTime(message.createdAt));
        }
    }
}
