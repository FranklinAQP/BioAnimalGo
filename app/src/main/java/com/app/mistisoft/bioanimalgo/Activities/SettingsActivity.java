package com.app.mistisoft.bioanimalgo.Activities;

import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.app.mistisoft.bioanimalgo.AppCompatPreferenceActivity;
import com.app.mistisoft.bioanimalgo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SettingsActivity extends BaseActivity {

    //Firebase
    private DatabaseReference settingsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Firebase
        settingsDB = FirebaseDatabase.getInstance().getReference().child("settings").child(getUid());

        Switch switch_aves = (Switch)findViewById(R.id.switch_aves);
        Switch switch_anfibios = (Switch)findViewById(R.id.switch_anfibios);
        Switch switch_mamiferos = (Switch)findViewById(R.id.switch_mamiferos);
        Switch switch_reptiles = (Switch)findViewById(R.id.switch_reptiles);

        switch_aves.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("NavigationDrawer","modificando aves a ON");
                    settingsDB.child("aves").setValue("ON");
                    Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("NavigationDrawer","modificando aves a OFF");
                    settingsDB.child("aves").setValue("OFF");
                    Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_LONG).show();
                }

            }
        });
        switch_anfibios.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("NavigationDrawer","modificando anfibios a ON");
                    settingsDB.child("anfibios").setValue("ON");
                    Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("NavigationDrawer","modificando anfibios a OFF");
                    settingsDB.child("anfibios").setValue("OFF");
                    Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_LONG).show();
                }

            }
        });
        switch_mamiferos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("NavigationDrawer","modificando mamiferos a ON");
                    settingsDB.child("mamiferos").setValue("ON");
                    Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("NavigationDrawer","modificando mamiferos a OFF");
                    settingsDB.child("mamiferos").setValue("OFF");
                    Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_LONG).show();
                }

            }
        });
        switch_reptiles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.i("NavigationDrawer","modificando reptiles a ON");
                    settingsDB.child("reptiles").setValue("ON");
                    Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("NavigationDrawer","modificando reptiles a OFF");
                    settingsDB.child("reptiles").setValue("OFF");
                    Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
