package baseproject.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import baseproject.manager.UtilManager;

/**
 * 项目名称: BaseProject
 * 创建人: Eshel
 * 创建时间:2017/10/2 14时42分
 * 描述: TODO
 */

public class BaseApplication extends Application{
private static Context mContext;
private static Handler mHandler;
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		mHandler = new Handler();
		UtilManager.initUtils();
	}

	@Override
	public void onTerminate() {
		UtilManager.deinitUtils();
		mContext = null;
		mHandler = null;
		super.onTerminate();
	}
	public static Context getContext(){
		return mContext;
	}

	public Handler getHandler() {
		return mHandler;
	}
}
