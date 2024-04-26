package com.softserve.edu.opencart.pages;

//import com.softserve.edu.opencart.data.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ProductsContainer {

    public final String PRODUCT_NOT_FOUND = "There is no product that matches the search criteria.";
    private final String PRODUCT_COMPONENT_CSSSELECTOR = "div#content div.col";
    //
    protected WebDriver driver;
    //protected Search search;
    //
    private List<ProductComponent> productComponents;

    public ProductsContainer(WebDriver driver) {
    //public ProductsContainer() {
        this.driver = driver;
        //search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        // init elements
        productComponents = new ArrayList<>();
        for (WebElement current : driver.findElements(By.cssSelector(PRODUCT_COMPONENT_CSSSELECTOR))) {
        //for (WebElement current : search.cssSelectors(PRODUCT_COMPONENT_CSSSELECTOR)) {
            productComponents.add(new ProductComponent(current));
        }
    }

    // Page Object

    // productComponents
    public List<ProductComponent> getProductComponents() {
        return productComponents;
    }

    // Functional

    public int getProductComponentsCount()
    {
        return getProductComponents().size();
    }

    public List<String> getProductComponentNames()
    {
        List<String> productComponentNames = new ArrayList<>();
        for (ProductComponent current : getProductComponents())
        {
            productComponentNames.add(current.getNameText());
        }
        return productComponentNames;
    }

    public ProductComponent getProductComponentByName(String productName)
    {
        ProductComponent result = null;
        for (ProductComponent current : getProductComponents())
        {
            if (current.getNameText().toLowerCase()
                    .equals(productName.toLowerCase()))
            {
                result = current;
                break;
            }
        }
        if (result == null)
        {
            // TODO Develop Custom Exception
            // Use String.format()
            throw new RuntimeException("ProductName: " + productName + " not Found.");
        }
        return result;
    }

    // TODO Move to Product
    public String getProductComponentPriceByName(String productName)
    //public String getProductComponentPriceByName(Product productName)
    {
        return getProductComponentByName(productName).getPriceText();
        //return getProductComponentByName(productName.getName()).getPriceText();
    }

    // TODO Move to Product
    public String getProductComponentDescriptionByName(String productName)
    {
        return getProductComponentByName(productName).getPartialDescriptionText();
    }

    // TODO Move to Product
    public void clickProductComponentAddToCartButtonByName(String productName)
    {
        getProductComponentByName(productName).clickAddToCartButton();
    }

    // TODO Move to Product
    public void clickProductComponentAddToWishButtonByName(String productName)
    {
        getProductComponentByName(productName).clickAddToWishButton();
    }

    /*-
    public String getProductComponentPriceByProduct(Product product)
    {
        return getProductComponentPriceByName(product.getName());
    }

    public String getProductComponentDescriptionByProduct(Product product)
    {
        return getProductComponentDescriptionByName(product.getName());
    }
    */

    // Business Logic

    /*-
    public ProductComponent getProductComponentByName(Product product)
    {
        return getProductComponentByName(product.getName());
    }
    */

}

