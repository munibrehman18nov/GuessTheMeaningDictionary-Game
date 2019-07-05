package com.example.user.game_guessthemeaningdictionary;

import android.media.MediaPlayer;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import stanford.androidlib.SimpleActivity;
import stanford.androidlib.SimpleList;

public class MainActivity extends SimpleActivity
{
    private HashMap<String, String> dictionary;
    private ArrayList<String> wordsList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> fiveRandWords;
    private ArrayList<String> defnsOfFiveRandWordsList;
    private String theWord;
    private static int score;
   // private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTraceLifecycle(true);
        score = 0;
        dictionary = new HashMap<String, String>();
        wordsList = new ArrayList<String>();

    //    mp = MediaPlayer.create(this, R.raw.song);
    }

    private void loadDataFromFile(Scanner scan)
    {
        while(scan.hasNextLine())
        {
            String line = scan.nextLine();
            String token[] = line.split(":");
            String word = token[0];
            String def = token[1];

            wordsList.add(word);
            dictionary.put(word, def);
        }
        scan.close();

    }

    @Override
    public void onSaveInstanceState(Bundle bundle, PersistableBundle outPersistentState)
    {
        super.onSaveInstanceState(bundle, outPersistentState);
        bundle.putString("theWord", theWord);
        bundle.putStringArrayList("fiveRandWords",fiveRandWords);
        bundle.putStringArrayList("defnsOfFiveRandWordsList", defnsOfFiveRandWordsList);
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle, PersistableBundle persistentState)
    {
        if(bundle.containsKey("theWord") && bundle.containsKey("defnsOfFiveRandWordsList"))
        {
            super.onRestoreInstanceState(bundle, persistentState);
            theWord = bundle.getString("theWord", "");
            fiveRandWords = bundle.getStringArrayList("fiveRandWords");
            defnsOfFiveRandWordsList = bundle.getStringArrayList("defnsOfFiveRandWordsList");
        }

        Log.d("bundle", "theWord = " + theWord);
        Log.d("bundle", "defnsOfFiveRandWordsList = " + defnsOfFiveRandWordsList);
    }

    /*    @Override
    protected void onPause()
    {
        super.onPause();
        mp.pause();
    }
*/
    @Override
    protected void onResume()
    {
        super.onResume();
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.dictionary));
        loadDataFromFile(scan);
        try {
            Scanner scan1 = new Scanner(openFileInput("addedWords_File.txt"));
            loadDataFromFile(scan1);
        } catch(Exception e)
        {
            log(e); }

        startGame();

        //mp.start();
    }

    /*@Override
    protected void onStop()
    {
        super.onStop();
        mp.stop();
    }*/


    private void startGame()
    {
        setFiveRandWords();
        setDefnsOfFiveRandWords();

    //-------------------------------------------------------------------------------------------------------------
        // OLD Version
        //------------------------------------------------------------------------------------------
        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, defnsOfFiveRandWordsList);
        //ListView lv = findViewById(R.id.defnsListView); // old-version
        //ListView lv = find(R.id.defnsListView); // stanford_lib version
        //lv.setAdapter(adapter);
        //------------------------------------------------------------------------------------------

        // NEW Version
        SimpleList.with(this).setItems(findListView(R.id.defnsListView), defnsOfFiveRandWordsList);
    //--------------------------------------------------------------------------------------------------------------


    // OLD Version of onItemClickListener
    //--------------------------------------------------------------------------------------------------------------
        /*findListView(R.id.defnsListView).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String defClicked = defnsOfFiveRandWordsList.get(position);
                String rightDef = dictionary.get(theWord);

                if(defClicked.equals(rightDef))
                {
                    //Toast.makeText(MainActivity.this,"You are awesome!", Toast.LENGTH_SHORT).show(); // old
                    toast("You are awesome"); // new
                    score = score + 1;
                    //TextView stv = findViewById(R.id.scoreTextView);
                    TextView stv = find(R.id.scoreTextView); // new
                    stv.setText("Score: "+score);
                }
                else
                {
                    //Toast.makeText(MainActivity.this,"You SUCK.", Toast.LENGTH_SHORT).show(); // old
                    toast("You SUCK.", Toast.LENGTH_SHORT); // new
                    score = score - 1;
                    //TextView stv = findViewById(R.id.scoreTextView);
                    TextView stv = find(R.id.scoreTextView); //new
                    stv.setText("Score: "+score);
                }
                startGame();
            }
        });*/
    //--------------------------------------------------------------------------------------------------------------
    // instead
    // NEW Version
        findListView(R.id.defnsListView).setOnItemClickListener(this);

    }

    // NEW Version of onItemClickListener
    @Override
    public void onItemClick(ListView list, int index)
    {
        String defClicked = defnsOfFiveRandWordsList.get(index);
        String rightDef = dictionary.get(theWord);

        if(defClicked.equals(rightDef))
        {
            //Toast.makeText(MainActivity.this,"You are awesome!", Toast.LENGTH_SHORT).show(); // old
            toast("You are awesome"); // new
            score = score + 1;
            //TextView stv = findViewById(R.id.scoreTextView);
            TextView stv = find(R.id.scoreTextView); // new
            stv.setText("Score: "+score);
        }
        else
        {
            //Toast.makeText(MainActivity.this,"You SUCK.", Toast.LENGTH_SHORT).show(); // old
            toast("You SUCK.", Toast.LENGTH_SHORT); // new
            score = score - 1;
            //TextView stv = findViewById(R.id.scoreTextView);
            TextView stv = find(R.id.scoreTextView); //new
            stv.setText("Score: "+score);
        }
        startGame();
    }

    private void setFiveRandWords()
    {
        fiveRandWords = new ArrayList<String>();
        Collections.shuffle(wordsList);
        for(int i=0; i<5; i++)
        {
            fiveRandWords.add(wordsList.get(i));
        }

        //------------------------------------------------
        //TextView tv = findViewById(R.id.wordTextView); // old
        //TextView tv = find(R.id.wordTextView); // new
        //theWord = fiveRandWords.get(0);
        //tv.setText(theWord);
        //------------------------------------------------
    //instead
        theWord = fiveRandWords.get(0);
        findTextView(R.id.wordTextView).setText(theWord); // recommended
    }

    private void setDefnsOfFiveRandWords()
    {
        defnsOfFiveRandWordsList = new ArrayList<>();
        for(int i=0; i<5; i++)
        {
            defnsOfFiveRandWordsList.add(dictionary.get(fiveRandWords.get(i)));
        }
        Collections.shuffle(defnsOfFiveRandWordsList);
    }



}
