package com.example.dictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText txtsearch;
    Button submit;
    TextView Mean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtsearch = (EditText)findViewById(R.id.txtsearch);
        submit = (Button)findViewById(R.id.btnsearch);
        Mean = (TextView)findViewById(R.id.result);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtsearch.getText().toString()))
                {
                    Toast.makeText( MainActivity.this,"No Empty Keyword",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference myRef= FirebaseDatabase.getInstance().getReference("meaning");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String searchKeyword = txtsearch.getText().toString();
                            if(dataSnapshot.child(searchKeyword).exists())
                            {
                                Mean.setText(dataSnapshot.child(searchKeyword).getValue().toString());
                            }else{
                                Toast.makeText(MainActivity.this,"No Result Found",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }
}
