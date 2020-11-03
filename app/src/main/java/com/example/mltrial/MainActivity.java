package com.example.mltrial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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


public class MainActivity<nDialog> extends AppCompatActivity {

    public static String number;
    public Button conf;
    Uri mImageUri;
    static String resultText;
    private ImageView imageView;
    TextView warn ;
    private InputImage image;
    private TextInputEditText t1;
    static ProgressDialog nDialog;
     ProgressBar pgsBar ;
     Context context=MainActivity.this;
     String pattern2="[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}" ;
    Pattern pattern = Pattern.compile(pattern2);
    Matcher matcher ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image_view);
        conf = findViewById(R.id.confirm_button);
        t1 = findViewById(R.id.regNo);
        warn=findViewById(R.id.warn);

     //   pgsBar = (ProgressBar) findViewById(R.id.pBar);
        nDialog =  new ProgressDialog(MainActivity.this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Get Data");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.dismiss();
        Toast.makeText(MainActivity.this, "This is my Toast message!",
                Toast.LENGTH_LONG);
        conf.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                {
                    number = t1.getText().toString().replaceAll("\\s", "");
                  //  nDialog.show();
                    new parsing(context).execute();
                    t1.setText("");

                }
            }
        });
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
                                resultText = result.getText();
                                System.out.println("RESULT TEXT=====================" + resultText);
                                for (Text.TextBlock block : result.getTextBlocks()) {
                                    String blockText = block.getText();
                                    Point[] blockCornerPoints = block.getCornerPoints();
                                    Rect blockFrame = block.getBoundingBox();
                                    for (Text.Line line : block.getLines()) {
                                        String lineText = line.getText();
                                        System.out.println("Line TEXT=====================" + lineText);
                                        Point[] lineCornerPoints = line.getCornerPoints();
                                        Rect lineFrame = line.getBoundingBox();
                                        for (Text.Element element : line.getElements()) {
                                            String elementText = element.getText();
                                            Point[] elementCornerPoints = element.getCornerPoints();
                                            Rect elementFrame = element.getBoundingBox();
                                            System.out.println("elemnt TEXT=====================" + elementText);
                                            resultText = resultText.replaceAll("\\s", "");
                                            if(resultText.charAt(2)=='O'|| resultText.charAt(2)=='o')
                                                     resultText = resultText.substring(0, 2)+'0'+ resultText.substring(2+ 1);
                                            t1.setText(resultText);
                                            if(pattern.matcher(resultText).matches())
                                            {
                                                number=resultText ;
                                                new parsing(context).execute() ;
                                            }
                                            else{
                                                warn.setText("Cant recognise registration number");

                                            }


                                            Toast.makeText(MainActivity.this, "This is my Toast message!",
                                                    Toast.LENGTH_LONG);

                                        }
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });
    }
}