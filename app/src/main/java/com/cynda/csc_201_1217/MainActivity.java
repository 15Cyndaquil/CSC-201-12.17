package com.cynda.csc_201_1217;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<String> hangmanWords = new ArrayList<>();
    private static ArrayList<String> usedLetters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView currentWord = (TextView) findViewById(R.id.currentWord);
        final EditText letterInput = (EditText) findViewById(R.id.letterInput);
        final Button guessBT = (Button) findViewById(R.id.guessBT);

        try {
            Scanner scan = new Scanner(getApplicationContext().getAssets().open("hangman.txt")).useDelimiter(" ");
            while (scan.hasNext()) {
                hangmanWords.add(scan.next());
            }
            scan.close();
            System.out.println(hangmanWords.toString());
            hangman(currentWord, letterInput, guessBT);
            Button reset = (Button) findViewById(R.id.reset);
            reset.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    usedLetters.clear();
                    hangman(currentWord, letterInput, guessBT);
                }
            });
        } catch (IOException exc) {
            System.out.println("File not found");
        }
    }

    private static void hangman(final TextView currentWord, final EditText letterInput, Button guessBT) {


        currentWord.setText("");
        currentWord.setHint(hangmanWords.get((int) (Math.random() * hangmanWords.size())));
        System.out.println(currentWord.getHint());
        for (int i = 0; i < currentWord.getHint().length(); i++) {
            currentWord.append("*");
        }

        guessBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder wordEdit = new StringBuilder(currentWord.getHint().toString());
                StringBuilder wordEdit2 = new StringBuilder(currentWord.getText().toString());

                if(letterInput.getText().length()==1){
                    usedLetters.add(letterInput.getText().toString());
                    letterInput.setText("");
                }

                for (int i = 0; i < usedLetters.size(); i++) {
                    int index = 0;
                    if (wordEdit.toString().toLowerCase().contains(usedLetters.get(i).toLowerCase())) {
                        while (index > -1) {
                            index = wordEdit.indexOf(usedLetters.get(i).toString(), index);
                            wordEdit2.replace(index, index+1, usedLetters.get(i));
                            if(wordEdit.lastIndexOf(usedLetters.get(i))==index){
                                index = -1;
                            }else{
                                index++;
                            }
                        }
                    }
                }
                currentWord.setText(wordEdit2.toString());
                System.out.println(usedLetters.toString());
            }
        });

    }
}
