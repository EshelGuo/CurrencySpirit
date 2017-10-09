package com.eshel.currencyspirit.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshel.currencyspirit.R;
import com.eshel.currencyspirit.factory.FragmentFactory;
import com.eshel.currencyspirit.fragment.CurrencyFragment;
import com.eshel.currencyspirit.fragment.EssenceFragment;
import com.eshel.currencyspirit.fragment.InformationFragment;
import com.eshel.currencyspirit.fragment.UserFragment;
import com.eshel.currencyspirit.util.UIUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import baseproject.base.BaseActivity;
import baseproject.util.DensityUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * createBy Eshel
 * createTime: 2017/10/2 20:22
 * desc: APP 主界面
 */

public class HomeActivity extends BaseActivity {

	@BindView(R.id.bottomBar)
	BottomBar bottomBar;
	@BindView(R.id.vp_main)
	ViewPager vpMain;
	@BindView(R.id.title)
	TextView title;
	@BindView(R.id.toolbar)
	LinearLayout toolbar;
	@BindView(R.id.top_cut_off_line)
	View topCutOffLine;
	@BindView(R.id.cut_off_line)
	View cutOffLine;
	private MainPagerAdapter mMainPagerAdapter;
	private int fragmentSize = 4;

	private final int INDEX_ESSENCE_FRAGMENT = 0;
	private final int INDEX_INFORMATION_FRAGMENT = 1;
	private final int INDEX_CURRENCY_FRAGMENT = 2;
	private final int INDEX_USER_FRAGMENT = 3;

	private int titleElevation = 5;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ButterKnife.bind(this);
		initView();
	}

	public void updateActionBar(String title) {
		this.title.setText(title);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
		}
	}

	private void initView() {
		initActionBar();
		initBottomBar();
		initViewPager();
	}

	private void initActionBar() {
		ViewGroup.LayoutParams layoutParams = topCutOffLine.getLayoutParams();
		if(layoutParams == null)
			layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			toolbar.setElevation(DensityUtil.dp2px(titleElevation/2));
			layoutParams.height = 0;
		} else {
			layoutParams.height = 1;
		}
		topCutOffLine.setLayoutParams(layoutParams);
		updateActionBar(UIUtil.getString(R.string.item_essence));
	}

	private void initViewPager() {
		mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
		vpMain.setAdapter(mMainPagerAdapter);
	}

	private BottomBarTab nearby;

	private void initBottomBar() {
		ViewGroup.LayoutParams layoutParams = topCutOffLine.getLayoutParams();
		if(layoutParams == null)
			layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			bottomBar.setElevation(DensityUtil.dp2px(titleElevation));
			layoutParams.height = 0;
		}else {
			layoutParams.height = 1;
		}
		cutOffLine.setLayoutParams(layoutParams);
		bottomBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				int[] tabIds = {R.id.item_essence, R.id.item_currency, R.id.item_information, R.id.item_user};
				for (int tabId : tabIds) {
					BottomBarTab tab = bottomBar.getTabWithId(tabId);
					for (int i = 0; i < tab.getChildCount(); i++) {
						View child = tab.getChildAt(i);
						switch (child.getId()) {
							case R.id.bb_bottom_bar_icon:
								AppCompatImageView icon = (AppCompatImageView) child;
								LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) icon.getLayoutParams();
								layoutParams.height = 0;
								layoutParams.weight = 3;
								icon.setLayoutParams(layoutParams);
								break;
						}
					}
				}
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					bottomBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
			}
		});

		bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
			@Override
			public void onTabSelected(@IdRes int tabId) {
				int currentItem;
				String title;
				switch (tabId) {
					case R.id.item_essence:
						currentItem = INDEX_ESSENCE_FRAGMENT;
						title = UIUtil.getString(R.string.item_essence);
						break;
					case R.id.item_information:
						currentItem = INDEX_INFORMATION_FRAGMENT;
						title = UIUtil.getString(R.string.item_information);
						break;
					case R.id.item_currency:
						currentItem = INDEX_CURRENCY_FRAGMENT;
						title = UIUtil.getString(R.string.item_currency);
						break;
					case R.id.item_user:
						currentItem = INDEX_USER_FRAGMENT;
						title = UIUtil.getString(R.string.item_user);
						break;
					default:
						currentItem = INDEX_ESSENCE_FRAGMENT;
						title = UIUtil.getString(R.string.item_essence);
						break;
				}
				updateActionBar(title);
				int[] tabIds = {R.id.item_essence, R.id.item_currency, R.id.item_information, R.id.item_user};
				for (int id : tabIds) {
					BottomBarTab tab = bottomBar.getTabWithId(id);
					AppCompatImageView icon = (AppCompatImageView) tab.findViewById(R.id.bb_bottom_bar_icon);
					if (id == tabId) {
						nearby = tab;
						icon.setImageState(new int[]{android.R.attr.state_checked}, true);
					} else {
						icon.setImageState(new int[]{-android.R.attr.state_checked}, true);
					}
				}
				vpMain.setCurrentItem(currentItem, false);
			}
		}, true);
	}

	private class MainPagerAdapter extends FragmentPagerAdapter {

		public MainPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment itemFragment;
			switch (position) {
				case INDEX_ESSENCE_FRAGMENT:
					itemFragment = FragmentFactory.getFragment(EssenceFragment.class);
					break;
				case INDEX_INFORMATION_FRAGMENT:
					itemFragment = FragmentFactory.getFragment(InformationFragment.class);
					break;
				case INDEX_CURRENCY_FRAGMENT:
					itemFragment = FragmentFactory.getFragment(CurrencyFragment.class);
					break;
				case INDEX_USER_FRAGMENT:
					itemFragment = FragmentFactory.getFragment(UserFragment.class);
					break;
				default:
					itemFragment = FragmentFactory.getFragment(EssenceFragment.class);
					break;
			}
			return itemFragment;
		}
		@Override
		public int getCount() {
			return fragmentSize;
		}
	}
	private long lastTime;
	private long exitIntervalTime = 1200;
	@Override
	public void onBackPressed() {
		// two click exit
		if(lastTime == 0){
			lastTime = System.currentTimeMillis();
			UIUtil.toast(UIUtil.getString(R.string.exit_app));
			return;
		}
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastTime < exitIntervalTime){
			super.onBackPressed();
			lastTime = 0;
		}else {
			lastTime = 0;
			onBackPressed();
		}
	}
}
