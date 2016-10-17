package ivan.osago.ui;

import android.util.Log;

import com.android.volley.toolbox.ImageLoader;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import ivan.osago.R;
import ivan.osago.calculator.Calculator;
import ivan.osago.network.VolleySingletone;

/**
 * Created by ivan on 15.10.16.
 */
public class PresemterImplemention implements Presenter, DatePickerDialog.OnDateSetListener{

    private Calculator calculator;
    View view;
    Model model;

    public PresemterImplemention(View view, Model model) {
        this.view = view;
        this.model = model;
        calculator = new Calculator();

        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.DAY_OF_MONTH)+"."+calendar.get(Calendar.MONTH)+"."+
                calendar.get(Calendar.YEAR);

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
                calculator.setAmount(view.getSumInsurance());
                calculator.setBeginDate(view.getBeginnDate());
                calculator.setCancelDate(view.getCancelledDate());
                calculator.setTermInsurance(view.getTermInsurace());
                double result = calculator.calculate();
                Log.d("Test",""+result);
                break;
            case Presenter.REQUEST_CLICK:


                break;
            case Presenter.SELECT_FIRST_DATE:
                createDialog("cancel");
                break;
            case Presenter.SELECT_SECOND_DATE:
                createDialog("begin");
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
            this.view.setCancelText(dayOfMonth+"."+monthOfYear+"."+year);
            return;
        }
        if(type.equals("begin")){
            this.view.setBeginText(dayOfMonth+"."+monthOfYear+"."+year);
        }
    }
}
