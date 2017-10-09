package baseproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.eshel.currencyspirit.CurrencySpiritApp;
import com.eshel.currencyspirit.R;

/**
 * 项目名称: BaseProject
 * 创建人: Eshel
 * 创建时间:2017/10/2 14时42分
 * 描述: TODO
 */

public abstract class BaseFragment extends Fragment{
	protected boolean onViewCreated = false;
	private FrameLayout mControl;
	private LoadState mState;
	private LoadState stateChanged;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(mControl == null) {
			mControl = new FrameLayout(getActivity());
			mControl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//			setInitView();
			changeState(LoadState.StateLoading);
		}
		onViewCreated = true;
		if(stateChanged != null){
			LoadState tempState = stateChanged;
			stateChanged = null;
			changeState(tempState);
		}
		return mControl;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		onViewCreated = false;
		super.onDestroyView();
	}

	private void setInitView(){
		TextView tv = new TextView(getActivity());
		tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		tv.setGravity(Gravity.CENTER);
		tv.setText(getTitle());
		mControl.removeAllViews();
		mControl.addView(tv);
	}
	private void setLoadingView(){
		View loadingView = View.inflate(getActivity(), R.layout.fragment_loading, null);
		mControl.removeAllViews();
		mControl.addView(loadingView);
	}
	private void setLoadFailedView(){
		View loadFailedView = View.inflate(getActivity(),R.layout.fragment_load_failed,null);
		mControl.removeAllViews();
		mControl.addView(loadFailedView);
	}
	private String getTitle(){
		return getClass().getSimpleName();
	}
	public void changeState(LoadState state){
		if(!onViewCreated) {
			stateChanged = state;
			return;
		}
		mState = state;
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				switch (mState){
					case StateNoLoad:
						setInitView();
						break;
					case StateLoading:
						setLoadingView();
						break;
					case StateLoadFailed:
						setLoadFailedView();
						break;
					case StateLoadSuccess:
						setLoadSuccessView();
						break;
				}
			}
		});
	}
	private void setLoadSuccessView(){
		mControl.removeAllViews();
		mControl.addView(getLoadSuccessView());
	}
	public abstract View getLoadSuccessView();
	public enum LoadState{
		StateLoading,StateLoadFailed,StateLoadSuccess,StateNoLoad
	}
	public LoadState getCurrState(){
		return mState;
	}
	public abstract void notifyView();
}
