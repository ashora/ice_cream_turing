package com.icecream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by Administrator on 2015/8/5.
 */
public class System extends Activity {
    private static final int IMAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system);
        this.getWindow().setBackgroundDrawableResource(R.drawable.ic_launcher);
        //ImageView my = (ImageView) findViewById(R.id.image);
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1  = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent1,IMAGE_CODE);
            }
        });


        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent();
                intent3.setClassName("com.android.settings", "com.android.settings.Settings");
                startActivity(intent3);
            }
        });

        //��������
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Intent.ACTION_MAIN);
                intent4.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent4);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        ContentResolver resolver;
        resolver = getContentResolver();
        Bitmap bm = null;
        if (requestCode == IMAGE_CODE) {
            try {
                //����uri
                Uri originalUri = data.getData();
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                //��ȡ·��
                String path = cursor.getString(column_index);

                Log.e("Lostinai", path);

            } catch (IOException e) {

                Log.e("Lostinai", e.toString());

            }

        }
        ((ImageView) findViewById(R.id.image)).setImageBitmap(bm);
    }





}
