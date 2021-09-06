package com.example.a4000essentialwordsbook1.SpannableString;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SpanStringBold {

    private final Context context;
    private SpannableString span;
    private Dialog wordDialog;
    private TextView word, phonetic, definition, example;
    private ImageView wordImage;

    private final ArrayList<Integer> startList = new ArrayList<>();
    private final ArrayList<Integer> endList = new ArrayList<>();
    private ImageButton plyAudioBtn, translateBtn;
    private Matcher matcher;
    private boolean flag = false;

    public SpanStringBold(Context context){
        this.context = context;
    }


    public void storyWordsBolder(String mainWord, String[] wordsList, ArrayList<WordModel> modelList){
        span = new SpannableString(mainWord);
        ExecutorService serviceThread = Executors.newFixedThreadPool(2);
        Handler handler = new Handler(Looper.getMainLooper());
        serviceThread.execute(() -> wordStoryBolder(span, mainWord, wordsList, modelList));
        serviceThread.execute(() -> {
            findPersianSentenceStoryThread(mainWord);
            handler.post(this::colorSetterToPersianSentences);
        });
        serviceThread.shutdown();
    }

    private void wordStoryBolder(SpannableString span, String mainWord, String[] wordsList, ArrayList<WordModel> modelList){

        FindWordsListStory findWordsListStory = new FindWordsListStory();
        findWordsListStory.storyWordsFinder(mainWord, wordsList);
        int[] start = findWordsListStory.getStart();
        int[] end = findWordsListStory.getEnd();

        for (int i = 0 ; i < start.length ; i++){
            int finalI = i;
            ClickableSpan storyOnclick = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    wordDialog = new Dialog(context, R.style.ThemeWithCorners);
                    wordLayoutDialog(modelList.get(finalI));
                }

                @Override
                public void updateDrawState(TextPaint txt){
                    txt.setUnderlineText(false);
                    txt.setColor(Color.rgb(0, 100, 0));
                    txt.setFakeBoldText(true);
                    txt.isFakeBoldText();
                }
            };

            span.setSpan(
                    storyOnclick
                    , start[i]
                    , end[i]
                    , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
    }


    private void findPersianSentenceStoryThread( String mainWord){
        Pattern pattern = Pattern.compile("\n\n");
        matcher = pattern.matcher(mainWord);
        persianBolder();
    }
    private void persianBolder(){
        while (matcher.find()){
            if (!flag){
                startList.add(matcher.start());
                flag = true;
            }else {
                endList.add(matcher.start());
                flag = false;
            }
        }

    }
    private void colorSetterToPersianSentences(){
        for (int i = 0 ; i <startList.size(); i++) {
            span.setSpan(
                    new ForegroundColorSpan(ContextCompat.getColor(context, R.color.perColor))
                    , startList.get(i)
                    , endList.get(i)
                    , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
    }


    public SpannableString getSpan() {
        return span;
    }

    private void wordLayoutDialog(WordModel modelList){
        wordDialog.setContentView(R.layout.word_pop_up_activity);
        viewsFinderById(modelList);
        wordDialog.show();
    }

    private void viewsFinderById(WordModel modelList){
        wordImage = wordDialog.findViewById(R.id.word_image_pop_up);
        word = wordDialog.findViewById(R.id.word_pop_up);
        phonetic  = wordDialog.findViewById(R.id.word_pop_up_phonetic);
        plyAudioBtn = wordDialog.findViewById(R.id.word_pop_up_ply_audio_btn);
        translateBtn = wordDialog.findViewById(R.id.word_pop_up_translate_btn);
        definition = wordDialog.findViewById(R.id.word_pop_up_definition);
        example = wordDialog.findViewById(R.id.word_pop_up_example);
        setViewsValues(modelList);
    }

    private void setViewsValues(WordModel modelList){
        wordImage.setImageResource(modelList.getWordImage());
        String txt = modelList.getWord();
        word.setText(txt);
        phonetic.setText(modelList.getPhonetic());
        definition.setText(modelList.getDefinition());
        example.setText(modelList.getExample());
    }


}
