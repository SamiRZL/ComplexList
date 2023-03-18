package com.example.complexlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    private ArrayList<String> listAchats = new ArrayList<>();
    private EditText addTextItem;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listAchats.add("10 kg de farine");
        listAchats.add("10 L d' huile");
        listAchats.add("4 kg de tomates");
        listAchats.add("10 Levures");
        listAchats.add("10 L d'eau");
        listAchats.add("1 extrait de vanille");
        listAchats.add("100 g de poivre noir");
        listAchats.add("200 g d'olives noir");

        addTextItem = findViewById(R.id.editTextItem);
        addButton = findViewById(R.id.addButton);
        ListView listAchatsView = findViewById(R.id.listAchatView);

        MyAdapter adapter = new MyAdapter(this, listAchats);
        listAchatsView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = addTextItem.getText().toString();
                if (!TextUtils.isEmpty(item)) {
                    listAchats.add(item);
                    addTextItem.setText("");
                    Toast.makeText(MainActivity.this, "Item added to shopping list", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter an item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public class MyAdapter extends ArrayAdapter<String> implements View.OnClickListener {

        private Context mContext;
        private ArrayList<String> mListAchats;

        public MyAdapter(Context context, ArrayList<String> listAchats) {
            super(context, R.layout.activity_ma_ligne, listAchats);
            mContext = context;
            mListAchats = listAchats;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(R.layout.activity_ma_ligne, parent, false);
            }

            // Get the item at the current position
            String item = getItem(position);

            // Find the views in the layout
            TextView textView = view.findViewById(R.id.textView1);
            ImageButton editButton = view.findViewById(R.id.editButton);
            ImageButton deleteButton = view.findViewById(R.id.deleteButton);

            // Set the values for the views
            textView.setText(item);

            // Set click listeners for the buttons
            editButton.setTag(position);
            editButton.setOnClickListener(this);

            deleteButton.setTag(position);
            deleteButton.setOnClickListener(this);

            return view;
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            switch (v.getId()) {
                case R.id.editButton:
                    // Handle Edit button click
                    // Get the item at the current position
                    String item = getItem(position);
                    // Create a dialog to edit the item
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Edit Item");

                    // Set up the input
                    final EditText input = new EditText(mContext);
                    input.setText(item);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String newItem = input.getText().toString().trim();
                            if (!TextUtils.isEmpty(newItem)) {
                                // Update the item in the list
                                mListAchats.set(position, newItem);
                                // Notify the adapter that the data set has changed
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "Item updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Please enter a valid item", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                    break;
                case R.id.deleteButton:
                    // Handle Delete button click
                    mListAchats.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Item deleted", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }}


