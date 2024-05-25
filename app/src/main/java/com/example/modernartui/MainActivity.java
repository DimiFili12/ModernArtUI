package com.example.modernartui;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    static private final String URL = "https://www.moma.org";
    SeekBar seekbar;
    View view1, view2, view3, view4, view5;
    int color1, color2, color3, color5, stepValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);
        view5 = findViewById(R.id.view5);

        stepValue = 0x001101;

        seekbar = findViewById(R.id.seekBar);
        seekbar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    //Change the colors of the views, when the seekBar value changes
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        changeBackground();
                    }
                    //Change the thumb when seekBar is clicked
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        seekbar.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb_on_touch));
                    }
                    //Reset the thumb when is not clicked
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        seekbar.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb));
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more_info:
                showDialogFragment();
                return true;
            default:
                return false;
        }
    }

    void showDialogFragment(){
        DialogFragment mDialog = AlertDialogFragment.newInstance();
        mDialog.show(getFragmentManager(),"Alert" );
    }

    public static class AlertDialogFragment extends DialogFragment{
        public static AlertDialogFragment newInstance() {
            return new AlertDialogFragment();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            View view = getActivity().getLayoutInflater().inflate(R.layout.dialog, null);

            TextView title = view.findViewById(R.id.textView);
            title.setText("Inspired by the works of artists such as Piet Mondrian and Ben Nicholson.\n\n");
            title.setTextSize(19);
            title.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView body = view.findViewById(R.id.textView2);
            body.setText("Click below to learn more!\n");
            body.setTextSize(18);
            body.setGravity(Gravity.CENTER_HORIZONTAL);

            Button visitBtn = view.findViewById(R.id.button2);
            visitBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent goToMOMA = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                    Intent chooseApp = Intent.createChooser(goToMOMA, "Choose your preferred browser:");
                    startActivity(chooseApp);
                }
            });

            Button notNowBtn = view.findViewById(R.id.button);
            notNowBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    getDialog().dismiss();
                }
            });

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view);

            return dialog;
        }
    }

    private void changeBackground() {
        int seekValue = seekbar.getProgress() * 5 / 100;
        //Initial colors of the boxes
        color1 = 0xff6977bf;
        color2 = 0xffe16199;
        color3 = 0xffb12a19;
        color5 = 0xff253a9c;

        color1 = color1 + (stepValue * seekValue);
        view1.setBackgroundColor(color1);

        color2 = color2 + (stepValue * seekValue);
        view2.setBackgroundColor(color2);

        color3 = color3 + (stepValue * seekValue);
        view3.setBackgroundColor(color3);

        color5 = color5 + (stepValue * seekValue);
        view5.setBackgroundColor(color5);
    }
}