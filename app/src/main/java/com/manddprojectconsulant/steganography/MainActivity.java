package com.manddprojectconsulant.steganography;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.manddprojectconsulant.steganography.Decryption.ExtractionActivity;
import com.manddprojectconsulant.steganography.Encryption.EmbeddingActivity;

public class MainActivity extends AppCompatActivity {

    RadioGroup rg_emb_ext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rg_emb_ext=findViewById(R.id.rg_emb_ext);




    }

    public void nextmethod(View view) {

      //  startActivity(new Intent(MainActivity.this, ExtractionActivity.class));

       int emb_ext_Id = rg_emb_ext.indexOfChild(rg_emb_ext.findViewById(rg_emb_ext.getCheckedRadioButtonId()));

        if(emb_ext_Id==0)
        {
            startActivity(new Intent(MainActivity.this, EmbeddingActivity.class));
        }
        else if(emb_ext_Id==1)
        {
            startActivity(new Intent(MainActivity.this, ExtractionActivity.class));
        }






    }
}