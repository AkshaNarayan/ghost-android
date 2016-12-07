package com.google.engedu.ghost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    private InputStream wordListStream;
    private Random random;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word)
    {
        return words.contains(word);
    }


    public String getAnyWordStartingWith(String prefix)


   {
       if(prefix==null) {
           String value= words.get(new Random().nextInt(10000)).substring(0,4);
           return value;
       }
       else
       {
           int b = 0;
           int e = words.size()- 1;
           while(b<= e) {
               int m= (b+e)/2;
               if(words.get(m).substring(0,prefix.length()).equals(prefix))
                   return words.get(m);
               else
               if(words.get(m).compareTo(prefix)<=0) {
                   b = m+1;
               }
               else {
                   e = m-1;
               }
           }

       }
       return null;
   }
    @Override
    public String getGoodWordStartingWith(String prefix) {
        return null;
    }
}
