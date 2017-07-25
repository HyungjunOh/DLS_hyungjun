package com.wisefn.dls.dls_hyungjun.fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.wisefn.dls.dls_hyungjun.R;
import com.wisefn.dls.dls_hyungjun.activity.MainActivity;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView imageView1, imageView2, imageView3, imageView4, imageView5;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageView1 = view.findViewById(R.id.home_img_view_1);
        imageView2 = view.findViewById(R.id.home_img_view_2);
        imageView3 = view.findViewById(R.id.home_img_view_3);
        imageView4 = view.findViewById(R.id.home_img_view_4);
        imageView5 = view.findViewById(R.id.home_img_view_main);

        setOnTouch();
        setOnClick();

        return view;
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



    private void setOnTouch(){
        imageView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imageView1.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageView1.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                }
                return false;
            }
        });
        imageView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imageView2.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageView2.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                }
                return false;
            }
        });
        imageView3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imageView3.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageView3.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                }
                return false;
            }
        });
        imageView4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imageView4.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageView4.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                }
                return false;
            }
        });
        imageView5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imageView5.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageView5.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
                }
                return false;
            }
        });
    }

    private void setOnClick(){
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "이미지버튼1", Toast.LENGTH_SHORT).show();

                ((MainActivity)getActivity()).homeButtonClicked(R.id.nav_option_1);

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "이미지버튼2", Toast.LENGTH_SHORT).show();

                ((MainActivity)getActivity()).homeButtonClicked(R.id.nav_option_2);

            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "이미지버튼3", Toast.LENGTH_SHORT).show();

                ((MainActivity)getActivity()).homeButtonClicked(R.id.nav_option_3);

            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "이미지버튼4", Toast.LENGTH_SHORT).show();

                ((MainActivity)getActivity()).homeButtonClicked(R.id.nav_option_4);

            }
        });
    }

}
