package ivan.osago.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.joda.time.DateTime;

import ivan.osago.R;
import ivan.osago.ui.password_dialog.PasswordDialog;

public class StartActivity extends AppCompatActivity implements PasswordDialog.Callback {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if(true){
            long time = System.currentTimeMillis()/1000;
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            long temp = preferences.getLong("time",-1);
            if(temp == -1){
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong("time",temp);
                editor.apply();
            } else {
                long dif = time - temp;
                if(dif > 3600){
                    Blockdialog dialog = new Blockdialog();
                    dialog.setCallback(new Blockdialog.Callback() {
                        @Override
                        public boolean checkPassword(String password) {
                            if(password.equals("123456789098654321")){
                                return true;
                            } else{
                                Intent intent = new Intent(getApplicationContext(), Block.class);
                                startActivity(intent);
                            }
                            return false;
                        }
                    });
                }
            }
        }
        settingsToolbar();
    }

    private void settingsToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("OSAGO");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                PasswordDialog dialog = new PasswordDialog();
                dialog.setCallback(this);
                dialog.show(getFragmentManager(),null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean checkPassword(String password) {
        if(password.equals("271012")){
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
