package obe.killua.imagebrowser.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesService {

	public static final int LISTPLAY = 0;
	public static final int CYCLEPLAY = 1;
	public static final int RANDOMPLAY = 2;

	private PreferencesService(){

	}

	public static void SavaBoolean(Context context,boolean bool, String name) {
		Editor edit = context.getSharedPreferences("user",
				Context.MODE_PRIVATE).edit();
		edit.putBoolean(name, bool);
		edit.commit();
	}

	public static boolean GetBoolean(Context context,String name,boolean defvalue) {
		return context.getSharedPreferences("user",
				Context.MODE_PRIVATE).getBoolean(name, defvalue);
	}

	public static void SaveString(Context context,String name,String content){
		Editor edit = context.getSharedPreferences("user",Context.MODE_PRIVATE).edit();
		edit.putString(name, content);
		edit.commit();
	}

	public static String GetString(Context context,String name,String defvalue) {
		return context.getSharedPreferences("user",
				Context.MODE_PRIVATE).getString(name, defvalue);
	}

	public static void SaveInt(Context context,String name,int content){
		Editor edit = context.getSharedPreferences("user",Context.MODE_PRIVATE).edit();
		edit.putInt(name,content);
		edit.commit();
	}

	public static int GetInt(Context context,String name,int defvalue) {
		return context.getSharedPreferences("user",
				Context.MODE_PRIVATE).getInt(name, defvalue);
	}
}
