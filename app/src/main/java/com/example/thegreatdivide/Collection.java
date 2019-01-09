package com.example.thegreatdivide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Collection extends AppCompatActivity {
    TextView numberofNames;
    TextView numberofGroups;
    TextView numberofSpaces;
    CheckBox checkBoxgroups;
    CheckBox checkBoxnames;
    int totalSizes = 0;

    String message;
    String addS ="";
    String isOrAre = "are";

    TextView leftPanel;
    TextView rightPanel;

    Button randomize;

    private ArrayList<CharSequence> namesList = new ArrayList<>();
    private ArrayList<CharSequence> groupsList = new ArrayList<>();
    private ArrayList<CharSequence> groupssizesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        numberofNames = findViewById(R.id.textView_collection_numNames);
        numberofSpaces = findViewById(R.id.textView_collection_numSpaces);
        numberofGroups = findViewById(R.id.textView_collection_numGroups);
        checkBoxgroups = findViewById(R.id.checkBox_collection_groups);
        checkBoxnames = findViewById(R.id.checkBox_collection_names);
        leftPanel = findViewById(R.id.textView_collection_backgroundLeftTop);
        rightPanel = findViewById(R.id.textView_collection_backgroundRightTop);
        randomize = findViewById(R.id.button_collection_randomize);


        //checkBoxgroups.setChecked(checkPasswordExist());
        checkBoxgroups.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxgroups.isChecked()) {
                    leftPanel.setVisibility(View.INVISIBLE);
                    if (checkBoxnames.isChecked())
                        randomize.setVisibility(View.VISIBLE);
                }
                else {
                    leftPanel.setVisibility(View.VISIBLE);
                    randomize.setVisibility(View.INVISIBLE);
                }
            }
        });


        checkBoxnames.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxnames.isChecked()) {
                    rightPanel.setVisibility(View.INVISIBLE);
                    if (checkBoxgroups.isChecked())
                        randomize.setVisibility(View.VISIBLE);
                } else {
                    rightPanel.setVisibility(View.VISIBLE);
                    randomize.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void addNamesOnClick(View view){
        Intent intent = new Intent(Collection.this, Names.class);
        intent.putCharSequenceArrayListExtra("pickle", namesList);
        startActivityForResult(intent, 1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234 && resultCode == RESULT_OK) {
            namesList = data.getCharSequenceArrayListExtra("pickle");
            numberofNames.setText("Number of Names: " + namesList.size());
        }
        if (requestCode == 4321 && resultCode == RESULT_OK) {
            groupsList = data.getCharSequenceArrayListExtra("pickles");
            numberofGroups.setText("Number of Groups: " + groupsList.size());
        }



        if (requestCode == 4321 && resultCode == RESULT_OK) {
            groupssizesList = data.getCharSequenceArrayListExtra("picklessizes");

            totalSizes = 0;
            for (int i = 0; i < groupssizesList.size(); i++){
                totalSizes += Integer.valueOf(groupssizesList.get(i).toString());
            }
            numberofSpaces.setText("Number of Spaces: " + totalSizes);
        }
    }

    public void addGroupsOnClick(View view){
        Intent intent = new Intent(Collection.this, Groups.class);
        intent.putCharSequenceArrayListExtra("pickles", groupsList);
        intent.putCharSequenceArrayListExtra("picklessizes", groupssizesList);
        startActivityForResult(intent, 4321);
    }

    public void randomizeTestOnClick(View view) {
        int totalSizes = 0;
        int spacesMinusNames;

        for (int i = 0; i < groupssizesList.size(); i++){
            totalSizes += Integer.valueOf(groupssizesList.get(i).toString());
        }

        spacesMinusNames = totalSizes - namesList.size();
        if (Math.abs(spacesMinusNames) == 1) {
            addS = " ";
            isOrAre = " is ";
        }
        else {
            addS = "s ";
            isOrAre = " are ";
        }


        if (totalSizes > namesList.size()){
            message = "Note: There" + isOrAre + spacesMinusNames + " more space" + addS + "in groups than there are names. Therefore, not all groups will be filled. Would you like to proceed?";
            showPopupWindow();
        }

        else if (totalSizes < namesList.size()){
            message = "Note: There" + isOrAre + Math.abs(spacesMinusNames) + " more name" + addS + "than there are spaces in groups. Therefore, not all names will be put into groups. Would you like to proceed?";
            showPopupWindow();
        }
        else
            randomize();



    }

    public void randomize()
    {
        Intent intent = new Intent(Collection.this, Randomizer.class);
        intent.putCharSequenceArrayListExtra("groups", groupsList);
        intent.putCharSequenceArrayListExtra("names", namesList);
        intent.putCharSequenceArrayListExtra("groupSizes", groupssizesList);
        startActivityForResult(intent, 1111);
    }



    public void showPopupWindow (){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                randomize();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.cancel();
            }
        });
        alert.show();
    }
}
