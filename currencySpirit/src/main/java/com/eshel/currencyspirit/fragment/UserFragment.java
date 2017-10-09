package com.eshel.currencyspirit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eshel.currencyspirit.R;
import com.eshel.currencyspirit.widget.OptionItemView;
import com.eshel.currencyspirit.widget.OverScrollView;

import baseproject.base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * createBy Eshel
 * createTime: 2017/10/4 20:42
 * desc: TODO
 */

public class UserFragment extends BaseFragment {
	SuccessViewHolder mSuccessViewHolder;
	Unbinder unbinder;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		container.postDelayed(new Runnable() {
			@Override
			public void run() {
				changeState(LoadState.StateLoadSuccess);
			}
		}, 500);
		unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public View getLoadSuccessView() {
		View root = View.inflate(getActivity(), R.layout.fragment_user, null);
		mSuccessViewHolder = new SuccessViewHolder();
		ButterKnife.bind(mSuccessViewHolder,root);
		mSuccessViewHolder.itemHistory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(),"历史",Toast.LENGTH_LONG).show();
			}
		});
		mSuccessViewHolder.itemAbout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		mSuccessViewHolder.itemOption.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		mSuccessViewHolder.itemPraised.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		return root;
	}

	@Override
	public void notifyView() {
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
	class SuccessViewHolder{
		@BindView(R.id.item_icon)
		OptionItemView itemIcon;
		@BindView(R.id.item_history)
		OptionItemView itemHistory;
		@BindView(R.id.item_praised)
		OptionItemView itemPraised;
		@BindView(R.id.item_about)
		OptionItemView itemAbout;
		@BindView(R.id.item_option)
		OptionItemView itemOption;
		@BindView(R.id.OverScroller)
		OverScrollView OverScroller;
	}
}
