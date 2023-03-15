package com.example.firestore_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText mTitleEt , mDescriptionEt;
    Button mSaveBtn, mListBtn;

    ProgressDialog pd;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Data");

        mTitleEt = findViewById(R.id.titleEt);
        mDescriptionEt = findViewById(R.id.descriptionEt);
        mSaveBtn = findViewById(R.id.saveBtn);
        mListBtn = findViewById(R.id.showBtn);

        pd = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        //click button to uplode data
          mSaveBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  String title = mTitleEt.getText().toString().trim();
                  String description = mDescriptionEt.getText().toString().trim();
                  uploadData(title,description);
              }
          });

          //click vtn to start ListActivity

        mListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ListActivity.class));
                finish();
            }
        });

    }

    private void uploadData(String title, String description) {
        pd.setTitle("Adding Data to Fierbase");
        pd.show();
        String id = UUID.randomUUID().toString();

        Map<String,Object> doc = new HashMap<>();
        doc.put("id",id); // id of data
        doc.put("title",title);
        doc.put("description",description);


        // add this data
        db.collection("Document").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();;
                        Toast.makeText(MainActivity.this,"Uploaded....",Toast.LENGTH_LONG);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();;
                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG);
                    }
                });



    }
}