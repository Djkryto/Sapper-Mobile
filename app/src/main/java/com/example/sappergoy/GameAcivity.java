package com.example.sappergoy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

import Custom.Context;

public class GameAcivity extends AppCompatActivity {
    private Integer _time = 0;
    private Integer _points = 0;
    private Integer _countClickChain = 0;
    private Context _context;
    private Handler _handler = new Handler();
    private ArrayList<TextView> _allChain = new ArrayList<TextView>();
    private ArrayList<TextView> _mine = new ArrayList<TextView>();
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            ChangeText();
            _handler.postDelayed(this, 1000);
        }
    };
    private boolean _openSocket = true;
    private boolean _gameOver = false;
    private TextView _timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        _timer = findViewById(R.id.timer);
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        // Генерация ячеек
        for(int i = 0; i < 70; i++){
            TextView text = new TextView(this);
            text.setBackgroundColor(Color.GRAY);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = 100;
            layoutParams.height = 100;
            layoutParams.setMargins(5,5,5,5);
            text.setLayoutParams(layoutParams);
            text.setGravity(Gravity.CENTER);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            text.setOnClickListener(this::ClickSoket);
            gridLayout.addView(text);
            _allChain.add(text);
        }

        _context = (Context) getIntent().getSerializableExtra("Context");
        int difficulty =  _context.Difficulty;
        int countMine = 15 * (difficulty + 1);
        Init();
        // Инициализвация мин
        for(int i = 0; i < countMine;i++){
            Random random = new Random();
            int ranI = random.nextInt(70);
            boolean off = false;
            for (int j = 0; j < _allChain.size();j++){
                if(_mine.contains(_allChain.get(ranI))){
                    i--;
                    off=true;
                    break;
                }
            }
            if(off) continue;
            _mine.add(_allChain.get(ranI));
        }
    }

    protected void onResume() {
        super.onResume();
        _handler.postDelayed(task, 1000);
    }

    protected void onPause(){
        super.onPause();
        _handler.removeCallbacks(task);
    }

    public void Back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        _context.Points = _points;
        intent.putExtra("Context", _context);
        startActivity(intent);
    }

    public void ChangeText(){
        _time++;
        Integer minute = _time / 60;
        Integer second = _time < 60 ? _time: _time % 60;
        String textSecond = "00";
        String textMinute = "00";
        if(second > 9){
            textSecond = second.toString();
        }

        if(second < 10){
            textSecond = '0' + second.toString();
        }

        if(minute != 0){
            textMinute= minute > 9 ? minute.toString() :  "0" + minute;
        }

        _timer.setText("Время " + textMinute + ':' + textSecond);
    }

    public void ChangeOpenSocket(View view){
        _openSocket = !_openSocket;
    }

    public void ClickSoket(View view){
        if(_gameOver)
            return;

        TextView text = (TextView) view;
        if(_openSocket){
            if(_mine.contains(text))
            {
                GameOver();
                return;
            }
            String content = text.getText().toString();
            if(content == "") {
                SetPoint();
                _countClickChain++;
                CheckOnWin();
            }
            Check(text);

            return;
        }

        text.setText("X");
    }

    private void GameOver(){
        _gameOver = true;
        for(int i = 0; i < _allChain.size(); i++){
            TextView text = _allChain.get(i);
            text.setText("");
            text.setBackgroundColor(Color.GREEN);
        }

        for(int i = 0; i < _mine.size(); i++){
            TextView text = _mine.get(i);
            text.setBackgroundColor(Color.RED);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Игра окончена")
                .setPositiveButton("ОК",null)
                .create().show();
    }

    private void Check(TextView view){
        int index = _allChain.indexOf(view);
        int column = index % 7;
        Integer count = 0;
        if(index < 7){
            if(index == 0){
                TextView textOne = _allChain.get(index + 1);
                TextView textTwo = _allChain.get(index + 7);
                TextView textTree = _allChain.get(index + 8);

                count = CheckOnMine(textOne, count);
                count = CheckOnMine(textTwo, count);
                count = CheckOnMine(textTree, count);
            }

            if(index > 0 && index < 6){
                TextView textOne = _allChain.get(index - 1);
                TextView textTwo = _allChain.get(index + 1);
                TextView textTree = _allChain.get(index + 6);
                TextView textFour = _allChain.get(index + 7);
                TextView textFive = _allChain.get(index + 8);

                count = CheckOnMine(textOne, count);
                count = CheckOnMine(textTwo, count);
                count = CheckOnMine(textTree, count);
                count = CheckOnMine(textFour, count);
                count = CheckOnMine(textFive, count);
            }

            if(index == 6){
                TextView textOne = _allChain.get(index + 7);
                TextView textTwo = _allChain.get(index + 6);
                TextView textTree = _allChain.get(index -1);

                count = CheckOnMine(textOne, count);
                count = CheckOnMine(textTwo, count);
                count = CheckOnMine(textTree, count);
            }
        }

        if(index > 6 && index < 63){
            if(column == 0){
                TextView textOne = _allChain.get(index - 7);
                TextView textTwo = _allChain.get(index - 6);
                TextView textTree = _allChain.get(index + 1);
                TextView textFour = _allChain.get(index + 7);
                TextView textFive = _allChain.get(index + 8);

                count = CheckOnMine(textOne, count);
                count = CheckOnMine(textTwo, count);
                count = CheckOnMine(textTree, count);
                count = CheckOnMine(textFour, count);
                count = CheckOnMine(textFive, count);
            }

            if(column > 0 && column < 6){
                TextView textOne = _allChain.get(index - 1);
                TextView textTwo = _allChain.get(index + 1);
                TextView textTree = _allChain.get(index + 6);
                TextView textFour = _allChain.get(index + 7);
                TextView textFive = _allChain.get(index + 8);
                TextView textSix = _allChain.get(index - 6);
                TextView textSeven = _allChain.get(index - 7);
                TextView textEight = _allChain.get(index - 8);

                count = CheckOnMine(textOne, count);
                count = CheckOnMine(textTwo, count);
                count = CheckOnMine(textTree, count);
                count = CheckOnMine(textFour, count);
                count = CheckOnMine(textFive, count);
                count = CheckOnMine(textSix, count);
                count = CheckOnMine(textSeven, count);
                count = CheckOnMine(textEight, count);
            }

            if(column == 6){
                TextView textOne = _allChain.get(index + 7);
                TextView textTwo = _allChain.get(index + 6);
                TextView textTree = _allChain.get(index - 1);
                TextView textFour = _allChain.get(index - 8);
                TextView textFive = _allChain.get(index - 7);

                count = CheckOnMine(textOne, count);
                count = CheckOnMine(textTwo, count);
                count = CheckOnMine(textTree, count);
                count = CheckOnMine(textFour, count);
                count = CheckOnMine(textFive, count);
            }
        }

        if(index > 62){
            if(index == 63){
                TextView textOne = _allChain.get(index + 1);
                TextView textTwo = _allChain.get(index - 7);
                TextView textTree = _allChain.get(index - 6);

                count = CheckOnMine(textOne, count);
                count = CheckOnMine(textTwo, count);
                count = CheckOnMine(textTree, count);
            }

            if(index > 63  && index < 69){
                TextView textOne = _allChain.get(index - 1);
                TextView textTwo = _allChain.get(index + 1);
                TextView textTree = _allChain.get(index - 6);
                TextView textFour = _allChain.get(index - 7);
                TextView textFive = _allChain.get(index - 8);

                count = CheckOnMine(textOne, count);
                count = CheckOnMine(textTwo, count);
                count = CheckOnMine(textTree, count);
                count = CheckOnMine(textFour, count);
                count = CheckOnMine(textFive, count);
            }

            if(index == 69){
                TextView textOne = _allChain.get(68);
                TextView textTwo = _allChain.get(61);
                TextView textTree = _allChain.get(62);

                count = CheckOnMine(textOne, count);
                count = CheckOnMine(textTwo, count);
                count = CheckOnMine(textTree, count);
            }
        }

        if(column == 0){

        }

        if(column == 6){

        }

        view.setText(count.toString());
        view.setBackgroundColor(Color.GREEN);
    }

    private Integer CheckOnMine(TextView view, Integer count){
        if(_mine.contains(view)){
            count++;
        }

        return count;
    }

    private void SetPoint(){
        _points++;
        TextView text = findViewById(R.id.userPoints);
        text.setText("Очки: " + _points.toString());
    }

    private void CheckOnWin(){
        if(_countClickChain.intValue() != _allChain.size() - _mine.size()){
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Вы победили")
                .setPositiveButton("Ура",null)
                .setMessage("Ваши очки: " + _points.toString())
                .create().show();
    }

    private void Init(){
        if(_context.Color != null){
            findViewById(R.id.main).setBackgroundColor(_context.Color);
        }
    }
}