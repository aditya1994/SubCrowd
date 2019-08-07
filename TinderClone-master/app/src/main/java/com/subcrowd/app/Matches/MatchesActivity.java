package com.subcrowd.app.Matches;


import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.subcrowd.app.ChooseLoginRegistrationActivity;
import com.subcrowd.app.MainActivity;
import com.subcrowd.app.R;
import com.subcrowd.app.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMatchesAdapter;
    private RecyclerView.LayoutManager mMatchesLayoutManager;
    private ImageButton mBack;


    private String cusrrentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        mBack = findViewById(R.id.matchesBack);

        cusrrentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mMatchesLayoutManager = new LinearLayoutManager(MatchesActivity.this);
        mRecyclerView.setLayoutManager(mMatchesLayoutManager);
        mMatchesAdapter = new MatchesAdapter(getDataSetMatches(), MatchesActivity.this);
        mRecyclerView.setAdapter(mMatchesAdapter);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MatchesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
        getUserMatchId();
    }

    private void getUserMatchId() {

        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(cusrrentUserID).child("connections").child("matches");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(DataSnapshot match : dataSnapshot.getChildren()){
                        FetchMatchInformation(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String userId = dataSnapshot.getKey();
                    String name = "";
                    String profileImageUrl = "";
                    String need = "";
                    String give = "";
                    String budget = "";
                    String lastMessage = "";
                    String lastTimeStamp = "";

                    if(dataSnapshot.child("name").getValue()!=null){
                        name = dataSnapshot.child("name").getValue().toString();
                    }
                    if(dataSnapshot.child("profileImageUrl").getValue()!=null){
                        profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                    }

                    if(dataSnapshot.child("need").getValue() != null){
                        need = dataSnapshot.child("need").getValue().toString();
                    }
                    if(dataSnapshot.child("give").getValue() != null){
                        give = dataSnapshot.child("give").getValue().toString();
                    }
                    if(dataSnapshot.child("budget").getValue() != null){
                        budget = dataSnapshot.child("budget").getValue().toString();
                    }
                    if(dataSnapshot.child("lastMessage").getValue() != null){
                        lastMessage = dataSnapshot.child("lastMessage").getValue().toString();
                    }
                    if(dataSnapshot.child("lastTimeStamp").getValue() != null){
                        String milliSec = dataSnapshot.child("lastTimeStamp").getValue().toString();
                        Long now;

                        try {
                            now = Long.parseLong(milliSec);
                            lastTimeStamp = convertMilliToRelativeTime(now);
                            String[] arrOfStr = lastTimeStamp.split(",");
                            lastTimeStamp = arrOfStr[0];
                        } catch (Exception e) {}

                    }


                    MatchesObject obj = new MatchesObject(userId, name, profileImageUrl, need, give, budget, lastMessage, lastTimeStamp);
                    resultsMatches.add(obj);
                    mMatchesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public String convertMilliToRelativeTime(Long now) {
        String time = DateUtils.getRelativeDateTimeString(this, now, DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
        return time;
    }

    private ArrayList<MatchesObject> resultsMatches = new ArrayList<MatchesObject>();
    private List<MatchesObject> getDataSetMatches() {
        return resultsMatches;
    }

}
