package com.example.wysse;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.myjson.MyJSONArray;
import com.example.myview.RefreshListView;
import com.example.myview.RefreshListView.RefreshListener;
import com.example.servelet.BaseServelet;

public class SchoolActivites extends Activity {

	static RefreshListView listView;

	static ArrayList<HashMap<String, Object>> mylist;
	static SimpleAdapter mSchedule;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mSchedule.notifyDataSetChanged();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_school_information);

		listView = (RefreshListView) findViewById(R.id.listView1);

		mylist = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 1; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("title", "软件学院活动");
			map.put("publishTime", "2012 12 31");
			mylist.add(map);
		}
		mSchedule = new SimpleAdapter(this, mylist, R.layout.news_item,
				new String[] { "title", "publishTime" }, new int[] {
						R.id.HeadText, R.id.DateText });
		listView.setAdapter(mSchedule);

		listView.setOnRefreshListener(new RefreshListener() {

			@Override
			public void refreshing() {
				try {
					JSONObject jsonobject = BaseServelet.connectGetJSONObject(
							"activity/refresh.php", null, null);
					if (jsonobject != null) {
						try {
							JSONArray jsonarray = jsonobject
									.getJSONArray("titleArray");
							JSONObject[] titleArray = new MyJSONArray(jsonarray)
									.getJSONObjectArray();
							mylist.clear();
							for (int i = 0; i < titleArray.length; i++) {
								HashMap<String, Object> map = new HashMap<String, Object>();
								map.put("title",
										titleArray[i].getString("title"));
								map.put("publishTime",
										titleArray[i].getString("publishTime"));
								map.put("id", titleArray[i].getString("id"));
								mylist.add(map);
							}
						} catch (JSONException e) {
							handler.post(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(getApplicationContext(),
											"解析失败", Toast.LENGTH_SHORT).show();
								}
							});
						} catch (Exception e) {
							handler.post(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(getApplicationContext(),
											"服务器连接失败", Toast.LENGTH_SHORT)
											.show();
								}
							});
						}

					} else {
						handler.post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(),
										"服务器连接失败", Toast.LENGTH_SHORT).show();
							}
						});
					}
				} catch (final Exception e) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					});
				}
			}

			@Override
			public void refreshed() {
				mSchedule.notifyDataSetChanged();
				listView.addFootView();
			}

			int count = 0;
			int index = 0;

			@Override
			public void more() {
				try {
					index = mylist.size();
					String id = (String) mylist.get(index - 1).get("id");

					ArrayList<NameValuePair> message = new ArrayList<NameValuePair>();
					message.add(new BasicNameValuePair("id", id));
					JSONObject jsonobject = BaseServelet.connectGetJSONObject(
							"activity/more.php", message, null);
					if (jsonobject != null) {
						count = 0;
						try {
							JSONArray jsonarray = jsonobject
									.getJSONArray("titleArray");
							JSONObject[] titleArray = new MyJSONArray(jsonarray)
									.getJSONObjectArray();
							count = titleArray.length;
							for (int i = 0; i < titleArray.length; i++) {
								HashMap<String, Object> map = new HashMap<String, Object>();
								map.put("title",
										titleArray[i].getString("title"));
								map.put("publishTime",
										titleArray[i].getString("publishTime"));
								map.put("id", titleArray[i].getString("id"));
								mylist.add(map);
							}
						} catch (JSONException e) {

						} catch (Exception e) {
							handler.post(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(getApplicationContext(),
											"服务器连接失败", Toast.LENGTH_SHORT)
											.show();
								}
							});
						}

					}
				} catch (final Exception e) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					});
				}

			}

			@Override
			public void more_done() {

				listView.finishFootView();

				mSchedule.notifyDataSetChanged();
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 <= 0)
					return;
				Intent intent = new Intent(SchoolActivites.this,
						ContentActivity.class);
				intent.putExtra("id", (String) mylist.get(arg2 - 1).get("id"));
				intent.putExtra("title",
						(String) mylist.get(arg2 - 1).get("title"));
				SchoolActivites.this.startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_school_information, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_reflesh:
			// new Reflesh().start();
		}
		return super.onOptionsItemSelected(item);
	}

}
