package ivan.osago.ui;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.joda.time.DateTime;

import java.util.Calendar;

import ivan.osago.Application;
import ivan.osago.calculator.Calculator;
import ivan.osago.network.Request;

/**
 * Created by ivan on 15.10.16.
 */
public class PresenterImplementation implements Presenter, DatePickerDialog.OnDateSetListener{

    private Calculator calculator;
    View view;
    Model model;

    public PresenterImplementation(View view, Model model) {
        this.view = view;
        this.model = model;
        calculator = new Calculator();

        DateTime calendare = new DateTime();

        String date = calendare.getDayOfMonth()+"."+calendare.getMonthOfYear()+"."+
                calendare.getYear();

        this.view.setBeginText(date);
        this.view.setCancelText(date);
    }

    @Override
    public void onDestroy() {
        view = null;
        model = null;
        calculator = null;
    }

    @Override
    public void onClick(int type) {
        switch (type){
            case Presenter.CALCULATE_CLICK:
                if(!view.getSumInsurance().equals("")){
                    calculator.setAmount(view.getSumInsurance());
                    calculator.setBeginDate(view.getBeginDate());
                    calculator.setCancelDate(view.getCancelledDate());
                    calculator.setTermInsurance(view.getTermInsurance());
                    SharedPreferences preferences =
                            PreferenceManager.getDefaultSharedPreferences(view.getActivity());
                    float coef = preferences.getFloat("hold_koef", (float)0.5);
                    calculator.setRETENTION_FACTOR(coef);

/*                    if (!view.getNumberOsago().equals("")){
                        Application.getInstancce().setNumberOsago(view.getNumberOsago());
                        Application.getInstancce().setSerialOsago(view.getSerialOsago());

                    }
                    else
                        view.setTextErrorOsagoNumber("Введите номер ОСАГО");*/
                    Application.getInstancce().setResult(calculator.calculate());
                }
                else
                    view.setTextErrorSummyInsurance("Введите сумму");

                break;
            case Presenter.REQUEST_CLICK:

                break;
            case Presenter.SELECT_FIRST_DATE:
                createDialog("cancel");
                break;
            case Presenter.SELECT_SECOND_DATE:
                createDialog("begin");
                break;
            case Presenter.CHECK_DATE:
                Request request = new Request(view.getActivity());
                request.request("http://dkbm-web.autoins.ru/dkbm-web-1.0/osagovehicle.htm");
                break;
            default:
        }
    }

    private void createDialog(String type){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        view.showDialog(dialog, type);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String type = view.getTag();
        if(type.equals("cancel")) {
            this.view.setCancelText(dayOfMonth+"."+(monthOfYear+1)+"."+year);
            return;
        }
        if(type.equals("begin")){
            this.view.setBeginText(dayOfMonth+"."+(monthOfYear+1)+"."+year);
        }
    }
}
