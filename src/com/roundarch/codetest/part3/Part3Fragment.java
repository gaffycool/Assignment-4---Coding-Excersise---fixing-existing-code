package com.roundarch.codetest.part3;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.roundarch.codetest.R;
import com.roundarch.codetest.part3.adapter.ListViewAdapter;
import com.roundarch.codetest.part3.model.Location;

import java.util.ArrayList;

public class Part3Fragment extends Fragment {

    private IntentFilter intentFilter;
    private ListViewAdapter listViewAdapter;
    private Receiver receiver;
    private Part3Service part3Service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part3, null);

        View emptyView = view.findViewById(R.id.empty_textview);
        ListView listView = view.findViewById(R.id.part3_listview);
        listView.setEmptyView(emptyView);

        // TODO - the listview will need to be provided with a source for data
        listViewAdapter = new ListViewAdapter(getActivity(), new ArrayList<Location>());
        listView.setAdapter(listViewAdapter);

        // TODO - (optional) you can set up handling to list item selection if you wish

        receiver = new Receiver();
        intentFilter = new IntentFilter();

        intentFilter.addAction(Part3Service.ACTION_SERVICE_DATA_UPDATED); //to pass action to intentfilter

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // TODO - when the fragment resumes, it would be a good time to register to receieve broadcasts
        // TODO - from the service.  The broadcast will serve as a way to inform us that data is available
        // TODO - for consumption

        // TODO - this is also a good place to leverage the Service's IBinder interface to tell it you want
        // TODO - to refresh data

        getActivity().registerReceiver(receiver, intentFilter);

        if (part3Service != null) {
            part3Service.updateData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);


    }

    // TODO - our listView needs a source of data, and here might be a good place to create that

    // TODO - we also need a means of responding to the Broadcasts sent by our Service

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            Part3Service.Part3ServiceBinder binder = (Part3Service.Part3ServiceBinder) iBinder;
            part3Service = binder.getConnection();
            part3Service.updateData();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            part3Service = null;
        }
    };


    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), Part3Service.class);

        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unbindService(mConnection);
    }


        class Receiver extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Part3Service.ACTION_SERVICE_DATA_UPDATED)) {


                    ArrayList<Location> location = intent.getParcelableArrayListExtra("res");
                    if (location != null) {
                        if (location == null || location.size() == 0)
                           // Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                            return;

                        listViewAdapter.clear();

                        listViewAdapter.addAll(location);
                    }
                }
            }
        }
    }