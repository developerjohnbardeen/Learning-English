package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SampleClasses.SampleClass;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordModel;
import com.example.a4000essentialwordsbook1.SpannableString.TextViewBolder;

import java.util.ArrayList;
import java.util.List;


public class WordSliderAdapter extends PagerAdapter implements View.OnClickListener{

    private final ArrayList<WordModel> models;
    private final ArrayList<Integer> flagList;
    private final LayoutInflater inflater;
    private final Context vPContext;
    private int nowPosition = -1;
    private final int[] trnsltPosition = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    private boolean flagListener = false;
    private boolean exmplFlag = true;

    private ImageView wordImage;
    private TextView wordNameText;
    private TextView wordPhoneticText;
    private TextView wordDefinitionText;
    private TextView wordDefinitionTranslateText;
    private TextView wordExampleText;
    private TextView wordTranslateExampleText;
    private TextView wordTranslate;
    private ImageButton translateBtn, plyAudioBtn;
    private Button translate;
    private int pos;

    public WordSliderAdapter(ArrayList<WordModel> models, ArrayList<Integer> flagList, Context vPContext, int newPosition) {
        this.models = models;
        this.flagList = flagList;
        this.pos = newPosition;
        this.vPContext = vPContext;
        inflater = LayoutInflater.from(vPContext);
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.slide_cardview_activity, container, false);
        viewsFinder(view, position);
        container.addView(view, 0);
        return view;
    }

    private void viewsFinder(View view, int position){
        wordImage = view.findViewById(R.id.word_image_detail);
        wordNameText = view.findViewById(R.id.word_detailed_name);
        wordPhoneticText = view.findViewById(R.id.word_detailed_phonetic);
        wordTranslate = view.findViewById(R.id.word_detailed_translate);
        wordDefinitionText = view.findViewById(R.id.word_detailed_definition);
        wordDefinitionTranslateText = view.findViewById(R.id.word_detailed_definition_translate);
        wordExampleText = view.findViewById(R.id.word_detailed_example);
        wordTranslateExampleText = view.findViewById(R.id.word_detailed_example_translate);
        translateBtn = view.findViewById(R.id.word_detailed_translate_btn);
        plyAudioBtn = view.findViewById(R.id.word_detailed_ply_audio_btn);

        translate = ((Activity) vPContext).findViewById(R.id.word_detailed_all_translate);
        translate.setOnClickListener(this);
        valuesGetter(position);

    }

    private void valuesGetter(int position){
        //flagStateChanger(position);

        WordModel vpModel = models.get(position);

        int image = vpModel.getWordImage();
        int id = vpModel.getId();
        String word = vpModel.getWord();
        String phonetic = vpModel.getPhonetic();
        String translateWord = vpModel.getTranslateWord();
        String definition = vpModel.getDefinition();
        String translateDef = vpModel.getTranslateDef();
        String example = vpModel.getExample();
        String translateExmpl = vpModel.getTranslateExmpl();

        translateBtn.setOnClickListener(this);
        plyAudioBtn.setOnClickListener(this);
        wordImage.setOnClickListener(v -> {
            Intent intent = new Intent(vPContext, SampleClass.class);
            intent.putParcelableArrayListExtra("sampleList", models);
            intent.putExtra("int", position);
            vPContext.startActivity(intent);
        });


        valuesSetter(image, word, phonetic, translateWord, translateDef, translateExmpl, id, position);
        stringBolder(definition, example, word);
    }

    private void valuesSetter(int image, String word, String phonetic,
                              String translateWord, String translateDef, String translateExmpl,
                              int id, int position){

        wordImage.setImageResource(image);
        wordNameText.setText(word);
        wordPhoneticText.setText(phonetic);
        wordTranslate.setText(translateWord);
        wordDefinitionTranslateText.setText(translateDef);
        wordTranslateExampleText.setText(translateExmpl);
    }

    private void stringBolder(String defWord,
                              String exmplWord,String word){

        TextViewBolder bolder = new TextViewBolder(vPContext);
        bolder.defTextBolder(wordDefinitionText, defWord, word);
        bolder.exmplTextBolder(wordExampleText, exmplWord, word);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.word_detailed_translate_btn):
                Toast.makeText(vPContext, "Translating is under process", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.word_detailed_ply_audio_btn):
                Toast.makeText(vPContext, "playing Audio is under process", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.word_image_detail):
                break;
            case (R.id.word_detailed_all_translate):
                break;
        }
    }


    public void flagStateChanger(int position){

        if (nowPosition != position){
            flagListener = false;

            nowPosition = position;
        }else {
            flagListener = true;
            nowPosition = nowPosition - 1;
        }
        Toast.makeText(vPContext, "now " + nowPosition + "\nnew " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
