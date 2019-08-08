package com.subcrowd.app.Matches;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.subcrowd.app.Chat.ChatActivity;
import com.subcrowd.app.R;

/**
 * Created by manel on 10/31/2017.
 */

public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView mMatchId, mMatchName, mLastTimeStamp, mLastMessage, mNeed, mGive, mBudget, mProfile;
    public ImageView mMatchImage;
    public MatchesViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchId = (TextView) itemView.findViewById(R.id.Matchid);
        mMatchName = (TextView) itemView.findViewById(R.id.MatchName);
        mLastMessage = (TextView) itemView.findViewById(R.id.lastMessage);
        mLastTimeStamp = (TextView) itemView.findViewById(R.id.lastTimeStamp);

        mNeed = (TextView) itemView.findViewById(R.id.needid);
        mGive = (TextView) itemView.findViewById(R.id.giveid);
        mBudget = (TextView) itemView.findViewById(R.id.budgetid);
        mMatchImage = (ImageView) itemView.findViewById(R.id.MatchImage);
        mProfile = (TextView) itemView.findViewById(R.id.profileid);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("matchId", mMatchId.getText().toString());
        b.putString("matchName", mMatchName.getText().toString());
        b.putString("lastMessage", mLastMessage.getText().toString());
        b.putString("lastTimeStamp", mLastTimeStamp.getText().toString());
        b.putString("budget", mBudget.getText().toString());
        b.putString("need", mNeed.getText().toString());
        b.putString("give", mGive.getText().toString());
        //Log.d("blah", mNeed.getText().toString() );
        b.putString("profile", mProfile.getText().toString());
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }
}
