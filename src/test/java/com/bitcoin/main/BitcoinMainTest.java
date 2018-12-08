package com.bitcoin.main;

import com.bitcoin.main.services.CoinBase;
import com.bitcoin.main.services.CoinBaseImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)

public class BitcoinMainTest {

    @Autowired
    private CoinBase coinBase;

    @Configuration
    static class ContextConfiguration {

        @Bean
        public CoinBase coinBase()
        {
            CoinBase coinBase = new CoinBaseImpl();
            return coinBase;
        }
    }

    @Test
    public void testLastYearPrices() {
        List<Double> pricesList = coinBase.getLastYearPrices();
        int sumof = pricesList.stream().mapToInt(Double:: intValue).limit(10).sum();
        System.out.println("first 10 value...."+sumof);
        Assert.assertTrue(sumof==96118);
    }

    @Test
    public void testLastMonthPries() {
        List<Double> pricesList = coinBase.getLastMonthPrices();
        Optional<Double> maxValue = pricesList.stream().reduce(Double::max);
        System.out.println(maxValue.get());

        Assert.assertTrue(maxValue.get() == 6355.71);
    }

    @Test
    public void testLastweekPrices() {
        List<Double> priceList = coinBase.getLastWeekPrices();
        Assert.assertTrue(priceList.size()>0);
    }

    @Test
    public void testPrediction() throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = dateformat.parse("2011/01/01");
        Date endDate = dateformat.parse("2011/05/01");
        String prediction = coinBase.predictDecision(startDate, endDate);
        Assert.assertTrue( prediction.equals("SELL"));
    }

    @Test
    public void testRollingAverage() {
        Date endDate = new Date();
        Calendar customFrom = Calendar.getInstance();
        customFrom.set(Calendar.MONTH, -1);
        Date startDate = customFrom.getTime();
        Double value = coinBase.getRollingAveragebwDates(startDate, endDate);
        Assert.assertTrue(value.equals(15.25));
    }
}
