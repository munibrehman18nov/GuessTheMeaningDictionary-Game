package com.example.user.game_guessthemeaningdictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import stanford.androidlib.SimpleActivity;

public class MainMenuActivity extends SimpleActivity
{

    private static final int ADD_WORD_ACTIVITY_CODE = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void playGameClick(View view) {
        setTraceLifecycle(true);
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        startActivity(MainActivity.class);
    }

    public void addWordClick(View view) {
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivityForResult(intent, ADD_WORD_ACTIVITY_CODE);    // OLD Version

        //startActivityForResult(AddWordActivity.class, ADD_WORD_ACTIVITY_CODE);  // NEW Version

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == ADD_WORD_ACTIVITY_CODE)
        {
            String word = intent.getStringExtra("newWord");
            String defn = intent.getStringExtra("defn");
            toast("You want to add the word "+word+": "+defn, Toast.LENGTH_LONG);
        }
    }
}
