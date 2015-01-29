package com.example.fatehrehman.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Hi", Toast.LENGTH_LONG).show();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        public PlaceholderFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            Button btn1 = (Button) rootView.findViewById(R.id.button1);
            btn1.setOnClickListener(this);
            Button btn2 = (Button) rootView.findViewById(R.id.button2);
            btn2.setOnClickListener(this);
            Button btn3 = (Button) rootView.findViewById(R.id.button3);
            btn3.setOnClickListener(this);
            Button btn4 = (Button) rootView.findViewById(R.id.button4);
            btn4.setOnClickListener(this);
            Button btn5 = (Button) rootView.findViewById(R.id.button5);
            btn5.setOnClickListener(this);
            Button btn6 = (Button) rootView.findViewById(R.id.button6);
            btn6.setOnClickListener(this);
            return rootView;
        }

        @Override
        public void onClick(View v) {
            int vid = v.getId();
            Button btn = (Button) v;
            String txt = "Welcome to menu";
            if (vid == R.id.button1) {
                txt +=  " Btn1 " + btn.getText().toString();
                startActivity(new Intent(this.getActivity(),Frags.class));
            } else if (vid == R.id.button2) {
                txt +=  " Btn2 " + btn.getText().toString();
            } else if (vid == R.id.button3) {
                txt +=  " Btn3 " + btn.getText().toString();
            } else if (vid == R.id.button4) {
                txt +=  " Btn4 " + btn.getText().toString();
            } else if (vid == R.id.button5) {
                txt +=  " Btn5 " + btn.getText().toString();
            } else if (vid == R.id.button6) {
                txt +=  " Btn6 " + btn.getText().toString();
            }
            Toast.makeText(getActivity(),txt,Toast.LENGTH_LONG).show();
        }
    }
}
