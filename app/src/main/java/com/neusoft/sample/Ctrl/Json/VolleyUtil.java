package com.neusoft.sample.Ctrl.Json;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyUtil {

	private volatile static RequestQueue requestQueue;


	public static RequestQueue getQueue(Context context) {
		if (requestQueue == null) {
			synchronized (VolleyUtil.class) {
				if (requestQueue == null) {
					requestQueue = Volley.newRequestQueue(context.getApplicationContext());
				}
			}
		}
		return requestQueue;
	}
}
