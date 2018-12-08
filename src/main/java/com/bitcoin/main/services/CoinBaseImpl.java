package com.bitcoin.main.services;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.Override;
import java.net.URL;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class CoinBaseImpl implements CoinBase{
    //logic to implement for interface calls

    private final static String SOURCE_URL="https://www.coinbase.com/api/v2/prices/BTC-USD/historic?period=all";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Map<Date, Double> prices = new HashMap<Date, Double>();
    private List<Double> priceList = null;
    double total = 0.00;

    public CoinBaseImpl(){
        getBitcoinPrices();
    }

    @Override
    public Map<Date, Double> getBitcoinPrices() {
        try{
        URL url = new URL(SOURCE_URL);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        if(responsecode != 200)
            System.out.println("couldn't receive proper response");
        else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            Scanner sc = new Scanner(url.openStream());
            String output = "";
            StringBuffer parentData = new StringBuffer();
            while((output = reader.readLine()) != null)
            {
                parentData = parentData.append(output);
            }

            System.out.println("\nJSON data in string format");
            System.out.println(parentData.toString());
            prices = getPrices(parentData.toString());
            sc.close();
            reader.close();
            conn.disconnect();
        }
        } catch(Exception e) {
            System.out.println(e);
        }
        return prices;
    }

    @Override
    public List<Double> getLastWeekPrices()
    {
        Date endDate = new Date();
        Calendar c = Calendar.getInstance();
        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i - 7);
        Date startDate = c.getTime();

        System.out.println("Doing query for dates startDate = "+ startDate + " End date = "+ endDate);

        return getPriceDatabwDate(startDate ,endDate);

    }

    @Override
    public List<Double> getLastMonthPrices()
    {
        Date endDate = new Date();

        Calendar today = Calendar.getInstance();
        today.add(Calendar.MONTH, -1);
        Date startDate = today.getTime();
        System.out.println("Date bw startDate = "+ startDate.getTime() + " End date = "+ endDate.getTime());

        return getPriceDatabwDate(startDate ,endDate);

    }

    @Override
    public List<Double> getLastYearPrices()
    {
        Date endDate = new Date();

        Calendar today = Calendar.getInstance();
        today.add(Calendar.YEAR, -1);
        Date startDate = today.getTime();
        System.out.println("Date bw startDate = "+ startDate.getTime() + " End date = "+ endDate.getTime());

        return getPriceDatabwDate(startDate ,endDate);

    }

    @Override
    public Double getRollingAveragebwDates(Date startDate, Date endDate) {
        Double rollingAveragePrice = null;
        List<Double> priceList = getPriceDatabwDate(startDate, endDate);

        priceList.forEach(k->{
            total = k.doubleValue() + total;
        });
        double average = total/priceList.size();
        return Double.valueOf(average);
    }

    @Override
    public String predictDecision(Date customFromDate, Date customEndDate) {
        List<Double> priceList = getCustomDatesorted(customFromDate, customEndDate);
        Double avePriceOfcustDates = getRollingAveragebwDates(customFromDate, customEndDate);
        String predictDecision = "HOLD";
        List<Double> Less_than=priceList.stream().filter(k->k<avePriceOfcustDates).collect(Collectors.toList());
        List<Double> greater_than = priceList.stream().filter(k->k>avePriceOfcustDates).collect(Collectors.toList());
        if(Less_than.size()>greater_than.size()) {
            predictDecision = "SELL";
        } else {
            predictDecision = "BUY";
        }
        return predictDecision;
    }


    private List<Double> getPriceDatabwDate(Date startDate, Date endDate) {
        priceList = null;
        prices.forEach((k,v)->{
            Date priceDate = (Date) k;
            Double priceValue = (Double)v;
            if(priceDate.compareTo(startDate)>=0 && priceDate.compareTo(endDate)<=0){
                if (priceList == null) {
                    priceList = new ArrayList<Double>();
                    priceList.add(priceValue);
                } else
                    priceList.add(priceValue);
            }

        });

        return priceList;
    }

    private List<Double> getCustomDatesorted(Date startDate, Date endDate) {
        Map<Date, Double> sortedPrices = prices.entrySet().stream()
                .sorted((e1,e2) -> e1.getKey().compareTo(e2.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        sortedPrices.forEach((k,v) -> {
            Date priceDate = (Date) k;
            Double priceValue = (Double)v;
            if(priceDate.compareTo(startDate)>=0 && priceDate.compareTo(endDate)<=0){
                if (priceList == null) {
                    priceList = new ArrayList<Double>();
                    priceList.add(priceValue);
                } else
                    priceList.add(priceValue);
            }
        });
        return priceList;
    }


    private Map<Date, Double> getPrices(String data) throws Exception {
        JSONParser parse = new JSONParser();
        JSONObject jobj = (JSONObject)parse.parse(data);

        JSONObject dta =  (JSONObject) jobj.get("data");

        String base = (String) dta.get("base");
        System.out.println("base = "+ base);
        String currency = (String) dta.get("currency");
        System.out.println("currency = "+ currency);
        JSONArray pricelist = (JSONArray) dta.get("prices");


        pricelist.stream().forEach(k->{
            JSONObject object = (JSONObject) k;
            Double price = Double.parseDouble((String) object.get("price"));
            Date priceDate = new Date();
            try {
                priceDate = dateFormat.parse((String) object.get("time"));
            } catch(ParseException e) {

            }
            prices.put(priceDate, price);
        });
        return prices;
    }
}
