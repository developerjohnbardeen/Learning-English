package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.QuizPieChartResult;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.a4000essentialwordsbook1.R;
import java.util.ArrayList;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;



public class QuizPieChartResultFragment extends Fragment {
    private final Context context;
    private TextView tvCorrect, tvWrong, tvSkipped;
    private PieChartView pieChart;
    private int correctCount, wrongCount, skippedCount;
    private final int[] answerCount;
    private final ArrayList quizResult = new ArrayList<>();
    private String correctTxt, wrongTxt, skippedTxt;

    public QuizPieChartResultFragment(Context context, int[] count){
        this.context = context;
        this.answerCount = count;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_quiz_pie_chart, container, false);
        viewsFinderById(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pieChartView();
    }





    private void pieChartView(){
        answerCounterGetter();
        setPieChartCountString();
        ArrayList<SliceValue>  pieData = new ArrayList<>();
        pieData.add(new SliceValue((correctCount * 5), ContextCompat.getColor(context, R.color.correctColor)).setLabel((correctCount * 5) + "%"));
        if (skippedCount != 0) {
            pieData.add(new SliceValue((skippedCount * 5), ContextCompat.getColor(context,R.color.skippedColor)).setLabel((skippedCount * 5) + "%"));
        }
        pieData.add(new SliceValue((wrongCount * 5), ContextCompat.getColor(context,R.color.wrongColor)).setLabel((wrongCount * 5) + "%"));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(12);
        pieChartData.setHasCenterCircle(true).setCenterText1("Game Pie Chart").setCenterText1FontSize(15).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChart.setPieChartData(pieChartData);
        pieChart.startDataAnimation();

    }

    private void setPieChartCountString(){
        correctTxt = Integer.toString(correctCount);
        wrongTxt = Integer.toString(wrongCount);
        skippedTxt = Integer.toString(skippedCount);


        tvCorrect.setText(correctTxt);
        tvWrong.setText(wrongTxt);
        tvSkipped.setText(skippedTxt);
        //pieChart;
    }



    private void answerCounterGetter(){
        correctCount = answerCount[0];
        wrongCount = answerCount[1];
        skippedCount = answerCount[2];
    }

    private void viewsFinderById(View view){
        tvCorrect = view.findViewById(R.id.tv_correct_answer_count);
        tvWrong = view.findViewById(R.id.tv_wrong_answer_count);
        tvSkipped = view.findViewById(R.id.tv_skipped_answer_count);
        pieChart = view.findViewById(R.id.pie_chart_quiz_result);

    }

}
