package com.harshith.hw9.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harshith.hw9.R;
import com.harshith.hw9.thirdPartyComponents.FastScrollRecyclerViewInterface;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yashw on 24-11-2016.
 */

public class LegislatorAdapter extends RecyclerView.Adapter<LegislatorAdapter.LegislatorViewHolder> implements FastScrollRecyclerViewInterface {

	private ArrayList<String> dataSet;
	private HashMap<String, Integer> mMapIndex;
	private Context mContext;

	public LegislatorAdapter(Context context, ArrayList<String> dataSet, HashMap<String, Integer> mapIndex) {
		this.dataSet=dataSet;
		this.mMapIndex=mapIndex;
		mContext=context;
	}

	@Override
	public LegislatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_legislator,parent,false);
		return new LegislatorViewHolder(v);
	}

	@Override
	public void onBindViewHolder(LegislatorViewHolder holder, int position) {
		holder.textViewName.setText(dataSet.get(position));
	}

	@Override
	public int getItemCount() {
		if(dataSet!=null)
			return dataSet.size();
		else
			return 0;
	}

	@Override
	public HashMap<String, Integer> getMapIndex() {
		return this.mMapIndex;
	}

	public class LegislatorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

		TextView textViewName;
		TextView textViewDetails;
		ImageView imageThumbNail;

		public LegislatorViewHolder(View itemView) {
			super(itemView);
			textViewName=(TextView)itemView.findViewById(R.id.text_view_name);
			textViewDetails=(TextView)itemView.findViewById(R.id.subHeader);
			imageThumbNail=(ImageView)itemView.findViewById(R.id.image_view_thumbnail);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
//			Intent intent=new Intent(mContext,LegislatorInfo.class);
		}
	}
}
