package com.example.a4000essentialwordsbook1.MarkedWords;

public interface MarkedWordRemover {
    void removeMarkedWord(int dbId, int unitId, int position, int wordId);
    void audioPlayer(int position);
}
