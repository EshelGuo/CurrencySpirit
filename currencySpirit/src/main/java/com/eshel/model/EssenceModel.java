package com.eshel.model;

import com.eshel.currencyspirit.factory.FragmentFactory;
import com.eshel.currencyspirit.fragment.EssenceFragment;

import java.util.ArrayList;

import baseproject.base.BaseFragment;

/**
 * createBy Eshel
 * createTime: 2017/10/5 03:29
 * desc: TODO
 */

public class EssenceModel {
	/**
	 * id : 326
	 * new_type : 1
	 * title : BigONE will launch today!
	 * update_time : 1506873600000
	 * url : https://help.big.one/hc/en-us/articles/115002361094-BigONE-will-launch-today-
	 * webicon : http://static.feixiaohao.com/PlatImages/20171005/7f1317d831384881b4dbae5640bec852_15_15.png
	 * webname : [BigOne]
	 */
	public static ArrayList<EssenceModel> essenceData = new ArrayList<>();
	public int id;
	public int new_type;
	public String title;
	public long update_time;
	public String url;
	public String webicon;
	public String webname;
	public static EssenceModel getEssenceDataByPosition(int position){
		return essenceData.get(position);
	}
	public static void notifyView(boolean isSuccess){
		BaseFragment essenceFragment = (BaseFragment) FragmentFactory.getFragment(EssenceFragment.class);
		if(isSuccess) {
			if (essenceFragment.getCurrState() != BaseFragment.LoadState.StateLoadSuccess)
				essenceFragment.changeState(BaseFragment.LoadState.StateLoadSuccess);
			else {
				essenceFragment.notifyView();
			}
		}else {
			essenceFragment.changeState(BaseFragment.LoadState.StateLoadFailed);
		}
	}
}
