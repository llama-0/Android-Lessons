package com.example.user.pointofsale;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.Currency;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import static android.app.ProgressDialog.show;

public class MainActivity extends AppCompatActivity {

    private TextView mNameText;
    private TextView mQuantityText;
    private TextView mDeliveryDateText;
    private Item mCurrentItem;
    private ArrayList<Item> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNameText = (TextView) findViewById(R.id.name_text);
        mQuantityText = (TextView) findViewById(R.id.quantity_text);
        mDeliveryDateText = (TextView) findViewById(R.id.date_text);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
    }



    private void showCurrentItem() {
        mNameText.setText(mCurrentItem.getName());
        mQuantityText.setText(getString(R.string.quantity_format, mCurrentItem.getQuantity()));
        mDeliveryDateText.setText(getString(R.string.date_format, mCurrentItem.getDeliveryDateString()));
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
        switch (item.getItemId()) {
            case R.id.action_reset:
                final Item clearedItem = mCurrentItem;
                mCurrentItem = new Item();
                showCurrentItem();
                Snackbar.make(findViewById(R.id.coordinator_layout), "Item cleared", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mCurrentItem = clearedItem;
                                showCurrentItem();
                                Snackbar.make(findViewById(R.id.coordinator_layout), "Item restored", Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        }).show();

                return true;
            case R.id.action_settings:
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void addItem() {
        DialogFragment df = new DialogFragment() {
            @NonNull
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add, null);
                builder.setView(view);
                //Capture
                final EditText nameEditText = (EditText) view.findViewById(R.id.edit_name); //without view. there would be null exeption
                final EditText quantityEditText = (EditText) view.findViewById(R.id.edit_quantity);
                final CalendarView deliveryDateView = (CalendarView) view.findViewById(R.id.calendar_view);

                //Buttons
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Todo
                        String name = nameEditText.getText().toString();
                        int quantity = Integer.parseInt(quantityEditText.getText().toString());
                        long deliveryDate = deliveryDateView.getDate();
                        mCurrentItem = new Item(name, quantity, new Date(deliveryDate));
                        mItems.add(mCurrentItem);
                        showCurrentItem();
                    }
                });
                return builder.create();
            }
        };
        df.show(getSupportFragmentManager(), " ");
    }
}
