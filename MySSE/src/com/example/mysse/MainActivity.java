package com.example.mysse;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.wysse.R;
import com.example.wysse.SchoolActivites;

public class MainActivity extends TabActivity implements
		TabHost.TabContentFactory {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final TabHost tabHost = this.getTabHost();
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("学院新闻")
				.setContent(new Intent(this, SchoolInformation.class)));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("学院通知")
				.setContent(new Intent(this, SchoolNews.class)));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("学院活动")
				.setContent(new Intent(this, SchoolActivites.class)));

		TabWidget tabWidget = tabHost.getTabWidget();
		int count = tabWidget.getChildCount();
		for (int i = 0; i < count; i++) {
			View view = tabWidget.getChildTabViewAt(i);
			view.getLayoutParams().height = 80;
			final TextView tv = (TextView) view
					.findViewById(android.R.id.title);
			tv.setTextSize(20);
		}

	}

	@Override
	public View createTabContent(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

}
