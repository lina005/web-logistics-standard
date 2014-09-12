package com.incito.logistics.util;

/**
 * @author xy-incito-wy
 * @Description 包装所有selenium的操作以及通用方法，简化用例中代码量
 * */
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.netease.qa.testng.TestResultListener;

public class SeleniumUtil {
	/** 使用Log4j，第一步就是获取日志记录器，这个记录器将负责控制日志信息 */
	public static Logger logger = Logger.getLogger(SeleniumUtil.class.getName());
	public ITestResult it = null;
	public WebDriver driver = null;
	public WebDriver window = null;

	/***
	 * 启动浏览器并打开页面
	 * */
	public void launchBrower(String browserName, ITestContext context, String platform) {

		String webUrl = context.getCurrentXmlTest().getParameter("testurl");
		int timeOut = Integer.valueOf(context.getCurrentXmlTest().getParameter("timeOut"));
		SelectExplorer select = new SelectExplorer();
		logger.info("Starting:[" + browserName + "]");
		driver = select.selectExplorerByName(browserName, context, platform);
		// 等待waitPageLoadTime秒后如果没有页面还是没有刷出来 就跑出异常
		try {
			hasLoadPageSucceeded(timeOut);
			get(webUrl);
			logger.info("Opening the test page:[" + webUrl + "]");
			maxWindow();
		} catch (Exception e) {
			refresh();
			logger.info("Web page was refreshed successfully");
		}

	}

	/**
	 * 最大化浏览器操作
	 * */
	public void maxWindow() {
		driver.manage().window().maximize();
	}

	/**
	 * 设定浏览器窗口大小： 设置浏览器窗口的大小有下面两个比较常见的用途：<br>
	 * 1、在统一的浏览器大小下运行用例，可以比较容易的跟一些基于图像比对的工具进行结合
	 * ，提升测试的灵活性及普遍适用性。比如可以跟sikuli结合，使用sikuli操作flash；<br>
	 * 2、在不同的浏览器大小下访问测试站点，对测试页面截图并保存，然后观察或使用图像比对工具对被测页面的前端样式进行评测。
	 * 比如可以将浏览器设置成移动端大小(320x480)，然后访问移动站点，对其样式进行评估；<br>
	 * */
	public void setBrowserSize(int width, int height) {
		driver.manage().window().setSize(new Dimension(width, height));
	}

	/**
	 * 包装查找元素的方法 element
	 * */
	public WebElement findElementBy(By by) {
		return driver.findElement(by);
	}

	/**
	 * 包装查找元素的方法 elements
	 * */
	public List<WebElement> findElementsBy(By by) {
		return driver.findElements(by);
	}

	/**
	 * 包装点击操作
	 * */
	public void click(WebElement element) {
		try {
			element.click();

		} catch (StaleElementReferenceException e) {
			logger.error("the element you clicked:[" + getLocatorByElement(element,">") + "] is no longer exist!");
		} catch (Exception e) {
			logger.error("failed to click element [" + getLocatorByElement(element,">")+"]");
		}
		logger.info("clicked element [" + getLocatorByElement(element,">")+"]");
	}

	/**
	 * 获得页面的标题
	 * 
	 * @return
	 * */
	public String getTitle() {
		return driver.getTitle();
	}

	/**
	 * 包装清除操作
	 * */
	public void clear(WebElement element) {
		try {
			element.clear();
		} catch (Exception e) {
			logger.error("failed to clear [" +  getLocatorByElement(element,">") + "] contents!");
		}
		logger.info("cleared the contents on [" +  getLocatorByElement(element,">")+"]");
	}

	/**
	 * 向输入框输入内容
	 * */
	public void type(WebElement element, String key) {
		try {
			element.sendKeys(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("failed to type the [" + key + "] to [" +  getLocatorByElement(element,">")+"]");
		}
		logger.info("typed：[" + key + "] to [" +  getLocatorByElement(element,">")+"]");
	}

	/**
	 * 模拟键盘操作的,比如Ctrl+A,Ctrl+C等 参数详解： 1、WebElement element - 要被操作的元素 2、Keys key
	 * - 键盘上的功能键 比如ctrl ,alt等 3、String keyword - 键盘上的字母
	 * */
	public void pressKeysOnKeyboard(WebElement element, Keys key, String keyword) {

		element.sendKeys(Keys.chord(key, keyword));
	}

	/**
	 * 在给定的时间内去查找元素，如果没找到则超时，抛出异常
	 * */
	public void waitForElementToLoad(int timeOut, final By By) {
		try{
		(new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				WebElement element = driver.findElement(By);
				logger.info("found the element ["+getLocatorByElement(element,">")+"]");
				return element.isDisplayed();
			}
		});  }catch(TimeoutException e){
			logger.error("TIME OUT!! "+timeOut+" second(s) has passed,but did not find element ["+By+"]");
			Assert.fail("TIME OUT!! "+timeOut+" second(s) has passed,but did not find element ["+By+"]");
			
		}
		
	}

	/**
	 * 当jquery正在读取或处理比较大的json文件的时候，怎样判断它的读取活动已经完成
	 */

	public void waitForAjaxDone(int timeOut) {
		try {
			(new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					JavascriptExecutor js = (JavascriptExecutor) d;
					return (Boolean) js.executeScript("return jQuery.active == 0"); // Jquery.active=0表示ajax活动已经完成
				}
			});
		} catch (TimeoutException e) {
			logger.error("Time out!! " + timeOut + " seconds passed and the jquery satate ：[!=0]");
			new TestResultListener().onTestFailure(it);
			quit();

		}

	}

	/**
	 * 判断文本是不是和需求要求的文本一致
	 * **/
	public void isTextCorrect(String actual, String expected) {
		try{
		Assert.assertEquals(actual, expected);
		}catch(AssertionError e){
			logger.info("the expected text is ["+expected+"] but found ["+actual+"]");
			Assert.fail("the expected text is [" + expected + "] but found [" + actual + "]");
			
		}

	}

	/**
	 * 判断编辑框是不是可编辑
	 * */
	public void isInputEdit(WebElement element) {

	}


	/**
	 * 等待alert出现
	 * */
	public Alert switchToPromptedAlertAfterWait(long waitMillisecondsForAlert) throws NoAlertPresentException {
		final int ONE_ROUND_WAIT = 100;
		NoAlertPresentException lastException = null;

		long endTime = System.currentTimeMillis() + waitMillisecondsForAlert;

		for (long i = 0; i < waitMillisecondsForAlert; i += ONE_ROUND_WAIT) {

			try {
				Alert alert = driver.switchTo().alert();
				return alert;
			} catch (NoAlertPresentException e) {
				lastException = e;
			}
			pause(ONE_ROUND_WAIT);

			if (System.currentTimeMillis() > endTime) {
				break;
			}
		}
		throw lastException;
	}

	/**
	 * 暂停当前用例的执行，暂停的时间为：sleepTime
	 * */
	public void pause(int sleepTime) {
		if (sleepTime <= 0) {
			return;
		}
		try {
			logger.info("The process pause " + sleepTime + " millisecond");
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * 退出
	 * */
	public void quit() {
		driver.quit();
	}

	/**
	 * 切换frame - 根据String类型（frame名字）
	 * */
	public void inFrame(String frameId) {
		driver.switchTo().frame(frameId);
	}

	/**
	 * 切换frame - 根据frame在当前页面中的顺序来定位
	 * */
	public void inFrame(int frameNum) {
		driver.switchTo().frame(frameNum);
	}

	/**
	 * 切换frame - 根据页面元素定位
	 * */
	public void switchFrame(WebElement element) {
		driver.switchTo().frame(element);
	}

	/**
	 * 选择下拉选项 -根据value
	 * */
	public void selectByValue(By by, String value) {
		Select s = new Select(driver.findElement(by));
		s.selectByValue(value);
	}

	/**
	 * 选择下拉选项 -根据index角标
	 * */
	public void selectByIndex(By by, int index) {
		Select s = new Select(driver.findElement(by));
		s.selectByIndex(index);
	}

	/**
	 * 选择下拉选项 -根据文本内容
	 * */
	public void selectByText(By by, String text) {
		Select s = new Select(driver.findElement(by));
		s.selectByVisibleText(text);
	}

	/**
	 * 执行JavaScript 方法
	 * */
	public void executeJS(String js) {
		((JavascriptExecutor) driver).executeScript(js);
	}

	/**
	 * 取得弹窗
	 * */
	public void switchWindow(By by, int sleepTime) {
		String currentWindows = driver.getWindowHandle();
		driver.findElement(by).click();
		pause(sleepTime);
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();
		while (it.hasNext()) {

			if (currentWindows == it.next())
				continue;
			window = driver.switchTo().window(it.next());
			// 这里可以写做了什么操作，最后操作做完之后会关闭此弹窗
			window.close();

		}
		// 切回到原来的窗口
		driver.switchTo().window(currentWindows);

	}

	/**
	 * get方法包装
	 * */
	public void get(String url) {
		driver.get(url);
	}

	/**
	 * close方法包装
	 * */
	public void close() {
		driver.close();
	}

	/**
	 * 刷新方法包装
	 * */
	public void refresh() {
		driver.navigate().refresh();
	}

	/**
	 * 后退方法包装
	 * */
	public void back() {
		driver.navigate().back();
	}

	/**
	 * 前进方法包装
	 * */
	public void forward() {
		driver.navigate().forward();
	}

	/**
	 * 包装selenium模拟鼠标操作 - 鼠标移动到指定元素
	 * */
	public void mouseMoveToElement(By by) {
		Actions builder = new Actions(driver);
		Actions mouse = builder.moveToElement(driver.findElement(by));
		mouse.perform();
	}

	/**
	 * 添加cookies,做自动登陆的必要方法
	 * */
	public void addCookies(int sleepTime) {
		pause(sleepTime);
		Set<Cookie> cookies = driver.manage().getCookies();
		for (Cookie c : cookies) {
			System.out.println(c.getName() + "->" + c.getValue());
			if (c.getName().equals("logisticSessionid")) {
				Cookie cook = new Cookie(c.getName(), c.getValue());
				driver.manage().addCookie(cook);
				System.out.println(c.getName() + "->" + c.getValue());
				System.out.println("添加成功");
			} else {
				System.out.println("没有找到logisticSessionid");
			}

		}

	}

	/** 获得CSS value */
	public String getCSSValue(WebElement e, String key) {

		return e.getCssValue(key);
	}

	/** 使用testng的assetTrue方法 */
	public void assertTrue(WebElement e, String content) {
		String str = e.getText();
		Assert.assertTrue(str.contains(content), "字符串数组中不含有：" + content);

	}

	/** 跳出frame */
	public void outFrame() {

		driver.switchTo().defaultContent();
	}

	// webdriver中可以设置很多的超时时间
	/** implicitlyWait。识别对象时的超时时间。过了这个时间如果对象还没找到的话就会抛出NoSuchElement异常 */
	public void implicitlyWait(int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}

	/** setScriptTimeout。异步脚本的超时时间。webdriver可以异步执行脚本，这个是设置异步执行脚本脚本返回结果的超时时间 */
	public void setScriptTimeout(int timeOut) {
		driver.manage().timeouts().setScriptTimeout(timeOut, TimeUnit.SECONDS);
	}

	/**
	 * pageLoadTimeout。页面加载时的超时时间。因为webdriver会等页面加载完毕在进行后面的操作，
	 * 所以如果页面在这个超时时间内没有加载完成，那么webdriver就会抛出异常
	 */

	public void hasLoadPageSucceeded(int pageLoadTime) {

		driver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);

	}

	/** 根据元素来获取此元素的定位值 */
	public String getLocatorByElement(WebElement element, String expectText) {
		String text = element.toString();
		String expect = null;
		try {
			expect = text.substring(text.indexOf(expectText) + 1,text.length()-1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("failed to find the string [" + expectText+"]");

		}

		return expect;
		
	}
	
	/**
	 * 利用ExcelDataProvider读取指定的excel表（表名是和类名一样）<br>
	 * 使用方法：<br>
	 * 1、在测试用例中写入：    @DataProvider(name = "dp")<br>
	 * 2、然后在接着引入此方法<br>
	 * 3、最后在测试用例的@Test处进行处理下<br>
	 * 	  	@Test(dataProvider = "dp" )<br>
     *		public void search(Map<String,String> data) {  <br>
     *       这里去数据 用data.get("") ""中是excel中的列名<br>
     *						}<br>
	 * */
    public Iterator<Object[]> dataFortestMethod(Method method) throws IOException {
        return new ExcelDataProvider(this.getClass().getName(),method.getName());
    }

}
