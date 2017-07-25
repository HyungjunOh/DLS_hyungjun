package com.wisefn.dls.dls_hyungjun.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
 * {@link Option4Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Option4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Option4Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<MktListData> mktListDataArrayList;
    private RecyclerView opt_4_listView;
    private MktRcAdapter mAdapter;
    private SwipeRefreshLayout opt_4_swipe;

    public Option4Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Option4Fragment newInstance(String param1, String param2) {
        Option4Fragment fragment = new Option4Fragment();
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
        final View view = inflater.inflate(R.layout.fragment_option4, container, false);

        opt_4_listView = (RecyclerView) view.findViewById(R.id.test_rcView);
        opt_4_swipe = (SwipeRefreshLayout) view.findViewById(R.id.opt_4_swipe);

        initOpt4();

        return view;
    }

    @Override
    public void onRefresh() {

        initOpt4();

    }

    public void initOpt4() {
        opt_4_swipe.setOnRefreshListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        opt_4_listView.setLayoutManager(layoutManager);

        mktListDataArrayList = new ArrayList<>();

        mAdapter = new MktRcAdapter(getActivity());

        opt_4_listView.setAdapter(mAdapter);

        makeList();

        ItemClickSupport.addTo(opt_4_listView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getContext(), mktListDataArrayList.get(position).getMktWriteID()+"", Toast.LENGTH_SHORT).show();

                String mktListDetail_ID = String.valueOf(mktListDataArrayList.get(position).getMktWriteID());

                Intent intent = new Intent(getContext(), MktDDActivity.class);
                intent.putExtra("id", mktListDetail_ID);
                startActivity(intent);
            }
        });
    }

    private void makeList() {

        MktListService api = RetroClient.getMktListService();

        Call<MktList> call = api.all2("20170714000000", "60");

        call.enqueue(new Callback<MktList>() {
            @Override
            public void onResponse(Call<MktList> call, Response<MktList> response) {

                if(response.isSuccessful()){

                    mktListDataArrayList = response.body().getData();

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
