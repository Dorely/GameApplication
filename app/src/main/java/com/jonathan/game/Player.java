package com.jonathan.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Jonathan on 2/24/2016.
 */
public class Player extends GameObject {

    private Bitmap spritesheet;
    private double dya;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    public Player(Bitmap res, int w, int h, int numFrames){
        x=100;
        y=GamePanel.HEIGHT/2;
        dy=0;
        height=h;
        width=w;
        spritesheet = res;

        Bitmap[] image =new Bitmap[numFrames];
        for(int i =0;i<image.length;i++){
            image[i] = Bitmap.createBitmap(spritesheet,i*width,0,width,height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();
    }

    public void setUp(boolean b){
        up = b;
    }

    public void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100){
            startTime=System.nanoTime();
        }
        animation.update();
        if(up){
            dy = (int)(dya-=1.1);
        }else{
            dy = (int)(dya+=1.1);
        }

        if(dy>10){
            dy=10;
        }
        if(dy<-10){
            dy=-10;
        }

        //stop it right outside the screen
        if(y>GamePanel.HEIGHT){
            y=GamePanel.HEIGHT;
            dy=0;
        }
        if(y<0){
            y=0;
            dy=0;
        }

        y+=dy*2;
        dy=0;

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }


    public boolean getPlaying(){
        return playing;
    }

    public void setPlaying(boolean b){
        playing = b;
    }

    public void resetDya(){
        dya=0;
    }


}
