package com.dabai.feedback;

import android.app.*;
import android.os.*;
import android.widget.*;
import java.text.*;
import java.net.*;
import java.util.*;
import android.graphics.*;
import android.view.View.*;
import android.view.*;


//记得在清单声明在后台隐藏
public class FeedBack extends Activity 
{
	private EditText ed1;
	private Button bu;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		setTheme(android.R.style.Theme_Material_Light_Dialog_Alert);
        super.onCreate(savedInstanceState);
	
		setTitle("反馈");
		LinearLayout ll1 = new LinearLayout(this);
		ll1.setOrientation(1);
		ed1 = new EditText(this);
		bu = new Button(this);
		ed1.setHint("请输入要反馈的问题");
		bu.setText("发送反馈");
		
		ll1.addView(ed1);
		ll1.addView(bu);
		bu.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					feedback(getString(R.string.app_name), ed1.getText().toString());			
				}
			});
        setContentView(ll1);
    }

	public void feedback(final String title, final String text)
	{
		new Thread(new Runnable(){

				private int qucode;

				@Override
				public void run()
				{
					try
					{
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss EEE", Locale.CHINA);
						String titlem = "[来自:" + Build.MODEL + "的反馈]";
						String textm = "---[" + sdf.format(new Date()) + "]";

						URL url = new URL("https://sc.ftqq.com/这里是你的SCKEY哦.send?text=" + title + titlem + "&desp=" + text + textm);
						HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
						urlConnection.setRequestMethod("GET");
						urlConnection.connect();
						final int code=urlConnection.getResponseCode();
						qucode = code;	
						runOnUiThread(new Runnable(){
								@Override
								public void run()
								{
									if (code == 200)
									{
										Toast.makeText(getApplicationContext(), "发送成功", 1).show();
										finish();
									}	
									else
									{
										Toast.makeText(getApplicationContext(), "发送失败", 1).show();
									}
								}
							});
					}
					catch (Exception e)
					{
						Toast.makeText(getApplicationContext(), "ERROR", 1).show();
					}
				}
			}).start();
	}

}
