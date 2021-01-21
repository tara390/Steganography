package com.manddprojectconsulant.steganography.Decryption;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.manddprojectconsulant.steganography.AlgoStego.PVDColor;
import com.manddprojectconsulant.steganography.AlgoStego.StegoPVD;
import com.manddprojectconsulant.steganography.EncryptionMethod.EncryptionActivity;
import com.manddprojectconsulant.steganography.MainActivity;
import com.manddprojectconsulant.steganography.R;

public class ExtractionActivity extends AppCompatActivity {


    private static final int RESULT_LOAD_IMAGE = 111;
    ImageView ivdecryptimage;
    TextInputEditText eddecrypttext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_extraction);

        init();



    }

    private void init() {

        eddecrypttext=findViewById(R.id.eddecrypttext);
        ivdecryptimage=findViewById(R.id.ivdecryptimage);

    }

    public void OpenGallery(View view) {

        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);


    }

    public void DecryptMethod(View view) {

        StegoPVD pvd = new PVDColor();
        Drawable d = ivdecryptimage.getDrawable();
        Bitmap StegoBitmap = ((BitmapDrawable) d).getBitmap();

        Object obj = pvd.stego(StegoBitmap, "", false);


        if (obj != null) {
            String secretStego = (String) obj;
            StringBuilder theLast = new StringBuilder();
            int[] strChar = new int[8];
            for (int i = 0; i < secretStego.length(); ) {
                strChar = new int[8];
                for (int j = 0; j < 8; j++) {
                    if (i < secretStego.length())
                        strChar[j] = Integer.parseInt(String.valueOf(secretStego.charAt(i++)));
                }

                int b = 0;
                int bin = 1;
                for (int k = strChar.length - 1; k >= 0; k--) {
                    b += strChar[k] * bin;
                    bin = bin * 2;
                }
                theLast.append(String.valueOf((char) b));
            }
            eddecrypttext.setText(theLast.toString());
        } else
            eddecrypttext.setText("No Stego In This Image !!");


    }

    public void backpressedDecrypt(View view) {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(ExtractionActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
        super.onBackPressed();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri fullPhotoUri = data.getData();
            ivdecryptimage.setImageURI(fullPhotoUri);
        }
    }

}