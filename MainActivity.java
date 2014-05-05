package exchangecards;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.Intent;
import android.view.MotionEvent;
import android.nfc.NfcAdapter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.util.Log;
import android.os.Parcelable;
import android.app.AlertDialog;
import android.content.DialogInterface;

import exchangecards.app.R;

import bluetooth.BluetoothDeviceFinder;
import bluetooth.BluetoothService;
import bluetooth.BluetoothUtil;
import bluetooth.NdefExchangeCardCallback;

import java.util.TreeMap;

public class MainActivity extends ActionBarActivity {
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

    private static int SETTING_ACTIVITY = 9999;
    private ExchangeCards exchangeCards;
    private TreeMap< String,TextView> textViewExchangeCardRecords = null;
    private TreeMap< String,CheckBox> checkBoxExchangeCardRecords = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();
        loadPreference(PreferenceManager.getDefaultSharedPreferences(this));
        BluetoothUtil.setBluetoothDiscoverable(this, 0);

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        nfcAdapter.setNdefPushMessageCallback(
                new NdefExchangeCardCallback(createAndroidBeamData()),
                this);
        exchangeCards = ExchangeCards.read(this);
    }
    @Override
    protected void onStop() {
        BluetoothService.stop(getApplicationContext());
        super.onStop();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent( this, SettingActivity.class );
            startActivityForResult(intent, SETTING_ACTIVITY);
            return true;
        }
        if (id == R.id.action_crossing) {
            Intent intent = new Intent( this, CrossingListActivity.class );
            startActivityForResult(intent, 1000);
            return true;
        }
        if (id == R.id.action_bluetooth) {
            BluetoothDeviceFinder finder = new BluetoothDeviceFinder();
            finder.startDiscovery(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SETTING_ACTIVITY && resultCode == RESULT_OK) {
            setSettingValues(data);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (!NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            return;
        }

        ExchangeCard recieveExchageCard = receiveExchangeCard(intent);
        ExchangeCards.isMAC(recieveExchageCard.macAddresses, exchangeCards);
        exchangeCards.cards.add(recieveExchageCard);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(recieveExchageCard.name + "さんから名刺を頂きました");
        alertDialog.setMessage("端末を一度離して名刺を送り返しましょう");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.d("AlertDialog", "Positive which :" + which);
            }
        });
        alertDialog.show();
    }

    private void initControls() {
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
        checkBoxExchangeCardRecords = new TreeMap<String, CheckBox>();
        checkBoxExchangeCardRecords.put(recordNames[0],(CheckBox)findViewById(R.id.checkBoxPost));
        checkBoxExchangeCardRecords.put(recordNames[1],(CheckBox)findViewById(R.id.checkBoxManagerial));
        checkBoxExchangeCardRecords.put(recordNames[2],(CheckBox)findViewById(R.id.checkBoxName));
        checkBoxExchangeCardRecords.put(recordNames[3],(CheckBox)findViewById(R.id.checkBoxCompanyName));
        checkBoxExchangeCardRecords.put(recordNames[4],(CheckBox)findViewById(R.id.checkBoxAddress));
        checkBoxExchangeCardRecords.put(recordNames[5],(CheckBox)findViewById(R.id.checkBoxTEL));
        checkBoxExchangeCardRecords.put(recordNames[6],(CheckBox)findViewById(R.id.checkBoxEmail));
        checkBoxExchangeCardRecords.put(recordNames[7],(CheckBox)findViewById(R.id.checkBoxTwitter));
        for( TreeMap.Entry<String, CheckBox> e : checkBoxExchangeCardRecords.entrySet()){
            e.getValue().setChecked(true);
        }
    }
    private void loadPreference(SharedPreferences prefs) {
        for(String recordName : recordNames){
            textViewExchangeCardRecords.get(recordName).setText(prefs.getString(recordName, ""));
        }
        //Bluetooth を探す
        int crossing = prefs.getInt("crossing", 0);
        BluetoothService.stop(getApplicationContext());
        if(crossing > 0) {
            BluetoothService.setTriggerAtmills((1000 * 60) * crossing);
            BluetoothService.start(getApplicationContext());
        }
    }
    private void setSettingValues(Intent data){
        Bundle bundle = data.getExtras();
        for(String recordName : recordNames){
            textViewExchangeCardRecords.get(recordName).setText(bundle.getString(recordName, ""));
        }
        int crossing = bundle.getInt("crossing", 0);
        BluetoothService.stop(getApplicationContext());
        if(crossing > 0) {
            BluetoothService.setTriggerAtmills((1000 * 60) * crossing);
            BluetoothService.start(getApplicationContext());
        }
    }
    private String getTextView(String key, TreeMap< String,TextView> textView, TreeMap< String,CheckBox> checkBox){
        if(checkBox.get(key).isChecked()){
            return textView.get(key).getText().toString();
        }
        return "";
    }
    private ExchangeCard receiveExchangeCard(Intent intent){
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        NdefRecord[] ndefRecords = msg.getRecords();
        String[] ndefStrings = new String[ndefRecords.length];
        for (int i = 0; i < ndefRecords.length; i++) {
            ndefStrings[i] = new String(ndefRecords[i].getPayload());
            Log.d("Android beam", ndefStrings[i]);
        }
        return new ExchangeCard(ndefStrings);
    }
    private String[] createAndroidBeamData() {
        ExchangeCard androidBeamData = new ExchangeCard();
        androidBeamData.post = getTextView( "post", textViewExchangeCardRecords, checkBoxExchangeCardRecords);
        androidBeamData.managerial = getTextView("managerial", textViewExchangeCardRecords, checkBoxExchangeCardRecords);
        androidBeamData.name = getTextView("", textViewExchangeCardRecords, checkBoxExchangeCardRecords);
        androidBeamData.companyname = getTextView("name", textViewExchangeCardRecords, checkBoxExchangeCardRecords);
        androidBeamData.address1 = getTextView("address1", textViewExchangeCardRecords, checkBoxExchangeCardRecords);
        androidBeamData.address2 = getTextView("address2", textViewExchangeCardRecords, checkBoxExchangeCardRecords);
        androidBeamData.tel = getTextView("tel", textViewExchangeCardRecords, checkBoxExchangeCardRecords);
        androidBeamData.email = getTextView("email", textViewExchangeCardRecords, checkBoxExchangeCardRecords);
        androidBeamData.twitter = getTextView("twitter", textViewExchangeCardRecords, checkBoxExchangeCardRecords);
        androidBeamData.macAddresses.add(BluetoothUtil.getTerminalMacAddress());
        return androidBeamData.packingAndroidBeamData();
    }

/*
    private boolean exchangeCard(){
        Intent intent = getIntent();
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra( NfcAdapter.EXTRA_NDEF_MESSAGES);
        if(rawMsgs == null)
            return false;
        for(NdefMessage msg : (NdefMessage[])rawMsgs){
            for(NdefRecord ndefRecord : msg.getRecords()){
                String recordString = new String(ndefRecord.getPayload());
                Log.d( "Android beam", recordString);
            }
        }
        return true;
    }
*/






    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TouchEvent", "X:" + event.getX() + ",Y:" + event.getY());
        return true;
    }
}
