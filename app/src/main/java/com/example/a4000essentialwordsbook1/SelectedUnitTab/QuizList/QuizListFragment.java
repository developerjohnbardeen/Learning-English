package com.example.a4000essentialwordsbook1.SelectedUnitTab.QuizList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.QuizFile.QuizMainActivity;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Settings.SettingsPreferencesActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;


public class QuizListFragment extends Fragment implements View.OnClickListener{
    private final String sQuizType = ExtraNotes.QUIZ_TYPE;
    private final String sCorrectList = ExtraNotes.CORRECT_LIST;
    private final String sWrongList = ExtraNotes.WRONG_LIST;
    private final String sSkippedList = ExtraNotes.SKIPPED_LIST;

    private final int  unitNum, dbNumb;
    private final String sDbNumber = ExtraNotes.DB_NUMBER;
    private final String sUnitNumber = ExtraNotes.UNIT_NUMBER;
    private final String sWordId = ExtraNotes.WORD_ID;
    private CardView photoWords;
    private CardView persianToEnglish;
    private CardView englishToPersian;
    private ImageView imgQuizView;

    public QuizListFragment(int unitNmb, int dbNumb){
        this.unitNum = unitNmb;
        this.dbNumb = dbNumb;
    }
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        Glide.with(requireActivity())
                .load(R.drawable.newquiztime)
                .placeholder(R.drawable.loadimg)
                .error(R.drawable.loadimg)
                .into(imgQuizView);
    }


    @Override
    public void onClick(View v) {
        Intent quizIntent = new Intent(requireActivity(), QuizMainActivity.class);
        switch (v.getId()){
            case (R.id.eng_to_per_quiz):
                quizIntent.putExtra(sQuizType, "engToPer");
                quizIntent.putExtra(sDbNumber, dbNumb );
                quizIntent.putExtra(sUnitNumber, unitNum);
                requireActivity().startActivity(quizIntent);
                break;
            case (R.id.per_to_eng_quiz):
                quizIntent.putExtra(sQuizType, "perToEng");
                quizIntent.putExtra(sDbNumber, dbNumb );
                quizIntent.putExtra(sUnitNumber, unitNum );
                requireActivity().startActivity(quizIntent);
                break;
            case (R.id.photo_word_quiz):
                quizIntent.putExtra(sQuizType, "picWord");
                quizIntent.putExtra(sDbNumber, dbNumb );
                quizIntent.putExtra(sUnitNumber, unitNum );
                requireActivity().startActivity(quizIntent);
                break;
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.alternate_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.alternate_setting_menu):
                settingStartActivity();
                return true;
            case (R.id.alternate_download_menu):
                Toast.makeText(requireActivity(), "Download Files is Under Process!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void settingStartActivity(){
        Intent settingIntent = new Intent(requireActivity(), SettingsPreferencesActivity.class);
        startActivity(settingIntent);
    }
    private void reviewMarkedWordStartActivity(){
        Intent reviewIntent = new Intent(requireActivity(), MarkedWordActivity.class);
        startActivity(reviewIntent);
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
