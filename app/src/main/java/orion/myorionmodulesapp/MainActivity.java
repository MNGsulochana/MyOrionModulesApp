package orion.myorionmodulesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import orion.signupmodule.SignUpActivity;

public class MainActivity extends AppCompatActivity {

    TextView chg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chg= (TextView) findViewById(R.id.clig);

        chg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(in);
            }
        });
    }
}
