package com.shailendra.bitmaphandlingdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private ImageView iv1, iv2, iv3, iv4;
    protected static final String TAG_LOG = MainActivity.class.getName();

    private OnClickListener mClickListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            FileOutputStream outStream;
            switch (view.getId()) {
            case R.id.outOfMemoryProneCodeBtn:
                Log.d(TAG_LOG, "Showing bitmap in imageView");
                iv1.setImageResource(R.drawable.big_image);
                iv2.setImageResource(R.drawable.big_image);
                iv3.setImageResource(R.drawable.big_image);
                iv4.setImageResource(R.drawable.big_image);

                Log.d(TAG_LOG, "Saving bitmap into memory");

                try {
                    Bitmap bigBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.big_image);
                    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    File file = new File(extStorageDirectory, "big_image.png");
                    outStream = new FileOutputStream(file);
                    bigBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                    Toast.makeText(MainActivity.this, "Saved at" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    Log.d(TAG_LOG, "Showing saved bitmap in imageView");
                    bigBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    iv1.setImageBitmap(bigBitmap);
                    iv2.setImageBitmap(bigBitmap);
                    iv3.setImageBitmap(bigBitmap);
                    iv4.setImageBitmap(bigBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.outOfMemoryProofCodeBtn:

                Log.d(TAG_LOG, "Showing bitmap in imageView");

                iv1.setImageBitmap(decodeResource(getResources(), R.drawable.big_image));
                iv2.setImageBitmap(decodeResource(getResources(), R.drawable.big_image));
                iv3.setImageBitmap(decodeResource(getResources(), R.drawable.big_image));
                iv4.setImageBitmap(decodeResource(getResources(), R.drawable.big_image));
                Log.d(TAG_LOG, "Saving bitmap into memory");

                try {
                    Bitmap bigBitmap = decodeResource(getResources(), R.drawable.big_image);
                    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    File file = new File(extStorageDirectory, "big_image_2.png");
                    file.createNewFile();
                    outStream = new FileOutputStream(file);
                    bigBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                    Toast.makeText(MainActivity.this, "Saved at" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    Log.d(TAG_LOG, "Showing saved bitmap in imageView");
                    bigBitmap = decodeFile(file.getAbsolutePath());
                    iv1.setImageBitmap(bigBitmap);
                    iv2.setImageBitmap(bigBitmap);
                    iv3.setImageBitmap(bigBitmap);
                    iv4.setImageBitmap(bigBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button oomProneCodeBtn = (Button) findViewById(R.id.outOfMemoryProneCodeBtn);
        oomProneCodeBtn.setOnClickListener(mClickListener);
        final Button oomProofCodeBtn = (Button) findViewById(R.id.outOfMemoryProofCodeBtn);
        oomProofCodeBtn.setOnClickListener(mClickListener);

        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);

    }

    public static Bitmap decodeResource(Resources res, int id) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        for (options.inSampleSize = 1; options.inSampleSize <= 32; options.inSampleSize++) {
            try {
                bitmap = BitmapFactory.decodeResource(res, id, options);
                Log.d(TAG_LOG, "Decoded successfully for sampleSize " + options.inSampleSize);
                break;
            } catch (OutOfMemoryError outOfMemoryError) {
                // If an OutOfMemoryError occurred, we continue with for loop and next inSampleSize value
                Log.e(TAG_LOG, "outOfMemoryError while reading file for sampleSize " + options.inSampleSize
                        + " retrying with higher value");
            }
        }
        return bitmap;
    }

    public static Bitmap decodeFile(String pathName) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        for (options.inSampleSize = 1; options.inSampleSize <= 32; options.inSampleSize++) {
            try {
                bitmap = BitmapFactory.decodeFile(pathName, options);
                Log.d(TAG_LOG, "Decoded successfully for sampleSize " + options.inSampleSize);
                break;
            } catch (OutOfMemoryError outOfMemoryError) {
                // If an OutOfMemoryError occurred, we continue with for loop and next inSampleSize value
                Log.e(TAG_LOG, "outOfMemoryError while reading file for sampleSize " + options.inSampleSize
                        + " retrying with higher value");
            }
        }
        return bitmap;
    }

}
