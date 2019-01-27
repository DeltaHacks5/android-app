package me.susieson.sportscanner;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ChatFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<Message> messageList;

    public ChatFragment() {
        User susie = new User("Susie");
        User anna = new User("Anna");
        User david = new User("David");
        User kyle = new User("Kyle");

        messageList = Arrays.asList(
                new Message("Hey team, my name is Anna. I look forward to playing soccer with you all!",
                        anna, new Date(2019, 1, 27, 8, 30)),
                new Message("Nice to meet you!",
                        david, new Date(2019, 1, 27, 8, 35)),
                new Message("Hi team, who has an extra pair of running shoes?",
                        susie, new Date(2019, 1, 27, 10, 32)),
                new Message("I do!",
                        anna, new Date(2019, 1, 27, 10, 33)),
                new Message("Thanks!",
                        susie, new Date(2019, 1, 27, 10, 33)),
                new Message("I completed the 30 days streak!  \uD83C\uDF89",
                        kyle, new Date(2019, 1, 27, 12, 30)));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMessageRecycler = view.findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mMessageRecycler.setAdapter(mMessageAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

    }
}
