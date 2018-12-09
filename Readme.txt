Project Details:
      Name: Bitcoin
1. Build pom.xml using "mvn clean install" help to resolve and download dependent library to local
2. Run BitcoinMain Class and make a restful service to access below services.
      
Restful Services:
      http://localhost:8080/bitcoin/lastyear -- retrieve last year prices list
      http://localhost:8080/bitcoin/lastmonth -- retrieve last month prices list
      http://localhost:8080/bitcoin/lastweek -- last week price list
      http://localhost:8080/bitcoin/averagePrice - average price for date from and to
      http://localhost:8080/bitcoin/decision - decision(HOLD/BUY/SELL) for date from and to


Sample:
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







