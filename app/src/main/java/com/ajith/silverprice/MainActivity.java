package com.ajith.silverprice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements EditDialog.ExampleDialogListener {

    private static final String TAG = "ajju";
    EditText old_weight, new_weight, kooli;
    TextView price, pay, left, reduced, wage, total;
    private CardView cardView;

    float cost;
    int percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        old_weight = findViewById(R.id.old_wt);
        new_weight = findViewById(R.id.new_wt);
        kooli = findViewById(R.id.kooli);

        price = findViewById(R.id.price);
        pay = findViewById(R.id.pay);
        left = findViewById(R.id.left);
        reduced = findViewById(R.id.reduced);
        wage = findViewById(R.id.wage);
        total = findViewById(R.id.total);
        cardView = findViewById(R.id.card);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        float highScore = sharedPref.getFloat("price", 1);
        int percentage = sharedPref.getInt("percentage", 68);

        cost = highScore;
        percent = percentage;

        price.setText(String.valueOf(highScore));
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putFloat("price", cost);
        editor.putInt("percentage", percent);
        editor.apply();

        cardView.setVisibility(View.INVISIBLE);
    }

    public void calculate(View view) {

        float w_n, w_o;
        float k;

        if (!TextUtils.isEmpty(new_weight.getText())){
            w_n = Float.parseFloat(new_weight.getText().toString());

            if (!TextUtils.isEmpty(new_weight.getText())){
                w_o = Float.parseFloat(old_weight.getText().toString());

                if (!TextUtils.isEmpty(new_weight.getText())){
                    k = Float.parseFloat(kooli.getText().toString());

                    String t_f = findValue(w_n, k, w_o);
                    if (t_f == null){
                        Toast.makeText(this, "Please enter Silver rate", Toast.LENGTH_SHORT).show();
                    }else {
                        total.setText(t_f);
                    }
                }else {
                    kooli.setError("Please enter");
                }
            }else {
                old_weight.setError("Please enter");
            }
        }else {
            new_weight.setError("Please enter");
        }
    }

    String findValue(float new_wt, float wage1, float old){

        float price1 = Float.parseFloat(price.getText().toString());

        if (price1 == 0){
            return null;
        }else {
            cardView.setVisibility(View.VISIBLE);
            double a = 0.01 * percent * old;

            left.setText(String.format("%.2f", a));
            pay.setText(String.format("%.2f", (new_wt - a)));
            reduced.setText(String.format("%.2f", (old - a)));
            wage.setText(String.format("%.2f", wage1));
            double total = ((price1/10) * (new_wt - a)) + wage1;
            return String.format("%.2f", total);
        }
    }

    public void insert(View view) {
        openDialog();
    }

    public void openDialog() {
        EditDialog exampleDialog = new EditDialog(price.getText().toString(), percent);
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String username, String per) {

        if (username.equals("")){
            price.setText("0");
        }else {
            price.setText(username);

            Log.d(TAG, "applyTexts: "+username+" & "+per);

            percent = Integer.parseInt(per);
            cost = Float.parseFloat(username);
        }
    }
}
