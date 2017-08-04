package orion.myorionapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import orion.myorionapp.R;
import orion.myorionapp.retrofit.DataClass;
import orion.myorionapp.retrofit.GetClientApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneNumberValidation extends Fragment {

    @BindView(R.id.ent_mobile)
    EditText mobile;
    @BindView(R.id.verify_mobile1)
    Button verify_mobile;
    @BindView(R.id.gen_otp1) Button gen_otp;
    @BindView(R.id.gen_otp_altrnate_mobile) Button otp_altrnate_mobile;
    @BindView(R.id.butn_con_phone) Button mobile_ctn_button;


    public PhoneNumberValidation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_phone_number_validation, container, false);

        ButterKnife.bind(getActivity(),v);
        //getdata();
        return v;
    }

    private void getdata() {

        GetClientApi clint_api=GetClientApi.retrofit.create(GetClientApi.class);

        Call<DataClass> call=clint_api.loadFollowers();

        call.enqueue(new Callback<DataClass>() {
            @Override
            public void onResponse(Call<DataClass> call, Response<DataClass> response) {
                DataClass dc=response.body();
               // String sc=dc.getAddress();
                String nam=dc.getName();
                Log.d("getame",nam);
            }

            @Override
            public void onFailure(Call<DataClass> call, Throwable t) {

            }
        });
    }

}
