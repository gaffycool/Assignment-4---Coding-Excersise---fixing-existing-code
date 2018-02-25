package com.roundarch.codetest.part1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.roundarch.codetest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAE on 25-feb-2018.
 */
public class Part1Fragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    // TODO - any member variables you need to store?

    private SeekBar topBar, bottomBar;
    private TextView getDifference;
    private ToggleButton btnToggle;
    private int calc;

    //FIXME: Improve something! Anything
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part1, null);

        // TODO - obtain references to your views from the layout
        btnToggle = view.findViewById(R.id.toggleButton);
        topBar = view.findViewById(R.id.topBar);
        bottomBar = view.findViewById(R.id.bottomBar);
        getDifference = view.findViewById(R.id.txtDifference);


        // TODO - hook up any event listeners that make sense for the task
        btnToggle.setOnClickListener(this);
        topBar.setOnSeekBarChangeListener(this);
        bottomBar.setOnSeekBarChangeListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        //hooking up toggle button to the seekbars using switchcase

        switch (view.getId())
        {
            case R.id.toggleButton:
                if (btnToggle.isChecked())
                {
                    topBar.setProgress(0);
                    bottomBar.setProgress(0);
                }
                break;
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

       difference(seekBar);

    }

    public void difference(SeekBar seekBar)
    {
        if (btnToggle.isChecked()) {


            if (seekBar == topBar) {
                bottomBar.setProgress(topBar.getProgress()); // set bottom bar to same as top bar

            } else {
                topBar.setProgress(bottomBar.getProgress());
            }
            getDifference.setText("0");
        }
            else
            {
                calc = (topBar.getProgress() - bottomBar.getProgress());
                getDifference.setText("" + calc); //update text to show differnece
            }
        }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
