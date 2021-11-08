package com.example.a4000essentialwordsbook1.SpannableString;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindWordsListStory {
    private final int[] start = new int[20];
    private final int[] end = new int[20];
    char verb = 'v';
    char whiteSpace = ' ';
    private Matcher matcher;


    public void storyWordsFinder(String strStory, String[] wordsList, String[] phoneticList) {
        Runnable thread = () -> {
            String mainString = strStory.toLowerCase();
            wordFunctionDeterminer(mainString, wordsList, phoneticList);
        };
        thread.run();
    }
    private void wordFunctionDeterminer(String txtContext, String[] wordsList, String[] phoneticList){
        int listSize = wordsList.length;
        for (int index = 0 ; index < listSize ; index ++){
            String phonetic = phoneticList[index];
            char letter = phonetic.charAt(phonetic.length() - 2);
            char letterTwo = phonetic.charAt(phonetic.length() - 3);
            boolean isVerb = ((letter == verb) && (letterTwo == whiteSpace));

            if (!isVerb){
                nonVerbWords(txtContext, wordsList[index], index);
            }else {
                wordCutter(txtContext, wordsList[index], index);
            }
        }
    }

    private void nonVerbWords(String txtContext, String word, int index){
        start[index] = txtContext.indexOf(word);
        end[index] = start[index] + word.length();
    }




    private void wordCutter(String txtContext, String wordContext, int index) {
/*        String word =
                String.valueOf(wordsList[index].toLowerCase().charAt(0)) +
                        wordsList[index].toLowerCase().charAt(1) +
                        wordsList[index].toLowerCase().charAt(2);*/

        boolean isThreeEqual = wordContext.length() == 3;
        boolean isFourEqual = (wordContext.length() == 4);
        boolean isFiveEqual = wordContext.length() == 5;
        boolean isFiveGreater = wordContext.length() > 5;

        String word;


        //wordListFinder(txtContext, wordContext, index);
        if (isFourEqual){
            word = fourEqual(wordContext);
            wordListFinder(txtContext, word, index);
        }else if (isFiveEqual){
            word = fiveEqual(wordContext);
            wordListFinder(txtContext, word, index);
        }else if (isFiveGreater){
            word = fiveGreater(wordContext);
            wordListFinder(txtContext, word, index);
        }else {
            ThreeEqual(txtContext, wordContext, index);
        }
    }

    private void ThreeEqual(String txtContext, String word, int index){
        /*Pattern pattern = Pattern.compile(word);
        matcher = pattern.matcher(txtContext);
        start[index] = matcher.start();
        end[index] = start[index] + word.length();*/
    }

    private String fourEqual(String word){
        return String.valueOf(word.toLowerCase().charAt(0)) +
                word.toLowerCase().charAt(1) +
                word.toLowerCase().charAt(2);
    }
    private String fiveEqual(String word){
        return String.valueOf(word.toLowerCase().charAt(0)) +
                word.toLowerCase().charAt(1) +
                word.toLowerCase().charAt(2) +
                word.toLowerCase().charAt(3);
    }
    private String fiveGreater(String word){
        return String.valueOf(word.toLowerCase().charAt(0)) +
                word.toLowerCase().charAt(1) +
                word.toLowerCase().charAt(2) +
                word.toLowerCase().charAt(3) +
                word.toLowerCase().charAt(4);
    }



    private void wordListFinder(String txtContext, String word, int index) {
        String mainString = txtContext;
        mainString = mainString.toLowerCase();
        int startWord = mainString.indexOf(word);
        int endWord = 0;
        if (startWord != -1) {

            for (int j = startWord; j < mainString.length(); j++) {
                char letter = mainString.charAt(j);
                boolean isEnd = endWordFlag(letter);
                if (isEnd) {
                    endWord = j;
                    break;
                }
            }

            start[index] = startWord;
            end[index] = endWord;
        }else {
            start[index] = 0;
            end[index] = 0;
        }
    }
    
    private boolean endWordFlag(char letter){
        return letter == ' ' || letter == ',' || letter == '?' || letter == '"' || letter == '`' || letter == '.' || letter == '!';
    }

    public int[] getStart() {
        return start;
    }
    public int[] getEnd() {
        return end;
    }

}