package com.harshith.hw9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.harshith.hw9.models.Legislator;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

public class LegislatorInfoActivity extends AppCompatActivity {

	private Legislator mLegislator;

	private ImageView mImageFavorite;
	private ImageView mFacebook;
	private ImageView mTwitter;
	private ImageView mWebsite;

	private ImageView mImageCandidatePhoto;
	private ImageView mImagePartyLogo;
	private TextView mTextViewPartyName;

	private TextView mTextViewName;
	private TextView mTextViewEmail;
	private TextView mTextViewChamber;
	private TextView mTextViewContact;
	private TextView mTextViewStartTerm;
	private TextView mTextViewEndTerm;
	private ProgressBar mProgressbarTerm;
	private TextView mTextViewOffice;
	private TextView mTextViewState;
	private TextView mTextViewFax;
	private TextView mTextViewBirthday;

	private final String APP_SETTINGS="settings";

	private final String FAV_LEGISLATOR_KEY="fav_legislators";

	private final String TAG=getClass().getSimpleName();

	private SharedPreferences mSharePreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_legislator_info);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mLegislator=getIntent().getParcelableExtra("legislator");
		mSharePreferences=getSharedPreferences(APP_SETTINGS,MODE_PRIVATE);
		setupUiComponents();
		populateFields();
	}

	private void setupUiComponents() {

		mImageFavorite = (ImageView)findViewById(R.id.image_favorite);
		mFacebook = (ImageView)findViewById(R.id.image_facebook);
		mTwitter  = (ImageView)findViewById(R.id.image_twitter);
		mWebsite  = (ImageView)findViewById(R.id.image_website);

		mImageCandidatePhoto = (ImageView)findViewById(R.id.image_candidate_picture);
		mImagePartyLogo  = (ImageView)findViewById(R.id.image_party_symbol);
		mTextViewPartyName  = (TextView) findViewById(R.id.text_party_name);

		mTextViewName = (TextView)findViewById(R.id.text_name);
		mTextViewEmail = (TextView)findViewById(R.id.text_email);
		mTextViewChamber = (TextView)findViewById(R.id.text_chamber);
		mTextViewContact = (TextView)findViewById(R.id.text_contact);
		mTextViewStartTerm = (TextView)findViewById(R.id.text_start_term);
		mTextViewEndTerm = (TextView)findViewById(R.id.text_end_term);
		mProgressbarTerm = (ProgressBar)findViewById(R.id.progressBar_term);
		mTextViewOffice = (TextView)findViewById(R.id.text_office);
		mTextViewState = (TextView)findViewById(R.id.text_state);
		mTextViewFax = (TextView)findViewById(R.id.text_fax);
		mTextViewBirthday = (TextView)findViewById(R.id.text_birthday);
	}

	private void populateFields() {
		String nameString;
		String photoUrl="https://theunitedstates.io/images/congress/original/"+mLegislator.getBioguideId()+".jpg";
		Picasso.with(this).load(photoUrl).into(mImageCandidatePhoto);
		if(mLegislator.getParty().equalsIgnoreCase("r")) {
			mImagePartyLogo.setImageResource(R.drawable.ic_party_republican);
			mTextViewPartyName.setText("Republican");
		} else {
			mImagePartyLogo.setImageResource(R.drawable.ic_party_democrate);
			mTextViewPartyName.setText("Democratic");
		}
		nameString = mLegislator.getTitle()+". "+mLegislator.getLastName()+" "+mLegislator.getFirstName();
		mTextViewName.setText(nameString);
		mTextViewEmail.setText(mLegislator.getOcEmail());
		mTextViewChamber.setText(mLegislator.getChamber());
		mTextViewContact.setText(mLegislator.getPhone());
		mTextViewStartTerm.setText(mLegislator.getTermStart());
		mTextViewEndTerm.setText(mLegislator.getTermEnd());
		mTextViewOffice.setText(mLegislator.getOffice());
		mTextViewState.setText(mLegislator.getStateName());
		mTextViewFax.setText(mLegislator.getFax());
		mTextViewBirthday.setText(mLegislator.getBirthday());
		if(mLegislator.getFacebookId()==null) {
			Toast.makeText(this,"Candidate doesn't have facebook Id",Toast.LENGTH_LONG).show();
		} else {
			mFacebook.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					String facebookId = "https://www.facebook.com/"+mLegislator.getFacebookId();
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId));
					startActivity(browserIntent);
				}
			});
		}
		if(mLegislator.getTwitterId()==null) {
			Toast.makeText(this,"Candidate doesn't have twitter Id",Toast.LENGTH_LONG).show();
		} else {
			mTwitter.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					String twitterId="http://www.twitter.com/"+mLegislator.getTwitterId();
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterId));
					startActivity(browserIntent);
				}
			});
		}
		if(mLegislator.getWebsite()==null) {
			Toast.makeText(this,"Candidate doesn't have a website",Toast.LENGTH_LONG).show();
		} else {
			mWebsite.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mLegislator.getWebsite()));
					startActivity(browserIntent);
				}
			});
		}
		setupTerm();
		if(mSharePreferences.contains(FAV_LEGISLATOR_KEY)) {
			Gson gson=new Gson();
			String jsonList=mSharePreferences.getString(FAV_LEGISLATOR_KEY,null);
			Type listType=new TypeToken<HashSet<String>>(){}.getType();
			HashSet<String> favLegislatorIds=gson.fromJson(jsonList,listType);
			if(favLegislatorIds!=null && favLegislatorIds.contains(mLegislator.getBioguideId()))
				mImageFavorite.setImageResource(R.drawable.ic_star_black_24dp);
		}
		mImageFavorite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Gson gson=new Gson();
				String jsonList=mSharePreferences.getString(FAV_LEGISLATOR_KEY,null);
				Log.d(TAG, "onClick: favList"+jsonList);
				Type listType=new TypeToken<HashSet<String>>(){}.getType();
				HashSet<String> favLegislators=gson.fromJson(jsonList,listType);
				if(favLegislators!=null && favLegislators.contains(mLegislator.getBioguideId())) {
					favLegislators.remove(mLegislator.getBioguideId());
					mImageFavorite.setImageResource(R.drawable.ic_star_border_black_24dp);
				} else {
					if(favLegislators==null)
						favLegislators=new HashSet<String>();
					favLegislators.add(mLegislator.getBioguideId());
					mImageFavorite.setImageResource(R.drawable.ic_star_black_24dp);
				}
				Log.d(TAG, "onClick: after toggle"+gson.toJson(favLegislators));
				mSharePreferences.edit().putString(FAV_LEGISLATOR_KEY,gson.toJson(favLegislators)).commit();
			}
		});
	}

	private void setupTerm() {
		int spent;
		int total;
		float percentage;
		Calendar startTerm = new GregorianCalendar();
		Calendar endTerm = new GregorianCalendar();
		Calendar now=new GregorianCalendar();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = sdf.parse(mLegislator.getTermStart());
			startTerm.setTime(date);
			date = sdf.parse(mLegislator.getTermEnd());
			endTerm.setTime(date);
			total=daysBetween(startTerm.getTime(),endTerm.getTime());
			spent=daysBetween(startTerm.getTime(),now.getTime());
			percentage=(spent/total)*100;
			Log.d(TAG, "setupTerm: "+percentage);
			mProgressbarTerm.setProgress((int)percentage);
		}catch (ParseException e) {

		}
	}

	public int daysBetween(Date d1, Date d2){
		return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
	
}
