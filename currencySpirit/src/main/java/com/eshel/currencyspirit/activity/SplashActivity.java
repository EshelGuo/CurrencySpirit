package com.eshel.currencyspirit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.eshel.currencyspirit.CurrencySpiritApp;
import com.eshel.currencyspirit.R;

import baseproject.base.BaseActivity;

/**
 * createBy Eshel
 * createTime: 2017/10/2 20:19
 * desc: 通用的 APP 欢迎界面
 */

public class SplashActivity extends BaseActivity {

	private int lifeTime = 300;
	private Runnable finishSplashTask = new Runnable() {
		@Override
		public void run() {
			enterHomeActivity();
		}
	};
	private Runnable mainTask = new Runnable() {
		@Override
		public void run() {
			updateNewVersion();
		}
	};

	private void updateNewVersion() {
		// TODO: 2017/10/4 检查更新
	}

	private void enterHomeActivity() {
		Intent intent = new Intent(this,HomeActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		getWindow().setBackgroundDrawableResource(R.drawable.splash);
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getSupportActionBar();
		if(actionBar != null)
			actionBar.hide();
		CurrencySpiritApp.getApp().getHandler().postDelayed(finishSplashTask,lifeTime);
		new Thread(mainTask).start();
	}
}
