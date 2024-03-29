package com.PHP.library;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.base.Function;
import com.google.common.io.Files;

/***
 * This is Selenium Wrapper Class, which encapsulates Selenium/WebDriver core
 * methods
 * 
 * @author Afgan Shahguliyev
 *
 */
public class phpLibrary {

	final static Logger logger = Logger.getLogger(phpLibrary.class);

	private WebDriver driver;
	private boolean isDemoMode = true;
	private boolean isRemote;

	public boolean getDemoMode() {
		return isDemoMode;
	}

	public void setDemoMode(boolean isDemoMode) {
		this.isDemoMode = isDemoMode;
	}

	/***
	 * This is the constructor
	 * 
	 * @param _driver
	 */
	public phpLibrary(WebDriver _driver) {
		driver = _driver;
	}

	public WebDriver chromeDHeadless() {
		try {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
			driver = new ChromeDriver(options);// options
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		return driver;
	}

	public WebDriver chromeDriver() {
		try {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
			driver = new ChromeDriver();// options
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		return driver;
	}

	public WebDriver ieDriver() {
		try {
			System.setProperty("webdriver.ie.driver", "src/test/resources/drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		return driver;
	}

	public WebDriver fireFoxDriver() {
		try {
			System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		return driver;
	}

	/***
	 * This is a custom hard coded wait using Thread.sleep()
	 * 
	 * @param inSeconds
	 */
	public static void customWait(double inSeconds) {
		try {
			Thread.sleep((long) (inSeconds * 1000));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * This scrolls browser view and make the element in the center
	 * 
	 * @param elem
	 */
	public void scrollToWebElement(WebElement elem) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", elem);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	/***
	 * This handles check-boxes and radio buttons.
	 * 
	 * @author Afgan (10/03/2019)
	 * @param isUserWantsToCheckTheBox
	 * @param by
	 */
	public void handleCheckBoxRadioBtn(boolean isUserWantsToCheckTheBox, By by) {
		try {
			WebElement checkboxElem = driver.findElement(by);
			if (isUserWantsToCheckTheBox == true) {
				boolean checkBoxState = checkboxElem.isSelected();
				if (checkBoxState == true) {
					// do nothing
				} else {
					checkboxElem.click();
				}
			} else {
				boolean checkBoxState = checkboxElem.isSelected();
				if (checkBoxState == true) {
					checkboxElem.click();
				} else {
					// do nothing
				}
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	/***
	 * This method clicks a WebElement
	 * 
	 * @param by
	 */
	public void clickBtn(By by) {
		try {
			WebElement elem = driver.findElement(by);
			elem.click();
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	/***
	 * This method selects a drop-down for given option value
	 * 
	 * @param by
	 * @param optionValue
	 */
	public void selectDropDown(By by, String optionValue) {
		try {
			Select dropdown = new Select(driver.findElement(by));
			dropdown.selectByVisibleText(optionValue);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	/***
	 * This method selects a drop-down for given option index
	 * 
	 * @param by
	 * @param optionValueIndex
	 */
	public void selectDropDown(By by, int optionValueIndex) {
		try {
			Select dropdown = new Select(driver.findElement(by));
			dropdown.selectByIndex(optionValueIndex);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	/***
	 * This method enters text string for given WebElement Text field
	 * 
	 * @param by
	 * @param text
	 */
	public void enterText(By by, String text) {
		try {
			WebElement elem = driver.findElement(by);
			elem.clear();
			elem.sendKeys(text);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	/***
	 * This is an explicitWait for element presence
	 * 
	 * @param by
	 * @return
	 */
	public WebElement waitUntilElementVisibility(By by) {
		WebElement dynamicElem = null;
		try {
			dynamicElem = (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return dynamicElem;
	}

	/***
	 * This is an explicitWait for element presence
	 * 
	 * @param by
	 * @return
	 */
	public WebElement waitUntilElementPresence(By by) {
		WebElement dynamicElem = null;
		try {
			dynamicElem = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return dynamicElem;
	}

	/***
	 * This is an explicitWait for element to be clickable
	 * 
	 * @param by
	 * @return
	 */
	public WebElement waitUntilElementClickable(By by) {
		WebElement dynamicElem = null;
		try {
			dynamicElem = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return dynamicElem;
	}

	/***
	 * This handles check-boxes and radio buttons.
	 * 
	 * @param isUserWantsToCheckTheBox
	 * @param instantVideoRadioBtn
	 */
	public void handleCheckBoxRadioBtn(boolean isUserWantsToCheckTheBox, WebElement instantVideoRadioBtn) {
		try {
			if (isUserWantsToCheckTheBox == true) {
				boolean checkBoxState = instantVideoRadioBtn.isSelected();
				if (checkBoxState == true) {
					// do nothing
				} else {
					instantVideoRadioBtn.click();
				}
			} else {
				boolean checkBoxState = instantVideoRadioBtn.isSelected();
				if (checkBoxState == true) {
					instantVideoRadioBtn.click();
				} else {
					// do nothing
				}
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	/***
	 * This is a fluent wait, waits dynamically for a WebElement and polls the
	 * source html
	 * 
	 * @param by
	 * @return WebElement
	 */
	public WebElement fluentWait(final By by) {
		WebElement targetElem = null;
		try {
			@SuppressWarnings("deprecation")
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS)
					.pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			targetElem = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(by);
				}
			});
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return targetElem;
	}

	public void enterText(WebElement element, String text) {
		try {
			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public String getCurrentTime() {
		String currentTime = null;
		try {
			Date date = new Date();
			logger.debug("Date: " + date);
			String tempTime = new Timestamp(date.getTime()).toString();
			currentTime = tempTime.replace(" ", "").replace("-", "").replace(":", "").replace(".", "");
			logger.debug("tempTime: " + tempTime);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return currentTime;
	}

	/***
	 * This method moves mouse to the given element by the locator
	 * 
	 * @param by
	 * @return
	 */
	public WebElement moveMouseToElement(By by) {
		WebElement element = null;
		try {
			element = driver.findElement(by);
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return element;
	}

	/***
	 * This method moves mouse to given WebElement
	 * 
	 * @param element
	 */
	public void moveMouseToElement(WebElement element) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	/***
	 * This method highlights Web-site Elements using Javascript
	 * 
	 * @param by
	 * @return WebElement
	 */
	public WebElement highlightElement(By by) {
		WebElement element = null;
		try {
			if (isDemoMode) {
				element = driver.findElement(by);
				WrapsDriver wrappedElement = (WrapsDriver) element;
				JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();

				for (int i = 0; i < 4; i++) {
					customWait(0.5);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
							"color: red; border: 2px solid yellow;");

					customWait(0.5);

					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
				}
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return element;
	}

	/***
	 * This method highlights Web-site Elements using Javascript
	 * 
	 * @param element
	 */
	public void highlightElement(WebElement element) {
		try {
			if (isDemoMode) {
				WrapsDriver wrappedElement = (WrapsDriver) element;
				JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();

				for (int i = 0; i < 4; i++) {
					customWait(0.5);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
							"color: red; border: 2px solid yellow;");

					customWait(0.5);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
				}
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public String captureScreenshot(String screenshotFileName, String filePath) {
		String screenshotFilePath = null;
		String finalPath = null;
		try {
			String timeStamp = getCurrentTime();
			try {
				if (filePath.isEmpty()) {
					checkDirectory("target/screenshots/");
					screenshotFilePath = "target/screenshots/" + screenshotFileName + timeStamp + ".png";
				} else {
					checkDirectory(filePath);
					screenshotFilePath = filePath + screenshotFileName + timeStamp + ".png";
				}

				File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				Files.copy(srcFile, new File(screenshotFilePath));

			} catch (Exception e) {
				e.printStackTrace();
			}

			finalPath = getAbsulatePath(screenshotFilePath);
			logger.info("Screenshot captured: " + finalPath);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return finalPath;
	}

	private String getAbsulatePath(String filePath) {
		String finalPath = null;
		try {
			File file = new File(filePath);
			finalPath = file.getAbsolutePath();
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return finalPath;
	}

	private void checkDirectory(String inputPath) {
		try {
			File file = new File(inputPath);
			String abPath = file.getAbsolutePath();
			File file2 = new File(abPath);

			if (!file2.exists()) {
				if (file2.mkdirs()) {
					System.out.println("Directories are created...");
				} else {
					System.out.println("Directories not created...");
				}
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public WebDriver startRemoteBrowser(String hubURL, String browser) {
		String tempHubURL = "http://192.168.1.82:1521/wd/hub";
		DesiredCapabilities cap = null;
		try {
			if (browser.contains("chrome")) {
				cap = DesiredCapabilities.chrome();
			} else if (browser.contains("ie")) {
				cap = DesiredCapabilities.internetExplorer();
			} else if (browser.contains("firefox")) {
				cap = DesiredCapabilities.firefox();
			} else {
				logger.info("You choose: '" + browser + "', Currently framework doesn`t support it.");
				logger.info("Starting default browser 'Internet Explorer'");
				cap = DesiredCapabilities.internetExplorer();
			}
			driver = new RemoteWebDriver(new URL(tempHubURL), cap);
			isRemote = true;
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);

		}

		return driver;
	}

	public WebDriver locaBrowsers(String browser) {

		try {
			browser = System.getProperty("browserV");
			if (browser.contains("chrome")) {
				driver = chromeDriver();
			} else if (browser.contains("firefox")) {
				driver = fireFoxDriver();
			} else if (browser.contains("ie")) {
				driver = ieDriver();
			} else if (browser.contains("headless")) {
				driver = chromeDHeadless();
			} else {
				// default browser
				driver = ieDriver();
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			Assert.assertTrue(false);
		}
		return driver;
	}

	public WebDriver switchToWindow(int index) {

		try {
			Set<String> allBrowsers = driver.getWindowHandles();
			Iterator<String> iterator = allBrowsers.iterator();
			List<String> windowHandles = new ArrayList<>();
			while (iterator.hasNext()) {
				String window = iterator.next();
				windowHandles.add(window);
			}
			// switch to index N
			driver.switchTo().window(windowHandles.get(index));
			// highlightElement(By.tagName("body"));
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return driver;
	}

	public void fileUpload(By by, String filePath) {
		try {
			WebElement fileUploadElem = driver.findElement(by);
			File tempFile = new File(filePath);
			String fullPath = tempFile.getAbsolutePath();
			if (isRemote == true) {
				((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
			}
			fileUploadElem.sendKeys(fullPath);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	/**
	 * This method detects alert and returns Alert object
	 * 
	 * @return Alert object
	 */
	public Alert isAlertPresent() {
		Alert alert = null;
		try {
			alert = driver.switchTo().alert();
			logger.info("Alert detected: {" + alert.getText() + "}");
		} catch (Exception e) {
			logger.info("Alert Not detected!");
		}
		return alert;
	}

	public void scrollToWebElementByPixel() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,200)");
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

}
