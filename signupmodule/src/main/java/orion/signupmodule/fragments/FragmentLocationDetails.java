package orion.signupmodule.fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import orion.signupmodule.R;
import orion.signupmodule.toget_location.GetCoordinates;
import orion.signupmodule.toget_location.LocationAddress;
import orion.signupmodule.toget_location.LocationResult;
import orion.signupmodule.toget_location.MyLocation;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLocationDetails extends Fragment implements LocationResult ,View.OnClickListener{

    EditText ent_city, ent_country, ent_address;
    EditText ent_pincode, ent_state;
    Button contin_loc;
    CoordinatorLayout location_cordnate_layout;

    FragmentPersonalDetails fragmentPersonal_Details;

    private MyLocation myLocation;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int INITIAL_REQUEST = 13;
    private static final int INITIAL_REQUEST1 = 14;

    double latitude, longitude;

    String pinc;
    boolean check_to = true;


    public FragmentLocationDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_fragment__location__details, container, false);

        ent_country = v.findViewById(R.id.entr_country);
        ent_city = v.findViewById(R.id.entr_city);
        ent_address = v.findViewById(R.id.entr_address);
        ent_pincode = v.findViewById(R.id.entr_pincode);
        ent_state = v.findViewById(R.id.entr_state);
        contin_loc=v.findViewById(R.id.ent_btn_loctn);
        location_cordnate_layout=v.findViewById(R.id.loctn_coord_layout);




        //  ButterKnife.bind(getActivity(), v);

        myLocation = new MyLocation();

        //BASED ON PHONE LOCATION WE WILL GET  ADDRESS

        if (!canAccessLocation() || !canAccessCoreLocation()) {
            //requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
            Log.d("gettinglocation", "shvdsh");
            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);

        } else {
            Log.d("gettingnw", "shvdsh");
            boolean networkPresent = myLocation.getLocation(getActivity(), this);
            if (!networkPresent) {
                showSettingsAlert();
            }
        }

        ent_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("changedon", "" + charSequence);


            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("welcome", "hjdsj");
                if (ent_pincode.getTag() == null) {
                    Log.d("welcome1", "hjdsj");
                    check_to = false;

                    int phg=ent_pincode.getText().toString().trim().length();

                    if(phg == 6)
                    {
                        Log.d("changedon1", "" + phg);
                        pinc=ent_pincode.getText().toString().trim();
                        Pattern p=Pattern.compile("^([1-9])([0-9]){5}$");

                        // Pattern p=Pattern.compile("^([0-9]{6})$");
                        Matcher m=p.matcher(pinc);

                        if(m.matches())
                        {
                            Log.d("changedon1valid", "valid" );


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    new GetCoordinates(getActivity(), ent_address, ent_state, ent_pincode, ent_city, ent_country,new Geocodehand() ).execute("" + pinc);

                                    // requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
                                }
                            });
                        }
                        else
                        {
                            Log.d("changedon1invalid", "invalid" );
                            Toast.makeText(getActivity(),"invalid Pincode",Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });

        contin_loc.setOnClickListener(this);
        return v;
    }

    private void showSettingsAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getActivity());
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getActivity().startActivity(intent);
                        requestPermissions(INITIAL_PERMS,INITIAL_REQUEST);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private boolean canAccessLocation() {



        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean canAccessCoreLocation() {

        return (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    private boolean hasPermission(String perm) {

        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getActivity(), perm));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case INITIAL_REQUEST:
                if (grantResults.length > 0) {

                    boolean GetAccountPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    Log.d("gramtacnt",""+GetAccountPermission);
                    boolean ReadPhoneStatePermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                    if (canAccessLocation() && canAccessCoreLocation() && GetAccountPermission && ReadPhoneStatePermission) {

                        boolean networkPresent = myLocation.getLocation(getActivity(), this);
                        if (!networkPresent) {
                            showSettingsAlert();
                        }

                        Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                }
                /*if (canAccessLocation() && canAccessCoreLocation() ) {
                    boolean networkPresent = myLocation.getLocation(getActivity(), this);
                    if (!networkPresent) {
                        showSettingsAlert();
                    }
                }*/
                break;
            /*case INITIAL_REQUEST1: {
                if (canAccessLocation() && canAccessCoreLocation()) {

                }
            }

            break;*/


        }

    }

    @Override
    public void onClick(View view) {

        String entpin=ent_pincode.getText().toString().trim();

        String entcity=ent_city.getText().toString().trim();

        String entaddres=ent_address.getText().toString().trim();

        String entstate=ent_state.getText().toString().trim();

        String entcuntry=ent_country.getText().toString().trim();

        if(entaddres.equals("")||entcity.equals("")||entpin.equals("")||entstate.equals("")||entcuntry.equals(""))
        {
            Snackbar snackbar=Snackbar.make(location_cordnate_layout,"please give all the values",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else {


//REALM PART ITS WORKING FINE
              /*  ++id;
                setRealmData(entcity,entstate,id);

            // refresh the realm instance
            RealmController.with(this).refresh();*/



            FragmentManager fm = getFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
            fragmentPersonal_Details = new FragmentPersonalDetails();
            ft.replace(R.id.container, fragmentPersonal_Details);
            ft.commit();
        }

    }

    @Override
    public void getLocation(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        final String result = "Latitude: " + location.getLatitude() +
                " Longitude: " + location.getLongitude();
        Log.d("whole_result", result);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //  show_text.setText(result);

                Log.d("thrd_lat", "" + latitude);
                Log.d("thrd_lng", "" + longitude);
                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(latitude, longitude,
                        getContext(), new GeocoderHandler());
            }
        });

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress, city = null, state = null, postalcode = null, country = null, mainaddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();


                    mainaddress = bundle.getString("mainaddress");
                    city = bundle.getString("city");
                    country = bundle.getString("country");
                    postalcode = bundle.getString("postalcode");

                    state = bundle.getString("state");


                    break;

                default:
                    mainaddress = null;
            }


            ent_address.setText(mainaddress);
            ent_city.setText(city);
            ent_country.setText(country);
            ent_pincode.setTag("my value");
            ent_pincode.setText(postalcode);
            ent_state.setText(state);
            ent_pincode.setTag(null);

        }
    }

    private class Geocodehand extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress, city = null, state = null, postalcode = null, country = null, mainaddress;
            switch (message.what) {
                case 0:
                    Bundle bundle = message.getData();


                    mainaddress = bundle.getString("mainaddress1");
                    city = bundle.getString("city1");
                    country = bundle.getString("country1");

                    state = bundle.getString("state1");

                    Log.d("getadre", mainaddress);
                    break;

                default:
                    mainaddress = null;
            }
            ent_address.setText(mainaddress);
            ent_city.setText(city);
            ent_country.setText(country);

            //   ent_pincode.setText(postalcode);
            ent_state.setText(state);
        }


    }
}
