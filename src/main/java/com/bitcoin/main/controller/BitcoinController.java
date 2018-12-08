package com.bitcoin.main.controller;

import com.bitcoin.main.services.CoinBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Date;

@RestController
@RequestMapping("/bitcoin")
public class BitcoinController {
    //restful call for all required calls

    @Autowired
    private CoinBase coinBase;


    @RequestMapping(value = "/lastyear", method = RequestMethod.GET)
    @ResponseBody
    public List<Double> getLastyearPrices() {
        return coinBase.getLastYearPrices();
    }

    @RequestMapping(value= "/lastmonth", method =RequestMethod.GET)
    @ResponseBody
    public List<Double> getLastMonthPrices() {
        return coinBase.getLastMonthPrices();
    }

    @RequestMapping(value = "/lastweek", method = RequestMethod.GET)
    @ResponseBody
    public List<Double> getLastweekPrices() {
        return coinBase.getLastWeekPrices();
    }

    @RequestMapping(value= "/averagePrice", method=RequestMethod.GET)
    @ResponseBody
    public Double getAveragePrice(@RequestParam Date dateFrom, @RequestParam Date dateTo) {
        return coinBase.getRollingAveragebwDates(dateFrom, dateTo);
    }

    @RequestMapping(value = "/decision", method=RequestMethod.GET)
    @ResponseBody
    public String decision(@RequestParam  Date dateFrom, @RequestParam Date dateTo) {
        return coinBase.predictDecision(dateFrom, dateTo);
    }


}
