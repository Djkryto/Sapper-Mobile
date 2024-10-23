package com.example.sappergoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Custom.Context;

public class MainActivity extends AppCompatActivity {

    private Context _context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void StartGame(View view){
        Intent intent = new Intent(this, GameAcivity.class);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        if(radioGroup.getCheckedRadioButtonId() != -1){
            RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            _context.Difficulty = ((RadioGroup)radioButton.getParent()).indexOfChild(radioButton);
            intent.putExtra("Context",_context);
        }else{
            intent.putExtra("Difficulty", 0);
        }
        startActivity(intent);
    }

    public void OpenTableLider(View view){
        Intent intent = new Intent(this, TableLiderActivity.class);
        intent.putExtra("Context",_context);
        startActivity(intent);
    }

    public void OpenOption(View view){
        Intent intent = new Intent(this, OptionActivity.class);
        intent.putExtra("Context",_context);
        startActivity(intent);
    }

    private void GetContext(){
        _context = (Context) getIntent().getSerializableExtra("Context");
        if(_context != null){
            if(_context.Difficulty != null){
                RadioGroup defaultButton = findViewById(R.id.radioGroup);
                defaultButton.clearCheck();

                if(_context.Difficulty.intValue() == 0){
                    RadioButton button = findViewById(R.id.radioButton);
                    button.setChecked(true);
                }

                if(_context.Difficulty.intValue() == 1){
                    RadioButton button = findViewById(R.id.radioButton2);
                    button.setChecked(true);
                }

                if(_context.Difficulty.intValue() == 2){
                    RadioButton button = findViewById(R.id.radioButton3);
                    button.setChecked(true);
                }
            }
            if(_context.Color != null){
                findViewById(R.id.main).setBackgroundColor(_context.Color);
            }
            return;
        }
        _context = new Context();
    }
}