package com.example.thegreatdivide;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Randomizer extends AppCompatActivity {

    private ArrayList<CharSequence> namesList = new ArrayList<>();
    private ArrayList<CharSequence> editedNamesList = new ArrayList<>();
    private ArrayList<CharSequence> groupsList = new ArrayList<>();
    private ArrayList<CharSequence> groupssizesList = new ArrayList<>();

    private ArrayList<CharSequence> randomizedNamesList = new ArrayList<>();
    private ArrayList<ArrayList<CharSequence>> listOfGroupLists;
    private ArrayList<CharSequence> activeList;
    private ArrayList<CharSequence> printList;

    TextView testPrint;
    String textForPrint = "";
    int totalSizes;
    boolean moreNames;
    int activeListSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomizer);

        namesList = getIntent().getCharSequenceArrayListExtra("names");
        editedNamesList = getIntent().getCharSequenceArrayListExtra("names");
        groupsList = getIntent().getCharSequenceArrayListExtra("groups");
        groupssizesList = getIntent().getCharSequenceArrayListExtra("groupSizes");

        testPrint = findViewById(R.id.textView_randomizer_printTest);
        testPrint.setMovementMethod(new ScrollingMovementMethod());

        listOfGroupLists = new ArrayList<>(groupsList.size());


        for (int i = 0; i < groupssizesList.size(); i++) {
            totalSizes += Integer.valueOf(groupssizesList.get(i).toString());
        }

        if (namesList.size() > totalSizes)
            moreNames = true;
        else
            moreNames = false;

        randomizeNames();
        putIntoGroups();


        for (int g = 0; g < listOfGroupLists.size(); g++) {
            printList = listOfGroupLists.get(g);

            for (int i = 0; i < printList.size(); i++) {
                textForPrint += printList.get(i).toString() + "\n";
            }

            textForPrint += "\n";
        }

        if (editedNamesList.size() != 0)
            textForPrint += "\nNames not included: ";
        while (editedNamesList.size() != 0) {
            if (editedNamesList.size() == 1)
                textForPrint += editedNamesList.remove(0);
            else
                textForPrint += editedNamesList.remove(0) + ", ";
        }


        testPrint.setText(textForPrint);


    }
    public void putIntoGroups() {

        for(int i = 0; i < groupsList.size();i++){ // adding a list for every single group
            listOfGroupLists.add(new ArrayList<CharSequence>());
        }

        for(int i = 0; i < listOfGroupLists.size(); i++){ // adding names to each of the lists
            activeList = listOfGroupLists.get(i);
            activeListSize = Integer.valueOf(groupssizesList.get(i).toString());

            activeList.add(groupsList.get(i)); // group name title

            while(activeListSize > 0 && randomizedNamesList.size() > 0) {
                activeList.add(randomizedNamesList.remove(0)); // adds name to list then removes
                activeListSize--;
            }

            if (randomizedNamesList.size() == 0) {
                if (activeList.size() ==1)
                    activeList.add("[ empty ]");
                else if (activeListSize > 0)
                    activeList.add("[ list incomplete ]");
            }
        }

    }

    private void randomizeNames() {
        int randomValue;

        if (moreNames) { // if there are going to be left over names
            for (int spacesLeft = totalSizes; spacesLeft > 0; spacesLeft--) {
                randomValue = (int) Math.ceil(Math.random() * editedNamesList.size()); // gives index in nameslist
                randomizedNamesList.add(editedNamesList.remove(randomValue - 1)); // adds and removes random value in names list

            }
        }
        else {
            for (int namesLeft = namesList.size(); namesLeft > 0; namesLeft--) {
                randomValue = (int) Math.ceil(Math.random() * editedNamesList.size()); // gives index in nameslist
                randomizedNamesList.add(editedNamesList.remove(randomValue - 1)); // adds and removes random value in names list
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1111 && resultCode == RESULT_OK) {
            namesList = data.getCharSequenceArrayListExtra("names");
        }
        if (requestCode == 1111 && resultCode == RESULT_OK) {
            groupsList = data.getCharSequenceArrayListExtra("groups");
        }

        if (requestCode == 1111 && resultCode == RESULT_OK) {
            groupssizesList = data.getCharSequenceArrayListExtra("groupSizes");
        }
    }

    public void returnOnClick(View view) {
        Intent intent = new Intent(Randomizer.this, Collection.class);
        intent.putCharSequenceArrayListExtra("names", namesList);
        intent.putCharSequenceArrayListExtra("groups", groupsList);
        intent.putCharSequenceArrayListExtra("groupSizes", groupssizesList);
        setResult(RESULT_OK, intent);
        finish();
    }
}