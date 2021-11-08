package com.example.a4000essentialwordsbook1.QuizFile;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.PreferencesNotes.TimerQuizPreferences;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class QuizMainActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout counterLayout, backPressLayout;
    private int[] randomIntList, optNumbGenerator;
    private String mainColumn, optionColumn, answerWord;
    private int columnId, qzHardFlag, pStatus = 0;
    private Intent quizResultIntent;
    private final String sDbNumber = ExtraNotes.DB_NUMBER;
    private final String sUnitNumber = ExtraNotes.UNIT_NUMBER;
    private final String sWordId = ExtraNotes.WORD_ID;
    private final String sQuizType = ExtraNotes.QUIZ_TYPE;
    private final String sCorrectList = ExtraNotes.CORRECT_LIST;
    private final String sWrongList = ExtraNotes.WRONG_LIST;
    private final String sSkippedList = ExtraNotes.SKIPPED_LIST;



    private SharedPreferences quizDurationPreference;
    private final String quizDurationPreferenceName = SettingsPreferencesNotes.SETTINGS_QUIZ_TIME_DURATION_PREFERENCE;
    private final String quizDurationKey = SettingsPreferencesNotes.QUIZ_TIME_DURATION_KEY;
    private final String cancelTimerKey = SettingsPreferencesNotes.CANCEL_QUIZ_TIMER_KEY;


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
    private TextView quizTypeTxtView;

    //quiz image
    private ImageView tvQuizImageView;

    private int dbNumber;
    private int unitNumb;

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
        //functions' Order Matters
        listsCreator();
        sampleProgress();
        viewsFindById();
        randomNumberGenerator();
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
                firstOptionFunctions();
                break;
            case(R.id.card_view_quiz_option_two):
                secondOptionsFunctions();
                break;
            case(R.id.card_view_quiz_option_three):
                thirdOptionsFunctions();
                break;
            case(R.id.card_view_quiz_option_four):
                FourthOptionsFunctions();
                break;
            case (R.id.main_quiz_tab_layout_bck_bttn_layout):
                onBackPressed();
                break;
        }
    }

    private void firstOptionFunctions(){
        timerCanceler();
        answersCheckerAndGenerator(txtOptionOne);
        if (!isTimerCanceled()){timerStarter();}
    }
    private void secondOptionsFunctions(){
        timerCanceler();
        answersCheckerAndGenerator(txtOptionTwo);
        if (!isTimerCanceled()){timerStarter();}
    }
    private void thirdOptionsFunctions(){
        timerCanceler();
        answersCheckerAndGenerator(txtOptionThree);
        if (!isTimerCanceled()){timerStarter();}
    }
    private void FourthOptionsFunctions(){
        timerCanceler();
        answersCheckerAndGenerator(txtOptionFour);
        if (!isTimerCanceled()){timerStarter();}}

    private void answersCheckerAndGenerator(TextView txtOption){
        answerChecker(txtOption);
        quizDataGenerator(true);
    }



    public void quizDataGenerator(boolean flag){
        if (pStatus < 20){
            //updateProgress();
            optionQuizNumberGenerator();
            int index = (pStatus);
            columnId = randomIntList[index];
            String[] strList;
            GenerateQuizData quizDataReceiver = new GenerateQuizData(this, columnId, dbNumber, unitNumb);
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
    public void viewsValueSetter(){

        txtTvWord.setText(strTvWord);
        txtOptionOne.setText(strOptionOne);
        txtOptionTwo.setText(strOptionTwo);
        txtOptionThree.setText(strOptionThree);
        txtOptionFour.setText(strOptionFour);
        tvQuizImageView.setImageResource(tvImage);
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
        if (!isTimerCanceled()) {
            timer = new CountDownTimer(tmDuration(), 10) {

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
                    } else {
                        skipAnswerChecker();
                        skippedAnswerCounter();
                        endOfQuestions();
                    }
                }
            };
            timer.start();
        }
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



    public void sampleProgress(){
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.circular);
        mProgress = findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(0); // Secondary Progress
        mProgress.setMax(tmDuration()); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
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
        skippedModel.setWordId(columnId);
        if (quizType.equalsIgnoreCase(photoWordType)) {
            skippedModel.setTvImage(tvImage);
        }
        skippedModel.setCorrectWord(answerWord);
        skippedModel.setTvWord(strTvWord);
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



    private void extraValueGetter(){
        Intent quizIntent = getIntent();
        quizType = quizIntent.getStringExtra(sQuizType);
        dbNumber = quizIntent.getIntExtra(sDbNumber, 1);
        unitNumb = quizIntent.getIntExtra(sUnitNumber, 1);
        columnNamesSetter();
        quizImageViewAndTextViewVisibility();
    }
    private void quizImageViewAndTextViewVisibility(){
        assert quizType != null;
        if (quizType.equalsIgnoreCase(photoWordType)){
            quizTypeTxtView.setText(R.string.Photo_word_quiz);
            tvQuizImageView.setVisibility(View.VISIBLE);
            counterLayoutMargin(1,10,2,3);
        }else if (quizType.equalsIgnoreCase(engType)){
            quizTypeTxtView.setText(R.string.eng_to_per_quiz);
            txtTvWord.setVisibility(View.VISIBLE);
            counterLayoutMargin(2,11,3,1);
        }else if (quizType.equalsIgnoreCase(persianType)){
            quizTypeTxtView.setText(R.string.Per_to_eng_quiz);
            counterLayoutMargin(4,12,1,3);
            txtTvWord.setVisibility(View.VISIBLE);
        }
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


    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null){timer.cancel();}
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerCanceler();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerCanceler();
    }

    private void endOfQuestions(){
        listSender();
        //finish();
        onBackPressed();
    }

    private void timerCanceler(){if (timer != null){timer.cancel();}}
    private void timerStarter(){if (timer !=null){timer.start();}}


    private void listSender(){
        quizResultIntent.putExtra(sDbNumber, dbNumber);
        quizResultIntent.putExtra(sUnitNumber, unitNumb);
        quizResultIntent.putParcelableArrayListExtra(sCorrectList, correctList);
        quizResultIntent.putParcelableArrayListExtra(sWrongList, wrongList);
        quizResultIntent.putParcelableArrayListExtra(sSkippedList, skippedList);
        startActivity(quizResultIntent);
    }
    private void listsCreator(){
        correctList = new ArrayList<>();
        wrongList =  new ArrayList<>();
        skippedList =  new ArrayList<>();
    }


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

    public void viewsFindById(){
        quizCardViewFindById();
        quizTextViewFindById();
        answerCounterViewsFindById();

        tvQuizImageView = findViewById(R.id.diamond_quiz_image_view);
        counterLayout = findViewById(R.id.time_counter_layout);
        backPressLayout = findViewById(R.id.main_quiz_tab_layout_bck_bttn_layout);
        timerComponentsVisibility();
        extraValueGetter();
        cardViewsMarginsSetter();
        componentClick();
    }

    private void cardViewsMarginsSetter(){
        cardViewAnswerCounterMarginsSetter();
        cardViewQuestionContainerMarginsSetter();
    }

    private void cardViewAnswerCounterMarginsSetter(){
        final double startVal = (quizType.equalsIgnoreCase(photoWordType)) ? 0.09 : 0.09;
        final double topVal = (quizType.equalsIgnoreCase(photoWordType)) ? 0.27 : 0.15;
        final double endVal = (quizType.equalsIgnoreCase(photoWordType)) ? 0.3 : 0.2;
        final double bottomVal = (quizType.equalsIgnoreCase(photoWordType)) ? 0.0001 : 0.0001;

        final int mrgStart = (int)(getWidth() * startVal);
        final int mrgTop = (int)(getHeight() * topVal);
        final int mrgEnd = (int)(getWidth() * endVal);
        final int mrgBottom = (int)(getHeight() * bottomVal);
        cardViewAnswerCounter(mrgStart, mrgTop, mrgEnd, mrgBottom);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    private void cardViewQuestionContainerMarginsSetter(){
        final double startVal = (quizType.equalsIgnoreCase(photoWordType)) ? 0.05 : 0.05;
        final double topVal = (quizType.equalsIgnoreCase(photoWordType)) ? 0.18 : 0.05;
        final double endVal = (quizType.equalsIgnoreCase(photoWordType)) ? 0.05 : 0.05;
        final double bottomVal = (quizType.equalsIgnoreCase(photoWordType)) ? 0.0001 : 0.0001;

        final int mrgStart = (int)(getWidth() * startVal);
        final int mrgTop = (int)(getHeight() * topVal);
        final int mrgEnd = (int)(getWidth() * endVal);
        final int mrgBottom = (int)(getHeight() * bottomVal);
        cardViewQuestionContainer(mrgStart, mrgTop, mrgEnd, mrgBottom);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }



    private void cardViewAnswerCounter(int marginStart, int marginTop, int marginEnd, int marginBottom){
        RelativeLayout.LayoutParams crdViewAnswerCounterParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        crdViewAnswerCounterParams.setMargins(marginStart, marginTop, marginEnd, marginBottom);
        crdViewAnswerCounter.setLayoutParams(crdViewAnswerCounterParams);
    }
    private void cardViewQuestionContainer(int marginStart, int marginTop, int marginEnd, int marginBottom){
        RelativeLayout.LayoutParams crdViewAnswerCounterParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        crdViewAnswerCounterParams.setMargins(marginStart, marginTop, marginEnd, marginBottom);
        cardViewQuizQuestion.setLayoutParams(crdViewAnswerCounterParams);
    }

    private boolean isTimerCanceled(){
        quizDurationPreference = getSharedPreferences(quizDurationPreferenceName, Context.MODE_PRIVATE);
        return quizDurationPreference.getBoolean(cancelTimerKey,false);
    }
    private int tmDuration(){
        quizDurationPreference = getSharedPreferences(quizDurationPreferenceName, Context.MODE_PRIVATE);
        int timeSeconds = quizDurationPreference.getInt(quizDurationKey,7);
        return ((timeSeconds * 1000) + 900);
    }

    private void timerComponentsVisibility(){
        if (isTimerCanceled()){
            counterLayout.setVisibility(View.GONE);
        }else {
            counterLayout.setVisibility(View.VISIBLE);
        }
    }




    private void answerCounterViewsFindById(){
        correctTxtCounter = findViewById(R.id.correct_answer_txt_counter);
        wrongTxtCounter = findViewById(R.id.wrong_answer_txt_counter);
        skippedTxtCounter = findViewById(R.id.skipped_answer_txt_counter);
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
        quizTypeTxtView = findViewById(R.id.main_quiz_type_title_text_View);
    }
    public void componentClick() {
        cardOptionOne.setOnClickListener(this);
        cardOptionTwo.setOnClickListener(this);
        cardOptionThree.setOnClickListener(this);
        cardOptionFour.setOnClickListener(this);
        cardSkipQuestion.setOnClickListener(this);
        tvQuizImageView.setOnClickListener(this);
        backPressLayout.setOnClickListener(this);

    }

    private int getWidth(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    private int getHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}