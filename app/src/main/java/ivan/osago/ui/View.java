package ivan.osago.ui;

import android.app.Activity;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

/**
 * Created by ivan on 15.10.16.
 */
public interface View {

    void calculate(android.view.View view);


    void selectDate(android.view.View view);

    Activity getActivity();

    void showDialog(DatePickerDialog dialog,String type);

    void setBeginText(String text);

    void setCancelText(String text);

    String getCancelledDate();

    String getBeginDate();

    String getTermInsurance();

    String getSumInsurance();

/*    String getNumberOsago();

    String getSerialOsago();*/

  //  void getSessionId();

/*    void setTextErrorOsagoNumber(String textError);*/

    void setTextErrorSummyInsurance(String textError);

}
