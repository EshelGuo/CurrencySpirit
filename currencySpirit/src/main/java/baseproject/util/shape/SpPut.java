package baseproject.util.shape;

import android.content.SharedPreferences;
import android.os.Build;

/**
 * Create 2017/9/5 By EshelGuo
 * 该对象可以 链式 往 SharedPreferences 对象中写入数据
 */

public class SpPut {
	private SharedPreferences.Editor editor;

	public SpPut(SharedPreferences.Editor editor) {
		this.editor = editor;
	}

	public SpPut put(String name , Object value){
		Config.putBySP(editor,name,value);
		return this;
	}
	public void commit(){
		if(editor != null) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
				editor.apply();
			} else {
				editor.commit();
			}
			editor = null;
		}
	}
}
