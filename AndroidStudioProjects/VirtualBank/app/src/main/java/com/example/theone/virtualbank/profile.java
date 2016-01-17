package com.example.theone.virtualbank;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by theone on 17/01/16.
 */
public class profile extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Session manager
        SessionManager session = new SessionManager(getActivity());
        View rootView = inflater.inflate(R.layout.profile, container, false);
        TextView abc1=(TextView)rootView.findViewById(R.id.tvNumber1);
        abc1.setText(session.Getusername());

        TextView abc2=(TextView)rootView.findViewById(R.id.tvNumber2);
        abc2.setText(session.Getpass());
        TextView abc3=(TextView)rootView.findViewById(R.id.tvNumber3);
        abc3.setText(session.Getemail());

        TextView abc4=(TextView)rootView.findViewById(R.id.tvNumber4);
        abc4.setText(session.GetMobile());

        TextView abc5=(TextView)rootView.findViewById(R.id.tvNumber5);
        abc5.setText(session.GetAccount());


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.parent_main, menu);
    }
}



