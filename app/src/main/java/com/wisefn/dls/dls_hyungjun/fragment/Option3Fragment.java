package com.wisefn.dls.dls_hyungjun.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wisefn.dls.dls_hyungjun.R;
import com.wisefn.dls.dls_hyungjun.adapter.CustomerAdapter;
import com.wisefn.dls.dls_hyungjun.bean.CustomerList.CustomerData;
import com.wisefn.dls.dls_hyungjun.bean.CustomerList;
import com.wisefn.dls.dls_hyungjun.Retrofit.MktListService;
import com.wisefn.dls.dls_hyungjun.Retrofit.RetroClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Option3Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Option3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Option3Fragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<CustomerData> customerDataArrayList;
    private RecyclerView c_recycleView;
    private CustomerAdapter mAdapter;

    private Button opt_3_button_1, opt_3_button_2, opt_3_button_3, opt_3_button_4, opt_3_button_5, opt_3_button_6;

    private ConstraintLayout opt_3_btn_layout;

    private ImageButton iB_office, iB_mobile;
    private EditText c_search_editText;
    private Button c_btn_search, opt_3_hide_hutton;
    private String name;

    private boolean VISBLE_FLAG = true;
    private boolean CLICK_FLAG = true;

    public Option3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Option3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Option3Fragment newInstance(String param1, String param2) {
        Option3Fragment fragment = new Option3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_option3, container, false);

        opt_3_btn_layout = (ConstraintLayout) view.findViewById(R.id.opt_3_btn_layout);
        c_search_editText = (EditText) view.findViewById(R.id.c_search_editText);
        c_btn_search = (Button) view.findViewById(R.id.c_btn_search);
        c_recycleView = (RecyclerView) view.findViewById(R.id.c_recycleView);
        iB_mobile = (ImageButton) view.findViewById(R.id.cus_btn_mobile_call);
        iB_office = (ImageButton) view.findViewById(R.id.cus_btn_office_call);
        opt_3_hide_hutton = (Button) view.findViewById(R.id.opt_3_hide_hutton);

        opt_3_button_1 = (Button) view.findViewById(R.id.opt_3_button_1);
        opt_3_button_2 = (Button) view.findViewById(R.id.opt_3_button_2);
        opt_3_button_3 = (Button) view.findViewById(R.id.opt_3_button_3);
        opt_3_button_4 = (Button) view.findViewById(R.id.opt_3_button_4);
        opt_3_button_5 = (Button) view.findViewById(R.id.opt_3_button_5);
        opt_3_button_6 = (Button) view.findViewById(R.id.opt_3_button_6);
        opt_3_button_1.setOnClickListener(this);
        opt_3_button_2.setOnClickListener(this);
        opt_3_button_3.setOnClickListener(this);
        opt_3_button_4.setOnClickListener(this);
        opt_3_button_5.setOnClickListener(this);
        opt_3_button_6.setOnClickListener(this);

        opt_3_hide_hutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(VISBLE_FLAG == true){
                    opt_3_btn_layout.setVisibility(View.GONE);
                    VISBLE_FLAG = false;
                }
                else{
                    opt_3_btn_layout.setVisibility(View.VISIBLE);
                    VISBLE_FLAG = true;
                }

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        c_recycleView.setLayoutManager(layoutManager);

        c_btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerDataArrayList = new ArrayList<>();

                mAdapter = new CustomerAdapter(getActivity());
                c_recycleView.setAdapter(mAdapter);

                name = c_search_editText.getText().toString();

                makeCList(name);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

        if(CLICK_FLAG == true){

            CLICK_FLAG = false;

            customerDataArrayList = new ArrayList<>();

            mAdapter = new CustomerAdapter(getActivity());
            c_recycleView.setAdapter(mAdapter);
            String name;
            switch (view.getId()){
                case R.id.opt_3_button_1:
                    name = "김";
                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.opt_3_button_2:
                    name = "박";
                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.opt_3_button_3:
                    name = "이";
                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.opt_3_button_4:
                    name = "정";
                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.opt_3_button_5:
                    name = "최";
                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.opt_3_button_6:
                    name = "황";
                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                    break;
                default :
                    name = "";
                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                    break;
            }
            makeCList(name);

        }

    }

    private void makeCList(String input) {

        String name = input;

        MktListService api = RetroClient.getMktListService();

        Call<CustomerList> call = api.mCustomerList(name);

        call.enqueue(new Callback<CustomerList>() {
            @Override
            public void onResponse(Call<CustomerList> call, Response<CustomerList> response) {

                if (response.isSuccessful()) {
                    customerDataArrayList = response.body().getCustomerData();

                    mAdapter.setCustomerDataList(customerDataArrayList);

                    CLICK_FLAG = true;

                } else {
                    Toast.makeText(getContext(), "worng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerList> call, Throwable t) {
                Toast.makeText(getContext(), "connection error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }
}
