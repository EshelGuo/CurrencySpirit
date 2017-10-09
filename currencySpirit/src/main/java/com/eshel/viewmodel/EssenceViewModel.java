package com.eshel.viewmodel;

import com.eshel.model.EssenceModel;
import com.eshel.net.api.NewListApi;
import com.eshel.net.factory.RetrofitFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import baseproject.util.Log;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * createBy Eshel
 * createTime: 2017/10/5 03:29
 * desc: TODO
 */

public class EssenceViewModel {
	static int start = 0;
	static int count = 20;
	public static void getEssenceData(){
		NewListApi newListApi = RetrofitFactory.getRetrofit().create(NewListApi.class);
		Call<ResponseBody> essence = newListApi.essence(start, count);
		essence.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				if(response.isSuccessful()){
					try {
						String json = response.body().string();
						Gson gson = new Gson();
						ArrayList<EssenceModel> data = gson.fromJson(json, new TypeToken<ArrayList<EssenceModel>>() {
						}.getType());
						start += count;
						EssenceModel.essenceData.addAll(data);
						EssenceModel.notifyView(true);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					String errMsg = "";
					try {
						errMsg = response.errorBody().string();
					} catch (IOException e) {
						e.printStackTrace();
					}
					EssenceModel.notifyView(false);
					Log.e(EssenceViewModel.class,errMsg);
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				EssenceModel.notifyView(false);
				Log.i(call.toString());
				t.printStackTrace();
			}
		});
	}
}
