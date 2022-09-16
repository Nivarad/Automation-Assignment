import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import PageObjects.LoginPage;
import PageObjects.SignupPage;
import PageObjects.WomenPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Tests {
	private WebDriver driver;
	private SoftAssert softAssertion;

	private String other="Hi , I am Niv and I am really excited of the opportunity to be part of your"
			+ " company , I hope you will like my assignment."; 
	private int emailNumber; 
	WebDriverWait wait;
	private LoginPage loginPage;
	private SignupPage signPage;
	private WomenPage womenPage;
	private String user="";
	
	
	@BeforeClass(description="initating WebDriver and open website")
	public void init(){
		
		//create and setup WebDriver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\arady\\Desktop\\chromedriver.exe");
		this.driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		this.wait = new WebDriverWait(driver,Duration.ofSeconds(15));
	}
//	@Test(priority=1, description="Create an account")
	public void signupTest()  {
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		signPage=new SignupPage(driver);
		loginPage=new LoginPage(driver);

		String mail=loginPage.createMail();
		if(mail.equals(""))
			assertEquals(false,true,"No mail was generated for the test");
		String password="12345";
		boolean error=signPage.signup("male","Niv","Arad",password,9,3,1996,"SecuritiThings",
				"Hey BeiHar","132 Apartment 1","Rosh HaAyin","48056",this.other,"0545672517",1,"Home");
		assertEquals(error,true,"There are errors in the information that was filled ");
		boolean checkExist=loginPage.verifyUserExist(mail,password);
		assertEquals(checkExist,true,"The user wasn't created , test Failed");
		System.out.println("User : "+mail +" was created \nTest Case 1 Passed!");
		this.user=mail;


	}
	
//	@Test(priority=2,description="Add items to cart")
	public void addItemsToCart() {
		womenPage=new WomenPage(driver,wait);
		womenPage.insertDressesToCart();
		String productCounter=driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/header[1]/div[3]/div[1]/div[1]/div[3]/div[1]/a[1]/span[1]")).getText();
		if(Integer.parseInt(productCounter) <Integer.parseInt("2"))
			assertEquals(true,false,"Not enough products were added\nTest Case 2 Failed");
		System.out.println( productCounter+" products were added to cart\nTest Case 2 Passed!");

	}
	
	
	
	@Test(priority=3, description="Buy items found in the cart")
	public void CompleteOrder() {

		//check that cart is not empty - if it is empty add products
		driver.get("http://automationpractice.com/index.php");
		String productCounter=driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/header[1]/div[3]/div[1]/div[1]/div[3]/div[1]/a[1]/span[1]")).getText();
		if(productCounter.equals("") ||Integer.parseInt(productCounter)>0){
			womenPage=new WomenPage(driver,wait);
			womenPage.insertDressesToCart();
		}
		String user="";
		driver.get("http://automationpractice.com/index.php?controller=order");
		driver.findElement(By.xpath("//a[@class='button btn btn-default standard-checkout button-medium']//span[contains(text(),'Proceed to checkout')]")).click();
		if(driver.findElement(By.className("page-heading")).getText().equals("AUTHENTICATION")){
			loginPage=new LoginPage(driver);
			user="arady1549@gmail.com";
			loginPage.buyCartAuthentication(user,"12345");
		}

		driver.findElement(By.xpath("//button[@type='submit']//span[contains(text(),'Proceed to checkout')]")).click();
		driver.findElement(By.id("cgv")).click();
		driver.findElement(By.xpath("//button[@type='submit']//span[contains(text(),'Proceed to checkout')]")).click();
		driver.findElement(By.className("bankwire")).click();
		driver.findElement(By.xpath("//button[@class='button btn btn-default button-medium']")).click();
		
		String message=driver.findElement(By.className("box")).getText();
		
		if(message.contains("Your order on My Store is complete.")) {
			//save message to a file
			
			System.out.println("Order completed \n Test Case 3 Passed");
			
			
			//create a file that contains the order details and further instructions 
			File file=null;
			if(user.equals(""))
				file=new File("src/OrdersCompleted/"+this.user+".txt");
			else
				file=new File("src/OrdersCompleted/"+user+".txt");

			//save exam in the file
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			writer.print(message);
			writer.close();	
		}
		
	}
	
	@AfterClass(description="closing WebDriver")
	public void closeConnection() {
		//driver.close();
	}

}
