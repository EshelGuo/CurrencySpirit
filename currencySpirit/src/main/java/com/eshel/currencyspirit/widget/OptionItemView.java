package com.eshel.currencyspirit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.eshel.currencyspirit.R;
import com.eshel.currencyspirit.widget.util.GlideCircleTransform;

import baseproject.util.DensityUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * createBy Eshel
 * createTime: 2017/10/7 00:50
 * desc: TODO
 */

public class OptionItemView extends FrameLayout {

	@BindView(R.id.iv_icon)
	ImageView ivIcon;
	@BindView(R.id.tv_title)
	TextView tvTitle;
	@BindView(R.id.iv_icon2)
	ImageView ivIcon2;
	private RelativeLayout mRoot;
	private int iconPaddingRight;
	public static final int MODE_TOP = 0;
	public static final int MODE_MIDDLE = 1;
	public static final int MODE_BOTTOM = 2;
	public static final int MODE_ALL = 3;

	public OptionItemView(Context context) {
		this(context, null);
	}

	public OptionItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public OptionItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.OptionItemView);
		int iconId = ta.getResourceId(R.styleable.OptionItemView_item_icon, -1);
		String title = ta.getString(R.styleable.OptionItemView_item_title);
		iconPaddingRight = (int) ta.getDimension(R.styleable.OptionItemView_iconPaddingRight, 0);
		int item_title_size = (int) ta.getInt(R.styleable.OptionItemView_item_title_size, 0);
		int mode = ta.getInt(R.styleable.OptionItemView_item_mode, 3);
		int icon2_visivity = ta.getInt(R.styleable.OptionItemView_item_icon2_visivity, 0);
		int icon_type = ta.getInt(R.styleable.OptionItemView_item_icon_type, 0);
		int item_title_gravity = ta.getInt(R.styleable.OptionItemView_item_title_gravity, 1);
		int item_title_style = ta.getInt(R.styleable.OptionItemView_item_title_style, 1);
		if(item_title_size != 0)
			tvTitle.setTextSize(item_title_size);
		TextPaint tp = tvTitle.getPaint();
		switch (item_title_style){
			case 0:
				tp.setFakeBoldText(true);
				break;
			case 1:
				tp.setFakeBoldText(false);
				break;
		}
		switch (item_title_gravity){
			case 0:
				tvTitle.setPadding(tvTitle.getPaddingLeft(),DensityUtil.dp2px(10),tvTitle.getPaddingRight(),tvTitle.getPaddingBottom());
				tvTitle.setGravity(Gravity.TOP);
				break;
			case 1:
				tvTitle.setGravity(Gravity.CENTER_VERTICAL);
				break;
			case 2:
				tvTitle.setGravity(Gravity.BOTTOM);
				tvTitle.setPadding(tvTitle.getPaddingLeft(),tvTitle.getPaddingTop(),tvTitle.getPaddingRight(),DensityUtil.dp2px(10));
				break;
		}

		if(iconId != -1)
			setIcon(iconId,icon_type);
		if(title != null)
			tvTitle.setText(title);
		setPaddingRight(iconPaddingRight);
		ivIcon2.setVisibility(icon2_visivity);
		if(mode != -1)
			setMode(mode);
		ta.recycle();
	}
	OnClickListener l;
	@Override
	public void setOnClickListener(OnClickListener l) {
		this.l = l;
		super.setOnClickListener(l);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(l != null)
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					tvTitle.setBackgroundColor(0xffF0F0F0);
					ivIcon.setBackgroundColor(0xffF0F0F0);
					break;
				default:
					tvTitle.setBackgroundColor(0xffffffff);
					ivIcon.setBackgroundColor(0xffffffff);
					break;
			}
		return super.onTouchEvent(event);
	}

	private int modeChange = -1;
	private void init() {
		mRoot = (RelativeLayout) View.inflate(getContext(), R.layout.view_option_item, null);
		ButterKnife.bind(this,mRoot);
		addView(mRoot);
		mRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				if(modeChange != -1) {
					setMode(modeChange);
					modeChange = -1;
				}
				ViewGroup.LayoutParams layoutParams = tvTitle.getLayoutParams();
				if(layoutParams != null)
					layoutParams.height = mRoot.getHeight();
				tvTitle.setLayoutParams(layoutParams);
				ViewGroup.LayoutParams params = ivIcon.getLayoutParams();
				params.width = (int) (mRoot.getHeight() * 1.0f+ OptionItemView.this.iconPaddingRight - DensityUtil.dp2px(10));
				ivIcon.setLayoutParams(params);
				modeChange = 3;
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					mRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
			}
		});
	}
	public void setIcon(int resId){
		setIcon(resId,TYPE_NORMAL);
	}
	private final int TYPE_CENTER = 1;
	private final int TYPE_NORMAL = 0;
	public void setIcon(int resId,int iconType){
		DrawableTypeRequest<Integer> load = Glide.with(getContext())
				.load(resId);
		if(iconType == TYPE_CENTER)
			load.transform(new GlideCircleTransform(getContext()));
		load.into(ivIcon);
	}
	public void setPaddingRight(int paddingRight){
		ivIcon.setPadding(ivIcon.getPaddingLeft(),ivIcon.getPaddingTop(),paddingRight,ivIcon.getPaddingBottom());
	}
	public void setTitle(CharSequence title){
		tvTitle.setText(title);
	}
	public void setMode(int mode){
		if(this.modeChange == -1) {
			modeChange = mode;
			return;
		}
		RelativeLayout.LayoutParams iconParams = (RelativeLayout.LayoutParams) ivIcon.getLayoutParams();
		RelativeLayout.LayoutParams titleParams = (RelativeLayout.LayoutParams) tvTitle.getLayoutParams();
		if(iconParams == null)
			iconParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		if(titleParams == null)
			titleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		int dp1 = DensityUtil.dp2px(1);
		switch (mode){
			case MODE_TOP:
				iconParams.topMargin = dp1;
				titleParams.topMargin = dp1;
				iconParams.bottomMargin = 0;
				titleParams.bottomMargin = dp1/2;
				break;
			case MODE_MIDDLE:
				iconParams.topMargin = 0;
				titleParams.topMargin = dp1/2;
				iconParams.bottomMargin = 0;
				titleParams.bottomMargin = dp1/2;
				break;
			case MODE_BOTTOM:
				iconParams.topMargin = 0;
				titleParams.topMargin = dp1/2;
				iconParams.bottomMargin = dp1;
				titleParams.bottomMargin = dp1;
				break;
			case MODE_ALL:
				iconParams.topMargin = dp1;
				titleParams.topMargin = dp1;
				iconParams.bottomMargin = dp1;
				titleParams.bottomMargin = dp1;
				break;
		}
		ivIcon.setLayoutParams(iconParams);
		tvTitle.setLayoutParams(titleParams);
	}
}
