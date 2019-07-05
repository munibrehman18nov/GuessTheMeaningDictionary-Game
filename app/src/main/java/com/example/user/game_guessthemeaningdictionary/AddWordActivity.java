package com.example.user.game_guessthemeaningdictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.PrintStream;

import stanford.androidlib.SimpleActivity;

public class AddWordActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTraceLifecycle(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
    }

    public void okClick(View view) {
        EditText newWordTextBox = (EditText)findViewById(R.id.newWord);
        EditText DefTextBox = (EditText)findViewById(R.id.newDef);
        String word = newWordTextBox.getText().toString();
        String defn = DefTextBox.getText().toString();

        //Intent intent = new Intent();
        //intent.putExtra("newWord", word);
        //intent.putExtra("defn", defn);

        PrintStream output = new PrintStream(openFileOutput("addedWords_File.txt", MODE_APPEND));
        output.println(word+":"+defn);
        output.close();

        finish("newWord", word, "defn", defn); // NEW_Version

        //setResult(RESULT_OK, intent);
        //finish();
    }
}
