package application.service;

import application.entity.GCurveModel;
import application.model.GCurveJson;
import application.service.impl.GCurveServices;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Configurable
public class MoexService {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final long TIMEOUT = 1000 * 60;

    private String MOEXUrl = "https://iss.moex.com/iss/engines/stock/zcyc.jsonp";
    private String firstUrlParameters = "?iss.only=params&date=";
    private String secondUrlParameters = "&iss.meta=off&lang=RU";

    private CloseableHttpClient client;
    private CloseableHttpResponse response;
    private HttpGet request;

    private StringBuilder jsonResultBuilder;
    private StringBuilder urlBuilder;

    private Gson gson;
    private GCurveJson gCurveModel;
    private List<String> result;

    private boolean stopThread = false;

    @Autowired
    private GCurveServices services;

    public MoexService() {
        gson = new Gson();
        result = new ArrayList<>();
        urlBuilder = new StringBuilder();
        jsonResultBuilder = new StringBuilder();
    }

    public void run() {
        Thread moexThread = new Thread(() -> {
            int counter = 0;
            int maxCounter = 10;
            Date currentDate;

            System.out.println("start tread");

            while (!stopThread) {
                Calendar rightNow = Calendar.getInstance();
                currentDate = rightNow.getTime();

                System.out.println("HttpClients");
                client = HttpClients.createDefault();
                request = new HttpGet(buildUrl(dateFormat.format(currentDate)));
                response = null;

                jsonResultBuilder.setLength(0);

                try {
                    response = client.execute(request);
                    int status = response.getStatusLine().getStatusCode();

                    if (status >= 200 && status < 300) {
                        System.out.println("parseData");
                        parseData();
                    } else {
                        System.out.println("Unexpected response status: " + status);
                    }

                    System.out.println("Parse json");
                    if (jsonResultBuilder.length() > 0) {
                        gCurveModel = gson.fromJson(String.valueOf(jsonResultBuilder), GCurveJson.class);
                        System.out.println(gCurveModel.getParams());

                        GCurveModel result1 = services.findCurveByDate(dateFormat.format(currentDate));
                        if (result1 == null) {
                            GCurveModel curve = setCurveData();
                            services.addCurve(curve);
                        } else {
                            GCurveModel curve = setCurveData();
                            services.updateCurve(curve);
                        }
                    }
                } catch (IOException | UnsupportedOperationException e) {
                    stopThread = true;
                    e.printStackTrace();
                } finally {
                    if (null != response) {
                        try {
                            System.out.println("clear all");
                            response.close();
                            client.close();
                            jsonResultBuilder.setLength(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                try {
                    System.out.println("Wait " + (TIMEOUT / 10000) + " minutes");
                    Thread.sleep(TIMEOUT);
                    System.out.println("Time is end");
                } catch (InterruptedException e) {
                    stopThread = true;
                    e.printStackTrace();
                }

                if (counter > maxCounter) {
                    Thread.currentThread().interrupt();
                    stopThread = true;

                    System.out.println("Command: interrupt service");
                }

                counter++;
            }

            if (stopThread) {
                System.out.println("Service is interrupt");
            }
        });

        moexThread.start();
    }

    private GCurveModel setCurveData() {
        System.out.println("setCurveData()");
        GCurveModel curve = new GCurveModel();
        List<Object> objectsList = gCurveModel.getParams().getData().get(0);
        List<String> columns = gCurveModel.getParams().getColumns();

        for (int i = 0; i < columns.size(); i++) {
            fillCurveData(curve, columns.get(i), objectsList.get(i));
        }

        columns.clear();
        objectsList.clear();

        return curve;
    }

    /**
     * Check value on null
     *
     * @param value
     * @return 0.0 if value is null, or value if is not
     */
    private double checkDoubleOnNull(Object value) {
        return value == null ? 0.0 : (double) value;
    }

    /**
     * Check value on null
     *
     * @param value
     * @return empty string if value is null, or value if is not
     */
    private String checkStringOnNull(Object value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Fill GCurveModel model
     *
     * @param curve - GCurveModel model
     * @param columnName - json object key
     * @param value - json object value
     */
    private void fillCurveData(GCurveModel curve, String columnName, Object value) {
        System.out.println("fillCurveData()");
        switch (columnName) {
            case "tradedate":
                curve.setTradedate(checkStringOnNull(value));
                break;
            case "tradetime":
                curve.setTradetime(checkStringOnNull(value));
                break;
            case "B1":
                curve.setB1(checkDoubleOnNull(value));
                break;
            case "B2":
                curve.setB2(checkDoubleOnNull(value));
                break;
            case "B3":
                curve.setB3(checkDoubleOnNull(value));
                break;
            case "T1":
                curve.setT1(checkDoubleOnNull(value));
                break;
            case "G1":
                curve.setG1(checkDoubleOnNull(value));
                break;
            case "G2":
                curve.setG2(checkDoubleOnNull(value));
                break;
            case "G3":
                curve.setG3(checkDoubleOnNull(value));
                break;
            case "G4":
                curve.setG4(checkDoubleOnNull(value));
                break;
            case "G5":
                curve.setG5(checkDoubleOnNull(value));
                break;
            case "G6":
                curve.setG6(checkDoubleOnNull(value));
                break;
            case "G7":
                curve.setG7(checkDoubleOnNull(value));
                break;
            case "G8":
                curve.setG8(checkDoubleOnNull(value));
                break;
            case "G9":
                curve.setG9(checkDoubleOnNull(value));
                break;
        }
    }

    /**
     * Parse HTTP result
     */
    private void parseData() throws IOException {
        String line;
        System.out.println("parseData()");
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        while ((line = br.readLine()) != null) {
            jsonResultBuilder.append(line);
        }

        line = null;
    }

    /**
     * Build url connection for current date
     *
     * @return url connection string
     */
    private String buildUrl(String date) {
        System.out.println("buildUrl()");
        urlBuilder.setLength(0);
        urlBuilder.append(MOEXUrl);
        urlBuilder.append(firstUrlParameters);
        urlBuilder.append(date);
        //urlBuilder.append("2017-09-01");
        urlBuilder.append(secondUrlParameters);

        System.out.println("buildUrl: URL is " + urlBuilder);

        return urlBuilder.toString();
    }
}
