package com.eshel.net.factory;

import com.eshel.currencyspirit.CurrencySpiritApp;
import com.eshel.net.Url;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * createBy Eshel
 * createTime: 2017/10/5 03:06
 * desc: TODO
 */

public class RetrofitFactory {
	private static Retrofit mRetrofit;

	public static Retrofit getRetrofit(){
		if(mRetrofit == null){
			synchronized (RetrofitFactory.class){
				if(mRetrofit == null){
					OkHttpClient client = new OkHttpClient.Builder()
							.connectTimeout(10000L, TimeUnit.MILLISECONDS)       //设置连接超时
							.readTimeout(10000L,TimeUnit.MILLISECONDS)          //设置读取超时
							.writeTimeout(10000L,TimeUnit.MILLISECONDS)         //设置写入超时
							.cache(new Cache(CurrencySpiritApp.getContext().getCacheDir(),10 * 1024 * 1024))   //设置缓存目录和10M缓存
							.build();
					mRetrofit = new Retrofit.Builder()
							.client(client)
							.baseUrl(Url.baseUrl)
							.build();
				}
			}
		}
		return mRetrofit;
	}

}
