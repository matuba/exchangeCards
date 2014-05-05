package exchangecards;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.view.View;
import android.view.View.OnClickListener;
import exchangecards.app.R;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Intent;
import android.text.format.Time;


/**
 * Created by shio_ito on 2014/03/16.
 */
public class SettingActivity extends Activity implements OnClickListener {
    Button buttonOK = null;
    Button buttonCancel = null;

    EditText editTextPost = null;
    EditText editTextManagerial = null;
    EditText editTextName = null;
    EditText editTextCompanyName = null;
    EditText editTextAddress1 = null;
    EditText editTextAddress2 = null;
    EditText editTextTEL = null;
    EditText editTextEmail = null;
    EditText editTextTwitter = null;
    EditText editTextFacebook = null;

    Spinner spinnerCrossing = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.setting_main);
        initControls();
        loadPreference( PreferenceManager.getDefaultSharedPreferences(this));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.back, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setResult(RESULT_CANCELED, null);
        finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {
        if(v == buttonOK) {
            commitPreference( PreferenceManager.getDefaultSharedPreferences(this));
            setResult(RESULT_OK, createResult());
            finish();
        }
        else if(v == buttonCancel){
            Intent data = new Intent();
            data.putExtra("key.canceledData", "キャンセル");
            setResult(RESULT_CANCELED, data);
            finish();
        }
    }

    private void initControls(){
        buttonOK = (Button)findViewById(R.id.buttonOK);
        buttonCancel = (Button)findViewById(R.id.buttonCancel);
        editTextPost = (EditText)findViewById(R.id.editTextPost);
        editTextManagerial = (EditText)findViewById(R.id.editTextManagerial);
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextCompanyName = (EditText)findViewById(R.id.editTextCompanyName);
        editTextAddress1 = (EditText)findViewById(R.id.editTextAddress1);
        editTextAddress2 = (EditText)findViewById(R.id.editTextAddress2);
        editTextTEL = (EditText)findViewById(R.id.editTextTEL);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        spinnerCrossing = (Spinner)findViewById(R.id.spinnerCrossing);
        editTextTwitter = (EditText)findViewById(R.id.editTextTwitter);

        buttonOK.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        ArrayList<String> list = new ArrayList<String>();
        list.add("しない");
        list.add("10分毎に確認");
        list.add("20分毎に確認");
        list.add("30分毎に確認");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCrossing);
        spinner.setAdapter(adapter);

        Log.d("Success", "initControls");
    }
    private Intent createResult(){
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("post", editTextPost.getText().toString());
        bundle.putString("managerial", editTextManagerial.getText().toString());
        bundle.putString("name", editTextName.getText().toString());
        bundle.putString("companyname", editTextCompanyName.getText().toString());
        bundle.putString("address1", editTextAddress1.getText().toString());
        bundle.putString("address2", editTextAddress2.getText().toString());
        bundle.putString("tel", editTextTEL.getText().toString());
        bundle.putString("email", editTextEmail.getText().toString());
        bundle.putInt("crossing", spinnerCrossing.getSelectedItemPosition());
        bundle.putString("twitter", editTextTwitter.getText().toString());
        data.putExtras(bundle);
        return data;
    }
    private void commitPreference(SharedPreferences prefs) {
        Time time = new Time("Asia/Tokyo");
        time.setToNow();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("post", editTextPost.getText().toString());
        editor.putString("managerial", editTextManagerial.getText().toString());
        editor.putString("name", editTextName.getText().toString());
        editor.putString("companyname", editTextCompanyName.getText().toString());
        editor.putString("address1", editTextAddress1.getText().toString());
        editor.putString("address2", editTextAddress2.getText().toString());
        editor.putString("tel", editTextTEL.getText().toString());
        editor.putString("email", editTextEmail.getText().toString());
        editor.putInt("crossing", spinnerCrossing.getSelectedItemPosition());
        editor.putString("twitter", editTextTwitter.getText().toString());

        String now = String.format("%04d%02d%02d%02d%02d%02d", time.year, (time.month + 1), time.monthDay, time.hour, time.minute, time.second);
        editor.putString("time", now);
        editor.commit();
        Log.d("Success", "commitPreference");
    }
    private void loadPreference(SharedPreferences prefs) {
        editTextPost.setText(prefs.getString("post", ""));
        editTextManagerial.setText(prefs.getString("managerial", ""));
        editTextName.setText(prefs.getString("name", ""));
        editTextCompanyName.setText(prefs.getString("companyname", ""));
        editTextAddress1.setText(prefs.getString("address1", ""));
        editTextAddress2.setText(prefs.getString("address2", ""));
        editTextTEL.setText(prefs.getString("tel", ""));
        editTextEmail.setText(prefs.getString("email", ""));
        spinnerCrossing.setSelection(prefs.getInt("crossing", 0));
        editTextTwitter.setText(prefs.getString("twitter", ""));
        Log.d("Success", "loadPreference");
    }

}
