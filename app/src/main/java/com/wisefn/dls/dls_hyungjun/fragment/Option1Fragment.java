package com.wisefn.dls.dls_hyungjun.fragment;


import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.wisefn.dls.dls_hyungjun.R;
import com.wisefn.dls.dls_hyungjun.Utility.ItemClickSupport;
import com.wisefn.dls.dls_hyungjun.bean.MktList;
import com.wisefn.dls.dls_hyungjun.bean.MktList.MktListData;
import com.wisefn.dls.dls_hyungjun.Retrofit.MktListService;
import com.wisefn.dls.dls_hyungjun.adapter.MktRcAdapter;
import com.wisefn.dls.dls_hyungjun.Retrofit.RetroClient;
import com.wisefn.dls.dls_hyungjun.activity.MktDDActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Option1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Option1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Option1Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<MktListData> mktListDataArrayList;
    private RecyclerView opt_1_rcView;
    private MktRcAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    public Option1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Option1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Option1Fragment newInstance(String param1, String param2) {
        Option1Fragment fragment = new Option1Fragment();
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
        final View view = inflater.inflate(R.layout.fragment_option1, container, false);


        opt_1_rcView = (RecyclerView) view.findViewById(R.id.opt_1_rcView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        opt_1_rcView.setLayoutManager(layoutManager);

        mktListDataArrayList = new ArrayList<>();

        mAdapter = new MktRcAdapter(getActivity());
        opt_1_rcView.setAdapter(mAdapter);

        makeList();

        ItemClickSupport.addTo(opt_1_rcView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getContext(), mktListDataArrayList.get(position).getMktWriteID()+"", Toast.LENGTH_SHORT).show();

                String mktListDetail_ID = String.valueOf(mktListDataArrayList.get(position).getMktWriteID());

                Intent intent = new Intent(getContext(), MktDDActivity.class);
                intent.putExtra("id", mktListDetail_ID);
                startActivity(intent);
            }
        });

        return view;
    }

    private void makeList() {

        MktListService api = RetroClient.getMktListService();

        Call<MktList> call = api.all2("20170714000000", "60");

        call.enqueue(new Callback<MktList>() {
            @Override
            public void onResponse(Call<MktList> call, Response<MktList> response) {

                if(response.isSuccessful()){
                    mktListDataArrayList = response.body().getData();

//                    adapter = new MktListAdapter(getContext(), mktListDataArrayList);
//                    opt_4_listView.setAdapter(adapter);
//                    mAdapter = new MktRcAdapter(mktListDataArrayList);
//                    opt_4_listView.setAdapter(mAdapter);
                    mAdapter.setMktList(mktListDataArrayList);

                } else {
                    Toast.makeText(getContext(), "worng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MktList> call, Throwable t) {
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
