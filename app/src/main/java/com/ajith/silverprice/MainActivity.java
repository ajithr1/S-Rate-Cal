package com.ajith.silverprice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements EditDialog.ExampleDialogListener {

    private static final String TAG = "ajju";
    EditText old_weight, new_weight, kooli;
    TextView price, new_w, old_w, kooli_value, total;
    private CardView cardView;

    ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        old_weight = findViewById(R.id.old_wt);
        new_weight = findViewById(R.id.new_wt);
        kooli = findViewById(R.id.kooli);

        price = findViewById(R.id.price);
        new_w = findViewById(R.id.weight_new);
        old_w = findViewById(R.id.old_w);
        kooli_value = findViewById(R.id.wage);
        total = findViewById(R.id.total);
        cardView = findViewById(R.id.card);

        p = new ProgressDialog(this);
    }

    public void calculate(View view) {

        //p.show();

        double w_n=0, w_o=0;
        double k=0;

        if (!TextUtils.isEmpty(new_weight.getText())){
            w_n = Double.parseDouble(new_weight.getText().toString());

            if (!TextUtils.isEmpty(new_weight.getText())){
                w_o = Double.parseDouble(old_weight.getText().toString());

                if (!TextUtils.isEmpty(new_weight.getText())){
                    k = Double.parseDouble(kooli.getText().toString());

                    String t_f = findValue(w_n, k, w_o);
                    if (t_f == null){
                        Toast.makeText(this, "Please enter Silver rate", Toast.LENGTH_SHORT).show();
                    }else {
                        new_w.setText(new_weight.getText().toString());
                        old_w.setText(old_weight.getText().toString());
                        kooli_value.setText(kooli.getText().toString());
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

    String findValue(double new_wt, double wage, double old){

        Log.d(TAG, "findValue: ");

        double price1 = Integer.parseInt(price.getText().toString());

        if (price1 == 0){
            return null;
        }else {
            cardView.setVisibility(View.VISIBLE);
            double total = ((price1/10) * (new_wt - (68%old))) + wage;
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
        price.setText(username);
    }
}
