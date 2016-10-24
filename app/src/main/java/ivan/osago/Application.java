package ivan.osago;

import android.util.Log;

/**
 * Created by ivan on 17.10.16.
 */

public class Application extends android.app.Application {
    private String phpSessionId;
    static Application mInstance;
    private String serialOsago;

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    private double result;

    public String getNumberOsago() {
        return numberOsago;
    }

    public void setNumberOsago(String numberOsago) {
        this.numberOsago = numberOsago;
    }

    public String getSerialOsago() {
        return serialOsago;
    }

    public void setSerialOsago(String serialOsago) {
        this.serialOsago = serialOsago;
    }

    private String numberOsago;

    public static Application getInstancce(){
        if (mInstance == null) {
            mInstance = new Application();
        }
        return mInstance;
    }

    public String getPhpSessionId() {
        return phpSessionId;
    }

    public void setPhpSessionId(String phpSessionId) {
        phpSessionId = regexp(phpSessionId);
        // JSESSIONID=83FD4E3F7188486C5AC3F59A0F302F83; Path=/dkbm-web-1.0/; HttpOnly
        this.phpSessionId = phpSessionId;
    }

    private String regexp(String setCookie){
        setCookie = setCookie.substring(setCookie.indexOf("=")+1,setCookie.indexOf(";"));
        Log.d("Test",""+setCookie);
        return setCookie;
    }
}
