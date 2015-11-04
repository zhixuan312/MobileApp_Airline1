package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReturnFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ReturnFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    TextView inBoundDate;
    TextView inBoundContent;
    String inDeTime,inArrTime;
    String inDeCity,inArrCity;
    String inBoundString;
    String [] testData = {"12/12/2015","2350","0525","03/04/2016","1320","1930","Singapore","Malaysia","Malaysia","Singapore"};

    public ReturnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_return, container, false);

        inBoundContent = (TextView)view.findViewById(R.id.detail_inboundContent);
        inBoundDate = (TextView)view.findViewById(R.id.detail_tv_inDate);
        inBoundDate.setText(testData[3]);
        inDeTime = testData [1];
        inArrTime = testData [2];
        inDeCity = testData [6];
        inArrCity = testData [7];

        inBoundContent.setText(inBoundString);
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
