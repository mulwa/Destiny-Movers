package com.example.gen.destinymovers;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gen.destinymovers.Pojo.Request;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class reguest_truck extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText m_truck, m_note;
    private TextView m_pick_address, m_drop_address,m_cost;
    private Button btn_request;
    private ProgressDialog mProgressDialog;
    private FirebaseDatabase database;
    private DatabaseReference  requestRef;
    private LatLng pickLatlng, dropLatLng;
    private String cost;
    private String pick_address, drop_address;
    private Double distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reguest_truck);
        initializeUI();
        getIntentData();
        database  = FirebaseDatabase.getInstance();
        requestRef  = database.getReference("requests");

        m_pick_address.setText(pick_address);
        m_drop_address.setText(drop_address);
        m_cost.setText(String.valueOf(distance));

        toolbar  = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()  != null){
            getSupportActionBar().setTitle("Request Transport");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInputs()){
                    submitRequest();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void initializeUI(){
        m_pick_address = findViewById(R.id.ed_pick_address);
        m_drop_address =  findViewById(R.id.ed_drop_address);
        m_cost = findViewById(R.id.ed_cost);
        m_truck = findViewById(R.id.ed_truck_type);
        m_note = findViewById(R.id.ed_desc);
        btn_request = findViewById(R.id.btnrequest);


    }
    private void getIntentData(){
        pick_address = getIntent().getStringExtra("PICK_ADDRESS");
        drop_address = getIntent().getStringExtra("DROP_ADDRESS");
        pickLatlng = getIntent().getExtras().getParcelable("PICK_LATLNG");
        dropLatLng = getIntent().getExtras().getParcelable("DROPLATLNG");
        distance = getIntent().getExtras().getDouble("DISTANCE");

    }
    private void submitRequest(){
        showDialog();
        String truck = m_truck.getText().toString().trim();
        String note = m_note.getText().toString().trim();

        Request request = new Request(pick_address,pickLatlng,drop_address,dropLatLng,cost,truck,note);

        requestRef.push().setValue(request, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                hideDialog();
                if(databaseError == null){
                    showToast("Your  Request has been submited");
                    clearUi();
//                    finish();
                }else {
                    showToast("An error has occured Please try again later"+databaseError.getMessage());
                }
            }
        });

    }
    private void clearUi(){
        m_pick_address.setText("");
        m_drop_address.setText("");
        m_note.setText("");
        m_cost.setText("");
        m_truck.setText("");
    }
    private boolean validateInputs(){
        if(TextUtils.isEmpty(m_pick_address.getText().toString().trim())){
            showToast("No pickup location");
            return false;
        }
        if(TextUtils.isEmpty(m_truck.getText().toString().trim())){
            showToast("Please Enter  the type of truck you want");
            return false;
        }
        if(TextUtils.isEmpty(m_note.getText().toString().trim())){
            showToast("Please Enter note about your request");
        }
        return true;
    }
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    private void showDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getApplicationContext());
            mProgressDialog.setMessage("Sending your  request Please wait...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();

    }

    private void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

    }
}
