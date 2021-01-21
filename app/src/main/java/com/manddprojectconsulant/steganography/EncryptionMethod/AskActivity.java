package com.manddprojectconsulant.steganography.EncryptionMethod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.manddprojectconsulant.steganography.MainActivity;
import com.manddprojectconsulant.steganography.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static com.manddprojectconsulant.steganography.EncryptionMethod.EncryptionActivity.bitmap;

public class AskActivity extends AppCompatActivity {

    ImageView ivEncryptedImage;
    Bitmap bitmapp;

    Boolean clicked=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_ask);

        //Bitmap bitmap = (Bitmap)this.getIntent().getParcelableExtra("encryptionimage");
        bitmapp = bitmap;
        ivEncryptedImage=findViewById(R.id.ivEncryptedImage);
        ivEncryptedImage.setImageBitmap(bitmapp);

    }

    public void backpressedforsave(View view) {




        Intent i = new Intent(AskActivity.this, EncryptionActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();


    }

    public void Share(View view) {

       /* File myDir = new File(Environment.getExternalStorageDirectory(), "StegoImage");

        Uri uri = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                ? FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", myDir)
                : Uri.fromFile(myDir);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_PICK);
        shareIntent= ShareCompat.IntentBuilder.from((Activity) AskActivity.this)
                .setChooserTitle("Share to")
                .setType("video/png")
                .setStream(uri)
                .getIntent();
        if (shareIntent.resolveActivity(AskActivity.this.getPackageManager()) != null) {

            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // shareIntent.setPackage("com.whatsapp");
            startActivity(shareIntent);
        }
*/

        if(clicked) {
            //Toast.makeText(AskActivity.this, "You already clicked!", 1000).show();

            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmapp, "Image I want to share", null);
            Uri uri = Uri.parse(path);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
            startActivity(Intent.createChooser(shareIntent, "Share Image"));


        } else {
            savefile();
        }

        clicked = true;









    }

    public void Save(View view) {
        savefile();
    }

    public void savefile(){


        Drawable d = ivEncryptedImage.getDrawable();
        Bitmap myBitmap = ((BitmapDrawable) d).getBitmap();

        File myDir = new File(Environment.getExternalStorageDirectory(), "StegoImage");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "SG-" + n + ".png";
        File file = new File(myDir, fname);
        if(file.exists()) file.delete();

        try{
            FileOutputStream out = new FileOutputStream(file);
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.flush();
            out.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Toast toast = Toast.makeText(AskActivity.this, "Save is Done !!", Toast.LENGTH_SHORT);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        refreshGallary(myDir);
        Intent i=new Intent(AskActivity.this,MainActivity.class);
        startActivity(i);
        finish();


    }


    private void refreshGallary(File file)
    { Intent i=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        i.setData(Uri.fromFile(file)); sendBroadcast(i);
    }
}