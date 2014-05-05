package exchangecards;

import android.app.Activity;
import android.util.Log;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by shio_ito on 2014/03/30.
 */
public class ExchangeCards  implements Serializable {
    public ArrayList<ExchangeCard> exchangeCards;

    public static boolean isMAC(String macA, ExchangeCards data) {
        for(ExchangeCard exchangeCard : data.exchangeCards){
            if( exchangeCard.isMAC(macA)){
                return true;
            }
        }
        return false;
    }
    public static boolean write(Activity activity, ExchangeCards data) {
        try {
            FileOutputStream fos = activity.openFileOutput("exchangecards.dat", activity.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            return true;
        } catch (Exception e) {
            Log.d("write", e.getMessage());
        }
        return false;
    }
    public static ExchangeCards read(Activity activity){
        try {
            FileInputStream fis = activity.openFileInput("exchangecards.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ExchangeCards data = (ExchangeCards) ois.readObject();
            ois.close();
            return data;
        } catch (Exception e) {
            Log.d("read", e.getMessage());
        }
        return null;
    }
}