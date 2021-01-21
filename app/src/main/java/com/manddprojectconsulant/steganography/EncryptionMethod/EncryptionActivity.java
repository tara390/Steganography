package com.manddprojectconsulant.steganography.EncryptionMethod;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.manddprojectconsulant.steganography.AlgoStego.PVDColor;
import com.manddprojectconsulant.steganography.AlgoStego.StegoPVD;
import com.manddprojectconsulant.steganography.MainActivity;
import com.manddprojectconsulant.steganography.R;

public class EncryptionActivity extends Activity {

    private static final int RESULT_LOAD_IMAGE = 111;
    TextInputEditText edwritetext;
    Button btnencrypt, btnopengallery;
    ImageView ivOriginal;
//    private SessionManager session;
    public static Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_encryption);

        initforencryptio();


    }

    private void initforencryptio() {

        edwritetext = findViewById(R.id.edwritetext);
        btnencrypt = findViewById(R.id.btnencrypt);
        btnopengallery = findViewById(R.id.btnopen);
        ivOriginal = findViewById(R.id.ivOriginal);

        btnencrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*BitmapDrawable drawable = (BitmapDrawable) ivOriginal.getDrawable();
                Bitmap bitmap = drawable.getBitmap();*/


                String edtext = edwritetext.getText().toString();
                StegoPVD pvd = new PVDColor();
                BitmapDrawable d = (BitmapDrawable) ivOriginal.getDrawable();
                Bitmap myBitmap = d.getBitmap();
                Object obj = pvd.stego(myBitmap, edtext, true);
                Bitmap newBitmap = (Bitmap) obj;

                bitmap = newBitmap;

                //Go for second Activity

                Intent encryption = new Intent(EncryptionActivity.this, AskActivity.class);
                //encryption.putExtra("encryptionimage", newBitmap);
                startActivity(encryption);
            }
        });

    }

    public void Gallery(View view) {

            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri fullPhotoUri = data.getData();
            ivOriginal.setImageURI(fullPhotoUri);
        }
    }

    public void backpressed(View view) {

        onBackPressed();

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(EncryptionActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
        super.onBackPressed();


    }



//    public void Encryption(View view) {
//        String edtext = edwritetext.getText().toString();
//        StegoPVD pvd = new PVDColor();
//        Drawable d = ivOriginal.getDrawable();
//        Bitmap myBitmap = ((BitmapDrawable) d).getBitmap();
//        Object obj = pvd.stego(myBitmap, edtext, true);
//        Bitmap newBitmap = (Bitmap) obj;
//
//
//        //Go for second Activity
//
//        Intent encryption = new Intent(EncryptionActivity.this, AskActivity.class);
//        encryption.putExtra("encryptionimage", newBitmap);
//        startActivity(encryption);
//
//
//    }
}
