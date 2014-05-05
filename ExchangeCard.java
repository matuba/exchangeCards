package exchangecards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by shio_ito on 2014/05/05.
 */
public class ExchangeCard implements Serializable
{
    private static final long serialVersionUID = 0;

    public String post = new String("");
    public String managerial = new String("");
    public String name = new String("");
    public String companyname = new String("");
    public String address1 = new String("");
    public String address2 = new String("");
    public String tel = new String("");
    public String email = new String("");
    public ArrayList<String> macAddresses = new ArrayList<String>();
    public String twitter = new String("");
    public String facebook = new String("");
    public String time = new String("");
    public int crossingNum = 0;

    public ExchangeCard(){}
    public ExchangeCard(String[] androidBeamData){
        if(androidBeamData[0].equals(Long.toString(serialVersionUID))) {
            post = androidBeamData[1];
            managerial = androidBeamData[2];
            name = androidBeamData[3];
            companyname = androidBeamData[4];
            address1 = androidBeamData[5];
            address2 = androidBeamData[6];
            tel = androidBeamData[7];
            email = androidBeamData[8];
            macAddresses = new ArrayList<String>();
            macAddresses.add(androidBeamData[9]);
            twitter = androidBeamData[10];
            facebook = androidBeamData[11];
            time = androidBeamData[12];
        }
    }

    public boolean isMAC(String macA) {
        for(String macB : macAddresses){
            if(macA.equals(macB)){
                return true;
            }
        }
        return false;
    }

    public String[] packingAndroidBeamData(){
        String[] androidBeamData = new String[13];
        androidBeamData[0] = Long.toString(serialVersionUID);
        androidBeamData[1] = post;
        androidBeamData[2] = managerial;
        androidBeamData[3] = name;
        androidBeamData[4] = companyname;
        androidBeamData[5] = address1;
        androidBeamData[6] = address2;
        androidBeamData[7] = tel;
        androidBeamData[8] = email;
        androidBeamData[9] = macAddresses.get(0);
        androidBeamData[10] = twitter;
        androidBeamData[11] = facebook;
        androidBeamData[12] = time;
        return androidBeamData;
    }



}
