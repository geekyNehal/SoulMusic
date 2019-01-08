package com.geekynehal.soulmusic;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import java.security.Permission;

public class PermissionsClass extends AppCompatActivity
{

    String[] permissionArray={Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.PROCESS_OUTGOING_CALLS,
                                Manifest.permission.RECORD_AUDIO};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(!hasPermission(PermissionsClass.this,permissionArray))
        {
            ActivityCompat.requestPermissions(PermissionsClass.this,permissionArray,126);
        }
        else
        {
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   Intent startAct=new Intent(PermissionsClass.this,MainActivity.class);
                   startActivity(startAct);
                   finish();
               }
           },1000);
        }
    }
    Boolean hasPermission(Context context,String[] permissions)
    {
        Boolean hasAllPermissions=true;
        for (String permission: permissions)
        {
            int res=context.checkCallingOrSelfPermission(permission);
            if (res!=PackageManager.PERMISSION_GRANTED)
            {
                hasAllPermissions=false;
            }
        }
        return hasAllPermissions;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 126:
                if(grantResults.length>=1&&grantResults[0]==PackageManager.PERMISSION_GRANTED
                                        &&grantResults[1]==PackageManager.PERMISSION_GRANTED
                                        &&grantResults[2]==PackageManager.PERMISSION_GRANTED
                                        &&grantResults[3]==PackageManager.PERMISSION_GRANTED
                                        &&grantResults[4]==PackageManager.PERMISSION_GRANTED)
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent startAct=new Intent(PermissionsClass.this,MainActivity.class);
                            startActivity(startAct);
                            finish();
                        }
                    },1000);
                }
                else
                {
                    Toast.makeText(PermissionsClass.this,"Please Grant all the permissions to continue",Toast.LENGTH_LONG).show();
                    this.finish();
                }
                break;
            default:
                Toast.makeText(PermissionsClass.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                this.finish();
                break;
        }
    }
}
