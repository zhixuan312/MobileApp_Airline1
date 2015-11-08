package zhang.zhixuan.mobileapp_airline;


import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView fnTV;
    TextView snTV;
    TextView adTV;
    TextView ciTV;
    TextView coTV;
    TextView zcTV;
    TextView cnTV;
TextView pf_tv_username;
    String firstN;
    String lastN;
    String address;
    String title;
    String city;
    String country;
    String zipCode;
    String contactN;
    String email;
    String username;

    Spinner spinner;
    ButtonRectangle update;
    MyDB db;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        fnTV = (TextView)view.findViewById(R.id.pf_et_fn);
        snTV = (TextView)view.findViewById(R.id.pf_et_sn);
        adTV = (TextView)view.findViewById(R.id.pf_et_ad);
        ciTV = (TextView)view.findViewById(R.id.pf_et_ci);
        coTV = (TextView)view.findViewById(R.id.pf_et_co);
        zcTV = (TextView)view.findViewById(R.id.pf_et_zp);
        cnTV = (TextView)view.findViewById(R.id.pf_et_cn);
        pf_tv_username = (TextView)view.findViewById(R.id.pf_tv_username);
        update = (ButtonRectangle)view.findViewById(R.id.pf_btn_update);
        email = getActivity().getIntent().getStringExtra("email");
        db = new MyDB(getActivity().getApplicationContext());
        getMemberRecord();

        fnTV.setText(firstN, TextView.BufferType.EDITABLE);
        snTV.setText(lastN, TextView.BufferType.EDITABLE);
        adTV.setText(address, TextView.BufferType.EDITABLE);
        ciTV.setText(city, TextView.BufferType.EDITABLE);
        coTV.setText(country, TextView.BufferType.EDITABLE);
        zcTV.setText(zipCode, TextView.BufferType.EDITABLE);
        cnTV.setText(contactN, TextView.BufferType.EDITABLE);
        pf_tv_username.setText("Welcome "+username);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("点击到了!");
                firstN = fnTV.getText().toString();
                lastN = snTV.getText().toString();
                address = adTV.getText().toString();
                city = ciTV.getText().toString();
                country = coTV.getText().toString();
                zipCode = zcTV.getText().toString();
                contactN = cnTV.getText().toString();
                updateRecord();
                fnTV.setText(firstN, TextView.BufferType.EDITABLE);
                snTV.setText(lastN, TextView.BufferType.EDITABLE);
                adTV.setText(address, TextView.BufferType.EDITABLE);
                ciTV.setText(city, TextView.BufferType.EDITABLE);
                coTV.setText(country, TextView.BufferType.EDITABLE);
                zcTV.setText(zipCode, TextView.BufferType.EDITABLE);
                cnTV.setText(contactN, TextView.BufferType.EDITABLE);
            }
        });


        return view;
    }

    public void update(){

    }
    public void getMemberRecord(){
        db.open();
        Cursor c = db.getMemberByEmail(email);

        if (c.moveToFirst()) {
            firstN = c.getString(1);
            lastN = c.getString(2);
            title = c.getString(4);
            address = c.getString(5);
            country = c.getString(6);
            city = c.getString(7);
            zipCode = c.getString(8);
            contactN = c.getString(9);
            username = c.getString(10);



        } else
            Toast.makeText(getActivity().getApplicationContext(), "No contact found", Toast.LENGTH_LONG).show();

        db.close();
    }
    public void updateRecord(){
        db.open();
        System.out.println("在这里!!");
        long id = db.updateMember(email, firstN, lastN, title, address, country, city, zipCode, contactN);

        if(id > 0){
            Toast.makeText(getActivity(), "Update successful.", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getActivity(),"Upddte fail.", Toast.LENGTH_LONG).show();


        db.close();
    }
}
