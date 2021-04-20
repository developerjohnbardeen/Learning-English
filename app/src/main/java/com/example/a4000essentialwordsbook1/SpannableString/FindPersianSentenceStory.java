package com.example.a4000essentialwordsbook1.SpannableString;


import android.content.Context;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.example.a4000essentialwordsbook1.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindPersianSentenceStory extends AsyncTask<Context, Context, SpannableString> {
    private final ArrayList<Integer> startList = new ArrayList<>();
    private final ArrayList<Integer> endList = new ArrayList<>();
    private final String text;
    private final SpannableString span;
    private Matcher matcher;
    private boolean flag;


    public FindPersianSentenceStory(String text, SpannableString span){
        this.text = text;
        this.span = span;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Pattern pattern = Pattern.compile("\n\n");
        matcher = pattern.matcher(text);
        flag = false;
    }


    @Override
    protected void onProgressUpdate(Context... values) {
        super.onProgressUpdate(values);
        for (int i = 0 ; i < startList.size() ; i++){

            span.setSpan(
                    new ForegroundColorSpan(values[0].getResources().getColor(R.color.perColor))
                    , startList.get(i)
                    , endList.get(i)
                    , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
    }

    @Override
    protected SpannableString doInBackground(Context... contexts) {
        persianBolder(contexts);
        return null;
    }


    @Override
    protected void onPostExecute(SpannableString spannableString) {
        super.onPostExecute(spannableString);
    }

    private void persianBolder(Context[] context){
        while (matcher.find()){
            if (!flag){
                int start = matcher.start();
                startList.add(start);
                publishProgress(context[0]);
                flag = true;
            }else {
                int end = matcher.start();
                endList.add(end);
                publishProgress(context[0]);
                flag = false;
            }
        }

    }

}
