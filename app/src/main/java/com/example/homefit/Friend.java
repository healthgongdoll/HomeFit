package com.example.homefit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Friend extends AppCompatActivity {

    private EditText friendSearch;
    private Button search;
    private TextView txtFriend;
    private ImageView imgfriend;
    private TextView friendlevelText;
    private Button addfriend;
    private DatabaseReference mDatabase;
    private static final String TAG = "hello";
    ChipNavigationBar underbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();




        underbar = findViewById(R.id.underbar);
        underbar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
                                               @Override
                                               public void onItemSelected(int id) {

                                                   switch (id) {
                                                       case R.id.main:
                                                           Intent intent = new Intent(Friend.this, MainActivity.class);
                                                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                           getApplicationContext().startActivity(intent);
                                                           break;
                                                       case R.id.friendSearch:
                                                           Intent intent1 = new Intent(Friend.this, Friend.class);
                                                           intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                           getApplicationContext().startActivity(intent1);

                                                           break;
                                                       case R.id.btnProfile:
                                                           Intent intent2 = new Intent(Friend.this, Profile.class);
                                                           intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                           getApplicationContext().startActivity(intent2);
                                                           break;
                                                   }
                                               }
                                           });



        search = findViewById(R.id.btnSearch);
        txtFriend = findViewById(R.id.txtFriend);
        friendSearch = findViewById(R.id.edtFriend);
        imgfriend = findViewById(R.id.imgFriend);
        friendlevelText = findViewById(R.id.friendlvl);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("users")
                        .whereEqualTo("id",friendSearch.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        txtFriend.append(document.get("name").toString());
                                        friendlevelText.setText("Lv. " + document.get("level").toString());
                                        Glide.with(Friend.this).load(document.get("photoUrl")).into(imgfriend);
                                    }
                                } else {
                                    startToast("Fail");
                                }
                            }
                        });

            }
        });
    }


            private void startToast(String msg) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }


}
