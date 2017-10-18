package ivan.osago.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ivan.osago.Application;
import ivan.osago.R;
import ivan.osago.Result;

public class ResultActivity extends AppCompatActivity {

/*    @BindView(R.id.insurer_name_result)
    TextView insurerName;//наименвоание страховщика
    @BindView(R.id.license_plate_result)
    TextView licensePlate;//Государственный регистрационный знак
    @BindView(R.id.policy_status_result)
    TextView policyStatus;//Статус договора ОСАГО на запрашиваемую дату
    @BindView(R.id.vin_result)
    TextView vin;//VIN
    @BindView(R.id.error_message)
    TextView errorMessage;
    @BindView(R.id.warning_message)
    TextView warningMessage;
    @BindView(R.id.result_sum)
    TextView resultSum;

    @BindView(R.id.error_result)
    CardView errorPlate;
    @BindView(R.id.succes_result)
    CardView succesPlate;*/
    @BindView(R.id.web_view)
    WebView webView;

    Result result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        webView = (WebView)findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://dkbm-web.autoins.ru/dkbm-web-1.0/osagovehicle.htm");

/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Результат");*/

//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        
        //result = new Result(getIntent().getExtras().getString("result"));
        //getResult();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

  /*  public void getResult() {

        if (result.errorId != 0  || result.insurerName.equals("null")){
            succesPlate.setVisibility(View.GONE);
            errorPlate.setVisibility(View.VISIBLE);
            if(result.insurerName.equals("null"))
                errorMessage.setText("Ошибка ввода данных или неверно введенный код безопасности");
            else
                errorMessage.setText(result.errorMessage);
            errorMessage.setVisibility(View.VISIBLE);
            warningMessage.setVisibility(View.GONE);
        } else if(result.validCaptcha == false || result.insurerName.equals("null")) {
            succesPlate.setVisibility(View.GONE);
            errorPlate.setVisibility(View.VISIBLE);
            warningMessage.setText(result.warningMessage);
            warningMessage.setVisibility(View.VISIBLE);
            errorMessage.setVisibility(View.GONE);
        } else {
            errorPlate.setVisibility(View.GONE);
            succesPlate.setVisibility(View.VISIBLE);
            insurerName.setText(result.insurerName);
            licensePlate.setText(result.licensePlate);
            policyStatus.setText(result.policyStatus);
            vin.setText(result.vin);
            resultSum.setText("Сумма: "+Application.getInstancce().getResult());
        }
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //outState.putString("result",result.result);
    }

}
