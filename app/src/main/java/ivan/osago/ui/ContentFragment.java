package ivan.osago.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ivan.osago.R;
import ivan.osago.network.DataRequest;
import ivan.osago.network.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment implements ivan.osago.ui.View,CaptchaDialog.HandleCallbac,
View.OnClickListener{

    public static final String CAPTCHA_DIALOG_TAG = "CAPTCHA_DIALOG";
    Presenter presenter;
    CaptchaDialog dialog;

   // @BindView(R.id.cancellation_date_field)
    TextView canceletionDateField;
  //  @BindView(R.id.policy_beginning_field)
    TextView policyBeginningField;
  //  @BindView(R.id.amount_nsurance_field)
    EditText amountInsuranceField;
/*    @BindView(R.id.policy_number_field)
    EditText policeNumberField;*/
   // @BindView(R.id.term_insurance_spinner)
    Spinner termInsuranceSpinner;
/*    @BindView(R.id.serial_osago)
    Spinner serialOsago;*/
    //@BindView(R.id.calculate_button)
    Button calculateButton;
/*    @BindView(R.id.osago_number_input)
    android.support.design.widget.TextInputLayout osagoNumberInput;*/
    //@BindView(R.id.summa_insurance_input)
    android.support.design.widget.TextInputLayout summaInsuranceInput;

    public ContentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this,view);
        policyBeginningField = (TextView)view.findViewById(R.id.policy_beginning_field);
        policyBeginningField.setOnClickListener(this);
        canceletionDateField = (TextView)view.findViewById(R.id.cancellation_date_field);
        canceletionDateField.setOnClickListener(this);

        amountInsuranceField = (EditText)view.findViewById(R.id.amount_nsurance_field);
        termInsuranceSpinner = (Spinner)view.findViewById(R.id.term_insurance_spinner);
        calculateButton = (Button)view.findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(this);
        summaInsuranceInput = (android.support.design.widget.TextInputLayout)view.findViewById(R.id.summa_insurance_input);

        presenter = new PresenterImplementation(this, new ModelImplementation());

        //getSessionId();

        amountInsuranceField.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == 66) {
                    amountInsuranceField.requestFocus();
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    //сеторы полей

    @Override
    public void setCancelText(String text) {
        canceletionDateField.setText(text);
        canceletionDateField.setTextColor(Color.BLACK);
    }

    //здесь отсавить начальной дат отрисовку
    @Override
    public void setBeginText(String text) {
        policyBeginningField.setText(text);
        policyBeginningField.setTextColor(Color.BLACK);
    }

    //гетеры полей
    @Override
    public String getCancelledDate() {
        return canceletionDateField.getText().toString();
    }

    @Override
    public String getBeginDate() {
        return policyBeginningField.getText().toString();
    }

    @Override
    public String getTermInsurance() {
        String[] array = getResources().getStringArray(R.array.term_insurance_array_entires);
        return array[termInsuranceSpinner.getSelectedItemPosition()];
    }

    @Override
    public String getSumInsurance() {
        return amountInsuranceField.getText().toString();
    }

/*    @Override
    public String getNumberOsago() {
        return policeNumberField.getText().toString();
    }

    @Override
    public String getSerialOsago() {
        String[] array = getResources().getStringArray(R.array.serial_policy_array_entires);
        return array[serialOsago.getSelectedItemPosition()];
    }*/


    //получение ссесион айдишника
/*    @Override
    public void getSessionId() {
        presenter.onClick(Presenter.CHECK_DATE);
    }*/

/*    @Override
    public void setTextErrorOsagoNumber(String textError) {
        osagoNumberInput.setError(textError);
    }*/

    @Override
    public void setTextErrorSummyInsurance(String textError) {
        summaInsuranceInput.setError(textError);
    }


    //Здесь запоминаетс нужное и выводится капчка диалог
    @OnClick(R.id.calculate_button)
    @Override
    public void calculate(android.view.View view) {
        summaInsuranceInput.setError(null);
        //osagoNumberInput.setError(null);

        presenter.onClick(Presenter.CALCULATE_CLICK);

        if(!getSumInsurance().equals("")){// && !getNumberOsago().equals("")
            dialog = new CaptchaDialog();
            dialog.setHandleCallbac(this);
            dialog.show(getFragmentManager(), CAPTCHA_DIALOG_TAG);
        }

    }


//показывать дату тоже здесь ДАТА
    //@OnClick({R.id.cancellation_date_field, R.id.policy_beginning_field})
    @Override
    public void selectDate(android.view.View view) {
        switch (view.getId()){
            case R.id.cancellation_date_field:
                presenter.onClick(Presenter.SELECT_FIRST_DATE);
                break;
            case R.id.policy_beginning_field:
                presenter.onClick(Presenter.SELECT_SECOND_DATE);
                break;
        }
    }

    @Override
    public void showDialog(DatePickerDialog dialog, String type) {
        dialog.show(getFragmentManager(), type);
    }

    @Override
    public void handle(String result) {
        Intent intent = new Intent(getActivity(),ivan.osago.ui.ResultActivity.class);
        intent.putExtra("result",result);
        getActivity().startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
     //   outState.putString("number_osago",getNumberOsago());
        outState.putString("sum",getSumInsurance());
        outState.putString("begin_date",getBeginDate());
        outState.putString("cancel_date",getCancelledDate());
       // outState.putInt("serial_osago",serialOsago.getSelectedItemPosition());
        outState.putInt("srok",termInsuranceSpinner.getSelectedItemPosition());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
           // policeNumberField.setText(savedInstanceState.getString("number_osago"));
            amountInsuranceField.setText(savedInstanceState.getString("sum"));
            policyBeginningField.setText(savedInstanceState.getString("begin_date"));
            canceletionDateField.setText(savedInstanceState.getString("cancel_date"));

           // serialOsago.setSelection(savedInstanceState.getInt("serial_osago"));
            termInsuranceSpinner.setSelection(savedInstanceState.getInt("srok"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.cancellation_date_field:
           case R.id.policy_beginning_field:
               selectDate(v);
               break;
           case R.id.calculate_button:
                calculate(v);
                break;
        }
    }
}
