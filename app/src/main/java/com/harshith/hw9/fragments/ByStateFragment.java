package com.harshith.hw9.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harshith.hw9.R;
import com.harshith.hw9.adapters.FragmentAdapter;
import com.harshith.hw9.adapters.LegislatorAdapter;
import com.harshith.hw9.models.Legislator;
import com.harshith.hw9.thirdPartyComponents.FastScrollRecyclerViewItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ByStateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ByStateFragment extends Fragment {

	private List<Legislator> myDataSet = new ArrayList<>();

	HashMap<String, Integer> mapIndex=new HashMap<>();

	private RecyclerView mByStatesRecylcerView;

	public ByStateFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment ByStateFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ByStateFragment newInstance(List<Legislator> dataSet, HashMap<String,Integer> mapIndex) {
		ByStateFragment fragment = new ByStateFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		fragment.myDataSet=dataSet;
		fragment.mapIndex=mapIndex;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_by_state, container, false);
		setupUi(view);
		return view;
	}

	private void setupUi(View view) {
		mByStatesRecylcerView = (RecyclerView) view.findViewById(R.id.recycler_view_by_state);
		mByStatesRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));
		FastScrollRecyclerViewItemDecoration decoration = new FastScrollRecyclerViewItemDecoration(getContext());
		LegislatorAdapter adapter=new LegislatorAdapter(getContext(),myDataSet,mapIndex);
		mByStatesRecylcerView.setAdapter(adapter);
		mByStatesRecylcerView.addItemDecoration(decoration);
		mByStatesRecylcerView.setItemAnimator(new DefaultItemAnimator());
	}

}
