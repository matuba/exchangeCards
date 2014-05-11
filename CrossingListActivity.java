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
import android.widget.AdapterView;
import android.view.View;

import java.util.List;
import java.util.ArrayList;

import exchangecards.app.R;

/**
 * Created by shio_ito on 2014/03/17.
 */
public class CrossingListActivity extends Activity implements AdapterView.OnItemClickListener{
    private GridView gridView;
    private ExchangeCards exchangeCards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crossing_list);

        gridView = (GridView)findViewById(R.id.gridView);
        gridView.setNumColumns(1);

        exchangeCards = ExchangeCards.read(this);
        List<String> lstStr = new ArrayList<String>();

        for(ExchangeCard card : exchangeCards.cards){
            StringBuilder gridViewText = new StringBuilder();
            gridViewText.append(card.time.substring(0,4));
            gridViewText.append("/");
            gridViewText.append(card.time.substring(4,6));
            gridViewText.append("/");
            gridViewText.append(card.time.substring(6, 8));

            gridViewText.append(" ");
            gridViewText.append(card.name);

            lstStr.add( new String(gridViewText));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, lstStr);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent( this, DetailActivity.class );
        intent.putExtra(DetailActivity.recordNames[0], exchangeCards.cards.get(position).post);
        intent.putExtra(DetailActivity.recordNames[1], exchangeCards.cards.get(position).managerial);
        intent.putExtra(DetailActivity.recordNames[2], exchangeCards.cards.get(position).name);
        intent.putExtra(DetailActivity.recordNames[3], exchangeCards.cards.get(position).companyname);
        intent.putExtra(DetailActivity.recordNames[4], exchangeCards.cards.get(position).address1);
        intent.putExtra(DetailActivity.recordNames[5], exchangeCards.cards.get(position).address2);
        intent.putExtra(DetailActivity.recordNames[5], exchangeCards.cards.get(position).tel);
        intent.putExtra(DetailActivity.recordNames[5], exchangeCards.cards.get(position).email);
        intent.putExtra(DetailActivity.recordNames[5], exchangeCards.cards.get(position).twitter);

        startActivity(intent);
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
