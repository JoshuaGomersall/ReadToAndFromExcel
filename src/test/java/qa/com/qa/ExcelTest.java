package qa.com.qa;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public class ExcelTest {

	WebDriver driver;

	@Before
	public void startup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Desktop\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Ignore
	@Before
	@Test
	public void getInfoTest() throws IOException {

		FileInputStream file = new FileInputStream("C:\\Users\\Admin\\Desktop\\DemoSiteDDT.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);

		// Reading
		for (int rowNum = 0; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
			for (int colNum = 0; colNum < sheet.getRow(rowNum).getPhysicalNumberOfCells(); colNum++) {
				XSSFCell cell = sheet.getRow(rowNum).getCell(colNum);
				String userCell = cell.getStringCellValue();
				System.out.println(userCell);
			}
		}
		file.close();
	}

	@Test
	public void test() throws InterruptedException, IOException {

		List<String> users = new ArrayList<>();
		List<String> passwords = new ArrayList<>();

		users.add("TestOne");
		users.add("TestTwo");
		users.add("TestThree");
		users.add("TestFour");
		passwords.add("PassOne");
		passwords.add("PassTwo");
		passwords.add("PassThree");
		passwords.add("PassFour");

		System.out.println(users);
		System.out.println(passwords);

		
		for (int i = 0; i < 4; i++) {

			String username = users.get(i);
			String password = passwords.get(i);

			driver.manage().window().maximize();
			driver.get("http://thedemosite.co.uk/");

			WebPageHome homepage = PageFactory.initElements(driver, WebPageHome.class);
			homepage.searchForAndClick();

			MakeUserPage createpage = PageFactory.initElements(driver, MakeUserPage.class);
			createpage.searchForAndLogin(username, password);

			driver.get("http://thedemosite.co.uk/login.php");
			LoginPage loginpage = PageFactory.initElements(driver, LoginPage.class);
			loginpage.searchForAndLogin(username, password);

			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Desktop computer = Desktop.getDesktop();
			computer.open(scrFile);

			loginpage.clicksubmit();

			File scrFile2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Desktop computer2 = Desktop.getDesktop();
			computer.open(scrFile2);

			Thread.sleep(50);
		}
		
		Thread.sleep(500);
	}
}
