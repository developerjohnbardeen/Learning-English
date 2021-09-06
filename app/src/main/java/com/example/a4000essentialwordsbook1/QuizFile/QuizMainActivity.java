package com.example.a4000essentialwordsbook1.QuizFile;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.QuizFile.QuizDataGenerator.GenerateQuizData;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.CorrectModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.SkippedModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.WrongModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizMainResultActivity;
import com.example.a4000essentialwordsbook1.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class QuizMainActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout counterLayout, answerCounterLayout;
    private int[] randomIntList, optNumbGenerator;
    private String mainColumn, optionColumn, answerWord;
    private int columnId, qzHardFlag, pStatus = 0;
    private Intent quizResultIntent;

    private final String engType = "engToPer",
            persianType = "perToEng", photoWordType = "picWord";

    //Quiz String & Image
    private String strTvWord, strOptionOne, strOptionTwo,
            strOptionThree, strOptionFour, quizType;
    private int tvImage;

    private ProgressBar mProgress;

    //quiz CardView
    private CardView cardOptionOne, cardOptionTwo, cardOptionThree,
            cardOptionFour, cardSkipQuestion, cardViewQuizQuestion,
            crdViewAnswerCounter;

    //quiz textView
    private TextView txtTvWord, txtOptionOne, questionCounter,
            txtOptionTwo, txtOptionThree, txtOptionFour, txtOptionSkip,
            correctTxtCounter, wrongTxtCounter, skippedTxtCounter;

    //quiz image
    private ImageView tvQuizImageView;

    //Quiz Lists
    private ArrayList<CorrectModel> correctList;
    private ArrayList<WrongModel> wrongList;
    private ArrayList<SkippedModel> skippedList;

    private int correctInt = 0, wrongInt = 0 , skippedInt = 0;
    private CountDownTimer timer;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);

        quizResultIntent = new Intent(this, QuizMainResultActivity.class);
        listsCreator();
        //functions' Order Matters
        sampleProgress();
        viewsFindById();
        randomNumberGenerator();
        extraValueGetter();
        quizDataGenerator(true);
        timerCountDown();
    }







    public void updateProgress(){
        String counterPlus = Integer.toString(pStatus);
        mProgress.setProgress(pStatus);
        questionCounter.setText(counterPlus);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.card_view_quiz_option_one):
                timer.cancel();
                answerChecker(txtOptionOne);
                quizDataGenerator(true);
                timer.start();
                break;
            case(R.id.card_view_quiz_option_two):
                timer.cancel();
                answerChecker(txtOptionTwo);
                quizDataGenerator(true);
                timer.start();
                break;
            case(R.id.card_view_quiz_option_three):
                timer.cancel();
                answerChecker(txtOptionThree);
                quizDataGenerator(true);
                timer.start();
                break;
            case(R.id.card_view_quiz_option_four):
                timer.cancel();
                answerChecker(txtOptionFour);
                quizDataGenerator(true);
                timer.start();
                break;
            case(R.id.card_view_quiz_option_quiz_skip_answer):
                skipAnswerChecker();
                quizDataGenerator(true);
                skippedAnswerCounter();
                break;
            default:
                break;
        }
    }



    public void quizDataGenerator(boolean flag){
        if (pStatus < 20){
            //updateProgress();
            optionQuizNumberGenerator();
            int index = (pStatus);
            columnId = randomIntList[index];
            String[] strList;
            GenerateQuizData quizDataReceiver = new GenerateQuizData(this, columnId);
            quizDataReceiver.quizDataGenerator(mainColumn, optionColumn);
            strList = quizDataReceiver.getWordsOptionList();
            tvImage = quizDataReceiver.getMainImage();
            strTvWord = quizDataReceiver.getMainWord();
            answerWord = quizDataReceiver.getAnswerWord();
            qzHardFlag = quizDataReceiver.getHardFlag();

            int optOne = optNumbGenerator[0];
            int optTwo = optNumbGenerator[1];
            int optThree = optNumbGenerator[2];
            int optFour = optNumbGenerator[3];


            strOptionOne = strList[optOne];
            strOptionTwo = strList[optTwo];
            strOptionThree = strList[optThree];
            strOptionFour = strList[optFour];
            tvQuizImageView.setImageResource(tvImage);
            viewsValueSetter();
        }else {
            skippedInt--;
            if (flag) {
                endOfQuestions();
            }else {
                skippedAnswerCounter();
            }
        }
        pStatus += 1;
    }



    public void answerChecker(TextView txtView){
        String answer = txtView.getText().toString();
        if (answerWord.equalsIgnoreCase(answer)){
            CorrectModel correctModel = new CorrectModel();
            if (quizType.equalsIgnoreCase(photoWordType)) {
                correctModel.setTvImage(tvImage);
            }
            correctModel.setTvWord(strTvWord);
            correctModel.setCorrectWord(answer);
            correctList.add(correctModel);
            correctAnswerCounter();
        }else {
            WrongModel wrongModel = new WrongModel();
            wrongModel.setId(columnId);
            if (quizType.equalsIgnoreCase(photoWordType)) {
                wrongModel.setTvImage(tvImage);
            }
            wrongModel.setHardFlag(qzHardFlag);
            wrongModel.setTvWord(strTvWord);
            wrongModel.setWrongWord(answer);
            wrongModel.setCorrectWord(answerWord);

            wrongList.add(wrongModel);
            wrongAnswerCounter();
        }
    }



    private void timerCountDown(){
        timer = new CountDownTimer(8000, 10){

            @Override
            public void onTick(long millisUntilFinished) {
                NumberFormat format = new DecimalFormat("0");
                long sec = (millisUntilFinished / 1000) % 60;
                questionCounter.setText(format.format(sec));
                mProgress.setProgress((int) millisUntilFinished);
            }

            @Override
            public void onFinish() {
                if (pStatus < 20) {
                    String txt = Integer.toString(0);
                    questionCounter.setText(txt);
                    mProgress.setProgress(0);
                    skipAnswerChecker();
                    skippedAnswerCounter();
                    quizDataGenerator(false);
                    timer.start();
                }else {
                    skipAnswerChecker();
                    skippedAnswerCounter();
                    endOfQuestions();
                }
            }
        };
        timer.start();
    }

    public void randomNumberGenerator(){
        boolean flag = false;
        randomIntList = new int[20];
        int rand;

        for (int i = 0 ; i < 20 ; i++){
            rand = ThreadLocalRandom.current().nextInt(1, 21);
            for (int num : randomIntList){
                if (num == rand){
                    i--;
                    flag = true;
                    break;
                }
            }
            if (!flag){
                randomIntList[i] = rand;
            }else {
                flag = false;
            }

        }
    }

    private void extraValueGetter(){
        Intent quizIntent = getIntent();
        quizType = quizIntent.getStringExtra("quizType");
        columnNamesSetter();
        quizImageViewAndTextViewVisibility();
    }

    private void quizImageViewAndTextViewVisibility(){
        assert quizType != null;
        if (quizType.equalsIgnoreCase(photoWordType)){
            tvQuizImageView.setVisibility(View.VISIBLE);
            counterLayoutMargin(1,10,2,3);
        }else if (quizType.equalsIgnoreCase(engType)){
            txtTvWord.setVisibility(View.VISIBLE);
            counterLayoutMargin(2,11,3,1);
        }else if (quizType.equalsIgnoreCase(persianType)){
            counterLayoutMargin(4,12,1,3);
            txtTvWord.setVisibility(View.VISIBLE);
        }
    }

    public void viewsFindById(){
        quizCardViewFindById();
        quizTextViewFindById();
        answerCounterViewsFindById();

        tvQuizImageView = findViewById(R.id.diamond_quiz_image_view);
        counterLayout = findViewById(R.id.time_counter_layout);
        cardViewAnswerCounter(60, 360, 10, 10);
        componentClick();
    }



    public void sampleProgress(){
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.circular);
        mProgress = findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(0); // Secondary Progress
        mProgress.setMax(7000); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
    }

    public void viewsValueSetter(){

        txtTvWord.setText(strTvWord);
        txtOptionOne.setText(strOptionOne);
        txtOptionTwo.setText(strOptionTwo);
        txtOptionThree.setText(strOptionThree);
        txtOptionFour.setText(strOptionFour);
        tvQuizImageView.setImageResource(tvImage);
    }

    public void columnNamesSetter(){

        if (quizType.equalsIgnoreCase(engType)){
            mainColumn = DB_NOTES.WORD;
            optionColumn = DB_NOTES.TRANSLATE_WORD;
        }else if (quizType.equalsIgnoreCase(persianType)){
            mainColumn = DB_NOTES.TRANSLATE_WORD;
            optionColumn = DB_NOTES.WORD;
        }else {
            mainColumn = DB_NOTES.TRANSLATE_WORD;
            optionColumn = DB_NOTES.WORD;
        }
    }

    public void optionQuizNumberGenerator(){
        boolean flag = false;
        int listSize = 4;
        optNumbGenerator = new int[]{-1, -1, -1, -1};
        int rand;

        for (int i = 0 ; i < listSize ; i++){
            rand = ThreadLocalRandom.current().nextInt(0, 4);
            for (int num : optNumbGenerator){
                if (num == rand){
                    i--;
                    flag = true;
                    break;
                }
            }
            if (!flag){
                optNumbGenerator[i] = rand;
            }else {
                flag = false;
            }
        }
    }

    private void skipAnswerChecker(){

        SkippedModel skippedModel = new SkippedModel();
        skippedModel.setTvWord(strTvWord);
        skippedModel.setCorrectWord(answerWord);
        skippedList.add(skippedModel);
    }

    private void correctAnswerCounter(){
        correctInt++;
        String correctAnswer = Integer.toString(correctInt);
        correctTxtCounter.setText(correctAnswer);
    }

    private void wrongAnswerCounter(){
        wrongInt++;
        String wrongAnswer = Integer.toString(wrongInt);
        wrongTxtCounter.setText(wrongAnswer);
    }

    private void skippedAnswerCounter(){
        skippedInt++;
        String skippedAnswer = Integer.toString(skippedInt);
        skippedTxtCounter.setText(skippedAnswer);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void endOfQuestions(){
        listSender();
        startActivity(quizResultIntent);
        //finish();
        onBackPressed();
    }

    private void answerCounterViewsFindById(){
        correctTxtCounter = findViewById(R.id.correct_answer_txt_counter);
        wrongTxtCounter = findViewById(R.id.wrong_answer_txt_counter);
        skippedTxtCounter = findViewById(R.id.skipped_answer_txt_counter);
    }

    private void correctListSender(){
        quizResultIntent.putParcelableArrayListExtra("correctList", correctList);
    }

    private void wrongListSender(){
        quizResultIntent.putParcelableArrayListExtra("wrongList", wrongList);
    }

    private void skippedListSender(){
        quizResultIntent.putParcelableArrayListExtra("skippedList", skippedList);
    }

    private void listSender(){
        correctListSender();
        wrongListSender();
        skippedListSender();
    }

    private void listsCreator(){
        correctList = new ArrayList<>();
        wrongList =  new ArrayList<>();
        skippedList =  new ArrayList<>();
    }

    private void quizCardViewFindById(){
        // cardView
        cardOptionOne = findViewById(R.id.card_view_quiz_option_one);
        cardOptionTwo = findViewById(R.id.card_view_quiz_option_two);
        cardOptionThree = findViewById(R.id.card_view_quiz_option_three);
        cardOptionFour = findViewById(R.id.card_view_quiz_option_four);
        cardSkipQuestion = findViewById(R.id.card_view_quiz_option_quiz_skip_answer);
        cardViewQuizQuestion = findViewById(R.id.cardview_quiz);
        crdViewAnswerCounter = findViewById(R.id.card_view_answer_counter_container);
    }
    private void quizTextViewFindById(){
        //TextView
        questionCounter = findViewById(R.id.tv);
        txtTvWord = findViewById(R.id.quiz_text_content);
        txtOptionOne = findViewById(R.id.txt_quiz_option_one);
        txtOptionTwo = findViewById(R.id.txt_quiz_option_two);
        txtOptionThree = findViewById(R.id.txt_quiz_option_three);
        txtOptionFour = findViewById(R.id.txt_quiz_option_four);
        txtOptionSkip = findViewById(R.id.txt_quiz_option_skip_answer);
    }


    /*private void functionsOfParams(){
        counterLayoutMargin();
        cardViewAnswerCounter();
    }*/

    private void counterLayoutMargin(int marginStart, int marginTop, int marginEnd, int marginBottom){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.BELOW, R.id.cardview_quiz);
        params.setMargins(marginStart, marginTop, marginEnd, marginBottom);
        counterLayout.setLayoutParams(params);
    }

    private void cardViewAnswerCounter(int marginStart, int marginTop, int marginEnd, int marginBottom){
        RelativeLayout.LayoutParams crdViewAnswerCounterParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        crdViewAnswerCounterParams.setMargins(marginStart, marginTop, marginEnd, marginBottom);
        crdViewAnswerCounter.setLayoutParams(crdViewAnswerCounterParams);
    }




    public void componentClick() {
        cardOptionOne.setOnClickListener(this);
        cardOptionTwo.setOnClickListener(this);
        cardOptionThree.setOnClickListener(this);
        cardOptionFour.setOnClickListener(this);
        cardSkipQuestion.setOnClickListener(this);
        tvQuizImageView.setOnClickListener(this);
    }

}