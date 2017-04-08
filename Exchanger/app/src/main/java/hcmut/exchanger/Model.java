package hcmut.exchanger;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;

/**
 * Created by PPQ on 4/8/17.
 */

public class Model {

    public Model(AppCompatActivity parentActivity) {
        this.parentActivity = parentActivity;
       // this.currencyDict = new HashMap<>();
    }

    public Map<String, Currency> getCurrencyDict() {
        SharedPreferences prefs = this.parentActivity.getSharedPreferences(
                this.parentActivity.getString(R.string.app_name),
                this.parentActivity.MODE_PRIVATE);
        String xml = prefs.getString(this.parentActivity.getString(R.string.key), "");
        Map<String, Currency> ret = new HashMap<>();
        try {

            String nodeName, currencyCode;
            String currencyCodeAttr = this.parentActivity.getString(R.string.currencyCodeAttr);
            String buyAttr = this.parentActivity.getString(R.string.buyAttr);
            String transferAttr = this.parentActivity.getString(R.string.transferAttr);
            String sellAttr = this.parentActivity.getString(R.string.sellAttr);
            String currencyTag = this.parentActivity.getString(R.string.currencyTag);
            double buyPrice;
            double transferPrice;
            double sellPrice;
            int eventType = -1;
            xml = xml.substring(1);

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new StringReader(xml));

            while(eventType != XmlPullParser.END_DOCUMENT) {

                eventType=parser.next();
                switch(eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        nodeName=parser.getName();
                        if (nodeName.equals(currencyTag)) {

                            currencyCode = parser.getAttributeValue(null, currencyCodeAttr);
                            buyPrice = Double.parseDouble(parser.getAttributeValue(null, buyAttr));
                            transferPrice = Double.parseDouble(parser.getAttributeValue(null, transferAttr));
                            sellPrice = Double.parseDouble(parser.getAttributeValue(null, sellAttr));
                            ret.put(currencyCode,
                                    new Currency(currencyCode, buyPrice, transferPrice, sellPrice));
                        }
                        break;
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void updatePref(String data) {
        SharedPreferences.Editor prefEditor = this.parentActivity.getSharedPreferences(
                this.parentActivity.getString(R.string.app_name),
                Context.MODE_PRIVATE).edit();
        prefEditor.putString(this.parentActivity.getString(R.string.key), data);
        prefEditor.commit();

    }

    private AppCompatActivity parentActivity;

    public static class Currency {

        public Currency(String currencyCode, double buy, double tranfer, double sell) {
            this.currencyCode = currencyCode;
            this.buyPrice = buy;
            this.transferPrice = tranfer;
            this.sellPrice = sell;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }
        public double getBuyPrice() {
            return  buyPrice;
        }
        public double getTranferPrice() {
            return transferPrice;
        }
        public double getSellPrice() {
            return sellPrice;
        }

        private String currencyCode;
        private double buyPrice;
        private double transferPrice;
        private double sellPrice;
    }
}
