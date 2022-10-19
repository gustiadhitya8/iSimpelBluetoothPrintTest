package com.ioh.isimpelbluetoothprinter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anggastudio.printama.Printama;

public class MainActivity extends AppCompatActivity {

    String text="-------------\n"+
            "This will be printed\n"+
            "Left aligned\n"+ // or Center or Right
            "cool isn't it?\n"+
            "------------------\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();

        //iSimpel Print Button
        Button print = (Button)findViewById(R.id.btnPrint);
        ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.AllLayout);
        Printama printama = new Printama(this);

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printama.showPrinterList(MainActivity.this, printerName->{
                    if(printama.getConnectedPrinter()!=null){
                        //Turn Receipt Dialog View into Bitmap
                        View v = layout; //Insert receipt view dialog into this v variable
                        Bitmap b = Bitmap.  createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                        Canvas c = new Canvas(b);
                        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                        v.draw(c);

                        //Bitmap b has receipt dialog now

                        //Print b using printama
                        Printama.with(MainActivity.this).connect(printama->{
                            printama.printImage(Printama.CENTER,b,Printama.FULL_WIDTH);
                            printama.close();
                        });
                    }
                });
            }
        });


    }

    private static String[]PERMISSIONS_STORAGE={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_PRIVILEGED
    };
    private static String[]PERMISSIONS_LOCATION={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_PRIVILEGED
    };

    private void checkPermissions(){
        int permission1= ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2=ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_SCAN);
        if(permission1!= PackageManager.PERMISSION_GRANTED){
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    1
            );
        }else if(permission2!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_LOCATION,
                    1
            );
        }
    }
}