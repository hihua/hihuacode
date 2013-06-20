package com.apps.game.market.activity;

import com.apps.game.market.R;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.util.Numeric;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

public class AppDownload extends Activity {
		
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		TextView text = new TextView(this);
		text.setText("测试");
		setContentView(text);
		
		//http://blog.csdn.net/dellheng/article/details/7176551
		//http://blog.csdn.net/panyongjie2577/article/details/7346033
		//http://blog.csdn.net/qinjuning/article/details/6915482
		//http://blog.csdn.net/liuhe688/article/details/6425225
		//http://blog.csdn.net/liuhe688/article/details/6586353
		//http://blog.csdn.net/liuhe688/article/details/6591089
		//http://blog.csdn.net/liuhe688/article/details/6623924
		 
	}
}
