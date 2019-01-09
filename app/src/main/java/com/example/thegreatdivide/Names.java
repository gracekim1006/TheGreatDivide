package com.example.thegreatdivide;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class Names extends AppCompatActivity {
    ArrayList<CharSequence> listItems = new ArrayList<>();
    ArrayAdapter<CharSequence> adapter;
    EditText txt;
    ListView show;
    EditText edittext;
    TextView displayTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_names);
        listItems = getIntent().getCharSequenceArrayListExtra("pickle");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        show = findViewById(R.id.listView_names_list);
        displayTotal = findViewById(R.id.textView_names_displayCount);
        displayTotal.setText("Total: " + listItems.size());
        show.setAdapter(adapter);

        show.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder delete = new AlertDialog.Builder(Names.this);
                delete.setMessage("Delete?");
                delete.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listItems.remove(position);
                        adapter.notifyDataSetChanged();
                        displayTotal.setText("Total: " + listItems.size());
                    }
                });
                delete.show();
                return true;

            }
        });
    }

    public void setNameinList(){
        String getInput = txt.getText().toString();

        if(listItems.contains(getInput)){
            Toast.makeText(getBaseContext(), "Name already exists. Please add a last name", Toast.LENGTH_LONG).show();
        }
        else if (getInput ==null || getInput.trim().equals("")){
            Toast.makeText(getBaseContext(), "Please add a name", Toast.LENGTH_LONG).show();
        }
        else {
            listItems.add(getInput);
            adapter.notifyDataSetChanged();
            displayTotal.setText("Total: " + listItems.size());

        }
    }


    public void showPopupWindow (View view){
        edittext = new EditText(Names.this);
        edittext.setHint(" Name");

        edittext.setInputType(InputType.TYPE_NULL);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Add New Name");
        alert.setView(edittext);
        alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                txt = edittext;
                setNameinList();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.cancel();
            }
        });
        alert.show();
    }
    public void DoneOnClick(View view){
        Intent intent = new Intent(Names.this, Collection.class);
        intent.putCharSequenceArrayListExtra("pickle", listItems);
        setResult(RESULT_OK, intent);
        finish();
    }



}
