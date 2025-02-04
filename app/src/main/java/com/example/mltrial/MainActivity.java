package com.example.mltrial;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import	android.provider.Settings ;


public class MainActivity<nDialog> extends AppCompatActivity {

    public static String number;
    public Button conf,check;
    Uri mImageUri;
    static String resultText;
    private ImageView imageView;
    private InputImage image;
    private TextInputEditText t1;
    static ProgressDialog nDialog;
    ProgressBar pgsBar;
    Context context = MainActivity.this;
    String pattern2 = "[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}";
    Pattern pattern = Pattern.compile(pattern2);
    Matcher matcher;

    private static int SPLASH_SCREEN_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check=findViewById(R.id.capture_image) ;
        imageView = findViewById(R.id.image_view);
        conf = findViewById(R.id.confirm_button);
        t1 = findViewById(R.id.regNo);

        //   pgsBar = (ProgressBar) findViewById(R.id.pBar);
        nDialog = new ProgressDialog(MainActivity.this);
        nDialog.setMessage("Fetching......Wait");
        nDialog.setTitle("Fetching Data");
        nDialog.setIndeterminate(true);
        nDialog.setCancelable(false);
        nDialog.dismiss();
        if(!isConnectedToInternet())
        {
            conf.setEnabled(false);
            check.setEnabled(false);

            Toast.makeText(this,"Please Connect to internet",Toast.LENGTH_LONG).show();
        }
//        else
//        {
//           // Here I've been added intent to open up data settings
//            Intent intent=new Intent(Settings.ACTION_WIRELESS_SETTINGS);
//            ComponentName cName = new ComponentName("com.android.phone","com.android.phone.NetworkSetting");
//            intent.setComponent(cName);
//        }

        conf.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                {
//                    number = t1.getText().toString().replaceAll("\\s", "");
//                    new parsing(context).execute();
//                    t1.setText("");

                    number = (t1.getText().toString().replaceAll("\\s", "").trim()).toUpperCase();
                    System.out.println("%%%%%%%%%%%%%%%%%%%%%" + number);
                    if (pattern.matcher(number).matches()) {
                        new parsing(context).execute();
                        t1.setText("");
                        imageView.setImageBitmap(null);
                    } else {
                        Toast.makeText(MainActivity.this, "Ahhh..Please check your number", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
    public void onChooseFile(View v) {
        CropImage.activity().start(MainActivity.this);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {


            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                imageView.setImageURI(mImageUri);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
                    imageFromBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception e = result.getError();
            Toast.makeText(this, "Possible error is :  " + e, Toast.LENGTH_SHORT);

        }
    }


    private void imageFromBitmap(Bitmap bitmap) {
        int rotationDegree = 0;
        // [START image_from_bitmap]
        image = InputImage.fromBitmap(bitmap, rotationDegree);
        // [END image_from_bitmap]

        TextRecognizer recognizer = TextRecognition.getClient();
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text result) {
                                // [START mlkit_process_text_block]
                                resultText = result.getText().trim();
                                System.out.println("RESULT TEXT=====================" + resultText);
                                resultText = resultText.replaceAll("\\s", "");
                                resultText = resultText.replaceAll("IND", "");
                                resultText = resultText.replaceAll("ind", "");
                                if (resultText.charAt(2) == 'O' || resultText.charAt(2) == 'o')
                                    resultText = resultText.substring(0, 2) + '0' + resultText.substring(2 + 1);
                                t1.setText(resultText);
                                if (pattern.matcher(resultText).matches()) {
                                    number = resultText;
                                    new parsing(context).execute();
                                    t1.setText("");
                                    imageView.setImageBitmap(null);
                                } else {
//                                                warn.setText("Cant recognise registration number");
                                    Toast.makeText(MainActivity.this, "Ahhh..Please retry or type vehicle number", Toast.LENGTH_LONG).show();
                                    t1.setText("");

                                }
//                                for (Text.TextBlock block : result.getTextBlocks()) {
//                                    String blockText = block.getText();
//                                    Point[] blockCornerPoints = block.getCornerPoints();
//                                    Rect blockFrame = block.getBoundingBox();
//                                    for (Text.Line line : block.getLines()) {
//                                        String lineText = line.getText();
//                                        System.out.println("Line TEXT=====================" + lineText);
//                                        Point[] lineCornerPoints = line.getCornerPoints();
//                                        Rect lineFrame = line.getBoundingBox();
//                                        for (Text.Element element : line.getElements()) {
//                                            String elementText = element.getText();
//                                            Point[] elementCornerPoints = element.getCornerPoints();
//                                            Rect elementFrame = element.getBoundingBox();
//                                            System.out.println("elemnt TEXT=====================" + elementText);
//                                            resultText = resultText.replaceAll("\\s", "");
//                                            resultText = resultText.replaceAll("IND", "");
//                                            resultText = resultText.replaceAll("ind", "");
//                                            if (resultText.charAt(2) == 'O' || resultText.charAt(2) == 'o')
//                                                resultText = resultText.substring(0, 2) + '0' + resultText.substring(2 + 1);
//                                            t1.setText(resultText);
//                                            if (pattern.matcher(resultText).matches()) {
//                                                number = resultText;
//                                                new parsing(context).execute();
//                                                t1.setText("");
//                                                imageView.setImageBitmap(null);
//                                            } else {
//                                              warn.setText("Cant recognise registration number");
//                                                Toast.makeText(MainActivity.this, "Ahhh Please type your number", Toast.LENGTH_LONG).show();
//                                                t1.setText("");
//
//                                            }
//
//                                        }
//                                    }
//                                }
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                        Toast.makeText(MainActivity.this, "Ahhh..Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                                    }
                                });
    }

    public void back(View view) {
        finish();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_record) {

            Intent add_mem = new Intent(this, history.class);
            startActivity(add_mem);

        }
        return super.onOptionsItemSelected(item);
    }



}