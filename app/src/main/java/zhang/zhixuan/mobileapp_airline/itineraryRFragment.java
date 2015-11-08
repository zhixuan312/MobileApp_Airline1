package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link itineraryRFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class itineraryRFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public itineraryRFragment() {
        // Required empty public constructor
    }
    private FlightEntity chosenFlight;
    private FlightEntity chosenFlightR;
    public static final String ARGUMENT = "chosenFlight";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chosenFlight = (FlightEntity)getActivity().getIntent().getSerializableExtra(ARGUMENT);
        chosenFlightR = (FlightEntity)getActivity().getIntent().getSerializableExtra("chosenFlightR");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_itinerary_r, container, false);
        TextView totalP = (TextView)view.findViewById(R.id.fg_itn_tv_priceR);
        TextView bc = (TextView)view.findViewById(R.id.fg_tv_path);

        TextView bcR = (TextView)view.findViewById(R.id.fg_tv_pathR);

        double p = chosenFlight.getPriceD() + chosenFlightR.getPriceD();
        DecimalFormat df = new DecimalFormat("0.00");
        String priceStr = df.format(p);
        totalP.setText("S$ "+priceStr);
        bc.setText("Out: "+chosenFlight.getBookingClassName());
        bcR.setText("In: " + chosenFlightR.getBookingClassName());

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
