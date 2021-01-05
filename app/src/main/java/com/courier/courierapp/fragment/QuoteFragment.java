package com.courier.courierapp.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.courier.courierapp.R;
import com.courier.courierapp.activity.MapActivity;
import com.courier.courierapp.utils.GpsUtils;
import com.courier.courierapp.utils.PermissionUtils;
import com.courier.courierapp.utils.PreferenceUtil;
import com.courier.courierapp.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.courier.courierapp.utils.AppConstant.LOCATION_PERMISSION_REQUEST_CODE;


public class QuoteFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    RelativeLayout calCardView;
    TextView pickUpDate;
    EditText fromAddr;
    EditText toAddr;

    Spinner serviceSpinner, itemSpinner, weightSpinner;

    RelativeLayout fromAdddrBlock;
    RelativeLayout toAddrBlock;

    ImageView fromAddrIcon, toAddrIcon;

    Button btnGetPrice;
    Button btnClear;

    String date;

    private String serviceSelected, itemSelected, weightSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ordershipment, null);

        //getPrice = (Button) view.findViewById(R.id.getPriceButton);

        calCardView = (RelativeLayout) view.findViewById(R.id.calCardView);
        pickUpDate = (TextView) view.findViewById(R.id.pickUpDate);
        fromAddr = (EditText) view.findViewById(R.id.fromAddress);
        toAddr = (EditText) view.findViewById(R.id.toAddress);
        btnGetPrice = (Button) view.findViewById(R.id.getPriceButton);
        btnClear = (Button) view.findViewById(R.id.clearQuote);


        fromAdddrBlock = (RelativeLayout) view.findViewById(R.id.fromAddressBlock);
        fromAddrIcon = (ImageView) view.findViewById(R.id.fromAddrIcon);
        toAddrIcon = (ImageView) view.findViewById(R.id.toAddrIcon);


        serviceSpinner = (Spinner) view.findViewById(R.id.selectService);
        itemSpinner = (Spinner) view.findViewById(R.id.itemDetail);
        weightSpinner = (Spinner) view.findViewById(R.id.itemWeight);

        ArrayAdapter<CharSequence> serviceAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.SelectService, android.R.layout.simple_spinner_item);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(serviceAdapter);


        ArrayAdapter<CharSequence> itemAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.SelectItem, android.R.layout.simple_spinner_item);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(itemAdapter);


        ArrayAdapter<CharSequence> weightAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.SelectWeight, android.R.layout.simple_spinner_item);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(weightAdapter);


        serviceSpinner.setOnItemSelectedListener(this);
        itemSpinner.setOnItemSelectedListener(this);
        weightSpinner.setOnItemSelectedListener(this);

        Calendar calendar = Calendar.getInstance();
        final int dayC = calendar.get(Calendar.DAY_OF_MONTH);
        final int monthC = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        final int yearC = calendar.get(Calendar.YEAR);


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        System.out.println("Today: " + dayOfTheWeek);
        //pickUpDate.setText("" + dayOfTheWeek);
        pickUpDate.setText("Select Date");


        calCardView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        int monthOfYear = month + 1;
                        date = dayOfMonth + "/" + monthOfYear + "/" + year;
                        pickUpDate.setText("" + date);


                    }
                }, yearC, monthC, dayC);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                Date d = new Date();
                String dayOfTheWeek = sdf.format(d);
                System.out.println("Today: " + dayOfTheWeek);
                //pickUpDate.setText("" + dayOfTheWeek);

                int daysOfWeekInMonth = 0;

                if (dayOfTheWeek.equals("Monday")) {
                    daysOfWeekInMonth = 5;
                } else if (dayOfTheWeek.equals("Tuesday")) {
                    daysOfWeekInMonth = 4;
                } else if (dayOfTheWeek.equals("Wednesday")) {
                    daysOfWeekInMonth = 3;
                } else if (dayOfTheWeek.equals("Thursday")) {
                    daysOfWeekInMonth = 2;
                } else if (dayOfTheWeek.equals("Friday")) {
                    daysOfWeekInMonth = 1;
                } else if (dayOfTheWeek.equals("Saturday")) {
                    daysOfWeekInMonth = 0;
                }


                long now = System.currentTimeMillis() - 1000;
                datePickerDialog.getDatePicker().setMinDate(now);
                //datePickerDialog.getDatePicker().setMaxDate(now+(1000*60*60*24*2));
                datePickerDialog.getDatePicker().setMaxDate(now + (1000 * 60 * 60 * 24 * daysOfWeekInMonth));


                //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-5000);
                //datePickerDialog.getDatePicker().get

                //System.out.println("FirstDayOfWeek" + datePickerDialog.getDatePicker().getFirstDayOfWeek());
                datePickerDialog.show();


            }
        });


        fromAddr.addTextChangedListener(new MyTextWatcher(fromAddr));
        toAddr.addTextChangedListener(new MyTextWatcher(toAddr));


        fromAddrIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fromAddress = "A";
                checkLocationPermission(fromAddress);

            }
        });

        toAddrIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toAddress = "B";
                checkLocationPermission(toAddress);
            }
        });


        btnGetPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (date != null) {
                    String latA = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.POINT_A_LAT);
                    String longA = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.POINT_A_LONG);

                    String latB = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.POINT_B_LAT);
                    String longB = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.POINT_B_LONG);

                    if (!latA.equals("null") && !longA.equals("null") && !latB.equals("null") && !longB.equals("null")) {

                        if (!serviceSelected.equals("Select Service") && !itemSelected.equals("Select Item") && !weightSelected.equals("Select Weight")) {
                            callFragment();
                        } else {
                            Toast.makeText(getActivity(), "Please Select Service", Toast.LENGTH_LONG).show();
                        }


                    } else {
                        Toast.makeText(getActivity(), "Please Select Correct Formet of Location", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getActivity(), "Please Select Date", Toast.LENGTH_LONG).show();
                }


            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PreferenceUtil.remove(getActivity(), PreferenceUtil.POINT_A_LAT);
                PreferenceUtil.remove(getActivity(), PreferenceUtil.POINT_A_LONG);

                PreferenceUtil.remove(getActivity(), PreferenceUtil.POINT_B_LAT);
                PreferenceUtil.remove(getActivity(), PreferenceUtil.POINT_B_LONG);

                pickUpDate.setText("");
                fromAddr.setText("");
                toAddr.setText("");

                serviceSpinner.setSelection(0);
                itemSpinner.setSelection(0);
                weightSpinner.setSelection(0);

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        String originLat = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.POINT_A_LAT);
        String originLong = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.POINT_A_LONG);

        System.out.println("FromPoint" + originLat + " " + originLong);

        String destiLat = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.POINT_B_LAT);
        String destiLong = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.POINT_B_LONG);

        System.out.println("ToPoint" + destiLat + " " + destiLong);

        if (originLat.equals("null") && originLong.equals("null") && destiLat.equals("null") && destiLong.equals("null")) {
            System.out.println("No Coordinates");

        }
        if (!originLat.equals("null") && !originLong.equals("null")) {

            getFromAddressReverseGeoCoding(originLat, originLong, 1);


        }
        if (!destiLat.equals("null") && !destiLong.equals("null")) {
            getFromAddressReverseGeoCoding(destiLat, destiLong, 2);
        }


    }

    private void getFromAddressReverseGeoCoding(String originLat, String originLong, int fromOrTo) {

        double originA = Double.parseDouble(originLat);
        double originB = Double.parseDouble(originLong);
        List<Address> geoAddresses = GpsUtils.getAddressFromMap(getActivity(), originA, originB);
        if (geoAddresses.size() != 0) {

            String address = geoAddresses.get(0).getAddressLine(0);
            String area = geoAddresses.get(0).getLocality();
            String city = geoAddresses.get(0).getAdminArea();
            String country = geoAddresses.get(0).getCountryName();
            String postalCode = geoAddresses.get(0).getPostalCode();
            String subAdminArea = geoAddresses.get(0).getSubAdminArea();
            String subLocality = geoAddresses.get(0).getSubLocality();
            String premises = geoAddresses.get(0).getPremises();
            String addressLine = geoAddresses.get(0).getAddressLine(0);

            System.out.println("MyAddress" + address + " " + area + " " + city + " " + postalCode);

            if (fromOrTo == 1) {
                fromAddr.setText(address + " " + area + " " + city + " " + postalCode);
            } else if (fromOrTo == 2) {
                toAddr.setText(address + " " + area + " " + city + " " + postalCode);
            }


        }

    }

    private void callFragment() {


        if (fromAddr.equals(null) && toAddr.equals(null) && serviceSpinner.getSelectedItemPosition() == 0 && itemSpinner.getSelectedItemPosition() == 0 && weightSpinner.getSelectedItemPosition() == 0) {
            ToastUtils.getInstance(getActivity()).showLongToast("Please enter all details");
        } else {


            Fragment fragment = new ShowPriceFragment();
            FragmentManager fragmentManager = getFragmentManager();

            Bundle args = new Bundle();
            args.putString("CASHTYPE", "NULL");
            args.putString("TRACKNO", "NULL");
            args.putString("DELIVDATA", date);
            fragment.setArguments(args);


            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.screenArea, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        }


    }

    private void checkLocationPermission(String addrPoint) {

        if (!PermissionUtils.hasPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                && !PermissionUtils.hasPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {

            PermissionUtils.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);


        } else {

            if (PermissionUtils.hasPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    && PermissionUtils.hasPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {


                Intent intent = new Intent(getActivity(), MapActivity.class);
                intent.putExtra("ADDRESSPOINT", addrPoint);
                startActivity(intent);
                //getActivity().finish();


            }

        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {

            case R.id.selectService:
                serviceSelected = parent.getItemAtPosition(position).toString();
                System.out.println("ItemSelected" + serviceSelected);
                break;

            case R.id.itemDetail:
                itemSelected = parent.getItemAtPosition(position).toString();
                System.out.println("ItemSelected1" + itemSelected);
                break;


            case R.id.itemWeight:
                weightSelected = parent.getItemAtPosition(position).toString();
                System.out.println("ItemSelected1" + weightSelected);
                break;


        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String fromAddress = fromAddr.getText().toString().trim();
            String toAddress = toAddr.getText().toString().trim();

            btnGetPrice.setEnabled(fromAddress.length() >= 5 && toAddress.length() >= 6);

            if (btnGetPrice.isEnabled()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnGetPrice.setBackground(getResources().getDrawable(R.drawable.rectangle_shpae));
                }
            } else if (!btnGetPrice.isEnabled()) {
                btnGetPrice.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnGetPrice.setBackground(getResources().getDrawable(R.color.btn_disable));
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


}
