import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
	@FindBy(id = "customer_firstname")
	private WebElement customerfirstname;
	private String other="Hi , I am Niv and I am really excited of the opportunity to be part of your"
			+ " company , I hope you will like my assignment."; 
	private int emailNumber=1557;
	WebDriverWait wait; 
	
	
	@BeforeClass(description="initating WebDriver and open website")
	public void init(){
		
		//create and setup WebDriver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\arady\\Desktop\\chromedriver.exe");
		this.driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		this.wait = new WebDriverWait(driver,Duration.ofSeconds(15));
		
		
		//Open browser in SignIn page
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=history");
		this.softAssertion= new SoftAssert();
		
		try {
			Assert.assertEquals(driver.getTitle(), "Login - My Store");
		}
		catch(AssertionError e) {
			driver.get("http://automationpractice.com/index.php");
			WebElement loginPage=driver.findElement(By.className("login"));
			Assert.assertEquals(loginPage.getText(), "Sign in","can't find login page");
			loginPage.click();
		
		}		
	}
	@Test(priority=1, description="Create an account")
	public void signupTest()  {
		  
		  
		  //Enter email address
		  //if email address taken - try another one	
		  String alertString;
		  String alert;
		  while(true || !alertString.equals("An account using this email address has already been registered. Please enter a valid password or request a new one.")) {
			  driver.findElement(By.cssSelector("[name='email_create']")).clear();
			  driver.findElement(By.cssSelector("[name='email_create']")).sendKeys("arady"+this.emailNumber+"@gmail.com");
			  
			  driver.findElement(By.xpath("//button[@name=\"SubmitCreate\"]")).click();
			  try {
			  alert=driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/ol[1]/li[1]")).getText();
			  }
			  catch(Exception e) {
			  	break;
			  }
			  
			  if(alert.trim().equals("An account using this email address has already been registered. Please enter a valid password or request a new one."))
				  this.emailNumber++;
			  else 
				  break; 
		  }
		  
		 System.out.println("Mail Address Used : arady"+this.emailNumber+"@gmail.com");
		 		
		 //fill sign in form
		  driver.findElement(By.id("id_gender1")).click(); 
		  driver.findElement(By.name("customer_firstname")).sendKeys("Niv");
		  driver.findElement(By.name("customer_lastname")).sendKeys("Arad");
		  driver.findElement(By.id("passwd")).sendKeys("12345");
		  
		  WebElement selectElement=driver.findElement(By.name("days"));
		  Select select=new Select(selectElement);
		  select.selectByValue("9");
		  selectElement=driver.findElement(By.name("months"));
		  select=new Select(selectElement);
		  select.selectByValue("3");
		  selectElement=driver.findElement(By.name("years"));
		  select=new Select(selectElement);
		  select.selectByValue("1996");
		  
		  driver.findElement(By.name("company")).sendKeys("SecuritiThings");
		  driver.findElement(By.id("address1")).sendKeys("Hey BeiHar");
		  driver.findElement(By.id("address2")).sendKeys("132 Apartment 1");
		  driver.findElement(By.id("city")).sendKeys("Rosh HaAyin");
		  driver.findElement(By.id("postcode")).sendKeys("48056");
		  driver.findElement(By.id("other")).sendKeys(this.other);
		  driver.findElement(By.name("phone_mobile")).sendKeys("054-5672517");
		  
		  driver.findElement(By.id("uniform-optin")).click();
		  selectElement=driver.findElement(By.id("id_state"));
		  select=new Select(selectElement);
		  select.selectByIndex(1);
		  
		  driver.findElement(By.id("alias")).clear();
		  driver.findElement(By.id("alias")).sendKeys("Home");
		  
		  driver.findElement(By.id("submitAccount")).click();
		  
		  //verify that the account was created
		  try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  if(driver.findElement(By.tagName("h1")).getText().equals("MY ACCOUNT")) {
			  System.out.println("User Created, Signing up done! \n Test Case 1 Passed");
		  }
		  else {
			  System.out.println("A problem occured , the user wasn't verified by the system \n Test failed");
			  assertEquals(true, false);
		  }
	}
	
	@Test(priority=2,description="Add items to cart")
	public void addItemsToCart() {
		
		//move to the products page and create a list of products
		driver.get("http://automationpractice.com/index.php?id_category=3&controller=category");	
		System.out.println(driver.findElement(By.className("navigation_page")).getText());
		List<WebElement> images=driver.findElements(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[3]/div[2]/ul[1]/li/div[1]/div[1]/div[1]/a[1]/img[1]"));
		for(int i=0;i<images.size();i++) {
			//if the product is a dress - order it
			WebElement imageLink=images.get(i);
			if(imageLink.getAttribute("alt").contains("Dress")) {
				imageLink.click();
				WebElement iframe=driver.findElement(By.xpath("//iframe[@class='fancybox-iframe']"));
				driver.switchTo().frame(iframe);
				
				driver.findElement(By.name("Submit")).click();
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cross"))).click();
				
				driver.switchTo().defaultContent();
				
				
			}
		}
		 System.out.println("All dresses were added to the cart! \n Test Case 2 Passed");
	}
	
	
	
	@Test(priority=3, description="Buy items found in the cart",dependsOnMethods = {"addItemsToCart","signupTest"})
	public void CompleteOrder() {
		driver.get("http://automationpractice.com/index.php?controller=order");
		driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[3]/div[1]/p[2]/a[1]/span[1]")).click();
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
			
			file=new File("src/OrdersCompleted/"+"arady"+this.emailNumber+".txt");	
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
		driver.close();
	}

}
