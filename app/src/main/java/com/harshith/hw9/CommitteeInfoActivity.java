package com.harshith.hw9;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.harshith.hw9.models.Committee;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

public class CommitteeInfoActivity extends AppCompatActivity {

	private Committee mCommittee;
	private TextView committeeId;
	private TextView committeeName;
	private TextView committeeChamber;
	private TextView committeeParentCommittee;
	private TextView committeeContact;
	private TextView committeeOffice;

	private ImageView favoriteIcon;
	private ImageView chamberIcon;

	private SharedPreferences mSharePreferences;

	private final String APP_SETTINGS="settings";

	private final String FAV_COMMITTEE_KEY="fav_committees";

	private final String TAG=getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_committee_info);
		mCommittee=getIntent().getParcelableExtra("committee");
		setupUiComponents();
		populateData();
		mSharePreferences=getSharedPreferences(APP_SETTINGS,MODE_PRIVATE);
	}

	private void setupUiComponents() {
		committeeId = (TextView)findViewById(R.id.text_committee_id);
		committeeName = (TextView)findViewById(R.id.text_committee_name);
		committeeChamber = (TextView)findViewById(R.id.text_chamber);
		committeeParentCommittee = (TextView)findViewById(R.id.text_parent_committee);
		committeeContact = (TextView)findViewById(R.id.text_committee_phone);
		committeeOffice = (TextView)findViewById(R.id.text_office);
		favoriteIcon = (ImageView)findViewById(R.id.image_committee_favorite);
		chamberIcon=(ImageView)findViewById(R.id.image_chamber);
	}

	private void populateData(){
		committeeId.setText(mCommittee.getCommitteeId());
		committeeName.setText(mCommittee.getName());
		committeeChamber.setText(mCommittee.getChamber());
		if(mCommittee.getChamber().equalsIgnoreCase("senate"))
			chamberIcon.setImageResource(R.drawable.ic_senate);
		else if(mCommittee.getChamber().equalsIgnoreCase("house"))
			chamberIcon.setImageResource(R.drawable.ic_house);
		committeeParentCommittee.setText(mCommittee.getParentCommitteeId());
		committeeContact.setText(mCommittee.getPhone());
		committeeOffice.setText(mCommittee.getOffice());
		if(mSharePreferences!=null && mSharePreferences.contains(FAV_COMMITTEE_KEY)) {
			Gson gson=new Gson();
			String jsonList=mSharePreferences.getString(FAV_COMMITTEE_KEY,null);
			Type listType=new TypeToken<HashSet<String>>(){}.getType();
			HashSet<String> favCommitteeIds=gson.fromJson(jsonList,listType);
			if(favCommitteeIds!=null && favCommitteeIds.contains(mCommittee.getCommitteeId()))
				favoriteIcon.setImageResource(R.drawable.ic_star_black_24dp);
		}
		favoriteIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Gson gson=new Gson();
				String jsonList=mSharePreferences.getString(FAV_COMMITTEE_KEY,null);
				Log.d(TAG, "onClick: favList"+jsonList);
				Type listType=new TypeToken<HashSet<String>>(){}.getType();
				HashSet<String> favCommittees=gson.fromJson(jsonList,listType);
				if(favCommittees!=null && favCommittees.contains(mCommittee.getCommitteeId())) {
					favCommittees.remove(mCommittee.getCommitteeId());
					favoriteIcon.setImageResource(R.drawable.ic_star_border_black_24dp);
				} else {
					if(favCommittees==null)
						favCommittees=new HashSet<String>();
					favCommittees.add(mCommittee.getCommitteeId());
					favoriteIcon.setImageResource(R.drawable.ic_star_black_24dp);
				}
				Log.d(TAG, "onClick: after toggle"+gson.toJson(favCommittees));
				mSharePreferences.edit().putString(FAV_COMMITTEE_KEY,gson.toJson(favCommittees)).commit();
			}
		});
	}
}
