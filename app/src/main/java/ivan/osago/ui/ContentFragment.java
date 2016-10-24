package ivan.osago.ui;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
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
public class ContentFragment extends Fragment implements ivan.osago.ui.View,Request.Callback {

    Presenter presenter;
    @BindView(R.id.cancellation_date_field)
    TextView canceletionDateField;
    @BindView(R.id.policy_beginning_field)
    TextView policyBeginningField;
    @BindView(R.id.amount_nsurance_field)
    EditText amountInsuranceField;
    @BindView(R.id.policy_number_field)
    EditText policeNumberField;
    @BindView(R.id.term_insurance_spinner)
    Spinner termInsuranceSpinner;
    @BindView(R.id.calculate_button)
    Button calculateButton;
    @BindView(R.id.networkImageView)
    ImageView imageView;
    @BindView(R.id.request_button)
    Button requestButton;
    @BindView(R.id.date_button)
    Button data_button;
    @BindView(R.id.сaptcha)
    EditText captcha;

    public ContentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this,view);
        presenter = new PresemterImplemention(this, new ModelImplementation());
        getSessionId();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void setCancelText(String text) {
        canceletionDateField.setText(text);
        canceletionDateField.setTextColor(Color.BLACK);
    }

    @Override
    public String getCancelledDate() {
        return canceletionDateField.getText().toString();
    }

    @Override
    public String getBeginnDate() {
        return policyBeginningField.getText().toString();
    }

    @Override
    public String getTermInsurace() {
        String[] array = getResources().getStringArray(R.array.term_insurance_array_entires);
        return array[termInsuranceSpinner.getSelectedItemPosition()];
    }

    @Override
    public String getSumInsurance() {
        return amountInsuranceField.getText().toString();
    }

    @Override
    public void getSessionId() {
        presenter.onClick(Presenter.CHECK_DATE);
    }


    @Override
    public void setBeginText(String text) {
        policyBeginningField.setText(text);
        policyBeginningField.setTextColor(Color.BLACK);
    }

    @OnClick(R.id.calculate_button)
    @Override
    public void calculate(android.view.View view) {
        presenter.onClick(Presenter.CALCULATE_CLICK);
    }

    @OnClick(R.id.request_button)
    @Override
    public void sendRequest(android.view.View view) {
        Request request = new Request(getActivity());
        request.setCallback(this);
        request.executeImageRequest("http://dkbm-web.autoins.ru/dkbm-web-1.0/simpleCaptcha.png");
    }

    @OnClick({R.id.cancellation_date_field, R.id.policy_beginning_field})
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
    public void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @OnClick(R.id.date_button)
    void clickDataButton(){
        DataRequest request = new DataRequest(getActivity());
        request.request("http://dkbm-web.autoins.ru/dkbm-web-1.0/osagovehicle.htm",
                captcha.getText().toString());
    }
}
