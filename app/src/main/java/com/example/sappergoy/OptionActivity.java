package com.example.sappergoy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import Custom.Context;

public class OptionActivity extends AppCompatActivity {

    private Context _context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetContext();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        GetContext();
    }

    public void SetColor(View view){
        if (view.getId() == R.id.Blue) {
            _context.Color = Color.BLUE;
            Init();
        }

        if (view.getId() == R.id.Yellow) {
            _context.Color = Color.YELLOW;
            Init();
        }

        if (view.getId() == R.id.Red) {
            _context.Color = Color.RED;
            Init();
        }
    }

    private void GetContext(){
        _context = (Context) getIntent().getSerializableExtra("Context");
        if(_context != null){
            Init();
        }
    }
    private void Init(){
        if(_context.Color != null){
            findViewById(R.id.main).setBackgroundColor(_context.Color);
        }
    }

    public void Back(View view){
        Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Context", _context);
        startActivity(intent);
        finish();
    }
}
