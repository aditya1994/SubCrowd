package com.subcrowd.app;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.onesignal.OneSignal;
import androidx.appcompat.app.AlertDialog;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private EditText mNameField, mPhoneField;
    private ProgressBar spinner;
    private Button mConfirm;
    private ImageButton mBack;
    private ImageView mProfileImage;
    private EditText mbudget;
    private Spinner need, give;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    private String userId, name, phone, profileImageUrl, userSex, userBudget, userNeed, userGive;
    private int needIndex, giveIndex;
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinner = (ProgressBar)findViewById(R.id.pBar);
        spinner.setVisibility(View.GONE);

        mNameField = (EditText) findViewById(R.id.name);
        mPhoneField = (EditText) findViewById(R.id.phone);

        mProfileImage = (ImageView) findViewById(R.id.profileImage);
        mBack = findViewById(R.id.settingsBack);

        mConfirm = (Button) findViewById(R.id.confirm);
        mbudget = (EditText) findViewById(R.id.budget_setting);
        need = (Spinner) findViewById(R.id.spinner_need_setting);
        give = (Spinner) findViewById(R.id.spinner_give_setting);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth != null && mAuth.getCurrentUser()!= null)
            userId = mAuth.getCurrentUser().getUid();
        else {

            finish();
        }

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.services, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        need.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter_give = ArrayAdapter.createFromResource(this,
                R.array.services, android.R.layout.simple_spinner_item);
        adapter_give.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        give.setAdapter(adapter_give);

        getUserInfo();

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                spinner.setVisibility(View.GONE);
                return;
            }
        });

        //toolbar
        Toolbar toolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);
    }
    //shows options for menu toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    //logout button pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          if(item.getItemId() == R.id.ContactUs) {
                new AlertDialog.Builder(SettingsActivity.this)
                    .setTitle("Contact Us")
                    .setMessage("Contact us: sub.crowd.app@gmail.com")
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("Dismiss", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            }  else if (item.getItemId() == R.id.logout) {
                  spinner.setVisibility(View.VISIBLE);
                  OneSignal.setSubscription(false);
                  mAuth.signOut();
                  Toast.makeText(this,"Log Out successful", Toast.LENGTH_LONG).show();
                  Intent intent = new Intent(SettingsActivity.this, ChooseLoginRegistrationActivity.class);
                  startActivity(intent);
                  finish();
                  spinner.setVisibility(View.GONE);
          }
         else if(item.getItemId() == R.id.deleteAccount) {
            new AlertDialog.Builder(SettingsActivity.this)
                    .setTitle("Are you sure?")
                    .setMessage("Deleting your account will result in completely removing your account from the system")
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    spinner.setVisibility(View.VISIBLE);
                                    if(task.isSuccessful()) {
                                        deleteUserAccount(userId);
                                        Toast.makeText(getApplicationContext(), "Account deleted successfully!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(SettingsActivity.this, ChooseLoginRegistrationActivity.class);
                                        startActivity(intent);
                                        finish();
                                        spinner.setVisibility(View.GONE);
                                        return;
                                    } else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        mAuth.signOut();
                                        Intent intent = new Intent(SettingsActivity.this, ChooseLoginRegistrationActivity.class);
                                        startActivity(intent);
                                        finish();
                                        spinner.setVisibility(View.GONE);
                                        return;
                                    }
                                }
                            });

                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("Dismiss", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }
    public void deleteMatch(String matchId, String chatId) {
        Log.d("delete", chatId);
        DatabaseReference matchId_in_UserId_dbReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("connections").child("matches").child(matchId);
        DatabaseReference userId_in_matchId_dbReference = FirebaseDatabase.getInstance().getReference().child("Users").child(matchId).child("connections").child("matches").child(userId);
        DatabaseReference yeps_in_matchId_dbReference = FirebaseDatabase.getInstance().getReference().child("Users").child(matchId).child("connections").child("yeps").child(userId);
        DatabaseReference yeps_in_userId_dbReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("connections").child("yeps").child(matchId);

        DatabaseReference matchId_chat_dbReference = FirebaseDatabase.getInstance().getReference().child("Chat").child(chatId);

        //delete the chatId in chat->chatId
        matchId_chat_dbReference.removeValue();
        //delete the matchId in Users->userId->connections->matches->matchId
        matchId_in_UserId_dbReference.removeValue();
        //delete the userId in Users->matchId->connections->matches->matchId
        userId_in_matchId_dbReference.removeValue();
        //delete yeps in matchId
        yeps_in_matchId_dbReference.removeValue();
        //delete yeps in curruserId
        yeps_in_userId_dbReference.removeValue();


    }
    public void deleteUserAccount(String userId) {
        DatabaseReference curruser_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        DatabaseReference curruser_matches_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("connections").child("matches");

        curruser_matches_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(DataSnapshot match : dataSnapshot.getChildren()){
                        deleteMatch(match.getKey(), match.child("ChatId").getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        curruser_matches_ref.removeValue();
        curruser_ref.removeValue();
    }

    private void getUserInfo() {
        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name")!=null){
                        name = map.get("name").toString();
                        mNameField.setText(name);
                    }
                    if(map.get("phone")!=null){
                        phone = map.get("phone").toString();
                        mPhoneField.setText(phone);
                    }
                    if(map.get("sex")!=null){
                        userSex = map.get("sex").toString();
                    }
                    if(map.get("budget")!= null){
                        userBudget = map.get("budget").toString();
                    }
                    else
                        userBudget = "0";
                    if(map.get("give") != null){
                        userGive = map.get("give").toString();

                    }
                    else
                        userGive = "";
                    if(map.get("need") != null){
                        userNeed = map.get("need").toString();
                    }
                    else
                        userNeed = "";
                    String[] services = getResources().getStringArray(R.array.services);
                    needIndex = giveIndex = 0;
                    for(int i = 0; i< services.length; i++){
                        if(userNeed.equals(services[i]))
                            needIndex = i;
                        if(userGive.equals(services[i]))
                            giveIndex = i;
                    }
                    //Log.d("setting", userNeed);
                    need.setSelection(needIndex);
                    give.setSelection(giveIndex);
                    mbudget.setText(userBudget);

                    Glide.clear(mProfileImage);
                    if(map.get("profileImageUrl")!=null){
                        profileImageUrl = map.get("profileImageUrl").toString();
                        switch(profileImageUrl){
                            case "default":
                                Glide.with(getApplication()).load(R.drawable.default_man).into(mProfileImage);
                                break;
                            default:
                                Glide.with(getApplication()).load(profileImageUrl).into(mProfileImage);
                                break;
                        }
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void saveUserInformation() {
        name = mNameField.getText().toString();
        phone = mPhoneField.getText().toString();
        userBudget = mbudget.getText().toString();
        userGive = give.getSelectedItem().toString();
        userNeed = need.getSelectedItem().toString();

        Map userInfo = new HashMap();
        userInfo.put("name", name);
        userInfo.put("phone", phone);
        userInfo.put("need", userNeed);
        userInfo.put("give", userGive);
        userInfo.put("budget", userBudget);
        mUserDatabase.updateChildren(userInfo);
        if(resultUri != null){
            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uri.isComplete());
                    Uri downloadUrl = uri.getResult();
                    Map userInfo = new HashMap();
                    userInfo.put("profileImageUrl", downloadUrl.toString());
                    mUserDatabase.updateChildren(userInfo);

                    finish();
                    return;
                }
            });
        }else{
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            mProfileImage.setImageURI(resultUri);
        }
    }

    //now logout is done using the toolbar button
   /* public void logoutUser(View view) {
        mAuth.signOut();
        Intent intent = new Intent(SettingsActivity.this, ChooseLoginRegistrationActivity.class);
        startActivity(intent);
        finish();
        return;
    }*/
}
