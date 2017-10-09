package baseproject.util.shape;

import android.content.Context;

import baseproject.interfaces.Utilable;


/**
 * Created by EshelGuo on 2017/6/10.
 * 一个简单的 SharedPreferences 存储的工具类, 封装了 SharedPreferences 里的一些方法, 使用起来更加简单
 */

public class ShapeUtil implements Utilable {
	protected static Context mContext;
	public static String DEFAULT_NAME;
	public static String lastConfigName;

	/**
	 * 该工具类依赖于一个 Context 对象, 该方法就是为了拿到 Context 对象
	 * 使用工具类前必须先调用 init() 方法传入一个 Context 对象
	 * 建议在 Application 类的 onCreate() 方法中 调用该方法传入一个 Context 对象
	 * @param context 上下文对象
	 */
	public void init(Context context){
		init(context,"default.config");
	}

	@Override
	public void deInit() {
		mContext = null;
	}

	public static void init(Context context,String DEFAULT_NAME){
		mContext = context.getApplicationContext();
		ShapeUtil.DEFAULT_NAME = DEFAULT_NAME;
	}

	/**
	 * 由于 工具类 持有的 Context 对象是 static 的, 所以需要在不需要工具类时 调用该方法将工具类关闭
	 * 建议在 Application 的 onTerminate() 方法中将工具类关闭
	 */
	public static void close(){
		mContext = null;
		Config.close();
	}

	public static void setDefaultName(String defaultName){
		if(defaultName != null)
			DEFAULT_NAME = defaultName;
	}
	/**
	 * 根据 一个 配置名 , 拿到一个 配置文件
	 * @param configName 配置名称
	 * @return Config 对象
	 * @see Config
	 */
	public static Config getConfig(String configName){
		return Config.open(configName);
	}

	/**
	 * 拿到上次使用的 Config 对象
	 * @return Config 对象
	 */
	public static Config getLastConfig(){
		if(lastConfigName != null)
			return Config.open(lastConfigName);
		return null;
	}

	/**
	 * 拿到一个默认的 Config 对象
	 * @return Config
	 */
	public static Config getDefaultConfig(){
		return Config.open(DEFAULT_NAME);
	}

	/**
	 * 存储数据到 默认的 Config 对象中
	 */
	public static void put(String name, Object value){
		getDefaultConfig().put(name,value);
	}

	/**
	 * 使用默认的 Config 对象来 put 数据
	 * 使用方法:<br>
	 * 		<pre>
	 ShapeUtil.putP(name,value)
			  .putP(name,value)
			  .putP(name,value)
			  .putP(name,value)
			  .commit();</pre>
	 * @return 返回一个 SpPut 对象 , 该对象可以进行链式存储数据
	 */
	public static SpPut putP(String name, Object value){
		return Config.open(DEFAULT_NAME).putP(name,value);
	}

	/**
	 * 使用默认的 Config 对象 取数据
	 * @param name key
	 * @param defValue 没有数据时的默认值
	 * @return 返回取到的数据
	 */
	public static  <T>T get(String name,T defValue){
		return getDefaultConfig().get(name,defValue);
	}
	protected static void check(){
		if(mContext == null){
			throw new NullPointerException("必须先调用 ShapeUtil.init() 方法进行初始化");
		}
	}
}