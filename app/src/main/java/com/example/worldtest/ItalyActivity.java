package com.example.worldtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ItalyActivity extends AppCompatActivity {
    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private int[] userAnswers;

    private TextView questionTextView;
    private RadioGroup answerRadioGroup;
    private Button nextButton, exitButton, backButton;
    private ImageView backgroundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_italy);

        questionTextView = findViewById(R.id.questionTextView);
        answerRadioGroup = findViewById(R.id.answerRadioGroup);
        nextButton = findViewById(R.id.nextButton);
        backgroundImageView = findViewById(R.id.backgroundImageView);
        exitButton = findViewById(R.id.exitButton);
        backButton = findViewById(R.id.backButton);

        setBackground();

        questions = new ArrayList<>();
        questions.add(new Question("Care este capitala Italiei?", new String[]{"Roma", "Milano", "Veneția"}, 0));
        questions.add(new Question("Unde se află Turnul Pisa?", new String[]{"Roma", "Milano", "Pisa"}, 2));
        questions.add(new Question("Care este numele celebrului muzeu din Vatican?", new String[]{"Muzeul Vaticanului", "Galeria Uffizi", "Museo Nazionale del Bargello"}, 0));
        questions.add(new Question("Unde se găsește Colosseumul?", new String[]{"Roma", "Milano", "Florența"}, 0));
        questions.add(new Question("Care este numele celei mai mari insule din Marea Adriatică, situată în partea nord-estică a Italiei?", new String[]{"Sicilia", "Sardinia", "Capri"}, 1));
        questions.add(new Question("În ce oraș se află celebrul Canal Grande?", new String[]{"Milano", "Veneția", "Napoli"}, 1));
        questions.add(new Question("Care este numele faimoasei catedrale gotice din Milano, cunoscută pentru statuia sa iconică a Fecioarei Maria?", new String[]{"Catedrala din Florența", "Bazilica San Marco", "Catedrala din Milano"}, 2));
        questions.add(new Question("Unde se află Fontana di Trevi, cea mai mare și cea mai faimoasă fântână barocă din Roma?", new String[]{"Piazza Navona", "Piazza di Spagna", "Piazza Barberini"}, 1));


        userAnswers = new int[questions.size()];

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRadioButtonId = answerRadioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    userAnswers[currentQuestionIndex] = answerRadioGroup.indexOfChild(findViewById(selectedRadioButtonId));
                    if (currentQuestionIndex < questions.size() - 1) {
                        currentQuestionIndex++;
                        loadQuestion();
                    } else {
                        showResults();
                    }
                } else {
                    Toast.makeText(ItalyActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItalyActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                    loadQuestion();
                }
            }
        });

        loadQuestion();
    }

    private void loadQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getQuestion());

        for (int i = 0; i < currentQuestion.getOptions().length; i++) {
            RadioButton radioButton = (RadioButton) answerRadioGroup.getChildAt(i);
            radioButton.setText(currentQuestion.getOptions()[i]);
        }

        answerRadioGroup.clearCheck();

        if (currentQuestionIndex == 0) {
            exitButton.setVisibility(View.VISIBLE);
        } else {
            exitButton.setVisibility(View.GONE);
        }

        backButton.setVisibility(currentQuestionIndex > 0 ? View.VISIBLE : View.GONE);
    }

    private void showResults() {
        int correctAnswersCount = 0;

        for (int i = 0; i < userAnswers.length; i++) {
            if (userAnswers[i] == questions.get(i).getCorrectOption()) {
                correctAnswersCount++;
            }
        }

        Toast.makeText(this, "Italy Test score: " + correctAnswersCount + "/" + questions.size(), Toast.LENGTH_LONG).show();
        finish();
    }

    private void setBackground() {
        Drawable drawable = getResources().getDrawable(R.drawable.italy_map);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            backgroundImageView.setBackground(drawable);
        } else {
            backgroundImageView.setImageDrawable(drawable);
        }
    }
}