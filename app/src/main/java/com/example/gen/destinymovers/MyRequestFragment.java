package com.example.gen.destinymovers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gen.destinymovers.Pojo.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyRequestFragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase database;
    private RecyclerView mRecyclerView;
    private FirebaseUser currentUser;
    private DatabaseReference userReqRef;
    private ProgressDialog mProgressDialog;
    private List<Request> requestList = new ArrayList<>();
    private MyRequestAdapter myRequestAdapter;
    public MyRequestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_request_fragment,container,false);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRecyclerView =  view.findViewById(R.id.rv_myRequest);
        userReqRef = database.getReference("requests").child("ByUser");

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if(currentUser != null){
                    LoadUserRequests(currentUser.getUid());

                }else {
                    showToast("Please Login First");
                }

            }
        };

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }

    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    private void LoadUserRequests(String userId){
        showDialog();
        userReqRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hideDialog();
                requestList.clear();
                for(DataSnapshot requestSnapshot : dataSnapshot.getChildren()){
                    Request request = requestSnapshot.getValue(Request.class);
                    requestList.add(request);

                }
//                set up recyclerview followed by  adapter
                myRequestAdapter = new MyRequestAdapter(requestList,getContext());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

                mRecyclerView.setAdapter(myRequestAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                hideDialog();

                showToast("An  error has occured:"+databaseError.getMessage());

            }
        });
    }
    private void showDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Please wait Fetching Your Request....");
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
