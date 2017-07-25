package com.wisefn.dls.dls_hyungjun.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wisefn.dls.dls_hyungjun.R;
import com.wisefn.dls.dls_hyungjun.Retrofit.Constants;
import com.wisefn.dls.dls_hyungjun.Retrofit.MktListService;
import com.wisefn.dls.dls_hyungjun.Retrofit.RetroClient;
import com.wisefn.dls.dls_hyungjun.activity.MainActivity;
import com.wisefn.dls.dls_hyungjun.adapter.CSLAdapter;
import com.wisefn.dls.dls_hyungjun.bean.CustomerSrchList;
import com.wisefn.dls.dls_hyungjun.bean.CustomerSrchList.CustomerSrchListData;
import com.wisefn.dls.dls_hyungjun.bean.InsertMkt;
import com.wisefn.dls.dls_hyungjun.bean.ProductList;
import com.wisefn.dls.dls_hyungjun.bean.ProductList.ProductListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wisefn.dls.dls_hyungjun.R.layout.test;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Option2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Option2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Option2Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //edit
    private Button opt_2_btn_check, opt_2_btn_cancel, opt_2_btn_product;
    private TextView opt_2_date, opt_2_time, opt_2_product, opt_2_cmpN;
    private EditText opt_2_csl_eT, opt_2_etTitle, opt_2_content;
    private ListView opt_2_nameListView;

    private String customer_ID, selectPrd_Code;

    private CSLAdapter cslAdapter;

    private List<CustomerSrchListData> customerSrchListDataList;
    private ArrayList<ProductListItem> productListItemArrayList;

    private long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat simpleDateFormat_2 = new SimpleDateFormat("HH:mm");
    String defaultDate = simpleDateFormat.format(date);
    String defaultTime = simpleDateFormat_2.format(date);

    Calendar mCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int date) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, date);
            updateLabel_date();
        }
    };
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            mCalendar.set(Calendar.HOUR_OF_DAY, hour);
            mCalendar.set(Calendar.MINUTE, minute);
            updateLabel_time();
        }
    };

    public Option2Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Option2Fragment newInstance(String param1, String param2) {
        Option2Fragment fragment = new Option2Fragment();
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
        View view = inflater.inflate(R.layout.fragment_option2, container, false);

        opt_2_btn_check = (Button)view.findViewById(R.id.button);
        opt_2_btn_cancel = (Button)view.findViewById(R.id.button2);
        opt_2_btn_product = (Button) view.findViewById(R.id.opt_2_btn_product);
        opt_2_date = (TextView)view.findViewById(R.id.opt_2_date);
        opt_2_time = (TextView)view.findViewById(R.id.opt_2_time);
        opt_2_product = (TextView) view.findViewById(R.id.opt_2_product);
        opt_2_cmpN = (TextView)view.findViewById(R.id.opt_2_cmpN);
        opt_2_csl_eT = (EditText)view.findViewById(R.id.opt_2_csl_eT);
        opt_2_etTitle = (EditText)view.findViewById(R.id.opt_2_etTitle);
        opt_2_content = (EditText)view.findViewById(R.id.opt_2_content);
        opt_2_date.setText(defaultDate);
        opt_2_time.setText(defaultTime);
        opt_2_nameListView = (ListView)view.findViewById(R.id.opt_2_nameListView);

        if(MainActivity.CSL == Constants.DATABASE.CUSTOMERSRCHLIST_N){
            makeCSList();
        } else {
            Log.e("CSL//NOT CALL", MainActivity.customerSrchListDataArrayList.get(0).getWATSEARCH());
        }

        customerSrchListDataList = new ArrayList<>();

        opt_2_csl_eT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b == true){
                    opt_2_nameListView.setVisibility(View.VISIBLE);
                } else {
                    opt_2_nameListView.setVisibility(View.INVISIBLE);
                }
            }
        });

        opt_2_csl_eT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String text = opt_2_csl_eT.getText().toString();
                Log.e("afterTextChange : ", text);
                cslAdapter.fillter(text);

            }
        });

        opt_2_nameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                customer_ID = ((CustomerSrchListData)cslAdapter.getItem(i)).getCusMemID().toString();
                opt_2_cmpN.setText(((CustomerSrchListData)cslAdapter.getItem(i)).getCusCmpNM());
                opt_2_csl_eT.setText(((CustomerSrchListData)cslAdapter.getItem(i)).getWATTEXT());
                opt_2_csl_eT.clearFocus();

            }
        });

        makePList();

        onClickMethod();

        return view;
    }

    private void makeCSList(){
        Log.e("makeCSList", "start");
        MainActivity.customerSrchListDataArrayList = new ArrayList<>();

        MktListService api = RetroClient.getMktListService();
        Call<CustomerSrchList> call = api.mCustomerSrchList("Y");
        call.enqueue(new Callback<CustomerSrchList>() {
            @Override
            public void onResponse(Call<CustomerSrchList> call, Response<CustomerSrchList> response) {

                if (response.isSuccessful()) {
                    MainActivity.customerSrchListDataArrayList = response.body().getData();
                    Log.e("CSL//CALL", MainActivity.customerSrchListDataArrayList.get(0).getWATSEARCH());
                    MainActivity.CSL = Constants.DATABASE.CUSTOMERSRCHLIST_Y;

                    cslAdapter = new CSLAdapter(getContext(), customerSrchListDataList);
                    opt_2_nameListView.setAdapter(cslAdapter);
                } else {
                    Toast.makeText(getContext(), "worng", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CustomerSrchList> call, Throwable t) {

            }
        });
    }

    private void makePList(){
        Log.e("makePList", "start");
        productListItemArrayList = new ArrayList<>();

        MktListService api = RetroClient.getMktListService();
        Call<ProductList> call = api.mProductList("Y");
        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {

                if (response.isSuccessful()) {
                    productListItemArrayList = response.body().getData();

                } else {
                    Toast.makeText(getContext(), "worng", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {

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

    private void onClickMethod(){
        opt_2_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), dateSetListener, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        opt_2_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(getContext(), timeSetListener, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true).show();

            }
        });

        opt_2_btn_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSelectOption();
            }
        });

        opt_2_btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = opt_2_etTitle.getText().toString();
                String visitor = MainActivity.getLOGIN_ID();
                String customer = customer_ID;
                String date = opt_2_date.getText().toString();
                String time = opt_2_time.getText().toString() + ":00";
                String insert_TM =date+" "+time;
                String product = opt_2_product.getText().toString();
                String content = opt_2_content.getText().toString();

                MktListService api = RetroClient.getMktListService();
                Call<InsertMkt> call = api.mMarketingWrite(title, visitor, customer, insert_TM, selectPrd_Code, content);
                call.enqueue(new Callback<InsertMkt>() {
                    @Override
                    public void onResponse(Call<InsertMkt> call, Response<InsertMkt> response) {

                        Log.e("call",call.toString());
                        if (response.isSuccessful()) {
                            String result = response.body().getData().getRESULT().toString();

                            if(result.equals("1"))
                                ((MainActivity)getActivity()).opt2clicked();
                            Log.e("insert", response.body().getReturnCode().toString());
                            Log.e("insert", response.body().getData().getRESULT().toString());

                        } else {
                            Toast.makeText(getContext(), "worng", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<InsertMkt> call, Throwable t) {

                    }
                });

                Log.e("insert Test", title + visitor +customer + insert_TM + product +content);

            }
        });

        opt_2_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).opt2clicked();
            }
        });
    }

    private void updateLabel_date(){
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        opt_2_date.setText(sdf.format(mCalendar.getTime()));
    }

    private void updateLabel_time(){
        String myFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        opt_2_time.setText(sdf.format(mCalendar.getTime()));
    }

    private void DialogSelectOption(){

        final String selectItem[] = new String[productListItemArrayList.size()];
        final String selectItemID[] = new String[productListItemArrayList.size()];
        final boolean select[] = new boolean[productListItemArrayList.size()];

        for(int i=0; i<selectItem.length; i++){
            selectItem[i] = productListItemArrayList.get(i).getProductNM();
            selectItemID[i] = productListItemArrayList.get(i).getProductCD();
            select[i] = false;
        }

        AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
        ab.setTitle("상품");
        ab.setMultiChoiceItems(selectItem, select, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton, boolean tf) {
                        Log.e("test", String.valueOf(selectItem[whichButton]));
                        select[whichButton] = true;
                    }
                })
                .setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        StringBuilder selectItems = new StringBuilder();
                        StringBuilder totalCode = new StringBuilder();
                        for(int i=0; i<select.length; i++){
                            if(select[i] == true){
                                selectItems.append(selectItem[i]).append(",");
                                totalCode.append(selectItemID[i]).append(",");
                            }
                        }
                        if(selectItems.toString() == "" || selectItems.toString().equals("")) {
                            opt_2_product.setText("상품을 선택해주세요.");
                        } else {
                            selectItems.delete(selectItems.length() - 1, selectItems.length());
                            Log.e("test", selectItems.toString());
                            opt_2_product.setText(selectItems.toString());

                            totalCode.delete(totalCode.length() - 1, totalCode.length());
                            Log.e("test", totalCode.toString());
                            selectPrd_Code = totalCode.toString();
                        }
                    }
                })
                .setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Cancel 버튼 클릭시
                        dialog.cancel();
                    }
                });
        ab.show();
    }
}
