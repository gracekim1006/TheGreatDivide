package com.example.thegreatdivide;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import android.app.AlertDialog;


public class Groups extends AppCompatActivity {
    ArrayList<CharSequence> listSizes = new ArrayList<>();
    ArrayList<CharSequence> listItems = new ArrayList<>();
    ArrayAdapter<CharSequence> adapter;
    EditText txt;
    EditText txt2;
    ListView show;
    TextView displayTotal;

    String addS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_groups);
        listItems = getIntent().getCharSequenceArrayListExtra("pickles");
        listSizes = getIntent().getCharSequenceArrayListExtra("picklessizes");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        show = findViewById(R.id.listView_groups_list);
        displayTotal = findViewById(R.id.textView_groups_displayCount);
        displayTotal.setText("Total: " + listItems.size());
        show.setAdapter(adapter);

        show.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder delete = new AlertDialog.Builder(Groups.this);
                delete.setMessage("Delete?");
                delete.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listItems.remove(position);
                        listSizes.remove(position);
                        adapter.notifyDataSetChanged();
                        displayTotal.setText("Total: " + listItems.size());
                    }
                });
                delete.show();
                return true;
            }
        });
    }

    public void setGroupinList(){
        String getInput = txt.getText().toString();
        String getInput2 = txt2.getText().toString();

        if(listItems.contains(getInput)){
            Toast.makeText(getBaseContext(), "Group name already exists. Please add a last group name", Toast.LENGTH_LONG).show();
        }
        else if (getInput ==null || getInput.trim().equals("")){
            Toast.makeText(getBaseContext(), "Please add a group name", Toast.LENGTH_LONG).show();
        }
        else {
            if (Integer.valueOf(getInput2) == 1)
                addS = " )";

            else
                addS = "s )";

            listItems.add(getInput.toUpperCase() + "  ( " + getInput2 + " member" + addS);
            listSizes.add(getInput2);
            adapter.notifyDataSetChanged();
            displayTotal.setText("Total: " + listItems.size());
        }
    }


    public void showPopupWindow (View view){
        final EditText editText1 = new EditText(Groups.this);
        editText1.setHint(" Group Name");
        final EditText editText2 = new EditText(Groups.this);
        editText2.setHint(" Number of Group Members");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        editText1.setInputType(InputType.TYPE_NULL);
        editText2.setInputType(InputType.TYPE_NULL);

        //editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        alert.setTitle("Add New Group");
        alert.setView(editText1);
        alert.setView(editText2);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(editText1);
        ll.addView(editText2);
        alert.setView(ll);

        alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                txt = editText1;
                txt2 = editText2;
                setGroupinList();
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
        Intent intent = new Intent(Groups.this, Collection.class);
        intent.putCharSequenceArrayListExtra("pickles", listItems);
        intent.putCharSequenceArrayListExtra("picklessizes", listSizes);
        setResult(RESULT_OK, intent);
        finish();
    }

}

