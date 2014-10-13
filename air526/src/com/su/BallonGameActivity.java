package com.su;

import com.su.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BallonGameActivity extends Activity {
	private ImageView imgshow;
	private Button btnRestart;
	private int level=0,count=0;
	private int[]imgs={R.drawable.balloon0,
			R.drawable.balloon1,R.drawable.balloon2,R.drawable.balloon3,
			R.drawable.balloon4,R.drawable.balloon5,R.drawable.balloon6,R.drawable.balloon7,
			R.drawable.balloon8};
	private sqlEngThread sqlEngine;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        imgshow=(ImageView)findViewById(R.id.imgshow);
        btnRestart=(Button)findViewById(R.id.btnRestart);
        initGame();
        btnRestart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initGame();
			}
		});
    }


	private void initGame() {
		// TODO Auto-generated method stub
		level=0;
		imgshow.setBackgroundResource(imgs[0]);
		btnRestart.setVisibility(View.INVISIBLE);
		sqlEngine=new sqlEngThread(new BallonHandler());
		sqlEngine.startThead();
	}
class BallonHandler extends Handler{
	@Override
	public void handleMessage(Message msg){
		if(msg.what==1){
			int d=msg.arg1;
			if(d>=180){
				count++;
				if(count>=5){
					level=level+1;
					if(level<=8){
						imgshow.setBackgroundResource(imgs[level]);
						if(level==8){
							sqlEngine.stopThead();
							btnRestart.setVisibility(View.VISIBLE);
						}
					}
					count=0;
					}
				}
			}
		}
	}
}

	
