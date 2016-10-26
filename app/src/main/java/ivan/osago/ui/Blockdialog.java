package ivan.osago.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ivan.osago.R;

/**
 * Created by ivan on 25.10.16.
 */

public class Blockdialog extends DialogFragment implements View.OnClickListener {
    Button ok,cancel;
    EditText password;
    android.support.design.widget.TextInputLayout inputLayout;

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    Callback callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View root = inflater.inflate(R.layout.password_dialog_layout, container, false);
        ok = (Button)root.findViewById(R.id.ok_password_button);
        cancel = (Button)root.findViewById(R.id.cancel_password_button);
        password = (EditText)root.findViewById(R.id.password);
        inputLayout = ( android.support.design.widget.TextInputLayout)root.findViewById(R.id.password_input);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok_password_button:
                inputLayout.setError(null);
                boolean result = callback.checkPassword(password.getText().toString());
                if(result)
                    dismiss();
                else
                    inputLayout.setError("Неверный пароль");
                break;
            case R.id.cancel_password_button:
                dismiss();
                break;
        }
    }

    public interface Callback{
        boolean checkPassword(String password);
    }
}
