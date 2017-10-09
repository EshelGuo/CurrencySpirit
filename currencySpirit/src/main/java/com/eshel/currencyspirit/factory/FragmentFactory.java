package com.eshel.currencyspirit.factory;

import android.support.v4.app.Fragment;
import java.util.HashMap;

/**
 * createBy Eshel
 * createTime: 2017/10/4 21:46
 * desc: Fragment 工厂类
 */

public class FragmentFactory {
	private static HashMap<Class,Fragment> mFragments = new HashMap<>();
	public static Fragment getFragment(Class clazz){
		Fragment fragment = mFragments.get(clazz);
		try {
			if(fragment == null){
				Object obj = clazz.newInstance();
				if(obj instanceof Fragment){
					fragment = (Fragment) obj;
					mFragments.put(clazz,fragment);
				}
			}
			return fragment;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
