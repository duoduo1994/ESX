package com.example.administrator.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityCollector {
	public static List<Activity> activities = new ArrayList<Activity>();

	public static void addActivity(Activity activity) {
		activities.add(activity);
	}

	public static void finishAll() {
		
		for (int i = 0; i < activities.size(); i++) {
			if (!activities.get(i).isFinishing()) {
				activities.get(i).finish();
				activities.remove(activities.get(i));
			}
		}
	}
}
