package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a4000essentialwordsbook1.Linsteners.DefinitionPlayListener;
import com.example.a4000essentialwordsbook1.Linsteners.ExamplePlayListener;
import com.example.a4000essentialwordsbook1.Linsteners.PlaySingleTrackInterface;
import com.example.a4000essentialwordsbook1.Linsteners.WordPlayListener;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.SpannableString.TextViewBolder;


public class SlideWordFragment extends Fragment implements View.OnClickListener,
        WordSlideCardViewActivity.SendFlagInterface {

    private final Context context;
    private final WordModel modelList;
    private final int startAnimationInt = 300;
    private final int endAnimationInt = 300;
    private ImageView imageWord;
    private TextView definition, translateDefinition,
            example, translateExample, word, wordPhonetic, wordTranslate;
    private ImageButton imageBtn, plyAudioBtn, trnsltButton;
    private boolean flag = false;
    private final WordPlayListener wordListener;
    private final DefinitionPlayListener definitionListener;
    private final ExamplePlayListener exampleListener;
//    private final WordPlayListener wordListener;
//    private final DefinitionPlayListener definitionListener;
//    private final ExamplePlayListener exampleListener;
    

    public static SlideWordFragment newInstance(Context context, WordModel model,
                                                WordPlayListener wordListener,
                                                DefinitionPlayListener definitionListener,
                                                ExamplePlayListener exampleListener){
        return new SlideWordFragment(context, model,
                wordListener,
                definitionListener,
                exampleListener);
    }

    public SlideWordFragment(Context context, WordModel models,
                             WordPlayListener wordListener,
                             DefinitionPlayListener definitionListener,
                             ExamplePlayListener exampleListener){
        this.context = context;
        this.modelList = models;
        this.wordListener = wordListener;
        this.definitionListener = definitionListener;
        this.exampleListener = exampleListener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_detail_slide, container, false);

        viewsFindById(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewsValuesSetter(modelList);
    }

    private void viewsFindById(View view){

        imageWord = view.findViewById(R.id.word_image_detailed);
        word = view.findViewById(R.id.word_detailed);
        wordPhonetic = view.findViewById(R.id.word_detailed_phonetic);
        wordTranslate = view.findViewById(R.id.word_detailed_translate);
        definition = view.findViewById(R.id.word_detailed_definition);
        translateDefinition = view.findViewById(R.id.word_detailed_definition_translate);
        imageBtn = view.findViewById(R.id.word_detailed_translate_btn);
        plyAudioBtn = view.findViewById(R.id.word_detailed_ply_audio_btn);
        example = view.findViewById(R.id.word_detailed_example);
        translateExample = view.findViewById(R.id.word_detailed_example_translate);
        //trnsltButton = ((Activity) context).findViewById(R.id.word_detail_all_translate_btn);
        thisOnClick();
    }

    private void thisOnClick(){
        imageBtn.setOnClickListener(this);
        plyAudioBtn.setOnClickListener(this);
        definition.setOnClickListener(this);
        example.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        //word_detailed_example word_detailed_definition

        switch (v.getId()){
            case (R.id.word_detailed_translate_btn):
                translateTextViewVisibility();
                break;
            case (R.id.word_detailed_ply_audio_btn):
                wordListener.wordCanPlay(true);
                break;
            case (R.id.word_detailed_example):
                exampleListener.exampleCanPlay(true);
                break;
            case (R.id.word_detailed_definition):
                definitionListener.definitionCanPlay(true);
                break;
        }
    }




    private void viewsValuesSetter(WordModel list){

        imageWord.setImageResource(list.getWordImage());
        word.setText(list.getWord());
        wordPhonetic.setText(list.getPhonetic());
        wordTranslate.setText(list.getTranslateWord());
        definition.setText(list.getDefinition());
        translateDefinition.setText(list.getTranslateDef());
        example.setText(list.getExample());
        translateExample.setText(list.getTranslateExmpl());
        stringBolder(list.getDefinition(), list.getExample(), list.getWord());
    }


    private void stringBolder(String defWord,
                              String exmplWord,String word){

        TextViewBolder bolder = new TextViewBolder(context);
        bolder.defTextBolder(definition, defWord, word);
        bolder.exmplTextBolder(example, exmplWord, word);
    }





    private void translateTextViewVisibility(){
        if (!flag){
            fadeBackViewAnimation();
            flag = true;
        }else {
            fadeOutViewAnimation();
            flag = false;
        }
    }


    private void fadeOutViewAnimation(){
        inVisibilitySetterTranslateWordAnimation();
        inVisibilitySetterTranslateDefinitionAnimation();
        inVisibilitySetterTranslateExampleAnimation();
    }
    private void fadeBackViewAnimation(){
        visibilitySetterTranslateWordAnimation();
        visibilitySetterTranslateExampleAnimation();
        visibilitySetterTranslateDefinitionAnimation();
    }



    private void visibilitySetterTranslateWordAnimation(){
        wordTranslate.animate()
                .y(wordPhonetic.getHeight() +10)
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        wordTranslate.setVisibility(View.VISIBLE);
                    }
                }).start();
    }
    private void visibilitySetterTranslateDefinitionAnimation(){
        translateDefinition.animate()
                .y(definition.getHeight() + 10)
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        translateDefinition.setVisibility(View.VISIBLE);
                    }
                }).start();
    }
    private void visibilitySetterTranslateExampleAnimation(){
        translateExample.animate()
                .y(example.getHeight() + 10)
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        translateExample.setVisibility(View.VISIBLE);
                    }}).start();
    }


    private void inVisibilitySetterTranslateWordAnimation(){
        wordTranslate.animate()
                .translationY(wordTranslate.getHeight())
                .alpha(0.0f)
                .setDuration(endAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        wordTranslate.setVisibility(View.INVISIBLE);
                    }
                }).start();
    }
    private void inVisibilitySetterTranslateDefinitionAnimation(){
        translateDefinition.animate()
                .translationY(translateDefinition.getHeight())
                .alpha(0.0f)
                .setDuration(endAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        translateDefinition.setVisibility(View.INVISIBLE);
                    }
                }).start();
    }
    private void inVisibilitySetterTranslateExampleAnimation(){
        translateExample.animate()
                .translationY(translateExample.getHeight())
                .alpha(0.0f)
                .setDuration(endAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        translateExample.setVisibility(View.INVISIBLE);
                    }
                }).start();
    }




    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        flag = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void sendFlag(boolean flag) {
        Toast.makeText(context, "" + flag, Toast.LENGTH_SHORT).show();
    }



}
