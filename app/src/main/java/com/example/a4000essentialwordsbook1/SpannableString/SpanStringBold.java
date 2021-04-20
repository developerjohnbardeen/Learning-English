package com.example.a4000essentialwordsbook1.SpannableString;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordModel;

import java.util.ArrayList;


public class SpanStringBold {

    private final Context context;
    private SpannableString span;
    private Dialog wordDialog;
    private TextView word, phonetic, definition, example;
    private ImageView wordImage;
    private ImageButton plyAudioBtn, translateBtn;
    public FindPersianSentenceStory persianFinder;

    public SpanStringBold(Context context){
        this.context = context;
    }


    public void storyWordsBolder(String mainWord, String[] wordsList, ArrayList<WordModel> modelList){

        span = new SpannableString(mainWord);
            wordStoryBolder(span, mainWord, wordsList, modelList);
            persianSentenceBolder(span, mainWord);


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


    private void persianSentenceBolder(SpannableString span, String mainWord){

        persianFinder = new FindPersianSentenceStory(mainWord, span);
        persianFinder.execute(context);
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
