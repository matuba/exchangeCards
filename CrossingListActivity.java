package exchangecards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.ArrayList;

import exchangecards.app.R;

/**
 * Created by shio_ito on 2014/03/17.
 */
public class CrossingListActivity extends Activity {
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crossing_list);

        gridView = (GridView)findViewById(R.id.gridView);
        gridView.setNumColumns(2);

        ExchangeCards exchangeCards = ExchangeCards.read(this);
        List<String> lstStr = new ArrayList<String>();
        lstStr.add( "交換日時");
        lstStr.add( "お名前");

        for(ExchangeCard card : exchangeCards.cards){
            lstStr.add( card.time.substring(0,4) + "/" + card.time.substring(4,6) + "/" + card.time.substring(6,8));
            lstStr.add( card.name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, lstStr);
        gridView.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.back, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
