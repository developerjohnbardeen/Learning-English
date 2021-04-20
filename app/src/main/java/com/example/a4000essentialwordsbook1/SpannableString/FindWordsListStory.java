package com.example.a4000essentialwordsbook1.SpannableString;



public class FindWordsListStory {
    private final String[] words = new String[20];
    private final int[] start = new int[20];
    private final int[] end = new int[20];


    public void storyWordsFinder(String strStory, String[] wordsList) {

        try {
            Runnable thread = () -> {
                String mainString = strStory.toLowerCase();
                wordCutter(mainString, wordsList);
            };
            thread.run();
        }catch (Exception ignored){
        }

    }

    private void wordCutter(String mainString, String[] wordsList) {
        int size = wordsList.length;

        for (int i = 0; i < size; i++) {
            String list = String.valueOf(wordsList[i].toLowerCase().charAt(0)) +
                    wordsList[i].toLowerCase().charAt(1) +
                    wordsList[i].toLowerCase().charAt(2);
            words[i] = list;
        }

        wordListFinder(mainString, words);
    }

    private void wordListFinder(String text, String[] wordList) {
        String mainString = text;
        mainString = mainString.toLowerCase();

        for (int i = 0; i < 20; i++) {
            String word = wordList[i];
            int startWord = mainString.indexOf(word);
            int endWord = 0;

            for (int j = startWord; j < mainString.length(); j++) {
                char letter = mainString.charAt(j);
                if (letter == ' ' || letter == ',' || letter == '?' || letter == '"' || letter == '`') {
                    endWord = j;
                    break;
                }
            }

            start[i] = startWord;
            end[i] = endWord;
        }
    }


    public int[] getStart() {
        return start;
    }

    public int[] getEnd() {
        return end;
    }

}