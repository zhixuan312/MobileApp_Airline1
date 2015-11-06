package zhang.zhixuan.mobileapp_airline;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {

    TicketDB db;
    String email;
    ListView lv;
    ListView lv1;
    private int oldPostion = -1;
    ArrayList<String[]>  bookingRecordsUnCheckedIn;
    ArrayList<String[]>  bookingRecordsCheckedIn;
    List<BookingRecordEntity> bookingRUnCheckedIn;
    List<BookingRecordEntity> bookingRCheckedIn;
    Profile facebookProfile;
    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());


        email = getActivity().getIntent().getStringExtra("email");
        db = new TicketDB(getActivity().getApplicationContext());
        bookingRecordsUnCheckedIn = getUncheckedInRecord(email);
        bookingRecordsCheckedIn = getCheckedInRecord(email);

       bookingRUnCheckedIn = new ArrayList<>();
        for(String[] br: bookingRecordsUnCheckedIn){
            BookingRecordEntity brE = new BookingRecordEntity();
            brE.expand = false;
            brE.referenceN = br[1];
            brE.passport = br[2];
            brE.origin = br[5];
            brE.destination = br[6];
            brE.depD = br[7];
            brE.ariD = br[8];
            bookingRUnCheckedIn.add(brE);
        }
        bookingRCheckedIn = new ArrayList<>();
        for(String[] br: bookingRecordsCheckedIn){
            BookingRecordEntity brE = new BookingRecordEntity();
            brE.expand = false;
            brE.referenceN = br[1];
            brE.passport = br[2];
            brE.origin = br[5];
            brE.destination = br[6];
            brE.depD = br[7];
            brE.ariD = br[8];
            bookingRCheckedIn.add(brE);
        }


    }

    public ArrayList<String[]> getUncheckedInRecord(String email) {

        db.open();

        Cursor c = db.getMemberByEmailUnCheckedIn(email);
        System.out.println("after getmEMBERBYEMAIL");

        if(c== null){
            return new ArrayList<String[]>();
        }

        ArrayList<String[]> records = new ArrayList<String[]>();

        if (c.moveToFirst()) {
            do {
                String[] record = {c.getString(0), c.getString(1), c.getString(2),c.getString(3), c.getString(4), c.getString(5),c.getString(6), c.getString(7), c.getString(8)};
                records.add(record);
            } while (c.moveToNext());
        }

        db.close();

        return records;
    }

    public ArrayList<String[]> getCheckedInRecord(String email) {

        db.open();

        Cursor c = db.getMemberByEmailUnCheckedIn(email);

        if(c== null){
            return new ArrayList<String[]>();
        }
        ArrayList<String[]> records = new ArrayList<String[]>();

        if (c.moveToFirst()) {
            do {
                String[] record = {c.getString(0), c.getString(1), c.getString(2),c.getString(3), c.getString(4), c.getString(5),c.getString(6), c.getString(7), c.getString(8)};
                records.add(record);
            } while (c.moveToNext());
        }

        db.close();

        return records;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_booking, container, false);

        lv = (ListView) view.findViewById(R.id.fb_lv_bookingCI);
        lv1 = (ListView) view.findViewById(R.id.fb_lv_bookingHistory);

        final MyAdapter adapter = new MyAdapter(getActivity().getApplicationContext());

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookingRecordEntity data= bookingRUnCheckedIn.get(position);
                if (oldPostion == position) {
                    if (data.expand) {
                        oldPostion = -1;
                    }
                    data.expand = !data.expand;
                } else {
                    oldPostion = position;
                    data.expand = true;
                }

                int totalHeight = 0;
                for (int i = 0; i < adapter.getCount(); i++) {
                    View viewItem = adapter.getView(i, null, lv);//这个很重要，那个展开的item的measureHeight比其他的大
                    viewItem.measure(0, 0);
                    totalHeight += viewItem.getMeasuredHeight();
                }

                ViewGroup.LayoutParams params = lv.getLayoutParams();
                params.height = totalHeight
                        + (lv.getDividerHeight() * (lv.getCount() - 1));
                lv.setLayoutParams(params);
                adapter.notifyDataSetChanged();
            }
        });
        final MyAdapter1 adapter1 = new MyAdapter1(getActivity().getApplicationContext());

        lv1.setAdapter(adapter1);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookingRecordEntity data= bookingRCheckedIn.get(position);
                if (oldPostion == position) {
                    if (data.expand) {
                        oldPostion = -1;
                    }
                    data.expand = !data.expand;
                } else {
                    oldPostion = position;
                    data.expand = true;
                }

                int totalHeight = 0;
                for (int i = 0; i < adapter1.getCount(); i++) {
                    View viewItem = adapter1.getView(i, null, lv1);//这个很重要，那个展开的item的measureHeight比其他的大
                    viewItem.measure(0, 0);
                    totalHeight += viewItem.getMeasuredHeight();
                }

                ViewGroup.LayoutParams params = lv1.getLayoutParams();
                params.height = totalHeight
                        + (lv1.getDividerHeight() * (lv1.getCount() - 1));
                lv1.setLayoutParams(params);
                adapter1.notifyDataSetChanged();
            }
        });

        return view;
    }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            System.out.println("getCount");
            return bookingRUnCheckedIn.size();
        }

        //        @Override
//        public Object getItem(int arg0) {
//            // TODO Auto-generated method stub
//            return null;
//        }
//
//        @Override
//        public long getItemId(int arg0) {
//            // TODO Auto-generated method stub
//            return 0;
//        }
        @Override
        public Object getItem(int position) {
            return bookingRUnCheckedIn.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        public final class ViewHolder {

            public TextView referenceN;
            public TextView itinerary;
            public TextView dep;
            public TextView ari;
            public TextView passport;
            public Button checkInBtn;
            public LinearLayout hiddenInfo;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("enter getView" + position);
            ViewHolder holder = new ViewHolder();

            final BookingRecordEntity bookingRecordEntity = bookingRUnCheckedIn.get(position);
            if (convertView == null) {


                convertView = mInflater.inflate(R.layout.bookingrecords, null);

                holder.referenceN = (TextView) convertView.findViewById(R.id.br_tv_rn);
                holder.itinerary = (TextView) convertView.findViewById(R.id.br_tv_iti);
                holder.dep = (TextView) convertView.findViewById(R.id.br_tv_dep);
                holder.checkInBtn = (Button) convertView.findViewById(R.id.br_btn_checkin);
                holder.hiddenInfo = (LinearLayout) convertView.findViewById(R.id.br_ll_gone);
                holder.passport = (TextView) convertView.findViewById(R.id.br_tv_passport);
                holder.ari = (TextView) convertView.findViewById(R.id.br_tv_ari);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }
            if (bookingRecordEntity.expand) {
                holder.hiddenInfo.setVisibility(View.VISIBLE);
            } else {
                holder.hiddenInfo.setVisibility(View.GONE);
            }

            holder.referenceN.setText("Reference Number: "+bookingRecordEntity.referenceN);
            holder.itinerary.setText(bookingRecordEntity.origin+" to "+bookingRecordEntity.destination);
            holder.dep.setText("Departs: " + bookingRecordEntity.depD);
            holder.ari.setText("Arrives: " + bookingRecordEntity.ariD);
            holder.passport.setText("Passport Information: " + bookingRecordEntity.passport);

            holder.checkInBtn.setTag(position + "");
            holder.checkInBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                   Intent intent = new Intent(getActivity().getApplicationContext(), WebCheckIn.class);
                    intent.putExtra("referenceN",bookingRUnCheckedIn.get(Integer.parseInt(v.getTag().toString())).referenceN);
                    intent.putExtra("passportN",bookingRUnCheckedIn.get(Integer.parseInt(v.getTag().toString())).passport);
//                    System.out.println("button"+flightNoChosen+"depdCHOSEN"+depDChosen);
//                    System.out.println("getText1"+flightNoChosen.getText());
//                    System.out.println("getText2"+depDChosen.getText());
                    startActivity(intent);

                }
            });


            return convertView;
        }
    }

    public class MyAdapter1 extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapter1(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            System.out.println("getCount"+bookingRCheckedIn.size());
            return bookingRCheckedIn.size();
        }

        //        @Override
//        public Object getItem(int arg0) {
//            // TODO Auto-generated method stub
//            return null;
//        }
//
//        @Override
//        public long getItemId(int arg0) {
//            // TODO Auto-generated method stub
//            return 0;
//        }
        @Override
        public Object getItem(int position) {
            return bookingRCheckedIn.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        public final class ViewHolder {

            public TextView referenceN;
            public TextView itinerary;
            public TextView dep;
            public TextView ari;
            public TextView passport;

            public LinearLayout hiddenInfo;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("enter getView" + position);
            ViewHolder holder = new ViewHolder();

            final BookingRecordEntity bookingRecordEntity = bookingRCheckedIn.get(position);
            if (convertView == null) {


                convertView = mInflater.inflate(R.layout.bookingrecordscheckedin, null);

                holder.referenceN = (TextView) convertView.findViewById(R.id.br_tv_rnC);
                holder.itinerary = (TextView) convertView.findViewById(R.id.br_tv_itiC);
                holder.dep = (TextView) convertView.findViewById(R.id.br_tv_depC);
                holder.hiddenInfo = (LinearLayout) convertView.findViewById(R.id.br_ll_goneC);
                holder.passport = (TextView) convertView.findViewById(R.id.br_tv_passportC);
                holder.ari = (TextView) convertView.findViewById(R.id.br_tv_ariC);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }
            if (bookingRecordEntity.expand) {
                holder.hiddenInfo.setVisibility(View.VISIBLE);
            } else {
                holder.hiddenInfo.setVisibility(View.GONE);
            }

            holder.referenceN.setText("Reference Number: "+bookingRecordEntity.referenceN);
            holder.itinerary.setText(bookingRecordEntity.origin+" to "+bookingRecordEntity.destination);
            holder.dep.setText("Departs: " + bookingRecordEntity.depD);
            holder.ari.setText("Arrives: " + bookingRecordEntity.ariD);
            holder.passport.setText("Passport Information: " + bookingRecordEntity.passport);




            return convertView;
        }
    }
    public void main_float_account (View view) {
        facebookProfile = Profile.getCurrentProfile();
        if (facebookProfile != null) {
            Intent intent = new Intent(getActivity(), FacebookAccountPage.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), LoginPage.class);
            startActivity(intent);
        }
    }

    public void main_float_checkIn (View view) {
        Intent intent = new Intent(getActivity(),WebCheckInHomePage.class);
        startActivity(intent);
    }

    public void main_float_search (View view) {
        Intent intent = new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
    }

}

