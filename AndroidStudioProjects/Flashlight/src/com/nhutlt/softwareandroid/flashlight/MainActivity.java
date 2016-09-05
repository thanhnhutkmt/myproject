package com.nhutlt.softwareandroid.flashlight;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	private Button mTurnOff, mTurnOn, mTurnOffExit;
	private Camera cam;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CameraInfo camInf = new CameraInfo();
        if (!this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
        	Toast.makeText(this, "This device has no flash light", Toast.LENGTH_LONG);
        	finish();
        } else {
        	int numberOfCam = Camera.getNumberOfCameras();
        	
         	Camera.getCameraInfo(CameraInfo.CAMERA_FACING_BACK, camInf);
			if (cam == null) {
				cam = Camera.open(CameraInfo.CAMERA_FACING_BACK);
		    	Parameters param = cam.getParameters();
		    	param.setFlashMode(Parameters.FLASH_MODE_TORCH);
		    	cam.setParameters(param);
			}
//			cam = Camera.open();
//	    	Parameters param = cam.getParameters();
//	    	param.setFlashMode(Parameters.FLASH_MODE_TORCH);
//	    	cam.setParameters(param);
        }
        mTurnOff = (Button)findViewById(R.id.turnoff);
        mTurnOn = (Button)findViewById(R.id.turnon);
        mTurnOffExit = (Button)findViewById(R.id.turnoffexit);
        
        mTurnOff.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (cam != null) {
					cam.stopPreview();
					cam.release();
					cam = null;			
				}
			}
		});
        
        mTurnOn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (cam == null) {
					cam = Camera.open(CameraInfo.CAMERA_FACING_BACK);
			    	Parameters param = cam.getParameters();
			    	param.setFlashMode(Parameters.FLASH_MODE_TORCH);
			    	cam.setParameters(param);
				}
			}
		});
        
        mTurnOffExit.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (cam != null) {
					cam.stopPreview();
					cam.release();
					cam = null;					
				}
				finish();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }    
}
