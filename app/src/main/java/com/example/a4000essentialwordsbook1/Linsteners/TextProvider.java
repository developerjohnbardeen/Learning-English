package com.example.a4000essentialwordsbook1.Linsteners;

import com.example.a4000essentialwordsbook1.Models.WordModel;

public interface TextProvider {
    WordModel getWordModel(int position);
    int getCount();
}
