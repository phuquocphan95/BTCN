package hcmut.exchanger;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by PPQ on 4/7/17.
 */

public class RateTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_rate, container, false);
        this.rootView = rootView;
        this.parentActivity = (MainActivity) getActivity();
        return rootView;
    }

    @Override
    public  void onStart() {
        super.onStart();
        this.parentActivity.getController().requestAPI();
    }

    public void updateRateTable() {
        Map<String, Model.Currency> currencyDict = this.parentActivity.getModel().getCurrencyDict();
        TableLayout table = (TableLayout)this.rootView.findViewById(R.id.TableLayout1);
        TableRow tableRow;
        TextView textView;
        Model.Currency currency;
        int i = 1;
        for (Map.Entry<String, Model.Currency> entry:currencyDict.entrySet()) {
            currency = entry.getValue();
            tableRow = (TableRow) table.getChildAt(i++);

            textView = (TextView) tableRow.getChildAt(0);
            textView.setText(currency.getCurrencyCode());

            textView = (TextView) tableRow.getChildAt(1);
            textView.setText(Double.toString(currency.getBuyPrice()));

            textView = (TextView) tableRow.getChildAt(2);
            textView.setText(Double.toString(currency.getSellPrice()));
        }
    }

    private View rootView;
    private MainActivity parentActivity;

}

