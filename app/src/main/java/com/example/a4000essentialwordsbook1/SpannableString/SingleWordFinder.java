package com.example.a4000essentialwordsbook1.SpannableString;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class SingleWordFinder {
    String mainWord;
    String word;
    TextView textView;

    private void textBolder(){
        SpannableString span = new SpannableString(mainWord);

        int spanStart = mainWord.indexOf(word);
        int spanEnd = mainWord.indexOf(word) + String.valueOf(word).length();

        StringBuilder cString = new StringBuilder();

        for (int i = 0 ; i < mainWord.length() ; i++){

            if (mainWord.charAt(i) == ' ')
                break;
            cString.append(mainWord.charAt(i));
        }

        if (word.equalsIgnoreCase(cString.toString())) {
            if (Character.isUpperCase(cString.charAt(0))) {
                String string = cString.toString();
                spanStart = mainWord.indexOf(string);
                spanEnd = mainWord.indexOf(word) + word.length() + 1;
            }
        }

        ClickableSpan exmplOnclick = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //Toast.makeText(context, word + "\n" + mainWord, Toast.LENGTH_SHORT).show();
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
        }catch (Exception e){
            //Toast.makeText(context, "string could not be found", Toast.LENGTH_SHORT).show();
        }
    }


}
