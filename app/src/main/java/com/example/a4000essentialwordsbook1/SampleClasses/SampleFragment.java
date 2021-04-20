package com.example.a4000essentialwordsbook1.SampleClasses;

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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordModel;
import com.example.a4000essentialwordsbook1.SpannableString.TextViewBolder;


public class SampleFragment extends Fragment implements View.OnClickListener, SampleClass.SendFlagInterface{
    private final Context context;
    private final WordModel models;
    private final int startAnimationInt = 300;
    private final int endAnimationInt = 300;
    private ImageView imageWord;
    private TextView word, wordPhonetic, wordTranslate;
    private TextView definition, translateDefinition;
    private TextView example, translateExample;
    private boolean flag = false;
    private boolean smplFlag = false;



    public SampleFragment(Context context, WordModel models){
        this.context = context;
        this.models = models;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);
        viewsFindById(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void viewsFindById(View view){

        ImageButton imageBtn = view.findViewById(R.id.word_sample_translate_btn);
        imageWord = view.findViewById(R.id.word_image_sample);
        word = view.findViewById(R.id.word_sample);
        wordPhonetic = view.findViewById(R.id.word_sample_phonetic);
        wordTranslate = view.findViewById(R.id.word_sample_translate);
        definition = view.findViewById(R.id.word_sample_definition);
        translateDefinition = view.findViewById(R.id.word_sample_detailed_definition_translate);
        example = view.findViewById(R.id.word_sample_example);
        imageBtn.setOnClickListener(this);

        translateExample = view.findViewById(R.id.word_sample_detailed_example_translate);
        viewsValuesSetter(models);

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
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        flag = false;
    }

    @Override
    public void onClick(View v) {
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
    public void sendFlag(boolean flag) {
        smplFlag = !flag;
    }


}








