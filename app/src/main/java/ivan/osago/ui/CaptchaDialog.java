package ivan.osago.ui;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import ivan.osago.R;
import ivan.osago.network.DataRequest;
import ivan.osago.network.Request;

/**
 * Created by ivan on 23.10.16.
 */
public class CaptchaDialog extends DialogFragment implements View.OnClickListener, Request.Callback{

    public static final int IMAGE_REQUEST = 0;
    public static final int SUCCESS_REQUEST = 1;
    public static final String URL_IMAGE = "http://dkbm-web.autoins.ru/dkbm-web-1.0/simpleCaptcha.png";
    public static final String DATA_REQUEST = "http://dkbm-web.autoins.ru/dkbm-web-1.0/osagovehicle.htm";
    TextView title;
    ImageView captcha,refresh_captcha;
    EditText captchaField;
    Button okButton,cancelButton;

    Bitmap image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.captcha_dialog_layout, container, false);
        title = (TextView)root.findViewById(R.id.title_dialog);
        captcha = (ImageView)root.findViewById(R.id.captcha);
        refresh_captcha = (ImageView)root.findViewById(R.id.refresh_captcha);
        captchaField = (EditText)root.findViewById(R.id.captcha_field);
        okButton = (Button)root.findViewById(R.id.ok_button);
        cancelButton = (Button)root.findViewById(R.id.cancel_button);

        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        refresh_captcha.setOnClickListener(this);

        request(IMAGE_REQUEST);

        return root;
    }

    private void request(int type){

        switch (type){
            case IMAGE_REQUEST:
                Request request = new Request(getActivity());
                request.setCallback(this);
                request.executeImageRequest(URL_IMAGE);
                break;
            case SUCCESS_REQUEST:
                DataRequest requestData = new DataRequest(getActivity());
                requestData.request(DATA_REQUEST,
                        captchaField.getText().toString());
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok_button:

                break;
            case R.id.cancel_button:
                dismiss();
                break;
            case R.id.refresh_captcha:
                request(IMAGE_REQUEST);
                break;
        }
    }

    @Override
    public void setImage(Bitmap bitmap) {
        captcha.setImageBitmap(bitmap);
    }
}
