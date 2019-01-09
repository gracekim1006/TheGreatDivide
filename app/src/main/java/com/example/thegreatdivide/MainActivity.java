package com.example.thegreatdivide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void beginOnClick(View view){
        Intent intent = new Intent(MainActivity.this, Collection.class);
        intent.putExtra("score", String.valueOf(counter));
        Log.d("Collection", "" + counter);
        startActivity(intent);
    }
}
