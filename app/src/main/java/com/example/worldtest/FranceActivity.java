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

public class FranceActivity extends AppCompatActivity {
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
        questions.add(new Question("Care este capitala Franței?", new String[]{"Londra", "Paris", "Berlin"}, 1));
        questions.add(new Question("Unde se află Turnul Eiffel, un simbol iconic al Franței?", new String[]{"Marsilia", "Nisa", "Paris"}, 2));
        questions.add(new Question("Care este numele celei mai lungi râuri din Franța?", new String[]{"Loira", "Rin", "Sena"}, 0));
        questions.add(new Question("În ce regiune a Franței se găsește celebrul castel Mont Saint-Michel?", new String[]{"Bretania", "Provence", "Normandia"}, 0));
        questions.add(new Question("Care este numele celebrului muzeu situat în Paris, cunoscut pentru găzduirea tabloului Mona Lisa?", new String[]{"Muzeul Luvru", "Muzeul Orsay", "Muzeul Pompidou"}, 0));
        questions.add(new Question("Unde se află Palatul Versailles, renumit pentru grădinile sale elaborate și arhitectura opulentă?", new String[]{"Lyon", "Nantes", "Versailles"}, 2));
        questions.add(new Question("Ce munte înalt se află la granița dintre Franța și Italia și este cel mai înalt vârf al Alpilor?", new String[]{"Mont Blanc", "Matterhorn", "Eiger"}, 0));
        questions.add(new Question("Care este numele regiunii viticole din Franța cunoscută pentru producția de șampanie?", new String[]{"Bordeaux", "Burgundia", "Champagne"}, 2));

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
                    Toast.makeText(FranceActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FranceActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

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

        Toast.makeText(this, "France Test score: " + correctAnswersCount + "/" + questions.size(), Toast.LENGTH_LONG).show();
        finish();
    }

    private void setBackground() {
        Drawable drawable = getResources().getDrawable(R.drawable.france_map);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            backgroundImageView.setBackground(drawable);
        } else {
            backgroundImageView.setImageDrawable(drawable);
        }
    }
}
