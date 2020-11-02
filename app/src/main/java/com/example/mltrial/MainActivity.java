package com.example.mltrial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {


    private ImageView imageView;
    public static TextView textView;
    public static String number;
    static Pattern pattern = Pattern.compile("[A-Z]{2}[0-9]{1,2}[A-Z]{1,2}[0-9]{1,4}");
    private InputImage image;
    static String resultText;
    Uri mImageUri;
    Button btn;
    private EditText t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_view);
        textView = findViewById(R.id.text_display);
        t1 = findViewById(R.id.regNo);
        btn = findViewById(R.id.confirm_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = t1.getText().toString();
                new parsing().execute();
                try {
                    Thread.sleep(3300);
                    // System.out.println(prasing.kl.toString());
                    printInfo(parsing.kl);
                } catch (InterruptedException | JSONException e) {
                    e.printStackTrace();
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
//                                textView.setText("Result     " + resultText.replaceAll("[^a-zA-Z0-9]", "") + "\nRegex:  " + pattern.matcher(resultText).matches());
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
                                            t1.setText(resultText);

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

//        public void check (View view){
////        number = t1.getText().toString();
////        new parsing().execute();
////        try {
////            Thread.sleep(3300);
////            // System.out.println(prasing.kl.toString());
////            printInfo(parsing.kl);
////        } catch (InterruptedException | JSONException e) {
////            e.printStackTrace();
////        }
//
//        }

    public void printInfo(JSONObject kl) throws JSONException, NullPointerException {
        try {
            JSONObject temp = new JSONObject(kl.getJSONObject("Vehicle").getString("vehicleJson").toString());

            textView.setText("Description:  " + temp.getString("Description"));
            textView.append("\nName" + temp.get("Zone"));
            textView.append("\nRegistration Year:  " + temp.getString("RegistrationDate"));
            textView.append("\nOwner:   " + temp.getString("Owner") + "\n");
            textView.append("\nLocation:  " + temp.getString("Location"));

            System.out.println("Description:  " + temp.getString("Description") +
                    "\nName" + temp.get("Zone") +
                    "\nRegistration Year:  " + temp.getString("RegistrationDate") +
                    "\nOwner:   " + temp.getString("Owner") + "\n" +
                    "\nLocation:  " + temp.getString("Location"));
        } catch (Exception e) {
        }
    }
}