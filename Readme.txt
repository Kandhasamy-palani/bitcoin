Main class:
      BitcoinMain
Restful Services:
      /bitcoin/lastyear -- retrieve last year prices list
      /bitcoin/lastmonth -- retrieve last month prices list
      /bitcoin/lastweek -- last week price list
      /bitcoin/averagePrice - average price for date from and to
      /bitcoin/decision - decision(HOLD/BUY/SELL) for date from and to

Localsever:
   http://localhost:8080

Sample :
http://localhost:8080/bitcoin/lastweek
http://localhost:8080/bitcoin/lastyear
http://localhost:8080/bitcoin/lastmonth
http://localhost:8080/bitcoin/averagePrice?dateFrom=2018/01/01&dateTo=2018/05/01
http://localhost:8080/bitcoin/decision?dateFrom=2018/01/01&dateTo=2018/05/01

Unittest:
BitcoinMainTest

Framework:
   SpringBoot
Language :
     Java 8






