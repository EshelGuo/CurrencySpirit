package com.eshel.currencyspirit;

import baseproject.base.BaseApplication;

/**
 * createBy Eshel
 * createTime: 2017/10/4 20:24
 * desc: TODO
 */

public class CurrencySpiritApp extends BaseApplication{
	private static CurrencySpiritApp app;
	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
	}

	@Override
	public void onTerminate() {
		app = null;
		super.onTerminate();
	}

	public static CurrencySpiritApp getApp() {
		return app;
	}
}
