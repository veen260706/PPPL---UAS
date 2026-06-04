package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ExamFormPage {

    private AppiumDriver driver;

    public ExamFormPage(AppiumDriver driver){
        this.driver = driver;
    }

    private By doctorDropdown = By.id("doctorDropdown");
    private By notesField = By.id("notesField");
    private By saveButton = By.id("saveButton");
    private By successMessage = By.id("successMessage");

    public void selectDoctor(){
        driver.findElement(doctorDropdown).click();
    }

    public void inputNotes(String notes){
        driver.findElement(notesField).sendKeys(notes);
    }

    public void clickSave(){
        driver.findElement(saveButton).click();
    }

    public String getSuccessMessage(){
        return driver.findElement(successMessage).getText();
    }
}