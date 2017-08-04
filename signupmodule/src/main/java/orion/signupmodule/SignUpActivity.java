package orion.signupmodule;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import orion.signupmodule.fragments.FragmentLocationDetails;

public class SignUpActivity extends AppCompatActivity {

    FragmentLocationDetails fragment_location_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FragmentManager fm=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft=fm.beginTransaction();
        fragment_location_details=new FragmentLocationDetails();
        ft.replace(R.id.container,fragment_location_details);
        ft.commit();

    }
}
