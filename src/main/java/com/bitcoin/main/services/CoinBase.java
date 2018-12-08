package com.bitcoin.main.services;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Configurable
@Component
public interface CoinBase {
    //interface to expose method for restfulcall
    public Map<Date, Double> getBitcoinPrices();

    List<Double> getLastWeekPrices();

    List<Double> getLastMonthPrices();

    List<Double> getLastYearPrices();

    Double getRollingAveragebwDates(Date startDate, Date endDate);

    String predictDecision(Date customFromDate, Date customEndDate);

}
