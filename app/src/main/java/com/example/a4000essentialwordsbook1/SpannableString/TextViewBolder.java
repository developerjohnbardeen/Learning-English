package com.example.a4000essentialwordsbook1.SpannableString;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class TextViewBolder extends SpannableStringBuilder {

    private final Context context;

    public TextViewBolder(Context context){
        this.context = context;
    }

    public void defTextBolder(TextView textView, String mainWord, String word){
        SpannableString span = new SpannableString(mainWord);
        mainWord = mainWord.toLowerCase();

        int spanStart = mainWord.indexOf(word);
        int spanEnd = spanStart + word.length();


        ClickableSpan defOnclick = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
            }

            @Override
            public void updateDrawState(TextPaint txt){
                txt.setUnderlineText(false);
                txt.setColor(Color.rgb(0, 100, 0));
                txt.setFakeBoldText(true);
                txt.isFakeBoldText();
            }
        };

        try {
            span.setSpan(
                    defOnclick
                    , spanStart
                    , spanEnd
                    , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );


            textView.setText(span);
            textView.setClickable(true);
            textView.setMovementMethod(LinkMovementMethod.getInstance());

        }catch (Exception ignored){
        }
    }

    public void exmplTextBolder(TextView textView, String mainWord, String word){
        SpannableString span = new SpannableString(mainWord);
        mainWord = mainWord.toLowerCase();

        int spanStart = mainWord.indexOf(word);
        int spanEnd = spanStart + word.length();


        ClickableSpan exmplOnclick = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
            }

            @Override
            public void updateDrawState(TextPaint txt){
                txt.setUnderlineText(false);
                txt.setColor(Color.rgb(0, 100, 0));
                txt.setFakeBoldText(true);
                txt.isFakeBoldText();
            }
        };

        try {
            span.setSpan(
                    exmplOnclick
                    , spanStart
                    , spanEnd
                    , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            textView.setText(span);

            textView.setClickable(true);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }catch (Exception ignored){
        }
    }
}