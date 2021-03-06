package com.init.luckyfriend.activity.Messages;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.init.luckyfriend.R;

import java.util.ArrayList;


import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.Notification.AppConstant;

public class MessagesFragment extends Fragment {

    RecyclerView recyclerView;
    MessageMultiplerowAdapter adapter;
    private ArrayList<WallDataBean> items=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);

        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);

        fillAdapter();


        adapter=new MessageMultiplerowAdapter(getActivity(),items);
        recyclerView.setAdapter(adapter);


        // Inflate the layout for this fragment
        return rootView;
    }
    private void fillAdapter() {

        int type;

        String content;

        for (int i = 0; i < 6; i++) {

            if (i == 0 || i == 1 || i == 2  ) {
                type = AppConstant.messageonline;
                //content = "Type 1: This is Multiple row layout";
            }
            else
            {
                type = AppConstant.messageoffline;
                //content = "Type 1: This is Multiple row layout";
            }


            items.add(new WallDataBean( type ));
        }


    }


}