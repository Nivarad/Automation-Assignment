package PageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class Utils {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private WomenPage womenPage;
    private LoginPage loginPage;


    public Utils(WebDriver driver,WebDriverWait wait){
        this.driver= driver;
        this.wait=wait;
    }

    public String[] purchaseCart(){

        //check that cart is not empty - if it is empty add products

        driver.get("http://automationpractice.com/index.php?controller=order");
        driver.findElement(By.xpath("//a[@class='button btn btn-default standard-checkout button-medium']//span[contains(text(),'Proceed to checkout')]")).click();
        String user=insureLoggedIn();
        driver.findElement(By.xpath("//button[@type='submit']//span[contains(text(),'Proceed to checkout')]")).click();
        driver.findElement(By.id("cgv")).click();
        driver.findElement(By.xpath("//button[@type='submit']//span[contains(text(),'Proceed to checkout')]")).click();
        driver.findElement(By.className("bankwire")).click();
        driver.findElement(By.xpath("//button[@class='button btn btn-default button-medium']")).click();

        String message = driver.findElement(By.className("box")).getText();

        if (message.toLowerCase().contains("your order on my store is complete.")) {
            //save message to a file

            System.out.println("Order completed \n Test Case 3 Passed");

            saveUserFile(message,user);
            String[] strArray=new String[] {message,user};
            return strArray;
        }

       return null;
    }
    public boolean checkIfCartEmpty(){
        driver.get("http://automationpractice.com/index.php");
        String productCounter = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/header[1]/div[3]/div[1]/div[1]/div[3]/div[1]/a[1]/span[1]")).getText();
        if (productCounter.equals("") || Integer.parseInt(productCounter) == 0)

            return true;
        return false;


    }
    public void saveUserFile(String message,String user){
            //create a file that contains the order details and further instructions
            File file = null;
            file = new File("src/OrdersCompleted/" + user + ".txt");
            LocalDate date = LocalDate.now(); // Create a date object

            //save exam in the file
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                System.out.println("File not found, further instructions weren't saved");
            }
            writer.print(date + "\n"+message);
            writer.close();
    }
    private String insureLoggedIn(){
        if (driver.findElement(By.className("page-heading")).getText().equals("AUTHENTICATION")) {
            loginPage = new LoginPage(driver);
            String user = "arady1549@gmail.com";
            loginPage.buyCartAuthentication(user, "12345");
            return user;
        }
        else
        {
            String currUrl= driver.getCurrentUrl();
            driver.get("http://automationpractice.com/index.php?controller=identity");
            String user=driver.findElement(By.id("email")).getAttribute("value");
            driver.get(currUrl);
            return user;
        }

    }

}
