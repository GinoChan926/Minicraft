package org.minicraft02160;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.minicraft02160.model.Items.IronAxeItem;
import org.minicraft02160.model.Items.ResourceItem;

import static org.junit.jupiter.api.Assertions.*;

public class ItemSteps {

    private IronAxeItem item;
    private ResourceItem apple;

    @Given("an Iron Axe item is created")
    public void anIronAxeItemIsCreated() {
        item = new IronAxeItem();
    }

    @Given("an Apple item is created")
    public void anAppleItemIsCreated() {
        apple = new ResourceItem("Apple", true, 10);    }

    @Then("the item name should be {string}")
    public void theItemNameShouldBe(String arg0) {
        switch (arg0) {
            case "Iron Axe":
                assertEquals(arg0, item.getName());
                break;
            case "Apple":
                assertEquals(arg0, apple.getName());
                break;
        }
    }

}
