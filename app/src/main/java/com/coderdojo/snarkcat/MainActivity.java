package com.coderdojo.snarkcat;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.util.Random;
import java.util.ArrayList;
import android.hardware.SensorManager;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {

    private float xAccel, yAccel, zAccel;
    private float xPreviousAccel, yPreviousAccel, zPreviousAccel;
    private boolean firstUpdate = true;
    private final float shakeThreshold = 3.5f;
    private boolean shakeInitiated = false;
    //SensorEventListener mySensorEventListener;
    SensorManager mySensorManager;


    private ArrayList<String> mComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mySensorManager.registerListener(this,
                mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mComments = new ArrayList<String>();
        mComments.add("Remember that joke you told me earlier? It went something along the lines of your grade.");
        mComments.add("Just because you have a paper that says you graduated from a university doesn't mean you're not an idiot.");
        mComments.add("You say you get ten times more girls than I do? Ten times zero is still zero...");
        mComments.add("My door is always open. Hopefully you'll leave...");
        mComments.add("Having a degree is not proof of not being stupid.");
        mComments.add("This is a list of everyone who asked for your opinion: No One!");
        mComments.add("If someone is fat, don't sugarcoat it... They'll eat that too.");
        mComments.add("You remind me of my Asian friend, Ug Lee.");
        mComments.add("I hope your day is as \"pleasant\" as I am.");
        mComments.add("Unfortunately, there is no vaccine for stupidity.");
        mComments.add("Have a horrible day.");
        mComments.add("I don't hate you, I'm just not excited about your existence.");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickNext(View view){
        Random r = new Random();
        try {
            TextView cText = (TextView)findViewById(R.id.Comment);
            cText.setText(
                    mComments.get(
                            r.nextInt(mComments.size())
                    )
            );
        }
        catch(Exception error) {
            System.out.println(error.getMessage());
        }
    }

    /*Accelerometer Code*/

    private boolean isAccelerationChanged() {
        float deltaX = Math.abs(xPreviousAccel - xAccel);
        float deltaY = Math.abs(yPreviousAccel - yAccel);
        float deltaZ = Math.abs(zPreviousAccel - zAccel);
        return (deltaX > shakeThreshold && deltaY > shakeThreshold)
                || (deltaX > shakeThreshold && deltaZ > shakeThreshold)
                || (deltaY > shakeThreshold && deltaZ > shakeThreshold);
    }

    private void updateAccelParameters(float xNewAccel, float yNewAccel, float zNewAccel) {
        if (firstUpdate) {
            xPreviousAccel = xNewAccel;
            yPreviousAccel = yNewAccel;
            zPreviousAccel = zNewAccel;
            firstUpdate = false;
        }
        else{
            xPreviousAccel = xAccel;
            yPreviousAccel = yAccel;
            zPreviousAccel = zAccel;
        }
        xAccel = xNewAccel;
        yAccel = yNewAccel;
        zAccel = zNewAccel;
    }

    private void executeShakeAction() {
        Random r = new Random();
        try {
            TextView cText = (TextView)findViewById(R.id.Comment);
            cText.setText(
                    mComments.get(
                            r.nextInt(mComments.size())
                    )
            );
        }
        catch(Exception error) {
            System.out.println(error.getMessage());
        }
    }

    public void onSensorChanged(SensorEvent se) {
        updateAccelParameters(se.values[0], se.values[1], se.values[2]);
        if ((!shakeInitiated) && isAccelerationChanged()) {
            shakeInitiated = true;
        }else if ((shakeInitiated) && isAccelerationChanged()){
            executeShakeAction();
        }else if((shakeInitiated) && (!isAccelerationChanged())){
            shakeInitiated = false;
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //setting the accuracy
    }


}
