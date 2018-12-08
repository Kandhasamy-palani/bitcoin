package com.bitcoin.main.web;

import com.bitcoin.main.services.CoinBase;
import com.bitcoin.main.services.CoinBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.bitcoin.main")
public class BitcoinMain{
    @Autowired
    private CoinBase coinbase;

    @Bean
    public CoinBase getCoinbase(){
        return this.coinbase = new CoinBaseImpl();
    }

    public static final String SOURCE_URL = "";

    public static void main(String[] args) {
        SpringApplication.run(BitcoinMain.class, args);
    }
}
