package com.example.myslidemenu;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myslidemenu.adapter.DrawerListAdapter;
import com.example.myslidemenu.model.Laptop;

public class MainActivity extends Activity {

	DrawerLayout drawer_layout;
	ListView lvSlideMenu;
	ActionBarDrawerToggle drawerToggle;

	String[] laptopNames;
	TypedArray laptopIcons;

	ArrayList<Laptop> laptopList;

	DrawerListAdapter adapter;

	CharSequence drawerTitle;
	CharSequence appTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
		lvSlideMenu = (ListView) findViewById(R.id.lvSlideMenu);

		laptopNames = getResources().getStringArray(R.array.array_laptop_names);
		laptopIcons = getResources().obtainTypedArray(
				R.array.array_laptop_icons);

		laptopList = new ArrayList<Laptop>();
		laptopList.add(new Laptop(laptopNames[0], laptopIcons.getResourceId(0,
				-1)));
		laptopList.add(new Laptop(laptopNames[1], laptopIcons.getResourceId(1,
				-1)));
		laptopList.add(new Laptop(laptopNames[2], laptopIcons.getResourceId(2,
				-1)));
		laptopList.add(new Laptop(laptopNames[3], laptopIcons.getResourceId(3,
				-1)));
		laptopList.add(new Laptop(laptopNames[4], laptopIcons.getResourceId(4,
				-1)));
		
		laptopIcons.recycle();

		adapter = new DrawerListAdapter(getApplicationContext(), laptopList);

		lvSlideMenu.setAdapter(adapter);

		lvSlideMenu.setOnItemClickListener(new SlideMenuItemClickListener());

		drawerTitle = appTitle = getTitle();

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(this, drawer_layout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
			@Override
			public void onDrawerClosed(View drawerView) {
				getActionBar().setTitle(appTitle);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(drawerTitle);
				invalidateOptionsMenu();
			}
		};

		drawer_layout.setDrawerListener(drawerToggle);

		if (savedInstanceState == null) {
			setFragmentToContentView(0);
		}

	}

	private class SlideMenuItemClickListener implements
			ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			setFragmentToContentView(position);
		}
	}

	public void setFragmentToContentView(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new AppleFragment();
			break;
		case 1:
			fragment = new AsusFragment();
			break;
		case 2:
			fragment = new DellFragment();
			break;
		case 3:
			fragment = new HPFragment();
			break;
		case 4:
			fragment = new LenovoFragment();
			break;
		default:
			break;
		}

		if (fragment != null) {
			FragmentManager manager = getFragmentManager();
			manager.beginTransaction()
					.replace(R.id.fragment_container, fragment).commit();
			lvSlideMenu.setItemChecked(position, true);
			lvSlideMenu.setSelection(position);
			setTitle(laptopNames[position]);
			drawer_layout.closeDrawer(lvSlideMenu);
		} else {
			Log.e("MainActivity", "Error in creating Fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		appTitle = title;
		getActionBar().setTitle(appTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = drawer_layout.isDrawerOpen(lvSlideMenu);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// add functionality for toggling nav-drawer upon clicking action bar
		// app icon/title
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		drawerToggle.onConfigurationChanged(newConfig);
	}

}
