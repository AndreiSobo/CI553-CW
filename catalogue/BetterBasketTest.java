package catalogue;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BetterBasketTest {
    
    private BetterBasket basket;

    @Before
    public void setUp() {
        basket = new BetterBasket();
    }

    @Test
    public void testGetDetailsWithEmptyBasket() {
        // Call getDetails and check the output
        String details = basket.getDetails();
        assertTrue(details.isEmpty()); // Assuming getDetails returns an empty string for an empty basket
    }

    @Test
    public void testGetDetailsWithOneProduct() {
        // Add a product to the basket
        Product product = new Product("1", "Product 1", 10.0, 2);
        basket.add(product);

        // Call getDetails and check the output
        String details = basket.getDetails();
        assertTrue(details.contains("Product 1"));
        assertTrue(details.contains("Total £20.0")); // 2*10 = 20
    }

    @Test
    public void testGetDetailsWithMultipleProducts() {
        // Add some products to the basket
        Product product1 = new Product("1", "Product 1", 10.0, 2);
        basket.add(product1);

        Product product2 = new Product("2", "Product 2", 20.0, 3);
        basket.add(product2);

        // Call getDetails and check the output
        String details = basket.getDetails();
        assertTrue(details.contains("Product 1"));
        assertTrue(details.contains("Product 2"));
        assertTrue(details.contains("Total £80.0")); // 2*10 + 3*20 = 70
    }
}