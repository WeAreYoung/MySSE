package com.example.wysse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class ContentActivity extends Activity {

	TextView titleText;
	WebView webview;

	private ProgressDialog progressBar;

	String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_content);

		Intent intent = getIntent();
		id = intent.getStringExtra("id");

		titleText = (TextView) findViewById(R.id.TitleText);
		titleText.setText(intent.getStringExtra("title"));

		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setBlockNetworkImage(true);
		webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

		progressBar = ProgressDialog.show(this, "加载", "我正在努力加载哦~~~");

		webview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			public void onPageFinished(WebView view, String url) {
				if (progressBar.isShowing()) {
					progressBar.dismiss();
				}
			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(ContentActivity.this, "Oh no! " + description,
						Toast.LENGTH_SHORT).show();
				new AlertDialog.Builder(ContentActivity.this).setTitle("出错啦！")
						.setMessage(description).setPositiveButton("确定", null)
						.show();

			}
		});
		webview.loadUrl("http://192.168.1.100/content.php?id=" + id);
	}
}
