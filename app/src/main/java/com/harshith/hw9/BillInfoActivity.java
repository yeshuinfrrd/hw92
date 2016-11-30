package com.harshith.hw9;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.harshith.hw9.models.Bill;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;

public class BillInfoActivity extends AppCompatActivity {

	private Bill mBill;

	private TextView mBillId;
	private TextView mBillTitle;
	private TextView mBillType;
	private TextView mBillSponsor;
	private TextView mChamber;
	private TextView mBillStatus;
	private TextView mBillIntroDate;
	private TextView mCongressUrl;
	private TextView mBillVersionStatus;
	private TextView mBillUrl;
	private ImageView favoriteIcon;

	private final String APP_SETTINGS="settings";
	private final String FAV_BILLS_KEY="fav_bills";
	private final String TAG=getClass().getSimpleName();
	private SharedPreferences mSharePreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill_info);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		mBill=getIntent().getParcelableExtra("bill");
		setSupportActionBar(toolbar);
		mSharePreferences=getSharedPreferences(APP_SETTINGS,MODE_PRIVATE);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		setupUiComponents();
		populateData();
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.home)
			finish();
		return super.onOptionsItemSelected(item);
	}

	private void setupUiComponents() {
		mBillId=(TextView)findViewById(R.id.text_view_bill_id);
		mBillTitle=(TextView)findViewById(R.id.text_title);
		mBillType=(TextView)findViewById(R.id.text_bill_type);
		mBillSponsor=(TextView)findViewById(R.id.text_sponsor);
		mChamber=(TextView)findViewById(R.id.text_chamber);
		mBillStatus=(TextView)findViewById(R.id.text_status);
		mBillIntroDate=(TextView)findViewById(R.id.text_intro_date);
		mCongressUrl=(TextView)findViewById(R.id.text_congress_url);
		mBillVersionStatus=(TextView)findViewById(R.id.text_version_status);
		mBillUrl=(TextView)findViewById(R.id.text_bill_url);
		favoriteIcon=(ImageView)findViewById(R.id.image_bill_favorite);
	}

	private void populateData() {
		mBillId.setText(mBill.getBillId());
		mBillTitle.setText(mBill.getOfficialTitle());
		mBillType.setText(mBill.getBillType());
		if(mBill.getSponsor()!=null) {
			String sponsor = mBill.getSponsor().getTitle() + ". " + mBill.getSponsor().getLastName() + " " + mBill.getSponsor().getFirstName();
			mBillSponsor.setText(sponsor);
		} else {
			mBillSponsor.setText("N/A");
		}
		mChamber.setText(mBill.getChamber());
		if(mBill.getHistory()!=null) {
			if(mBill.getHistory().getActive()) {
				mBillStatus.setText("Active");
			} else {
				mBillStatus.setText("InActive");
			}
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outputFormat=new SimpleDateFormat("MMM dd,yyyy");
		try {
			String date=outputFormat.format(sdf.parse(mBill.getIntroducedOn()));
			mBillIntroDate.setText(date);
		} catch (ParseException e) {
			mBillIntroDate.setText("N/A");
			e.printStackTrace();
		}
		if(mBill.getUrls()!=null) {
			mCongressUrl.setText(mBill.getUrls().getCongress());
		} else {
			mCongressUrl.setText("N/A");
		}
		if(mBill.getLastVersion()!=null) {
			mBillVersionStatus.setText(mBill.getLastVersion().getVersionName());
		} else {
			mBillVersionStatus.setText("N/A");
		}
		if(mBill.getLastVersion()!=null && mBill.getLastVersion().getUrls()!=null) {
			mBillUrl.setText(mBill.getLastVersion().getUrls().getPdf());
		} else {
			mBillUrl.setText("N/A");
		}
		if(mSharePreferences.contains(FAV_BILLS_KEY)) {
			Gson gson=new Gson();
			String jsonList=mSharePreferences.getString(FAV_BILLS_KEY,null);
			Type listType=new TypeToken<HashSet<String>>(){}.getType();
			HashSet<String> favBillIds=gson.fromJson(jsonList,listType);
			if(favBillIds!=null && favBillIds.contains(mBill.getBillId()))
				favoriteIcon.setImageResource(R.drawable.ic_star_black_24dp);
		}
		favoriteIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Gson gson=new Gson();
				String jsonList=mSharePreferences.getString(FAV_BILLS_KEY,null);
				Log.d(TAG, "onClick: favList"+jsonList);
				Type listType=new TypeToken<HashSet<String>>(){}.getType();
				HashSet<String> favBills=gson.fromJson(jsonList,listType);
				if(favBills!=null && favBills.contains(mBill.getBillId())) {
					favBills.remove(mBill.getBillId());
					favoriteIcon.setImageResource(R.drawable.ic_star_border_black_24dp);
				} else {
					if(favBills==null)
						favBills=new HashSet<String>();
					favBills.add(mBill.getBillId());
					favoriteIcon.setImageResource(R.drawable.ic_star_black_24dp);
				}
				Log.d(TAG, "onClick: after toggle"+gson.toJson(favBills));
				mSharePreferences.edit().putString(FAV_BILLS_KEY,gson.toJson(favBills)).commit();
			}
		});
	}
	
}
