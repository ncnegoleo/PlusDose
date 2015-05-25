package br.com.ifpb.solarsoft.fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import br.com.ifpb.solarsoft.R;

/**
 * Created by leonardo on 24/05/2015.
 */
public class FragmentVacinasTomadas extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_vacinas_tomadas, null);



        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText("Fragment 2");

        return view;
    }

    public void testMetod() {
        Toast.makeText(getActivity(), "aaa", Toast.LENGTH_SHORT).show();
    }
}
