package hcmut.exchanger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by PPQ on 4/7/17.
 */

public class ExchangerTab extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_exchanger, container, false);
        this.rootView = rootView;
        this.parentActivity = (MainActivity) getActivity();
        this.fromCode = "USD";
        this.toCode = "VND";
        return rootView;
    }

    @Override
    public  void  onStart() {
        super.onStart();
        Map<String, Model.Currency> currencyDict = this.parentActivity.getModel().getCurrencyDict();
        Spinner spinnerFrom = (Spinner) this.rootView.findViewById(R.id.spinnerFrom);
        Spinner spinnerTo = (Spinner) this.rootView.findViewById(R.id.spinnerTo);
        List<String> currency = new ArrayList<>();

        for (Map.Entry<String, Model.Currency> entry: currencyDict.entrySet()) {
            currency.add(entry.getValue().getCurrencyCode());

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this.parentActivity,
                android.R.layout.simple_spinner_dropdown_item,
                currency);
        AdapterView.OnItemSelectedListener listener =
                new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String code = parent.getItemAtPosition(position).toString();
                Spinner spinner = (Spinner) parent;
                switch (spinner.getId()) {
                    case R.id.spinnerFrom:
                        fromCode = code;
                        break;
                    default:
                        toCode = code;
                        break;
                }

            }

            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        };

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(dataAdapter);
        spinnerFrom.setOnItemSelectedListener(listener);
        spinnerFrom.setSelection(dataAdapter.getPosition("USD"));
        spinnerTo.setAdapter(dataAdapter);
        spinnerTo.setOnItemSelectedListener(listener);
        spinnerTo.setSelection(dataAdapter.getPosition("VND"));
        Button btnExchange = (Button) this.rootView.findViewById(R.id.btnExchange);
        final TextView resultTextView = (TextView) this.rootView.findViewById(R.id.textViewTo);

        btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtFrom = (EditText)rootView.findViewById(R.id.editTextFrom);
                double amount = Double.parseDouble(edtFrom.getText().toString());
                double newAmount = ExchangerTab.this.parentActivity.getController().exchangeMoney(
                        ExchangerTab.this.fromCode,
                        ExchangerTab.this.toCode,
                        amount);
                resultTextView.setText(Double.toString(newAmount));
            }
        });
    }


    private View rootView;
    private MainActivity parentActivity;
    private String fromCode;
    private String toCode;

}
