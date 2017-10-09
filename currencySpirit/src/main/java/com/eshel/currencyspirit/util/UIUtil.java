package com.eshel.currencyspirit.util;

import android.widget.Toast;

import com.eshel.currencyspirit.CurrencySpiritApp;

/**
 * createBy Eshel
 * createTime: 2017/10/5 01:46
 * desc: TODO
 */

public class UIUtil {

	private static Toast mToast;

	public static String getString(int resId){
		return CurrencySpiritApp.getContext().getResources().getString(resId);
	}
	public static int getColor(int resId){
		return CurrencySpiritApp.getContext().getResources().getColor(resId);
	}
	public static void toast(final CharSequence text){
		CurrencySpiritApp.getApp().getHandler().post(new Runnable() {
			@Override
			public void run() {
				if(mToast == null) {
					synchronized (Toast.class) {
						if(mToast == null) {
							mToast = Toast.makeText(CurrencySpiritApp.getContext(), text, Toast.LENGTH_LONG);
						}
					}
				}else {
					mToast.setText(text);
				}
				mToast.show();
			}
		});
	}
}
