package hcmut.exchanger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PPQ on 4/7/17.
 */

public class Controller {

    public Controller(MainActivity parentActivity,
                      RateTab parentRateTab,
                      ExchangerTab parentExchangerTab,
                      Model mainModel) {
        super();
        this.parentActivity = parentActivity;
        this.mainModel = mainModel;
        this.parentRateTab = parentRateTab;
        this.parentExchangerTab = parentExchangerTab;

    }

    public void requestAPI () {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(this.parentActivity.getString(R.string.url))
                .build();
        client.newCall(request).enqueue(
                new ControllerCallback()
        );
    }
    private class ControllerCallback implements Callback {

        @Override
        public void onFailure(Call call, IOException e) {}

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responseXml = response.body().string();
            Controller.this.mainModel.updatePref(responseXml);

            // UI updating program must run on UIThread
            Controller.this.parentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Handle UI here
                    Controller.this.parentRateTab.updateRateTable();
                }
            });

        }
    }

    private MainActivity parentActivity;
    private RateTab parentRateTab;
    private ExchangerTab parentExchangerTab;
    private Model mainModel;
}


