package com.example.a4000essentialwordsbook1.MarkedWords.ReviewWords;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.chabbal.slidingdotsplash.ViewPagerAdapter;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordModel;
import com.example.a4000essentialwordsbook1.SpannableString.TextViewBolder;
import com.example.a4000essentialwordsbook1.UpdateDatabases.UpdateWordDatabase;

import java.util.ArrayList;

public class ReviewWordSlidePagerAdapter extends ViewPagerAdapter {

    private ImageView tvImage;
    private TextView tvWord;
    private TextView txtPhonetic;
    private ImageButton pltWordBtn, translateWordBtn, removeItemBtn, btn;
    private TextView txtDefinition, txtTranslateDef, txtExample, txtTranslateExmpl;
    private CardView cardView;
    private ImageView plyImg;

    private ArrayList<WordModel> reviewList;
    private final Context rwContext;
    private final LayoutInflater inflater;


    public ReviewWordSlidePagerAdapter(Context context, ArrayList<WordModel> list){
        this.inflater = LayoutInflater.from(context);
        this.rwContext = context;
        this.reviewList = list;
    }


    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.slide_word_review_pager_adapter, container, false);
        viewsFinder(view, position);
        container.addView(view, 0);
        Toast.makeText(rwContext, "" + getItemPosition(container), Toast.LENGTH_SHORT).show();
        return view;
    }


    private void viewsFinder(View view, int position){

        tvImage = view.findViewById(R.id.word_review_detailed_image_detail);
        tvWord = view.findViewById(R.id.word_review_detailed_name);
        txtPhonetic = view.findViewById(R.id.word_review_detailed_phonetic);
        pltWordBtn = view.findViewById(R.id.word_review_detailed_ply_audio_btn);
        translateWordBtn = view.findViewById(R.id.word_review_detailed_translate_btn);
        txtDefinition = view.findViewById(R.id.word_review_detailed_definition);
        txtTranslateDef = view.findViewById(R.id.word_review_detailed_definition_translate);
        txtExample = view.findViewById(R.id.word_review_detailed_example);
        txtTranslateExmpl = view.findViewById(R.id.word_review_detailed_example_translate);
        removeItemBtn = view.findViewById(R.id.remove_review_word_item);
        cardView = ((Activity) rwContext).findViewById(R.id.review_marked_word_card_view_word_detailed_container);
        btn = ((Activity) rwContext).findViewById(R.id.review_marked_word_translate);
        plyImg = ((Activity) rwContext).findViewById(R.id.review_marked_word_detailed_card_ply_image);

        btn.setOnClickListener(v ->
                Toast.makeText(rwContext, "Hello There", Toast.LENGTH_SHORT).show());


        //btn = Objects.requireNonNull(getActivity()).findViewById(R.id.click_button);
        valuesGetter(position);
    }

    private void valuesGetter(int position){
        WordModel wordModel = reviewList.get(position);

        int image = wordModel.getWordImage();
        int id = wordModel.getId();
        String word = wordModel.getWord();
        String phonetic = wordModel.getPhonetic();
        String wordTranslate = wordModel.getTranslateWord();
        String wordDefinition = wordModel.getDefinition();
        String wordTranslateDef = wordModel.getTranslateDef();
        String wordExample = wordModel.getExample();
        String wordTranslateExmpl = wordModel.getTranslateExmpl();


        valuesSetter(image, id, word, phonetic, wordTranslate, wordDefinition,
                wordTranslateDef, wordExample, wordTranslateExmpl, position);


        removeItemBtn.setOnClickListener(v -> {
            removeItems(position, id);
        });
    }

    private void valuesSetter(int image, int id, String word, String phonetic,
                              String wordTranslate, String definition, String translateDef,
                              String example, String translateExmpl, int position){
        tvImage.setImageResource(image);
        tvWord.setText(word);
        txtPhonetic.setText(phonetic);
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

    private void removeItems(int position, int columnId){

        reviewList.remove(position);
        if (reviewList.size() == 0){
            cardViewGetDownAnimation();
            cardView.setVisibility(View.INVISIBLE);
            ((Activity) rwContext).onBackPressed();
        }
        notifyDataSetChanged();
        updateHardFlagDatabase(columnId);

    }

    private void updateHardFlagDatabase(int columnId){
        UpdateWordDatabase updateHardFlag = new UpdateWordDatabase(rwContext);
        String columnName = DB_NOTES.HARD_FLAG;
        updateHardFlag.wordDatabaseUpdate(columnName, columnId, 0);
    }

    private void cardViewGetDownAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 500f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.setDuration(300);
        animator.addUpdateListener(animation -> {
            float progress = (float) animation.getAnimatedValue();
            cardView.setTranslationY(progress);
        });
        animator.start();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public View getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    //once notifyDataSetChange is clicked
    // this method will remove and reload all items
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}