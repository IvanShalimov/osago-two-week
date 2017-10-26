package ivan.osago.calculator;


import android.util.Log;

import org.joda.time.DateTime;

/**
 * Created by ivan on 15.10.16.
 */
public class Calculator {

    public float getRETENTION_FACTOR() {
        return RETENTION_FACTOR;
    }

    public void setRETENTION_FACTOR(float RETENTION_FACTOR) {
        this.RETENTION_FACTOR = RETENTION_FACTOR;
    }

    private float RETENTION_FACTOR=(float)0.5;

    DateTime cancelDate;
    DateTime beginDate;
    int amount;
    int termInsurance = 3;

    public int getTermInsurance() {
        return termInsurance;
    }

    public void setTermInsurance(int termInsurance) {
        this.termInsurance = termInsurance;
    }

    public void setTermInsurance(String termInsurance) {
        this.termInsurance = Integer.valueOf(termInsurance);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAmount(String amount) {
        this.amount = Integer.valueOf(amount);
    }

    public DateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(DateTime beginDate) {
        this.beginDate = beginDate;
    }

    public void setBeginDate(String beginDate) {
        String[] date = beginDate.split("\\.");
        Log.d("Test",""+beginDate);
        this.beginDate = new DateTime(
                Integer.valueOf(date[2]),
                Integer.valueOf(date[1]),
                Integer.valueOf(date[0]),
                0,
                0);
    }
    public DateTime getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(DateTime cancelDate) {
        this.cancelDate = cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        String[] date = cancelDate.split("\\.");
        Log.d("Test",""+cancelDate);
        this.cancelDate = new DateTime(
                Integer.valueOf(date[2]),
                Integer.valueOf(date[1]),
                Integer.valueOf(date[0]),
                0,
                0);
    }

    public double calculate(){
        try {
            Log.d("Test","RETENTION_FACTOR = "+RETENTION_FACTOR+" amount = "+amount+
            " amount/365 = "+amount/getPeriod()+" getBalance() = "+ getBalance());
            double result = (amount/getPeriod())*getBalance()*RETENTION_FACTOR;
            return  result;
        }catch(IllegalStateException exception){
            return 0;
        }
    }

    private int getPeriod(){
        switch (termInsurance) {
            case 3:
                return 91;
            case 4:
                return 122;
            case 5:
                return 152;
            case 6:
                return 182;
            case 7:
                return 213;
            case 8:
                return 243;
            case 9:
                return 274;
            case 12:
                return 365;
        }
        return 1;
    }

    int getBalance() throws IllegalStateException {
        Log.d("Test","beginDate = "+beginDate.monthOfYear().get());
        Log.d("Test","cancelDate = "+cancelDate.monthOfYear());
        Log.d("Test","beginDate = "+beginDate.getDayOfYear());
        Log.d("Test","cancelDate = "+cancelDate.getDayOfYear());
        int mathCancelDate = 0;
        if(cancelDate.year().get() > beginDate.year().get()){
            mathCancelDate = 365;
        }
        int different =(cancelDate.getDayOfYear()+mathCancelDate) - beginDate.getDayOfYear();
        Log.d("Test","termInsurance = "+termInsurance+" different = "+different);
        switch (termInsurance){
            case 3:
                different = 91 - different;
                break;
            case 4:
                different = 122 - different;
                break;
            case 5:
                different = 152 - different;
                break;
            case 6:
                different = 183 - different;
                break;
            case 7:
                different = 213 - different;
                break;
            case 8:
                different = 244 - different;
                break;
            case 9:
                different = 274 - different;
                break;
            case 12:
                different = 365 - different;
                break;
        }

        if(different<0){
            throw new IllegalStateException();
        } else
            return different;
    }
}
