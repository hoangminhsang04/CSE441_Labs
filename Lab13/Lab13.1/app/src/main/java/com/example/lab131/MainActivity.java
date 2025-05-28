package com.example.lab131;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView selection;

    AutoCompleteTextView singleComplete;
    MultiAutoCompleteTextView multiComplete;

    String arr[] = {"Hà nội", "Huế", "Sài gòn",
            "Hà giang", "Hội an", "Kiên giang",
            "Lâm đồng", "Long khánh"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selection = findViewById(R.id.selection);

        singleComplete = findViewById(R.id.editauto);
        ArrayAdapter<String> myadapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                arr
        );
        singleComplete.setAdapter(myadapter);

        multiComplete = findViewById(R.id.multiAutoCompleteTextView);
        multiComplete.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                arr
        ));

        multiComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        singleComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int il, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int il, int i2) {
                selection.setText(singleComplete.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
