/*
 * 요기요 등록 음식점 이하의 리스트들에 접근
 */
package com.spring.conv;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.conv.service.AddressVO;
import com.spring.conv.service.GSVO;

// jsp 파일에 input 값을 입력하지 않아도 자동적으로 크롤링 페이지를 띄워줌
// 로그인 회원가입 형태로 실행해보면 될지도 모름. 
@Controller
public class Selenium_conv {
	// WebDriver
		private WebDriver driver;
		

	@RequestMapping(value = "/start.do", method = RequestMethod.GET)
	public String start(AddressVO addr, Model model) throws InterruptedException {
		
		System.out.println("start() 실행");

		System.out.println("placeName: " + addr.getPlaceName());
		System.out.println("addr: " + addr.getAddr());

		Selenium_conv selTest = new Selenium_conv();
		selTest.crawl(addr,model);

		System.out.println("성공적 크롤링 ");
		System.out.println("ddfsadfas");
		return "outputList";

	}

	
    




	

	// Properties
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static final String WEB_DRIVER_PATH = "C:/Crawler/Selenium/chromedriver_win32/chromedriver.exe";
	//public static final String WEB_DRIVER_PATH = "/usr/lib/chromium-browser/chromedriver";
	
	
	
	// 크롤링 할 URL
	private String base_url;

	public Selenium_conv() {
		super();

		// System Property SetUp
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

	    //ChromeOptions options = new ChromeOptions();
	   // options.addArguments("headless");
	 
	    //driver = new ChromeDriver(options);

		// Driver SetUp
		driver = new ChromeDriver();
		base_url = "https://www.yogiyo.co.kr/mobile/#/";
		// base_url = "https://www.naver.com/";

	}

	public void crawl(AddressVO vo, Model model) {

		try {
			String add = vo.getAddr();
			
			ArrayList<GSVO> list = new ArrayList<GSVO>();
			ArrayList<String> nameList = new ArrayList<String>();
			ArrayList<String> priceList = new ArrayList<String>();
			ArrayList<String> stockList = new ArrayList<String>();

			// get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
			driver.get(base_url);
			Thread.sleep(1500);
			// 요기요 검색 창 접근, 창 비우기, 창에 주소 넣기. 엔터키 입력(검색 클릭과 같은 동작)
			WebElement address = driver.findElement(By.cssSelector("#search > div > form > input"));
			address.clear();
			address.sendKeys(add);
			address.sendKeys("\n"); // address.submit()이 동작하지 않음.
			Thread.sleep(2000);

			// '편의점/마트' 카테고리 클릭
			driver.findElement(By.cssSelector("#category > ul > li:nth-child(14)")).click();
			Thread.sleep(2000);

			/*
			 * driver.findElement(By.cssSelector(
			 * "img[src='image/category-convenience-store.png']")).click();
			 */

			// 경로: #content > div 이면 출력값: css selector: #content > div
			// 우리 동네 플러스, 슈퍼레드위크 추천, 요기요 등록 음식점 -> 세 영역으로 나뉨(2~4 index로 접근,
			// div:nth-child(2~4) 그런데 prodsCount.size() 값이 왜 7이 나오지?
			List<WebElement> prodsCount = driver.findElements(By.cssSelector("#content > div > div"));
			// System.out.println("Total number of products are " + prodsCount.size());
			// System.out.println(j +"번째 출력 내용" + prodsCount.get(j++).getText());

			// '요기요 등록 음식점' 아래에 있는 편의점 지점 리스트들에 접근 위한 인덱스. 추출 작업.
			int startIndex = -1;

			// 끝 index 설정 이슈 -> 대강 10으로 두기 vs prodsCount.size() 값으로 두기
			for (int j = 2; j <= prodsCount.size(); j++) { // ****** 시작 인덱스 = 2 (1 아님) *******
				String startName = driver
						.findElement(By.cssSelector(
								"#content > div > div:nth-child(" + j + ") > div > div.ranking-guide.ng-scope > p"))
						.getText();
				// System.out.println(startName + "문자열 길이: " + startName.length());
				// if절 진입 못하는 문제 발생. 왜 ? 문자열의 길이를 구해보면 길이의 뒤로 공백문자열이 들어가 있는 경우가 존재.
				// trim() 함수 이용. 앞 뒤 공백 문자열 제거해줌.
				startName = startName.trim();
				if (startName.equals("요기요 등록 음식점")) {
					System.out.println(startName);
					startIndex = j;
					break;

				}
			}

			/*
			 * // startIndex('요기요 등록 음식점')에 해당하는 편의점 리스트의 정보 출력(스크롤 하기 전)
			 * System.out.println(driver .findElement(By.cssSelector(
			 * "#content > div > div:nth-child(" + startIndex +
			 * ") > div > div.restaurant-list")) .getText());
			 * 
			 * Thread.sleep(3000);
			 */

			// #content > div > div:nth-child(4) > div // 요기요 등록 음식점 이하 div

			// 무한 스크롤(확인)
			WebElement element = driver.findElement(By.cssSelector("body"));
			element.sendKeys(Keys.END);

			
			/*
			 * 확인됨. int jj = 1; while(true) { System.out.println("리스트 개수 확인하는 로직 접근: " +
			 * jj);
			 * if(driver.findElement(By.cssSelector("#content > div > div:nth-child(4) > " +
			 * "div > div.restaurant-list > div:nth-child("+jj+")")) == null) { break; }
			 * String pName =
			 * driver.findElement(By.cssSelector("#content > div > div:nth-child(" +
			 * startIndex + ") > div > div.restaurant-list" + " > div:nth-child(" + jj + ")"
			 * +
			 * "> div > table > tbody > tr > td:nth-child(2) > div > div.restaurant-name.ng-binding"
			 * )) .getText(); jj++; } System.out.println("무한 스크롤 뒤 편의점 총 개수: " + jj);
			 */

			// jsp에서 전달한 편의점 지점명과 지점명이 같은 편의점을 검색해 클릭하는 행위
			// 전달한 편의점 지점명에 해당하는 편의점이 없다면 요기요 서비스 제공 지점이 아님

		
			
			
			int idx = 1;
			while (true) {
				String placeName = driver.findElement(By.cssSelector("#content > div > div:nth-child(" + startIndex
						+ ") > div > div.restaurant-list" + " > div:nth-child(" + idx + ")"
						+ "> div > table > tbody > tr > td:nth-child(2) > div > div.restaurant-name.ng-binding"))
						.getText();
				System.out.println(placeName);
				placeName = placeName.trim();
				if (placeName.equals(vo.getPlaceName())) {

					/*
					 * ***********스크롤 처리하기 전의 편의점은 클릭 처리가 안됨. ********************
					 * sendKeys(Keys.END); 를 수행하면 페이지 스크롤 끝까지 되는데 이 후에 driver.findElement()를 수행할 때,
					 * 편의점의 모든 리스트 요소에 접근할 것이라고 생각. 하지만, NoSuchElementException 발생. 해당 요소를 찾을 수 없다고
					 * 나옴. 해결: 스크롤 하여 해당 요소 위치에 접근해야 클릭 처리를 할 수 있음.
					 **************************************************************/

					WebElement ele = driver.findElement(By.cssSelector("#content > div > div:nth-child(" + startIndex
							+ ") > div > div.restaurant-list > div:nth-child(" + idx + ")"));
					Actions actions = new Actions(driver);
					actions.moveToElement(ele);
					actions.perform();

					driver.findElement(By.cssSelector("#content > div > div:nth-child(" + startIndex
							+ ") > div > div.restaurant-list > div:nth-child(" + idx + ")")).click();
					Thread.sleep(3000);
					System.out.println("목적 편의점 지점까지 도달");
					break;

					
					  
				} else { 
					idx++;					  
					  
				}
					 

					

				
			}
			long startTime = System.currentTimeMillis();
			// 2부터 카테고리 시작
			int categoryIdx = 3;
			// k = 19번 부터는 요기요에서 막아 놓은 것 같다. 개별로 19이상의 인덱스에 접근은 가능한데....
			for (int k = categoryIdx; k < 18; k++) {
			System.out.println("loop진입");		  
			/*  출처: https://stackoverrun.com/ko/q/13064334 driver.findElement() will not
			  return null if no element is found so your condition that checks that is not
			  necessary. If no element is found, an exception is thrown which you are
			  catching. You can avoid all the try/catches by using .findElements() (note
			  the plural). If the element is not found, an empty list will be returned. You
			  can then check for an empty list and avoid all the exceptions.
			 */
			  List<WebElement> el =	driver.findElements(By.cssSelector("#menu > div > div:nth-child("
					  				+k+") > div.panel-heading > h4")); 
			  if(!el.isEmpty()) {
				  //System.out.println("not empty");
				 
			 	  el.get(0).click();
			 	  //System.out.println("클릭 ");
			 	  //Thread.sleep(500);
			 	  int innerCategoryIdx = 1;
			 	  while(true) {
			 		  	//System.out.println("while 진입");
						List<WebElement> innerEl = driver
								.findElements(By.cssSelector("#menu > div > div:nth-child(" + k + ") > "
										+ "div.panel-collapse.collapse.in.btn-scroll-container > div > ul > li:nth-child("
										+ innerCategoryIdx + ")"));
						
			 			//System.out.println("재고 string 길이: " + isStock.length());
			 			
			 			// <재고 있는 상품만 데이터 저장>
			 			// 상품 재고가 없는 상품의 재고를 출력해보면 빈 문자열로 나오고, 길이도 0이지만,
			 			// isStock != null 문장으로 조건에 추가하면 상품 재고 없는 상품도 출력됨.
			 			// 즉, 모든 상품이 출력됨
						if (!innerEl.isEmpty()) {
							
							// 이 문장을 if 위로 뺐을 경우, innerEl이 null인 상황으로 가정하면
							// NoSuchElementException 발생
							String isStock = driver.findElement(By.cssSelector("#menu > div > div:nth-child(" + k
									+ ") > div.panel-collapse.collapse.in.btn-scroll-container > div > ul > li:nth-child("
									+ innerCategoryIdx
									+ ") > table > tbody > tr > td.menu-text > div.menu-stock.ng-binding")).getText();
							// 반드시 재고(String)값이 0이상인 데이터만 접근
							// 요기요에서 품절 상태인 경우 재고 표시란이 없어짐(length == 0)
							if (isStock.length() != 0) {
								GSVO gsVO = new GSVO();
								String url = driver.findElement(By.cssSelector("#menu > div > div:nth-child(" + k
										+ ") > div.panel-collapse.collapse.in.btn-scroll-container > div > ul > li:nth-child("
										+ innerCategoryIdx + ") > table > tbody > tr > td.photo-area > div"))
										.getAttribute("style");
								int start = url.indexOf("https://");
								int last = url.indexOf("?");
								if (last != -1) {
									String ok = url.substring(start, last);
									gsVO.setImageUrl(ok);
								}

								gsVO.setProductName(driver.findElement(By.cssSelector("#menu > div > div:nth-child(" + k
										+ ") > "
										+ "div.panel-collapse.collapse.in.btn-scroll-container > div > ul > li:nth-child("
										+ innerCategoryIdx + ") > "
										+ "table > tbody > tr > td.menu-text > div.menu-name.ng-binding")).getText());
								gsVO.setProductPrice(
										driver.findElement(By.cssSelector("#menu > div > div:nth-child(" + k + ") > "
												+ "div.panel-collapse.collapse.in.btn-scroll-container > div > ul > "
												+ "li:nth-child(" + innerCategoryIdx
												+ ") > table > tbody > tr > td.menu-text > div.menu-price > "
												+ "span:nth-child(1)")).getText());
								gsVO.setProductStock(driver.findElement(By.cssSelector("#menu > div > div:nth-child("
										+ k
										+ ") > div.panel-collapse.collapse.in.btn-scroll-container > div > ul > li:nth-child("
										+ innerCategoryIdx
										+ ") > table > tbody > tr > td.menu-text > div.menu-stock.ng-binding"))
										.getText());
								// System.out.println("상품명: " + gsVO.getProductName());
								// System.out.println("상품 가격: " + gsVO.getProductPrice());
								// System.out.println("상품 재고: " + gsVO.getProductStock());
								// System.out.println("------------------------");
								list.add(gsVO);
								innerCategoryIdx++;
							}
							else {
								break;
							}
						}
						
						else {
							//System.out.println("empty");
							break;
						}
			 	  }
			  }
			}
			
			long endTime = System.currentTimeMillis();
			System.out.println((endTime - startTime));
			System.out.println("리스트 사이즈: " + list.size());
			
			model.addAttribute("list",list);
			
			
			  
			
			

			/*
			 * [문제1] 서울 서초구 서초동 1303-33 현위치 주소가 이렇게 떴는데 요기요에서 이 주소값으로 검색 버튼이 눌러지지 않음. 요기요에서
			 * 인식하는 주소가 아닌듯.
			 * 
			 * 예) 서울 서초구 서초동 1337-17 으로 검색하면 서울 서초구 서초동 1337-17 보원빌딩으로 바뀌어서 검색됨 -> 요기요는 현위치를
			 * 근처 빌딩 또는 건물 위치까지 검색해야 나오는 듯. -> 현 주소 + 건물명까지 붙혀서 주소값을 보강해주세요
			 * 
			 * 
			 * [문제2] GS25-서초점 -> 요기요 상에 등록되어 있는데 검색 버튼 안눌림. 즉, 편의점 지점 명으로 검색시 주소로 변환되어 검색되는데
			 * 되는게 있고 않되는 게 있음.
			 */

		} catch (

		Exception e) {

			e.printStackTrace();

		} finally {
			
			driver.close();
			
		}
		
	}

}