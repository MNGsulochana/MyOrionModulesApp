package orion.signupmodule.fragments;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import orion.signupmodule.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPersonalDetails extends Fragment implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{



    EditText edit_name;
    EditText confrm_pswd;
    EditText ent_psw;
     EditText ent_mailid;
     Button con;
     CoordinatorLayout coord_layout;


    RadioButton female_btn;

     RadioButton male_btn;
    RadioGroup radioGroup;
    //  @BindView(R.id.dob)
    static TextView date_of_birth;

    ImageButton datepick_imagbtn;

    String mail_id;
    public static final int RequestPermissionCode = 1;

    Pattern pattern;
    Account[] account;
    private static int year;
    private static int month;
    private static int day;


    public FragmentPersonalDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_fragment__personal__details, container, false);

        edit_name=v.findViewById(R.id.edit_name);
        confrm_pswd=v.findViewById(R.id.ent_cnfmpswd);
        ent_psw=v.findViewById(R.id.ent_pswd);
        ent_mailid=v.findViewById(R.id.ent_mailid);
        con=v.findViewById(R.id.button_continue);
        female_btn=v.findViewById(R.id.gend_female);
        male_btn=v.findViewById(R.id.gend_male);

        radioGroup=v.findViewById(R.id.radio_group);
        date_of_birth=v.findViewById(R.id.dob);
        datepick_imagbtn=v.findViewById(R.id.datepick_imagebtn);
        male_btn=v.findViewById(R.id.gend_male);
        coord_layout=v.findViewById(R.id.coordinator_layout);

        radioGroup.setOnCheckedChangeListener(this);
        datepick_imagbtn.setOnClickListener(this);
        pattern= Patterns.EMAIL_ADDRESS;



        //TO GET EMAILID

        getmailid();


        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        edit_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ent_mailid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        return v;
    }

    private void getmailid() {

        try {
            account = AccountManager.get(getActivity()).getAccounts();
        }
        catch (SecurityException e) {
        }

        for (Account TempAccount : account) {

            if (pattern.matcher(TempAccount.name).matches()) {

                String namemail=TempAccount.name;

                ent_mailid.setText(namemail);
                String[] part=namemail.split("@");

                if(part.length>1)
                {

                    edit_name.setText(part[0]);
                }
            }

           /* if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
                String email = possibleEmails.get(0);
                String[] parts = email.split("@");

                if (parts.length > 1)
                    return parts[0];
            }*/
        }

    }


    public void clickOnContinuePay(View v)
    {
        String gendr=null;
        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find which radioButton is checked by id
        if (selectedId == female_btn.getId()) {

            gendr=female_btn.getText().toString();
        } else if (selectedId == male_btn.getId()) {
            gendr=male_btn.getText().toString();
        }

        String mailid=ent_mailid.getText().toString().trim();
        String name=edit_name.getText().toString().trim();
        String paswd=ent_psw.getText().toString().trim();
        String cnfrm_pswd=confrm_pswd.getText().toString().trim();

        Log.d("getnam",name);

        if(!name.equals("") && ! mailid.equals("") && !paswd.equals("") && !cnfrm_pswd.equals("") && ! gendr.equals(""))
        {
            if(paswd.equals(cnfrm_pswd)) {

                /*userSignupDetails.setU_mailid(mailid);
                userSignupDetails.setUname(name);
                userSignupDetails.setuPswd(paswd);*/


               /* FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                phoneNumberValidation = new PhoneNumberValidation();
                ft.replace(R.id.container, phoneNumberValidation);
                ft.commit();*/

               /* Intent in=new Intent(getActivity(), TodisplayRealmData.class);
                startActivity(in);*/
                Toast.makeText(getActivity(), "welcometo Frag", Toast.LENGTH_SHORT).show();
            }
            else {

                //here I need to add snack bar
                Snackbar snackbar=Snackbar.make(coord_layout,"pasword mismatch",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

        }
        else {

            Snackbar snackbar=Snackbar.make(coord_layout,"provide all the values",Snackbar.LENGTH_SHORT);
            snackbar.show();

        }



    }

    @Override
    public void onClick(View view) {

        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getFragmentManager(), "DatePicker");
        Log.d("selected12","smnbsdmn");


    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

        int check_id=radioGroup.getCheckedRadioButtonId();

        if(check_id == R.id.gend_female)
        {
            Toast.makeText(getActivity(),"female selectd",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(),"male selectd",Toast.LENGTH_SHORT).show();
        }

       /* switch (i)
        {
            case female_btn:

                Toast.makeText(getActivity(),"female selectd",Toast.LENGTH_SHORT).show();

                //  date_pick.setVisibility(View.VISIBLE);

                break;
            case R.id.gend_male:
                // date_pick.setVisibility(View.VISIBLE);
                break;
        }*/

    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // return super.onCreateDialog(savedInstanceState);
            return new DatePickerDialog(getActivity(),this,year,month,day);

        }

        @Override
        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {

            Log.d("selected","smnbsdmn");
            year = selectedYear;
            month = selectedMonth+1;
            day = selectedDay;
            String s=""+year+"-"+month+"-"+day;

            date_of_birth.setText(s);
            Log.d("selecteddate",s);

        }
    }


}
