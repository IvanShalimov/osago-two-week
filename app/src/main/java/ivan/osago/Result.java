package ivan.osago;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ivan on 24.10.16.
 */

public class Result {

    public String insurerName;//наименвоание страховщика
    public String bodyNumber;
    public String chassisNumber;
    public String licensePlate;//Государственный регистрационный знак
    public String policyStatus;//Статус договора ОСАГО на запрашиваемую дату
    public String vin;//VIN
    public boolean validCaptcha;
    public String errorMessage;
    public int errorId;
    public String warningMessage;
    public String result;

    public Result (String JSON){
        result = JSON;
        try {
            JSONObject root = new JSONObject(JSON);
            insurerName = root.getString("insurerName");
            bodyNumber = root.getString("bodyNumber");
            chassisNumber = root.getString("chassisNumber");
            licensePlate = root.getString("licensePlate");
            policyStatus = root.getString("policyStatus");
            vin = root.getString("vin");
            validCaptcha = root.getBoolean("validCaptcha");
            errorMessage = root.getString("errorMessage");
            errorId = root.getInt("errorId");
            warningMessage = root.getString("warningMessage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
