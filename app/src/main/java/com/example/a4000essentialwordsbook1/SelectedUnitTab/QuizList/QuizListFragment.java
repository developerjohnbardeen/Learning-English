package com.example.a4000essentialwordsbook1.SelectedUnitTab.QuizList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.QuizFile.QuizMainActivity;
import com.example.a4000essentialwordsbook1.R;



public class QuizListFragment extends Fragment implements View.OnClickListener{
    private final Context quzContext;
    private String intentValue = "quizType";
    private final int  unitNum, dbNumb;
    private CardView photoWords;
    private CardView persianToEnglish;
    private CardView englishToPersian;
    private ImageView imgQuizView;

    public QuizListFragment(Context context, int unitNmb, int dbNumb){
        this.quzContext = context;
        this.unitNum = unitNmb;
        this.dbNumb = dbNumb;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.quiz_fragment_activity, parent, false);
        setComponents(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    public void setComponents(View view){
        englishToPersian = view.findViewById(R.id.eng_to_per_quiz);
        persianToEnglish = view.findViewById(R.id.per_to_eng_quiz);
        photoWords = view.findViewById(R.id.photo_word_quiz);
        imgQuizView = view.findViewById(R.id.image_Story_quiz);

        setComponentValues();
        setOnclickListener();
    }

    public void setOnclickListener(){
        englishToPersian.setOnClickListener(this);
        persianToEnglish.setOnClickListener(this);
        photoWords.setOnClickListener(this);
    }

    public void setComponentValues(){
        Glide.with(quzContext)
                .load(R.drawable.newquiztime)
                .placeholder(R.drawable.loadimg)
                .error(R.drawable.loadimg)
                .into(imgQuizView);
    }


    @Override
    public void onClick(View v) {
        Intent quizIntent = new Intent(quzContext, QuizMainActivity.class);
        switch (v.getId()){
            case (R.id.eng_to_per_quiz):
                quizIntent.putExtra(intentValue, "engToPer");
                quzContext.startActivity(quizIntent);
                break;
            case (R.id.per_to_eng_quiz):
                quizIntent.putExtra(intentValue, "perToEng");
                quzContext.startActivity(quizIntent);
                break;
            case (R.id.photo_word_quiz):
                quizIntent.putExtra(intentValue, "picWord");
                quzContext.startActivity(quizIntent);
                break;
        }
    }





    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
