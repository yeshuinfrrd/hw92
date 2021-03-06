package com.harshith.hw9;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.harshith.hw9.fragments.BillsFragment;
import com.harshith.hw9.fragments.CommitteeFragment;
import com.harshith.hw9.fragments.FavoritesFragment;
import com.harshith.hw9.fragments.LegislatorFragment;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		getSupportActionBar().setTitle("Legislators");
		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.tab_layout_container, LegislatorFragment.newInstance())
				.addToBackStack("legislator")
				.commit();
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		if(id == R.id.home)
			super.onBackPressed();

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		FragmentManager fragmentManager=getSupportFragmentManager();
		// Handle navigation view item clicks here.
		switch (item.getItemId()) {
			case R.id.nav_legislators:
				getSupportActionBar().setTitle("Legislators");
				fragmentManager.beginTransaction()
						.replace(R.id.tab_layout_container,LegislatorFragment.newInstance(),"legislatorFragment").commit();
				break;
			case R.id.nav_bills:
				getSupportActionBar().setTitle("Bills");
				fragmentManager.beginTransaction()
						.replace(R.id.tab_layout_container,BillsFragment.newInstance(),"billsFragment").commit();
				break;
			case R.id.nav_committees:
				getSupportActionBar().setTitle("Committtee");
				fragmentManager.beginTransaction()
						.replace(R.id.tab_layout_container, CommitteeFragment.newInstance(),"committeeFragment").commit();
				break;
			case R.id.nav_favorites:
				getSupportActionBar().setTitle("Favorites");
				fragmentManager.beginTransaction()
						.replace(R.id.tab_layout_container, FavoritesFragment.newInstance(),"legislatorFragment").commit();
				break;
			case R.id.nav_about_me:
				Intent intent=new Intent(this,AboutMeActivity.class);
				startActivity(intent);
				break;
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

}
