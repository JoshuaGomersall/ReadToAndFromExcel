package qa.com.qa;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

@RunWith(Parameterized.class)
public class Para {

	WebDriver driver;

	@Parameters
	public static Collection<Object[]> data() throws IOException {

		FileInputStream file = new FileInputStream("C:\\Users\\Admin\\Desktop\\DemoSiteDDT.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);

		for (int rowNum = 1; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
			for (int colNum = 0; colNum < 2; colNum++) {
				XSSFCell cell = sheet.getRow(rowNum).getCell(colNum);
				String userCell = cell.getStringCellValue();
			}
		}

		Object[][] ob = new Object[sheet.getPhysicalNumberOfRows() - 1][2];

		for (int row = 1; row < sheet.getPhysicalNumberOfRows(); row++) {
			for (int col = 0; col < 2; col++) {
				ob[row - 1][col] = sheet.getRow(row).getCell(col).getStringCellValue();
			}
		}
		return Arrays.asList(ob);
	}

	private String x;
	private String y;

	public Para(String x, String y) {
		this.x = x;
		this.y = y;
	}
	
	

	@Before
	public void startup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Desktop\\chromedriver.exe");
	}

	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void accounttest() throws IOException, InterruptedException {
		driver = new ChromeDriver();
		
		driver.manage().window().setPosition(new Point(-2000, 0));
		
		//driver.manage().window().maximize();
		driver.get("http://thedemosite.co.uk/");

		WebPageHome homepage = PageFactory.initElements(driver, WebPageHome.class);
		homepage.searchForAndClick();

		MakeUserPage createpage = PageFactory.initElements(driver, MakeUserPage.class);
		createpage.searchForAndLogin(x, y);

		driver.get("http://thedemosite.co.uk/login.php");
		LoginPage loginpage = PageFactory.initElements(driver, LoginPage.class);
		loginpage.searchForAndLogin(x, y);

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Desktop computer = Desktop.getDesktop();
		computer.open(scrFile);

		loginpage.clicksubmit();

		File scrFile2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Desktop computer2 = Desktop.getDesktop();
		computer.open(scrFile2);

		Thread.sleep(50);
	}
}
