package com.example.a4000essentialwordsbook1.MarkedWords.ReviewWords;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
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
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.SpannableString.TextViewBolder;


public class FragmentReviewWord extends Fragment implements View.OnClickListener{
    private final Context rwContext;
    private final WordModel reviewList;
    private ImageView tvImage;
    private TextView txtWord, txtPhonetic, txtWordTranslate;
    private ImageButton plytWordBtn, translateWordBtn, removeItemBtn;
    private TextView txtDefinition, txtTranslateDef, txtExample, txtTranslateExmpl;
    private final int startAnimationInt = 300;
    private final int endAnimationInt = 300;
    private boolean flag = false;
    private RemoveCardItem removeCardItem;

    public static FragmentReviewWord newInstance(Context context, WordModel model){
        return new FragmentReviewWord(context, model);
    }

    public FragmentReviewWord (Context context, WordModel model){
        this.rwContext = context;
        this.reviewList = model;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_word_fragment , container, false);
        viewsFinderById(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        valuesGetter(reviewList);
        thisOnClickListener();
    }


    private void valuesGetter(WordModel wordReviewList){

        int image = wordReviewList.getWordImage();
        int id = wordReviewList.getId();
        String word = wordReviewList.getWord();
        String phonetic = wordReviewList.getPhonetic();
        String wordTranslate = wordReviewList.getTranslateWord();
        String definition = wordReviewList.getDefinition();
        String translateDef = wordReviewList.getTranslateDef();
        String example = wordReviewList.getExample();
        String translateExmpl = wordReviewList.getTranslateExmpl();


        valuesSetter(image, id, word, phonetic, wordTranslate, definition,
                translateDef, example, translateExmpl);

    }

    private void valuesSetter(int image, int id, String word, String phonetic,
                              String wordTranslate, String definition, String translateDef,
                              String example, String translateExmpl){
        tvImage.setImageResource(image);
        txtWord.setText(word);
        txtPhonetic.setText(phonetic);
        txtWordTranslate.setText(wordTranslate);
        txtDefinition.setText(definition);
        txtTranslateDef.setText(translateDef);
        txtExample.setText(example);
        txtTranslateExmpl.setText(translateExmpl);

        stringBolder(txtDefinition, txtExample, definition , example, word);
    }

    private void stringBolder(TextView txtDefinition, TextView txtExample,
                              String defWord, String exmplWord, String word){

        TextViewBolder bolder = new TextViewBolder(rwContext);
        bolder.defTextBolder(txtDefinition, defWord, word);
        bolder.exmplTextBolder(txtExample, exmplWord, word);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.word_review_detailed_translate_btn):
                translateTextViewVisibility();
                break;
            case (R.id.remove_review_word_item):
                removeCardItem.removeItem();
                break;
        }
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
        txtWordTranslate.animate()
                .y(txtPhonetic.getHeight() +10)
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        txtWordTranslate.setVisibility(View.VISIBLE);
                    }
                }).start();
    }
    private void visibilitySetterTranslateDefinitionAnimation(){
        txtTranslateDef.animate()
                .y(txtDefinition.getHeight() + 10)
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        txtTranslateDef.setVisibility(View.VISIBLE);
                    }
                }).start();
    }
    private void visibilitySetterTranslateExampleAnimation(){
        txtTranslateExmpl.animate()
                .y(txtExample.getHeight() + 10)
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        txtTranslateExmpl.setVisibility(View.VISIBLE);
                    }}).start();
    }


    private void inVisibilitySetterTranslateWordAnimation(){
        txtWordTranslate.animate()
                .translationY(txtWordTranslate.getHeight())
                .alpha(0.0f)
                .setDuration(endAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        txtWordTranslate.setVisibility(View.INVISIBLE);
                    }
                }).start();
    }
    private void inVisibilitySetterTranslateDefinitionAnimation(){
        txtTranslateDef.animate()
                .translationY(txtTranslateDef.getHeight())
                .alpha(0.0f)
                .setDuration(endAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        txtTranslateDef.setVisibility(View.INVISIBLE);
                    }
                }).start();
    }
    private void inVisibilitySetterTranslateExampleAnimation(){
        txtTranslateExmpl.animate()
                .translationY(txtTranslateExmpl.getHeight())
                .alpha(0.0f)
                .setDuration(endAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        txtTranslateExmpl.setVisibility(View.INVISIBLE);
                    }
                }).start();
    }


    private void viewsFinderById(View view){

        tvImage = view.findViewById(R.id.word_review_detailed_image);
        txtWord = view.findViewById(R.id.word_review_detailed_name);
        txtPhonetic = view.findViewById(R.id.word_review_detailed_phonetic);
        txtWordTranslate = view.findViewById(R.id.word_review_translate_detailed);
        plytWordBtn = view.findViewById(R.id.word_review_detailed_ply_audio_btn);
        translateWordBtn = view.findViewById(R.id.word_review_detailed_translate_btn);
        txtDefinition = view.findViewById(R.id.word_review_detailed_definition);
        txtTranslateDef = view.findViewById(R.id.word_review_detailed_definition_translates);
        txtExample = view.findViewById(R.id.word_review_detailed_example);
        txtTranslateExmpl = view.findViewById(R.id.word_review_detailed_example_translate);
        removeItemBtn = view.findViewById(R.id.remove_review_word_item);
    }
    private void thisOnClickListener(){
        removeItemBtn.setOnClickListener(this);
        translateWordBtn.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        flag = false;
    }


    public interface RemoveCardItem{
        void removeItem();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        try {
            removeCardItem = (RemoveCardItem) activity;
        }catch (Exception e){
            Toast.makeText(activity, "An Error Occurred", Toast.LENGTH_SHORT).show();
        }
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


}
