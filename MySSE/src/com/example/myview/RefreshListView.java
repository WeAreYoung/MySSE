package com.example.myview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wysse.R;

public class RefreshListView extends ListView implements OnScrollListener {

	private float mDownY;
	private float mMoveY;

	private int mCurrentScrollState;

	private final static int NONE_PULL_REFRESH = 0;
	private final static int ENTER_PULL_REFRESH = 1;
	private final static int OVER_PULL_REFRESH = 2;
	private final static int EXIT_PULL_REFRESH = 3;
	private int mPullRefreshState = 0;

	private final static int REFRESH_BACKING = 0;
	private final static int REFRESH_BACED = 1;
	private final static int REFRESH_RETURN = 2;
	private final static int REFRESH_DONE = 3;

	private final static int FOOTER_DONE = 4;
	private final static int FOOTER_DONE_NONE = 5;

	private LinearLayout mHeaderLinearLayout = null;
	private LinearLayout mFooterLinearLayout = null;
	private TextView mHeaderTextView = null;
	private ProgressBar mHeaderProgressBar = null;
	private TextView mFooterTextView = null;
	private ProgressBar mFooterProgressBar = null;

	private RefreshListener mRefreshListener = null;

	public void setOnRefreshListener(RefreshListener refreshListener) {
		this.mRefreshListener = refreshListener;
	}

	public RefreshListView(Context context) {
		super(context);
	}

	public RefreshListView(Context context, AttributeSet attr) {
		super(context, attr);
		init(context);
	}

	void init(final Context context) {
		mHeaderLinearLayout = (LinearLayout) LayoutInflater.from(context)
				.inflate(R.layout.refresh_list_header, null);
		addHeaderView(mHeaderLinearLayout);
		mHeaderTextView = (TextView) findViewById(R.id.refresh_list_header_text);
		mHeaderProgressBar = (ProgressBar) findViewById(R.id.refresh_list_header_progressbar);

		mFooterLinearLayout = (LinearLayout) LayoutInflater.from(context)
				.inflate(R.layout.refresh_list_footer, null);
		addFooterView(mFooterLinearLayout);
		mFooterProgressBar = (ProgressBar) findViewById(R.id.refresh_list_footer_progressbar);
		mFooterTextView = (TextView) mFooterLinearLayout
				.findViewById(R.id.refresh_list_footer_text);
		mFooterLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (context.getString(R.string.app_list_footer_more).equals(
						mFooterTextView.getText())) {
					mFooterTextView.setText(R.string.app_list_footer_loading);
					mFooterProgressBar.setVisibility(View.VISIBLE);
					if (mRefreshListener != null) {

						new Thread() {
							public void run() {
								if (mRefreshListener != null) {
									mRefreshListener.more();
								}
								Message msg = mHandler.obtainMessage();
								msg.what = FOOTER_DONE;
								mHandler.sendMessage(msg);
							};
						}.start();
					}
				}
			}

		});

		setSelection(1);
		setOnScrollListener(this);
		mFooterLinearLayout.setPadding(mFooterLinearLayout.getPaddingLeft(),
				mFooterLinearLayout.getPaddingTop(),
				mFooterLinearLayout.getPaddingRight(), 600);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			mMoveY = event.getY();
			if (mPullRefreshState == OVER_PULL_REFRESH) {
				mHeaderLinearLayout.setPadding(
						mHeaderLinearLayout.getPaddingLeft(),
						(int) ((mMoveY - mDownY) / 3),
						mHeaderLinearLayout.getPaddingRight(),
						mHeaderLinearLayout.getPaddingBottom());
			}
			break;
		case MotionEvent.ACTION_UP:
			if (mPullRefreshState == OVER_PULL_REFRESH
					|| mPullRefreshState == ENTER_PULL_REFRESH) {
				new Thread() {
					public void run() {
						Message msg;
						while (mHeaderLinearLayout.getPaddingTop() > 1) {
							msg = mHandler.obtainMessage();
							msg.what = REFRESH_BACKING;
							mHandler.sendMessage(msg);
							try {
								Thread.sleep(5);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						msg = mHandler.obtainMessage(0);
						if (mPullRefreshState == OVER_PULL_REFRESH) {
							msg.what = REFRESH_BACED;
						} else {
							msg.what = REFRESH_RETURN;
						}
						mHandler.sendMessage(msg);
					}
				}.start();
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
				&& firstVisibleItem == 0
				&& (mHeaderLinearLayout.getBottom() >= 0 && mHeaderLinearLayout
						.getBottom() < mHeaderLinearLayout.getMeasuredHeight())) {
			mPullRefreshState = ENTER_PULL_REFRESH;
			mHeaderTextView.setText("下拉刷新");
		} else if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
				&& firstVisibleItem == 0
				&& (mHeaderLinearLayout.getBottom() >= mHeaderLinearLayout
						.getMeasuredHeight())) {
			if (mPullRefreshState == ENTER_PULL_REFRESH
					|| mPullRefreshState == NONE_PULL_REFRESH) {
				mPullRefreshState = OVER_PULL_REFRESH;
				mDownY = mMoveY;
				mHeaderTextView.setText("松手刷新");
			}
		} else if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
				&& firstVisibleItem != 0) {
			if (mPullRefreshState == ENTER_PULL_REFRESH) {
				mPullRefreshState = NONE_PULL_REFRESH;
			}
		} else if (mCurrentScrollState == SCROLL_STATE_FLING
				&& firstVisibleItem == 0) {
			if (mPullRefreshState == NONE_PULL_REFRESH) {
				setSelection(1);
			}
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mCurrentScrollState = scrollState;
		mFooterLinearLayout.setPadding(mFooterLinearLayout.getPaddingLeft(),
				mFooterLinearLayout.getPaddingTop(),
				mFooterLinearLayout.getPaddingRight(), getHeight()
						- mFooterLinearLayout.getHeight());

	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		setSelection(1);

	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_BACKING:
				mHeaderLinearLayout.setPadding(
						mHeaderLinearLayout.getPaddingLeft(),
						(int) (mHeaderLinearLayout.getPaddingTop() * 0.75f),
						mHeaderLinearLayout.getPaddingRight(),
						mHeaderLinearLayout.getPaddingBottom());
				break;

			case REFRESH_BACED:
				mHeaderTextView.setText("正在加载...");
				mHeaderProgressBar.setVisibility(View.VISIBLE);
				mPullRefreshState = EXIT_PULL_REFRESH;
				new Thread() {
					public void run() {
						if (mRefreshListener != null) {
							mRefreshListener.refreshing();
						}
						Message msg = mHandler.obtainMessage();
						msg.what = REFRESH_DONE;
						mHandler.sendMessage(msg);
					};
				}.start();
				break;

			case REFRESH_RETURN:
				mHeaderTextView.setText("下拉刷新");
				mHeaderProgressBar.setVisibility(View.INVISIBLE);
				mHeaderLinearLayout.setPadding(
						mHeaderLinearLayout.getPaddingLeft(), 0,
						mHeaderLinearLayout.getPaddingRight(),
						mHeaderLinearLayout.getPaddingBottom());
				mPullRefreshState = NONE_PULL_REFRESH;
				setSelection(1);
				break;

			case REFRESH_DONE:
				mHeaderTextView.setText("下拉刷新");
				mHeaderProgressBar.setVisibility(View.INVISIBLE);
				mHeaderLinearLayout.setPadding(
						mHeaderLinearLayout.getPaddingLeft(), 0,
						mHeaderLinearLayout.getPaddingRight(),
						mHeaderLinearLayout.getPaddingBottom());
				mPullRefreshState = NONE_PULL_REFRESH;
				setSelection(1);
				if (mRefreshListener != null) {
					mRefreshListener.refreshed();
					setSelection(1);
				}
				break;

			case FOOTER_DONE:
				finishFootView();
				if (mRefreshListener != null) {
					mRefreshListener.more_done();
				}
				break;

			default:
				break;
			}
		}
	};

	public interface RefreshListener {
		void refreshing();

		void refreshed();

		void more();

		void more_done();
	}

	public void finishFootView() {
		mFooterProgressBar.setVisibility(View.INVISIBLE);
		mFooterTextView.setText(R.string.app_list_footer_more);
	}

	public void addFootView() {
		if (getFooterViewsCount() == 0) {
			addFooterView(mFooterLinearLayout);
		}
	}

	public void removeFootView() {
		if (getFooterViewsCount() != 0) {
			removeFooterView(mFooterLinearLayout);
		}
	}

}
