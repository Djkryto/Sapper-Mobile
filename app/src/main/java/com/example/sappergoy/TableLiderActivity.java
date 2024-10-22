package com.example.sappergoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Custom.Context;

public class TableLiderActivity extends AppCompatActivity {
    private Context _context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_lider);
        GetContext();
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

    public void Back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Context",_context);
        startActivity(intent);
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
        
        if(_context.Points != null){
            TextView view = findViewById(R.id.point);
            view.setText("Очки: " + _context.Points.toString());
        }
    }
}
