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
    }
    private Intent createResult(){
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        data.putExtras(bundle);
        return data;
    }
}
