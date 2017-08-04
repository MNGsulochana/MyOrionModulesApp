package orion.myorionapp.fragments;


import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import butterknife.OnClick;
import orion.myorionapp.R;
import orion.myorionapp.pojoclasses.UserSignupDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_personal_details extends Fragment implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{

    @BindView(R.id.edit_name) EditText edit_name;
    @BindView(R.id.ent_cnfmpswd) EditText confrm_pswd;
    @BindView(R.id.ent_pswd) EditText ent_psw;
    @BindView(R.id.ent_mailid) EditText ent_mailid;
    @BindView(R.id.button_continue) Button con;
    @BindView(R.id.coordinator_layout) CoordinatorLayout coord_layout;

    @BindView(R.id.gend_female) RadioButton female_btn;

    @BindView(R.id.gend_male) RadioButton male_btn;
    @BindView(R.id.radio_group) RadioGroup radioGroup;
  //  @BindView(R.id.dob)
    static TextView date_of_birth;


   @BindView(R.id.datepick_imagebtn) ImageButton datepick_imagbtn;

    UserSignupDetails userSignupDetails;

    String mail_id;
    public static final int RequestPermissionCode = 1;

    Pattern pattern;
    Account[] account;


    private static final String[] INITIAL_PERMS = {
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_PHONE_STATE
    };

    private static int year;
    private static int month;
    private static int day;

    PhoneNumberValidation phoneNumberValidation;



    public Fragment_personal_details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_fragment_personal_details, container, false);
       // con=v.findViewById(R.id.button_continue);
        userSignupDetails=new UserSignupDetails();

        date_of_birth=(TextView) v.findViewById(R.id.dob);

       // enableRuntimePermission();


        ButterKnife.bind(this,v);

//AFTER BINDING VIEW ONLY WE CAN APPLY LISTENERS TO THE VIEWS

        radioGroup.setOnCheckedChangeListener(this);
        datepick_imagbtn.setOnClickListener(this);
        pattern= Patterns.EMAIL_ADDRESS;



        //TO GET EMAILID

        getmailid();


        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

//THIS CODE IS NOT WORKING

        /*TelephonyManager tm= (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String f=tm.getLine1Number();
        Log.d("lenfg",""+f.length());
        if(f.equals("")||f==null)
        {
            Log.d("getphone","enteeeed");
            f=getNoFromWatsApp();
        }

        Log.d("getphone",f);
*/


        return v;
    }

    private void enableRuntimePermission() {

        ActivityCompat.requestPermissions(getActivity(), INITIAL_PERMS, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean GetAccountPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPhoneStatePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (GetAccountPermission && ReadPhoneStatePermission) {

                        Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity(),"Permission Denied",Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }

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
//TO GET PHONE NUMBER ITS NOT GETTING MOBILE NUMBER

    private String  getNoFromWatsApp() {
        Log.d("getphone1","enteeeed");
        AccountManager am = AccountManager.get(getActivity());
        Account[] accounts = am.getAccounts();
        String phoneNumber = "";

        for (Account ac : accounts) {
            Log.d("getphone2","enteeeed");
            String acname = ac.name;
            String actype = ac.type;
            // Take your time to look at all available accounts
            if (actype.equals("com.whatsapp")) {
                Log.d("getphone3","enteeeed");
                phoneNumber = ac.name;
                int x=ac.describeContents();
                Log.d("getphone",phoneNumber);
                Log.d("getphone23",""+x);

            }
        }
        return phoneNumber;
    }

    @OnClick(R.id.button_continue)
    public void clickOnContinue()
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

                userSignupDetails.setU_mailid(mailid);
                userSignupDetails.setUname(name);
                userSignupDetails.setuPswd(paswd);




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
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

        switch (i)
        {
            case R.id.gend_female:

                Toast.makeText(getActivity(),"female selectd",Toast.LENGTH_SHORT).show();

              //  date_pick.setVisibility(View.VISIBLE);

                break;
            case R.id.gend_male:
               // date_pick.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public void onClick(View view) {

        // set date picker as current date


        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getFragmentManager(), "DatePicker");
        Log.d("selected12","smnbsdmn");





    }


    @SuppressLint("ValidFragment")
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
