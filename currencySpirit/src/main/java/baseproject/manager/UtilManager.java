package baseproject.manager;


import baseproject.base.BaseApplication;
import baseproject.interfaces.Utilable;
import baseproject.util.DensityUtil;
import baseproject.util.Log;
import baseproject.util.shape.ShapeUtil;

/**
 * createBy Eshel
 * createTime: 2017/10/2 15:26
 * desc: 工具类管理类
 */

public class UtilManager {
	static Class<?>[] utilables = {Log.class,DensityUtil.class, ShapeUtil.class};
	public static void initUtils(){
		for (Class<?> utilable : utilables) {
			try {
				Object utilObj = utilable.newInstance();
				if(utilObj instanceof Utilable){
					Utilable util = (Utilable) utilObj;
					util.init(BaseApplication.getContext());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Error e) {
				e.printStackTrace();
			}
		}
		setUtilConfig();
	}

	public static void setUtilConfig(){
		Log.openLog();
		ShapeUtil.setDefaultName("config.sp");
	}

	public static void deinitUtils(){
		for (Class<?> utilable : utilables) {
			try {
				Object utilObj = utilable.newInstance();
				if(utilObj instanceof Utilable){
					Utilable util = (Utilable) utilObj;
					util.deInit();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Error e) {
				e.printStackTrace();
			}
		}
	}
}
