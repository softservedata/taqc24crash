package com.softserve.edu.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropdownComponent {

    //public static final Logger logger = LoggerFactory.getLogger(DropdownComponent.class); // org.slf4j.LoggerFactory
    protected final String OPTIONNAME_NOT_FOUND = "OptionName not Found.";
    //
    protected WebDriver driver;
    //protected Search search;
    //
    private List<WebElement> listOptions;

    public DropdownComponent(WebDriver driver, By searchLocator) {
    //public DropdownComponent(By searchLocator) {
        this.driver = driver;
        //search = SearchStrategy.getSearch();
        initElements(searchLocator);
    }

    private void initElements(By searchLocator) {
        // init elements
        listOptions = driver.findElements(searchLocator);
        //
        //listOptions = search.searchWebElements(searchLocator); // for Strategy
    }

    // Page Object

    // listOptions
    public List<WebElement> getListOptions() {
        return listOptions;
    }

    // Functional

    // listOptions
    public WebElement getDropdownOptionByPartialName(String optionName) {
        WebElement result = null;
        for (WebElement current : getListOptions()) {
            if (current.getText().toLowerCase().contains(optionName.toLowerCase())) {
                result = current;
                break;
            }
        }
        // TODO Move to Utility
        if (result == null) {
            // TODO Develop Custom Exception 
            throw new RuntimeException(OPTIONNAME_NOT_FOUND);
        }
        return result;
    }

    public List<String> getListOptionsText() {
        List<String> result = new ArrayList<>();
        for (WebElement current : getListOptions()) {
            result.add(current.getText());
        }
        return result;
    }

    public boolean isExistDropdownOptionByPartialName(String optionName) {
        boolean isFound = false;
        for (String current : getListOptionsText()) {
            if (current.toLowerCase().contains(optionName.toLowerCase())) {
                isFound = true;
                break;
            }
        }
        return isFound;
    }

    public void clickDropdownOptionByPartialName(String optionName) {
        getDropdownOptionByPartialName(optionName).click();
    }

    // Business Logic

}