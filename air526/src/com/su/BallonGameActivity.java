package com.su;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BallonGameActivity extends Activity {
	private ImageView imgshow;
	private Button btnRestart;
	private int level=0,count=0;
	private int[]imgs={R.drawable.balloon0,
			R.drawable.balloon1,R.drawable.balloon2,R.drawable.balloon3,
			R.drawable.balloon4,R.drawable.balloon5,R.drawable.balloon6,R.drawable.balloon7,
			R.drawable.balloon8};
	private sqlEngThread sqlEngine;
	
	private int recLen = 0; 
    private TextView txtView;
    private TextView showend;
    private Thread ti;
    
   
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        imgshow=(ImageView)findViewById(R.id.imgshow);
        btnRestart=(Button)findViewById(R.id.btnRestart);
        txtView = (TextView)findViewById(R.id.show_time);
        showend= (TextView)findViewById(R.id.show_end);
        
         ti = new Thread(new MyThread());
      	ti.start();
      	
        initGame();
        
      //new Thread(new MyThread()).start();
      		
         
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
		showend.setVisibility(View.INVISIBLE);
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
						
							showend.setText("¹§Ï²Äú£¡");
							showend.setVisibility(View.VISIBLE);
							showend.setText("¹§Ï²£¡ ÄúÓÃÁË "+recLen+"Ãë£¡");
							sqlEngine = null;
						}
					}
					count=0;
					}
				}
			}
		}
	}

final Handler handler = new Handler(){          // handle 
    public void handleMessage(Message msg){ 
        switch (msg.what) { 
        case 1: 
            recLen++; 
            txtView.setText("" + recLen); 
        } 
        super.handleMessage(msg); 
    } 
}; 

public class MyThread implements Runnable{      // thread 
    @Override 
    public void run(){ 
        while(level<8){ 
            try{ 
                Thread.sleep(1000);     // sleep 1000ms 
                Message message = new Message(); 
                message.what = 1; 
                handler.sendMessage(message); 
            }catch (Exception e) { 
            } 
        }
    }
    
    
    }
}



	
