/*
 * 요기요 등록 음식점 이하의 리스트들에 접근
 * 
 * package com.spring.conv;
 * 
 * import java.util.List;
 * 
 * import org.openqa.selenium.By; import org.openqa.selenium.JavascriptExecutor;
 * import org.openqa.selenium.Keys; import org.openqa.selenium.WebDriver; import
 * org.openqa.selenium.WebElement; import
 * org.openqa.selenium.chrome.ChromeDriver; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod;
 * 
 * import com.spring.conv.service.AddressVO;
 * 
 * // jsp 파일에 input 값을 입력하지 않아도 자동적으로 크롤링 페이지를 띄워줌 // 로그인 회원가입 형태로 실행해보면 될지도 모름.
 * 
 * @Controller public class Selenium_conv2 {
 * 
 * @RequestMapping(value = "/start2.do", method = RequestMethod.GET) public void
 * start(AddressVO addr) throws InterruptedException {
 * 
 * System.out.println("start() 실행");
 * 
 * 
 * System.out.println("placeName: " + addr.getPlaceName());
 * 
 * Selenium_conv2 selTest = new Selenium_conv2(); selTest.crawl(addr);
 * 
 * System.out.println("성공적 크롤링 ");
 * 
 * }
 * 
 * // WebDriver private WebDriver driver;
 * 
 * // Properties public static final String WEB_DRIVER_ID =
 * "webdriver.chrome.driver"; public static final String WEB_DRIVER_PATH =
 * "C:/Crawler/Selenium/chromedriver_win32/chromedriver.exe";
 * 
 * // 크롤링 할 URL private String base_url;
 * 
 * public Selenium_conv2() { super();
 * 
 * // System Property SetUp System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
 * 
 * // Driver SetUp driver = new ChromeDriver(); base_url =
 * "https://www.yogiyo.co.kr/mobile/#/"; // base_url = "https://www.naver.com/";
 * 
 * }
 * 
 * public void crawl(AddressVO vo) {
 * 
 * try { String add = vo.getPlaceName();
 * 
 * // get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다) driver.get(base_url);
 * 
 * // 요기요 검색 창 접근, 창 비우기, 창에 주소 넣기. 엔터키 입력(검색 클릭과 같은 동작) WebElement address =
 * driver.findElement(By.cssSelector("#search > div > form > input"));
 * address.clear(); address.sendKeys(add); address.sendKeys("\n"); //
 * address.submit()이 동작하지 않음. Thread.sleep(5000);
 * 
 * // '편의점/마트' 카테고리 클릭
 * driver.findElement(By.cssSelector("#category > ul > li:nth-child(14)")).click
 * (); Thread.sleep(2000);
 * 
 * 
 * driver.findElement(By.cssSelector(
 * "img[src='image/category-convenience-store.png']")).click();
 * 
 * 
 * // 경로: #content > div 이면 출력값: css selector: #content > div // 우리 동네 플러스,
 * 슈퍼레드위크 추천, 요기요 등록 음식점 -> 세 영역으로 나뉨(2~4 index로 접근, // div:nth-child(2~4) 그런데
 * prodsCount.size() 값이 왜 7이 나오지? List<WebElement> prodsCount =
 * driver.findElements(By.cssSelector("#content > div > div")); //
 * System.out.println("Total number of products are " + prodsCount.size()); //
 * System.out.println(j +"번째 출력 내용" + prodsCount.get(j++).getText());
 * 
 * // '요기요 등록 음식점' 아래에 있는 편의점 지점 리스트들에 접근 위한 인덱스. 추출 작업. int startIndex = -1;
 * 
 * // 끝 index 설정 이슈 -> 대강 10으로 두기 vs prodsCount.size() 값으로 두기 for (int j = 2; j
 * <= prodsCount.size(); j++) { // ****** 시작 인덱스 = 2 (1 아님) ******* String
 * startName = driver .findElement(By.cssSelector(
 * "#content > div > div:nth-child(" + j +
 * ") > div > div.ranking-guide.ng-scope > p")) .getText(); //
 * System.out.println(startName + "문자열 길이: " + startName.length()); // if절 진입
 * 못하는 문제 발생. 왜 ? 문자열의 길이를 구해보면 길이의 뒤로 공백문자열이 들어가 있는 경우가 존재. // trim() 함수 이용. 앞
 * 뒤 공백 문자열 제거해줌. startName = startName.trim(); if
 * (startName.equals("요기요 등록 음식점")) { System.out.println(startName); startIndex
 * = j; break;
 * 
 * } }
 * 
 * 
 * // startIndex('요기요 등록 음식점')에 해당하는 편의점 리스트의 정보 출력(스크롤 하기 전)
 * System.out.println(driver.findElement(By.cssSelector(
 * "#content > div > div:nth-child("+startIndex+") > div > div.restaurant-list")
 * ).getText());
 * 
 * Thread.sleep(3000);
 * 
 * //#content > div > div:nth-child(4) > div // 요기요 등록 음식점 이하 div
 * 
 * 
 * // 무한 스크롤(확인) WebElement element =
 * driver.findElement(By.cssSelector("body")); element.sendKeys(Keys.END);
 * Thread.sleep(3000);
 * 
 * int jj = 1; while(true) { System.out.println("리스트 개수 확인하는 로직 접근");
 * if(driver.findElement(By.cssSelector("#content > div > div:nth-child(4) > " +
 * "div > div.restaurant-list > div:nth-child("+jj+")")) == null) { break; }
 * String pName =
 * driver.findElement(By.cssSelector("#content > div > div:nth-child(" +
 * startIndex + ") > div > div.restaurant-list" + " > div:nth-child(" + jj + ")"
 * +
 * "> div > table > tbody > tr > td:nth-child(2) > div > div.restaurant-name.ng-binding"
 * )) .getText(); jj++; } System.out.println("무한 스크롤 뒤 편의점 총 개수: " + jj); //
 * jsp에서 전달한 편의점 지점명과 지점명이 같은 편의점을 검색해 클릭하는 행위 int idx = 1; while (true) {
 * String placeName =
 * driver.findElement(By.cssSelector("#content > div > div:nth-child(" +
 * startIndex + ") > div > div.restaurant-list" + " > div:nth-child(" + idx +
 * ")" +
 * "> div > table > tbody > tr > td:nth-child(2) > div > div.restaurant-name.ng-binding"
 * )) .getText(); System.out.println(placeName); placeName = placeName.trim();
 * if (placeName.equals(vo.getPlaceName())) {
 * driver.findElement(By.cssSelector("#content > div > div:nth-child(" +
 * startIndex + ") > div > div.restaurant-list > div:nth-child(" + idx +
 * ")")).click(); System.out.println("목적 편의점 지점까지 도달"); break; } idx++; }
 * Thread.sleep(3000); System.out.println("***********");
 * 
 * // System.out.println(driver.findElement(By.cssSelector("#content > div > //
 * div:nth-child("+startIndex+") > div > div.restaurant-list > //
 * div")).getSize());
 * 
 * // #content > div > div:nth-child(4) > div > div.restaurant-list > //
 * div:nth-child(1) // 리스트 중 index로 구분 됨. // #content > div > div:nth-child(4) >
 * div > div.restaurant-list > // div:nth-child(1) > div > table > tbody > tr >
 * td:nth-child(2) > div > // div.restaurant-name.ng-binding // #content > div >
 * div:nth-child(4) > div > div.restaurant-list > // div:nth-child(2) > div >
 * table > tbody > tr > td:nth-child(2) > div > //
 * div.restaurant-name.ng-binding // 지점 찾을때는 <td>의 title 값으로 찾기 -
 * 
 * 
 * //System.out.println(driver.findElement(By.cssSelector("#content > div")).
 * getSize()); //List<WebElement> els = driver.findElements(By.
 * cssSelector("#content > div > div:nth-child(4) > div > div.restaurant-list"))
 * ; for(int i=1; i<5; i++) { //els.add(driver.findElement(By.
 * cssSelector("#content > div > div:nth-child(4) > div > div.restaurant-list > div:nth-child("
 * +i+")"))); System.out.println("i: " + i + "점포명: " +
 * driver.findElement(By.cssSelector("#content > div > " +
 * "div:nth-child(4) > div > div.restaurant-list > div:nth-child("
 * +i+") > div > table > " +
 * "tbody > tr > td:nth-child(2) > div > div.restaurant-name.ng-binding")).
 * getText()); } //#content > div > div:nth-child(4) > div > div.restaurant-list
 * > div:nth-child(1) > div > table > tbody > tr > td:nth-child(2) > div >
 * div.restaurant-name.ng-binding Thread.sleep(2000);
 * 
 * 
 * 
 * 
 * [문제1] 서울 서초구 서초동 1303-33 현위치 주소가 이렇게 떴는데 요기요에서 이 주소값으로 검색 버튼이 눌러지지 않음. 요기요에서
 * 인식하는 주소가 아닌듯.
 * 
 * 예) 서울 서초구 서초동 1337-17 으로 검색하면 서울 서초구 서초동 1337-17 보원빌딩으로 바뀌어서 검색됨 -> 요기요는 현위치를
 * 근처 빌딩 또는 건물 위치까지 검색해야 나오는 듯. -> 현 주소 + 건물명까지 붙혀서 주소값을 보강해주세요
 * 
 * 
 * [문제2] GS25-서초점 -> 요기요 상에 등록되어 있는데 검색 버튼 안눌림. 즉, 편의점 지점 명으로 검색시 주소로 변환되어 검색되는데
 * 되는게 있고 않되는 게 있음.
 * 
 * 
 * } catch (
 * 
 * Exception e) {
 * 
 * e.printStackTrace();
 * 
 * } finally {
 * 
 * driver.close(); }
 * 
 * }
 * 
 * }
 */