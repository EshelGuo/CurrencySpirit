package com.eshel.currencyspirit.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eshel.currencyspirit.R;
import com.eshel.currencyspirit.util.UIUtil;
import com.eshel.currencyspirit.widget.RecycleViewDivider;
import com.eshel.model.EssenceModel;
import com.eshel.viewmodel.EssenceViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import baseproject.base.BaseFragment;
import baseproject.util.DensityUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * createBy Eshel
 * createTime: 2017/10/4 20:41
 * desc: 精华 fragment
 */

public class EssenceFragment extends BaseFragment {

	private RecyclerView mRv_essence;
	private EssenceAdapter mEssenceAdapter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EssenceViewModel.getEssenceData();
	}

	@Override
	public View getLoadSuccessView() {
		ViewGroup parent = (ViewGroup) View.inflate(getActivity(), R.layout.view_essence, null);
		mRv_essence = (RecyclerView) parent.findViewById(R.id.rv_essence);
		mRv_essence.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
		mRv_essence.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.HORIZONTAL,
				DensityUtil.dp2px(1),UIUtil.getColor(R.color.dividerColor),DensityUtil.dp2px(10),DensityUtil.dp2px(10)));
		mEssenceAdapter = new EssenceAdapter();
		mRv_essence.setAdapter(mEssenceAdapter);
		return parent;
	}

	@Override
	public void notifyView() {
		mEssenceAdapter.notifyDataSetChanged();
	}

	public class EssenceAdapter extends RecyclerView.Adapter<EssenceViewHolder> {

		@Override
		public EssenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new EssenceViewHolder();
		}

		@Override
		public void onBindViewHolder(EssenceViewHolder holder, int position) {
			holder.bindDataToView(EssenceModel.getEssenceDataByPosition(position));
		}

		@Override
		public int getItemCount() {
			ArrayList<EssenceModel> essenceData = EssenceModel.essenceData;
			if(essenceData != null)
				return essenceData.size();
			return 0;
		}
	}

	public class EssenceViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.time)
		TextView time;
		@BindView(R.id.title)
		TextView title;
		@BindView(R.id.icon)
		ImageView icon;
		private SimpleDateFormat mFormat;

		public EssenceViewHolder() {
			super(LayoutInflater.from(getActivity()).inflate(R.layout.item_essence, null));
			ButterKnife.bind(this,itemView);
			icon.setImageDrawable(new ColorDrawable(0xFFE7E7E7));
			icon.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					icon.setMinimumHeight((int) (icon.getWidth() * 0.75f));
					icon.setMaxHeight((int) (icon.getWidth() * 0.75f));
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
						itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
					}
				}
			});
		}
		public void bindDataToView(EssenceModel essenceModel){
			if(mFormat == null)
				mFormat = new SimpleDateFormat(UIUtil.getString(R.string.item_time_format), Locale.getDefault());
			time.setText(mFormat.format(new Date(essenceModel.update_time)));
			title.setText(essenceModel.title);
			Glide.with(getActivity()).
					load(essenceModel.webicon)
					.into(icon);
		}
	}
}
