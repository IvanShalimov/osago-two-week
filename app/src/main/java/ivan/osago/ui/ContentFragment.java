package ivan.osago.ui;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ivan.osago.R;
import ivan.osago.network.VolleySingletone;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment implements ivan.osago.ui.View {

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
    com.android.volley.toolbox.NetworkImageView imageView;
    @BindView(R.id.request_button)
    Button requestButton;

    public ContentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this,view);
        presenter = new PresemterImplemention(this, new ModelImplementation());
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
        presenter.onClick(Presenter.REQUEST_CLICK);
        ImageLoader mImageLoader = VolleySingletone.getInstance(getActivity()).getImageLoader();
/*        mImageLoader.get("http://dkbm-web.autoins.ru/dkbm-web-1.0/simpleCaptcha.png",
                ImageLoader.getImageListener(imageView,
                        R.drawable.ic_menu_gallery, R.drawable.ic_menu_share));*/
        imageView.setImageUrl("http://dkbm-web.autoins.ru/dkbm-web-1.0/simpleCaptcha.png", mImageLoader);
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
}
