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

public class RomaniaActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_france);

        questionTextView = findViewById(R.id.questionTextView);
        answerRadioGroup = findViewById(R.id.answerRadioGroup);
        nextButton = findViewById(R.id.nextButton);
        backgroundImageView = findViewById(R.id.backgroundImageView);
        exitButton = findViewById(R.id.exitButton);
        backButton = findViewById(R.id.backButton);

        setBackground();

        questions = new ArrayList<>();
        questions.add(new Question("Care este capitala României?", new String[]{"Cluj-Napoca", "Timișoara", "București"}, 2));
        questions.add(new Question("Unde se află Castelul Bran, cunoscut și sub numele de Castelul lui Dracula?", new String[]{"Sibiu", "Brașov", "Iași"}, 1));
        questions.add(new Question("Care este numele celebrului castel situat pe Valea Prahovei și asociat cu legenda Contelui Dracula?", new String[]{"Castelul Bran", "Castelul Peleș", "Castelul Corvinilor"}, 1));
        questions.add(new Question("În ce oraș se găsește Cetatea Alba Carolina, o fortificație istorică impresionantă?", new String[]{"Alba Iulia", "Sibiu", "Cluj-Napoca"}, 0));
        questions.add(new Question("Unde se află Mănăstirea Voroneț, cunoscută pentru picturile sale exterioare de culoare albastră?", new String[]{"Suceava", "Maramureș", "Bucovina"}, 0));
        questions.add(new Question("Care este numele celei mai mari cetăți medievale din Europa, situată în județul Hunedoara?", new String[]{"Cetatea Sibiu", "Cetatea Deva", "Cetatea Râșnov"}, 1));
        questions.add(new Question("Unde se află Delta Dunării, una dintre cele mai mari și mai bine conservate delte de coastă din Europa?", new String[]{"Constanța", "Tulcea", "Mangalia"}, 1));
        questions.add(new Question("Care este numele zonei din România cunoscută pentru peisajele sale montane și pentru Castelul Peleș?", new String[]{"Transilvania", "Bucovina", "Muntenia"}, 2));

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
                    Toast.makeText(RomaniaActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RomaniaActivity.this, MainActivity.class);
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

        Toast.makeText(this, "Romania Test score: " + correctAnswersCount + "/" + questions.size(), Toast.LENGTH_LONG).show();
        finish();
    }

    private void setBackground() {
        Drawable drawable = getResources().getDrawable(R.drawable.romania_map);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            backgroundImageView.setBackground(drawable);
        } else {
            backgroundImageView.setImageDrawable(drawable);
        }
    }
}