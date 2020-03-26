package com.ajith.silverprice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements EditDialog.ExampleDialogListener {

    private static final String TAG = "ajju";
    EditText old_weight, new_weight, kooli;
    TextView price, pay, left, reduced, wage, total;
    private CardView cardView;

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

    }

    public void calculate(View view) {

        float w_n=0, w_o=0;
        float k=0;

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

        //p.hide();
    }

    String findValue(float new_wt, float wage1, float old){

        double price1 = Integer.parseInt(price.getText().toString());

        if (price1 == 0){
            return null;
        }else {

            Toast.makeText(this, String.valueOf((new_wt - (0.68 * old))), Toast.LENGTH_SHORT).show();

            cardView.setVisibility(View.VISIBLE);
            double a = 0.68 * old;
            left.setText(String.valueOf(a));
            pay.setText(String.valueOf(new_wt - a));
            reduced.setText(String.valueOf(old - a));
            wage.setText(String.valueOf(wage1));
            double total = ((price1/10) * (new_wt - a)) + wage1;
            return String.valueOf(total);
        }
    }

    public void insert(View view) {
        openDialog();

    }

    public void openDialog() {
        EditDialog exampleDialog = new EditDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String username) {
        if (username.equals("")){
            price.setText("0");
        }else {
            price.setText(username);
        }
    }
}
