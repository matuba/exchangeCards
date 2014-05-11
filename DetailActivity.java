package exchangecards;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeMap;

import exchangecards.app.R;


/**
 * Created by shio_ito on 2014/03/16.
 */
public class DetailActivity extends Activity implements OnClickListener {
    public static String[] recordNames = new String []{
            "post",
            "managerial",
            "name",
            "companyname",
            "address1",
            "address2",
            "tel",
            "email",
            "twitter"};
    private TreeMap< String,TextView> textViewExchangeCardRecords = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_detail);
        initControls();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        for(String recordName : recordNames){
            textViewExchangeCardRecords.get(recordName).setText(bundle.getString(recordName, ""));
        }
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
    }

    private void initControls(){
        textViewExchangeCardRecords = new TreeMap<String, TextView>();
        textViewExchangeCardRecords.put(recordNames[0], (TextView)findViewById(R.id.textPost));
        textViewExchangeCardRecords.put(recordNames[1],(TextView)findViewById(R.id.textManagerial));
        textViewExchangeCardRecords.put(recordNames[2],(TextView)findViewById(R.id.textName));
        textViewExchangeCardRecords.put(recordNames[3],(TextView)findViewById(R.id.textCompanyName));
        textViewExchangeCardRecords.put(recordNames[4],(TextView)findViewById(R.id.textAddress1));
        textViewExchangeCardRecords.put(recordNames[5],(TextView)findViewById(R.id.textAddress2));
        textViewExchangeCardRecords.put(recordNames[6],(TextView)findViewById(R.id.textTEL));
        textViewExchangeCardRecords.put(recordNames[7],(TextView)findViewById(R.id.textEmail));
        textViewExchangeCardRecords.put(recordNames[8],(TextView)findViewById(R.id.textTwitter));
    }
    private Intent createResult(){
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        data.putExtras(bundle);
        return data;
    }
}
