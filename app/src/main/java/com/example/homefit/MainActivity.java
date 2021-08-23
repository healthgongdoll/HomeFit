package com.example.homefit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView txtMain;
    private ViewPager2 viewPager2;
   private ChipNavigationBar underbar;
 FragmentManager fragmentManager;
    private static final String TAG = "Mainactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Firebase Auth
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, Loading.class);
        startActivity(intent);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null)
        {
            myStartActivity(Sign_Up.class);
        }else{
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(user.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if(document != null)
                        {
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                                myStartActivity(MemberInfo.class);
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });

        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
                                                                                                                                                                                                               
        underbar = findViewById(R.id.underbar);
        underbar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {

                switch (id)
                {
                    case R.id.main:
                        FirebaseAuth.getInstance().signOut();
                        myStartActivity(Sign_Up.class);
                        break;
                    case R.id.friendSearch:
                        Intent intent1 = new Intent(MainActivity.this,Friend.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent1);

                       break;
                    case R.id.btnProfile:
                        Intent intent2 = new Intent(MainActivity.this,Profile.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent2);
                      break;
                }

            }
        });


       viewPager2 = findViewById(R.id.viewPagerImageSlider);

       List<SliderItem> sliderItems= new ArrayList<>();
       sliderItems.add(new SliderItem(R.drawable.menu1,R.string.beginner));
        sliderItems.add(new SliderItem(R.drawable.menu2,R.string.Intermediate));
        sliderItems.add(new SliderItem(R.drawable.menu3,R.string.expert));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(48));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);

            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);




    }



    public void mOnClick(View v)
    {

        int position;
        if(viewPager2.getCurrentItem() == 0)
        {
            Intent intent = new Intent(MainActivity.this,Beginner.class);
            startActivity(intent);
        }
        if(viewPager2.getCurrentItem() == 1)
        {
            Intent intent = new Intent(MainActivity.this,Intermediate.class);
            startActivity(intent);
        }
        if(viewPager2.getCurrentItem() == 2)
        {
            Intent intent = new Intent(MainActivity.this,Expert.class);
            startActivity(intent);
        }
    }
    private void myStartActivity(Class c)
    {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
