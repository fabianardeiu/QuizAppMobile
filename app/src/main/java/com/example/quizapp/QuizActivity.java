package com.example.quizapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.quizapp.Domain.Answer;
import com.example.quizapp.Domain.Question;
import java.util.ArrayList;


public class QuizActivity extends AppCompatActivity {

    private static int score = 0;
    private static int questionNr = 0;
    private static ArrayList<Question> questions = new ArrayList<Question>();


    public QuizActivity() {
        score = 0;
        ArrayList<Answer> q1Answers = new ArrayList<Answer>();
        q1Answers.add(new Answer(" cout << \"Hello World\";", false));
        q1Answers.add(new Answer(" System.out.println(\"Hello World\");", false));
        q1Answers.add(new Answer(" Console.WriteLine(\"Hello World\");", true));
        questions.add(new Question("What is a correct syntax to output \"Hello World\" in C#?", q1Answers));

        ArrayList<Answer> q2Answers = new ArrayList<Answer>();
        q2Answers.add(new Answer(" # This is a comment", false));
        q2Answers.add(new Answer(" // This is a comment", true));
        q2Answers.add(new Answer(" /* This is a comment", false));
        questions.add(new Question("How do you insert COMMENTS in C# code?", q2Answers));

        ArrayList<Answer> q3Answers = new ArrayList<Answer>();
        q3Answers.add(new Answer("<>", false));
        q3Answers.add(new Answer("=", false));
        q3Answers.add(new Answer("==", true));
        questions.add(new Question("Which operator can be used to compare two values?", q3Answers));

        ArrayList<Answer> q4Answers = new ArrayList<Answer>();
        q4Answers.add(new Answer(" class myObj = new MyClass();", false));
        q4Answers.add(new Answer(" MyClass myObj = new MyClass();", true));
        q4Answers.add(new Answer(" new myObj = MyClass();", false));
        questions.add(new Question("What is the correct way to create an object called myObj of MyClass?", q4Answers));

        ArrayList<Answer> q5Answers = new ArrayList<Answer>();
        q5Answers.add(new Answer("abstract", false));
        q5Answers.add(new Answer("final", false));
        q5Answers.add(new Answer("private", true));
        questions.add(new Question("Which access modifier makes the code only accessible within the same class?", q5Answers));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final Button nextQuestion = findViewById(R.id.button_confirm_next);
        final Button backToMenu = findViewById(R.id.button_back_to_menu);
        backToMenu.setVisibility(View.GONE);

        TextView question = findViewById(R.id.text_view_question);
        TextView answer1 = findViewById(R.id.radioButton1);
        TextView answer2 = findViewById(R.id.radioButton2);
        TextView answer3 = findViewById(R.id.radioButton3);
        TextView score = findViewById(R.id.text_view_score);
        getNextQuestion(question, answer1, answer2, answer3, nextQuestion, backToMenu, score);

        nextQuestion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                RadioGroup answers = findViewById(R.id.answers);
                if(answers.getCheckedRadioButtonId() == -1){
                    Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_LONG).show();
                }
                else{
                    TextView question = findViewById(R.id.text_view_question);
                    TextView answer1 = findViewById(R.id.radioButton1);
                    TextView answer2 = findViewById(R.id.radioButton2);
                    TextView answer3 = findViewById(R.id.radioButton3);
                    TextView score = findViewById(R.id.text_view_score);


                    updateScore(answers);
                    getNextQuestion(question, answer1, answer2, answer3, nextQuestion, backToMenu,score);
                    answers.clearCheck();
                }
            }
        });

        backToMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                backToMenu();
            }
        });
    }

    private void backToMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void getNextQuestion(TextView question, TextView answer1, TextView answer2, TextView answer3, Button nextQuestion, Button backToMenu, TextView score){
        if(questions.size() == questionNr){
            question.setVisibility(View.GONE);
            answer1.setVisibility(View.GONE);
            answer2.setVisibility(View.GONE);
            answer3.setVisibility(View.GONE);
            nextQuestion.setVisibility(View.GONE);
            backToMenu.setVisibility(View.VISIBLE);
            score.setText("Correct answers: " + QuizActivity.score);
        }else {

            Question questionToDisplay = this.getQuestion(questionNr);
            question.setText(questionToDisplay.getText());

            ArrayList<Answer> answers = questionToDisplay.getAnswers();
            answer1.setText(answers.get(0).getText());
            answer2.setText(answers.get(1).getText());
            answer3.setText(answers.get(2).getText());
            questionNr +=1;
        }
    }

    private Question getQuestion(int questionNr){
        return questions.get(questionNr);
    }

    private void updateScore(RadioGroup answers){
        Question questionToCalculate = this.getQuestion(questionNr-1);
        int userAnswerId = answers.getCheckedRadioButtonId();
        RadioButton userAnswer = findViewById(userAnswerId);
        for (int i=0; i < questionToCalculate.getAnswers().size(); i++){
            if(questionToCalculate.getAnswers().get(i).isCorrect()){
                if(questionToCalculate.getAnswers().get(i).getText().equals(userAnswer.getText())){
                    score +=1;
                }
            }
        }
    }

}
