package com.jonathan.game;

/**
 * Created by Jonathan on 2/23/2016.
 */
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.jonathan.myapplication.R;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH = 856;
    public static final int HEIGHT= 480;
    public static final int MOVESPEED = -5;

    private MainThread mainThread;
    private Thread thread;
    private Player player;

    //    Thread increment = new Thread(new Increment());
//    increment.start();
    private Background bg;


    public GamePanel(Context context){
        super(context);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        mainThread = new MainThread(getHolder(), this);
        thread = new Thread(mainThread);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{mainThread.setRunning(false);
                thread.join();

            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.grassbg1));
        player = new Player(BitmapFactory.decodeResource(getResources(),R.drawable.helicopter),65,25,3);
        //we can safely start the game loop
        mainThread.setRunning(true);
        thread.start();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying()){
                player.setPlaying(true);
            }else{
                player.setUp(true);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
            player.setUp(false);
            return true;
        }



        return super.onTouchEvent(event);
    }

    public void update(){
        if(player.getPlaying()){
            bg.update();
            player.update();
        }
    }

    @Override
    public void draw(Canvas canvas){

        final float scaleFactorX = getWidth()/(WIDTH*1.f);//1.f apparently is a float value forcing this to be a float calculation
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        if(canvas!=null){
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);
            canvas.restoreToCount(savedState);

        }
    }
}
