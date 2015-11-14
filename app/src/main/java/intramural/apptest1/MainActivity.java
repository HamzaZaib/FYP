package intramural.apptest1;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.content.Context;
import android.hardware.*;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener, View.OnClickListener {

    private SensorManager sensorManager;
    private Sensor mCompass;
    private TextView count,angle;
    private float count1;
    boolean activityRunning,first=true;
    private DrawView drawView;
    private RelativeLayout layout;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    private float previousdegree = 0f;
    private Sensor countSensor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_UI);


        setContentView(R.layout.activity_main);
        layout=(RelativeLayout)findViewById(R.id.relativeLayout);
        count = (TextView) findViewById(R.id.count);
        angle= (TextView) findViewById(R.id.Angle);

        drawView = new DrawView(this);
        drawView.setBackgroundColor(Color.BLACK);
        drawView.setMinimumHeight(73);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, count.getId());

        layout.addView(drawView, params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
        if (mAccelerometer != null) {
            sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Accelerometer not available!", Toast.LENGTH_LONG).show();
        }
        if (mMagnetometer != null) {
            sensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Accelerometer not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        sensorManager.unregisterListener(this, countSensor);
        sensorManager.unregisterListener(this, mAccelerometer);
        sensorManager.unregisterListener(this, mMagnetometer);
        // if you unregister the last listener, the hardware will stop detecting step events
//        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor=event.sensor;
        if(sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if (first) {
                first = false;
                System.out.println("error first");
                //Toast.makeText(this, "sensor result", Toast.LENGTH_LONG).show();
                count1 = Float.parseFloat(String.valueOf(event.values[0]));
            }
            if (activityRunning) {
                System.out.println("error later");
                //Toast.makeText(this, "sensor value 1", Toast.LENGTH_LONG).show();
                drawView.invalidate();
                count.setText((Float.parseFloat(String.valueOf(event.values[0])) - count1) + "");
            }
        }
        if (event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            Toast.makeText(this, "I am here", Toast.LENGTH_LONG).show();
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            float azimuthInRadians = mOrientation[0];
            float azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians)+360)%360;
            angle.setText(azimuthInRadians +" radians and "+azimuthInDegress+" degrees");
            float diff=Math.abs(azimuthInDegress-previousdegree);
            if(diff>90){
                previousdegree=azimuthInDegress;
                if(azimuthInDegress<45 || azimuthInDegress>315){
                    drawView.current= DrawView.Side.North;
                }
                else if(azimuthInDegress>45 || azimuthInDegress <135){
                    drawView.current= DrawView.Side.East;
                }
                else if(azimuthInDegress>135 || azimuthInDegress <225){
                    drawView.current= DrawView.Side.South;
                }
                else{
                    drawView.current= DrawView.Side.West;
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onClick(View v) {
        first=true;
        count.setText("0");
    }
}