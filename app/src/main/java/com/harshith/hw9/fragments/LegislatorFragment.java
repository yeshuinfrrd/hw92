package com.harshith.hw9.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harshith.hw9.R;
import com.harshith.hw9.adapters.FragmentAdapter;
import com.harshith.hw9.adapters.LegislatorAdapter;
import com.harshith.hw9.thirdPartyComponents.FastScrollRecyclerViewItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LegislatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LegislatorFragment extends Fragment {

	private HashMap<String, Integer> mapIndex=new HashMap<>();
	private ArrayList<String> myDataSet=new ArrayList<>();

	private TabLayout mTabLayout;
	private ViewPager mViewPager;

	public LegislatorFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @return A new instance of fragment LegislatorFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static LegislatorFragment newInstance() {
		LegislatorFragment fragment = new LegislatorFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
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
		View view= inflater.inflate(R.layout.fragment_legislator, container, false);
		setupTabs(view);
		setupData();
		return view;
	}

	private void setupData() {
		for(int i=0; i<26; i++) {
			myDataSet.add(Character.toString((char)(65 + i)) + " Row item");
		}
		mapIndex = calculateIndexesForName(myDataSet);
	}

	private HashMap<String, Integer> calculateIndexesForName(ArrayList<String> items){
		HashMap<String, Integer> mapIndex = new LinkedHashMap<String, Integer>();
		for (int i = 0; i<items.size(); i++){
			String name = items.get(i);
			String index = name.substring(0,1);
			index = index.toUpperCase();

			if (!mapIndex.containsKey(index)) {
				mapIndex.put(index, i);
			}
		}
		return mapIndex;
	}

	private void setupTabs(View view) {
		mViewPager = (ViewPager)view.findViewById(R.id.view_pager_container);
		mTabLayout = (TabLayout)view.findViewById(R.id.section_tabs);
		mTabLayout.addTab(mTabLayout.newTab().setText("By State"));
		mTabLayout.addTab(mTabLayout.newTab().setText("House"));
		mTabLayout.addTab(mTabLayout.newTab().setText("Senate"));
		ArrayList<Fragment> fragmentList = new ArrayList<>();
		fragmentList.add(ByStateFragment.newInstance(myDataSet,mapIndex));
		fragmentList.add(ByStateFragment.newInstance(myDataSet,mapIndex));
		fragmentList.add(ByStateFragment.newInstance(myDataSet,mapIndex));
		FragmentAdapter adapter=new FragmentAdapter(getFragmentManager(),getContext(),fragmentList);
		mViewPager.setAdapter(adapter);
		mViewPager.setOffscreenPageLimit(fragmentList.size());
		mTabLayout.setupWithViewPager(mViewPager);
	}

}
