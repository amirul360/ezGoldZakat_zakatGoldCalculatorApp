package com.example.myapplicationcalc;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText inputValue;//weight of gold
    EditText inputDis;//gold value
    Button calculate;
    TextView resultBox;
    TextView textWeightVal;
    TextView textGoldValue;
    TextView textZakatPayble;
    Button btnreset;

    RadioButton radioButtonwear;
    RadioButton radioButtonkeep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply insets to the root 'main' layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.AboutActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputDis = findViewById(R.id.inputDis);
        inputValue = findViewById(R.id.inputValue);
        calculate = findViewById(R.id.calculate);
        resultBox = findViewById(R.id.resultBox);
        btnreset = findViewById(R.id.btnreset);
        textWeightVal = findViewById(R.id.textWeightVal);
        textGoldValue = findViewById(R.id.textGoldValue);
        textZakatPayble = findViewById(R.id.textZakatPayble);
        radioButtonkeep=findViewById(R.id.radioButtonkeep);
        radioButtonwear=findViewById(R.id.radioButtonwear);

        // The Toolbar defined in the layout has the id "my_toolbar".
//        Toolbar my_toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(findViewById(R.id.my_toolbar));


        resultBox.setText("0.00");
        textWeightVal.setText("0.00");
        textGoldValue.setText("0.00");
        textZakatPayble.setText("0.00");


//        inputValue;//weight of gold
//         inputDis;//gold value


        calculate.setOnClickListener(view -> {
            try {
                String disStr = inputDis.getText().toString();
                String valStr = inputValue.getText().toString();
                double calculatedValue=Double.parseDouble("0");
                double textWeight=Double.parseDouble("0");
                double zakatPayble=Double.parseDouble("0");
                String type= "";
                if (disStr.isEmpty() || valStr.isEmpty()) {
                    Toast.makeText(this, "Please enter all values", Toast.LENGTH_SHORT).show();
                    return;
                }

                double dis = Double.parseDouble(disStr);
                double val = Double.parseDouble(valStr);



                if (radioButtonkeep.isChecked()) {

                    type="keep";
                } else if (radioButtonwear.isChecked()) {

                    type="wear";
                } else {

                    Toast.makeText(MainActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (val <= 0) {
                    Toast.makeText(this, "weight must be greater than 0", Toast.LENGTH_SHORT).show();
                }

                if (dis <= 0) {
                    Toast.makeText(this, "value must be greater than 0", Toast.LENGTH_SHORT).show();
                }else {
                    if(type.equals("keep"))
                    {   textWeight=(val-85);
                        zakatPayble=((val-85)*dis);
                        calculatedValue=(zakatPayble)*0.025;
                    }
                    else if(type.equals("wear"))
                    {   textWeight=(val-200);
                        zakatPayble=((val-200)*dis);
                        calculatedValue=(zakatPayble)*0.025;
                    }

                    if(calculatedValue<0 && zakatPayble<0)
                    {
                        calculatedValue=0;
                        zakatPayble=0;
                    }

                    textWeightVal.setText(String.format("%.2f", textWeight));
                    textGoldValue.setText(String.format("%.2f", (val*dis)));
                    textZakatPayble.setText(String.format("%.2f", zakatPayble));
                    resultBox.setText(String.format("%.2f", calculatedValue));
                }

            } catch (NumberFormatException nfe) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            }
        });

        btnreset.setOnClickListener(view -> {

            inputDis.setText("");
            inputValue.setText("");


            resultBox.setText("0.00");
            textWeightVal.setText("0.00");
            textGoldValue.setText("0.00");
            textZakatPayble.setText("0.00");


            radioButtonkeep.setChecked(false);
            radioButtonwear.setChecked(false);



            Toast.makeText(MainActivity.this, "Calculator Reset", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItem = item.getItemId();

         if (selectedItem == R.id.menuSettings) {
            Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (selectedItem == R.id.menuShare) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this app! - https://github.com/amirul360/ezGoldZakat.git");
            startActivity(Intent.createChooser(shareIntent, "Share via"));

            Toast.makeText(this, "Share selected", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(item.getItemId()==R.id.menuAbout)
        { Toast.makeText(this, "About selected", Toast.LENGTH_SHORT).show();
            Intent aboutIntent=new Intent(this,AboutActivity.class);
            startActivity(aboutIntent);

        }
        
        return super.onOptionsItemSelected(item);
    }

}

