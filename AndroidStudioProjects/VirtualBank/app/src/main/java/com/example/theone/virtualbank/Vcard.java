package com.example.theone.virtualbank;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by theone on 17/01/16.
 */
public class Vcard extends Fragment {
    View  rootView;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Session manager
        SessionManager session = new SessionManager(getActivity());
        rootView = inflater.inflate(R.layout.card, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Vc Card");
        return rootView;

    }
}
