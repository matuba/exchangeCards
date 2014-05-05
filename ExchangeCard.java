package exchangecards;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by shio_ito on 2014/05/05.
 */
public class ExchangeCard
{
    public String post;
    public String managerial;
    public String name;
    public String companyname;
    public String address1;
    public String address2;
    public String tel;
    public String email;
    public String twitter;
    public String facebook;
    public String time;
    public int crossingNum;
    public ArrayList<String> macAddresses;
    public boolean isMAC(String macA) {
        for(String macB : macAddresses){
            if(macA.equals(macB)){
                return true;
            }
        }
        return false;
    }

    public String[] packingAndroidBeamData(TreeMap packingData){

    }

    public void setData(String[] elements){

    }

}
