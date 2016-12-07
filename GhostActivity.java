package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private String WordFragment="";
    private GhostDictionary dictionary;
    private ArrayList<String> wordList;
    TextView mghostText, gamestatus;
    private boolean userTurn = false;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        mghostText= (TextView)findViewById(R.id.ghostText) ;
        gamestatus = (TextView)findViewById(R.id.gameStatus);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new SimpleDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        onStart(null);

    }

    public String StarterWord()
    {

        String temp =  wordList.get(random.nextInt(9999) + 1);
        WordFragment=temp;
        return temp;
    }

//call this everytime there is a new chance or when the game starts

    @Override
    public boolean onKeyUp ( int keyCode, KeyEvent event) {
        TextView label = (TextView) findViewById(R.id.gameStatus);

        String word = null;

        word = (String) mghostText.getText() + "" + ((char) event.getUnicodeChar());
        mghostText.setText(word);

        if (dictionary.isWord(word)) {
            gamestatus.setText("Valid word formed");
        }

        userTurn = false;
        label.setText(COMPUTER_TURN);
        computerTurn();
        return super.onKeyUp(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void computerTurn()
    {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        String s=(String)mghostText.getText();
        if(s.length()>3 && dictionary.isWord(s))
        {
            updateGameStatus(false);
            Toast.makeText(getApplicationContext(),"Job Done :P"+s, Toast.LENGTH_SHORT).show();
        }
        else
        {
            String word=dictionary.getAnyWordStartingWith(s);
            if(word==null)
            {
                updateGameStatus(false);
                Toast.makeText(getApplicationContext(),"Invalid Word "+s, Toast.LENGTH_SHORT).show();
            }
            else
            {
                int len=s.length();
                String com="";
                if(len!=0)
                {
                    char ch=word.charAt(len);
                    String cat=String.valueOf(ch);
                    com=s.concat(cat);
                }
                else
                {
                    final int N = 26;

                    Random r = new Random();
                    char ch= (char)(97+r.nextInt(N));
                    com=String.valueOf(ch);
                }
                mghostText.setText(com);
                userTurn = true;
                label.setText(USER_TURN);
            }
        }
    }


    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        //userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
//        if (userTurn) {
//            label.setText(USER_TURN);
        //       } else {
        label.setText(COMPUTER_TURN);
        computerTurn();
        //       }
        return true;
    }

    public void OnReset(View view) {

        WordFragment= "";
        onStart(null);
    }


    private void updateGameStatus(boolean userTurn) {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if(userTurn){
            label.setText("User won");
        }else{
            label.setText("Computer won");
        }
    }


    public void challenge(View v)
    {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        String s=(String)mghostText.getText();
        if(s!=null)
        {
            if(s.length()>3 && dictionary.isWord(s))
            {
                updateGameStatus(true);
                Toast.makeText(getApplicationContext(),"Job Done! :P Word is- "+s, Toast.LENGTH_SHORT).show();
            }
            else
            {
                String word=dictionary.getAnyWordStartingWith(s);
                if(word==null)
                {
                    updateGameStatus(true);
                    Toast.makeText(getApplicationContext(),"Invalid word "+s, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    updateGameStatus(false);
                    Toast.makeText(getApplicationContext(),"Word having prefix "+s+" is "+word, Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

}
