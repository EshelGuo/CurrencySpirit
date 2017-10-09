package baseproject.util.shape;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.util.LruCache;

import java.util.HashSet;
import java.util.Set;

/**
 * Create 2017/9/4 By EshelGuo
 * 该类封装了 SharedPreferences 对象中的 get 方法和 put 方法, 使用该类可以完成对 SharedPreferences 的所有操作
 * 可以 使用 ShapeUtil.open(name,mode) 来拿到 Config 对象
 */
public class Config{
		public static final int MODE_PRIVATE = Context.MODE_PRIVATE;
		public static final int MODE_APPEND = Context.MODE_APPEND;
		public static final int MODE_WORLD_READABLE = Context.MODE_WORLD_READABLE;
		public static final int MODE_WORLD_WRITEABLE = Context.MODE_WORLD_WRITEABLE;
		private static android.support.v4.util.LruCache<String, Config> mCache = new android.support.v4.util.LruCache<String, Config>(4);
		private SharedPreferences sp;

		private Config(String configName, int mode) {
			ShapeUtil.check();
			sp = ShapeUtil.mContext.getSharedPreferences(configName, mode);
			mCache.put(configName,this);
		}
		static Config open(String configName){
			if(mCache == null)
				mCache = new LruCache<String, Config>(4);
			return open(configName,MODE_PRIVATE);
		}
		static void close(){
			if(mCache != null) {
				mCache.evictAll();
				mCache = null;
			}
		}
		static Config open(String configName, int mode){
			Config config = mCache.get(configName);
			if(config != null)
				return config;
			return new Config(configName,mode);
		}
		public void put(String name , Object value){
			SharedPreferences.Editor edit = sp.edit();
			put(edit,name,value);
			edit.commit();
		}
		public SpPut putP(String name , Object value){
			SharedPreferences.Editor edit = sp.edit();
			return new SpPut(edit).put(name,value);
		}
		protected static void putBySP(SharedPreferences.Editor edit, String name, Object value){
			if(value instanceof Integer){
				edit.putInt(name, (Integer) value);
			}else if(value instanceof Boolean){
				edit.putBoolean(name, (Boolean) value);
			}else if(value instanceof String){
				edit.putString(name, (String) value);
			}else if (value instanceof Long)
				edit.putLong(name, (Long) value);
			else if (value instanceof Float)
				edit.putFloat(name, (Float) value);
			else if (value instanceof Set){
				Set<String> set = new HashSet<String>();
				for (Object s : (Set) value) {
					set.add(s.toString());
				}
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					edit.putStringSet(name,set);
				}
			}else {
				edit.putString(name,value.toString());
			}
		}
		private Config put(SharedPreferences.Editor edit, String name, Object value){
			putBySP(edit,name,value);
			return this;
		}
		public <T>T get(String name , T value){
			if (value == null)
				return null;
			Object result = null;
			if(value instanceof Integer){
				result = sp.getInt(name, (Integer) value);
			}else if(value instanceof Boolean){
				result = sp.getBoolean(name, (Boolean) value);
			}else if(value instanceof String){
				result = sp.getString(name, (String) value);
			}else if (value instanceof Long)
				result = sp.getLong(name, (Long) value);
			else if (value instanceof Float)
				result = sp.getFloat(name, (Float) value);
			else if (value instanceof Set){
				Set<String> set = new HashSet<String>();
				for (Object s : (Set) value) {
					set.add(s.toString());
				}
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					result = sp.getStringSet(name,set);
				}
			}else {
				result = sp.getString(name,value.toString());
			}
			if(result.getClass() != value.getClass()){
				throw new ClassCastException("给定值类型跟取出结果类型不相同: \n\t给定默认值 value 类型: "+value.getClass().getName() +"\n\t返回结果类型: "+result.getClass().getName());
			}else {
				T t = (T) result;
				return t;
			}
		}
	}