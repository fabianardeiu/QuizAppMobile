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
import java.util.Random;


public class QuizActivity extends AppCompatActivity {

    private static int score;
    private static int questionNr;
    private static int displayedQuestions;
    private static int questionsToDisplay = 5;
    private ArrayList<Question> questions = new ArrayList<Question>();
    private static Random rnd = new Random();

    public QuizActivity() {
        score = 0;
        questionNr = 0;
        displayedQuestions = 0;
        seedQuestions();
    }

    private void seedQuestions(){
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

        ArrayList<Answer> q6Answers = new ArrayList<Answer>();
        q6Answers.add(new Answer("Value types", true));
        q6Answers.add(new Answer("Reference types", false));
        q6Answers.add(new Answer("Pointer types", false));
        questions.add(new Question("Which of the following variable types can be assigned a value directly in C#?", q6Answers));

        ArrayList<Answer> q7Answers = new ArrayList<Answer>();
        q7Answers.add(new Answer("ToChar", false));
        q7Answers.add(new Answer("ToByte", true));
        q7Answers.add(new Answer("ToDateTime", false));
        questions.add(new Question("Which of the following converts a type to a byte value in C#?", q7Answers));

        ArrayList<Answer> q8Answers = new ArrayList<Answer>();
        q8Answers.add(new Answer("ToString", false));
        q8Answers.add(new Answer("ToSingle", false));
        q8Answers.add(new Answer("ToUInt16", true));
        questions.add(new Question("Which of the following converts a type to an unsigned int type in C#?", q8Answers));

        ArrayList<Answer> q9Answers = new ArrayList<Answer>();
        q9Answers.add(new Answer("elif", true));
        q9Answers.add(new Answer("if", false));
        q9Answers.add(new Answer("define", false));
        questions.add(new Question("Which of the following preprocessor directive allows creating a compound conditional directive in C#?", q9Answers));

        ArrayList<Answer> q10Answers = new ArrayList<Answer>();
        q10Answers.add(new Answer("var a = new IComparable()", true));
        q10Answers.add(new Answer("var a = new [] {0};", false));
        q10Answers.add(new Answer("var a = new Int32();", false));
        questions.add(new Question("Which of the following statements is not valid to create new object in C#?", q10Answers));

        ArrayList<Answer> q11Answers = new ArrayList<Answer>();
        q11Answers.add(new Answer("int[,] b = new int[10, 2];", false));
        q11Answers.add(new Answer("int[][][] cc = new int[10][2][];", true));
        q11Answers.add(new Answer("int[][] c = new int[10][];", false));
        questions.add(new Question("The followings are some examples of integer arrays. Which expression is not valid in C#?", q11Answers));

        ArrayList<Answer> q12Answers = new ArrayList<Answer>();
        q12Answers.add(new Answer("class MyClass where T : IComparable", false));
        q12Answers.add(new Answer("class MyClass where T : class", false));
        q12Answers.add(new Answer("Both", true));
        questions.add(new Question("When defining a class using C# Generics, which of the followings is invalid?", q12Answers));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        generateQuestions();

        final Button nextQuestion = findViewById(R.id.button_confirm_next);
        final Button backToMenu = findViewById(R.id.button_back_to_menu);
        backToMenu.setVisibility(View.GONE);

        TextView question = findViewById(R.id.text_view_question);
        TextView answer1 = findViewById(R.id.radioButton1);
        TextView answer2 = findViewById(R.id.radioButton2);
        TextView answer3 = findViewById(R.id.radioButton3);
        TextView scoreText = findViewById(R.id.text_view_score);
        getNextQuestion(question, answer1, answer2, answer3, nextQuestion, backToMenu, scoreText);

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
                    TextView scoreText = findViewById(R.id.text_view_score);

                    updateScore(answers);
                    getNextQuestion(question, answer1, answer2, answer3, nextQuestion, backToMenu,scoreText);
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

    private void generateQuestions(){
        int index = rnd.nextInt(questions.size());
        if (index > questions.size()/2){
            questionNr = index-questionsToDisplay;
        }
        else {
            questionNr = index;
        }
    }

    private void getNextQuestion(TextView question, TextView answer1, TextView answer2, TextView answer3, Button nextQuestion, Button backToMenu, TextView scoreText){
        if(displayedQuestions == questionsToDisplay){
            question.setVisibility(View.GONE);
            answer1.setVisibility(View.GONE);
            answer2.setVisibility(View.GONE);
            answer3.setVisibility(View.GONE);
            nextQuestion.setVisibility(View.GONE);
            backToMenu.setVisibility(View.VISIBLE);
            scoreText.setText("Score: " + score + "/" + questionsToDisplay);
        }else {
            Question questionToDisplay = this.questions.get(questionNr);
            question.setText(questionToDisplay.getText());

            ArrayList<Answer> answers = questionToDisplay.getAnswers();
            answer1.setText(answers.get(0).getText());
            answer2.setText(answers.get(1).getText());
            answer3.setText(answers.get(2).getText());
            questionNr +=1;
            displayedQuestions +=1;
        }
    }

    private void updateScore(RadioGroup answers){
        Question questionToCalculate = this.questions.get(questionNr-1);
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
