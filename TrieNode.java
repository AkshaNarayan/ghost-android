package com.google.engedu.ghost;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class TrieNode
{
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode()
    {
        children = new HashMap<>();
        isWord=false;

    }

    public HashMap<String, TrieNode> getChildren()
    {
        return children;
    }

    public void add(String word)
    {
        children.put("",new TrieNode());

        TrieNode currnode=children.get("");

        int len=word.length();
        for(int i=0;i<len;i++)
        {
            HashMap<String,TrieNode> child=currnode.getChildren();
            char b=word.charAt(i);
            if(!child.containsKey(b))
            {
                TrieNode temp=new TrieNode();
                child.put(b+"",temp);
                currnode=temp;
            }
            else
            {   currnode=child.get(b);
            }
        }
        currnode.isWord=true;
    }


    public String getAnyWordStartingWith(String s)
    {
    TrieNode t= new TrieNode();
    HashMap thash;
    int len=s.length();
    int i=0;
    while (i<len)
    {
        thash=t.children;
        String key=String.valueOf(s.charAt(i));
        if(thash.containsKey(key))
        {
            t=(TrieNode)thash.get(key);
        }
        else
            return null;
        i++;
    }
    String randomKey=null;
    while(t!=null)
    {
        thash=t.children;
        //        HashMap<String, String> x;

        Random random    = new Random();
        ArrayList<String> keys      = new ArrayList<String>(thash.keySet());
        randomKey = keys.get( random.nextInt(keys.size()) );
        t = (TrieNode)thash.get(randomKey);
    }
    //  if(t==null)
    return randomKey;
}

    public boolean isWord(String s) {

        TrieNode t=new TrieNode();
        HashMap thash;
        int l=s.length();
        int i=0;
        while (i<l)
        {
            thash=t.children;
            String key=String.valueOf(s.charAt(i));
            if(thash.containsKey(key))
            {
                t=(TrieNode)thash.get(key);
            }
            else
                return false;
            i++;
        }
        thash=t.children;
        if(thash.containsKey(s))
            return true;
        return false;
    }


    public String getGoodWordStartingWith(String s)
    {
        return null;
    }
}
