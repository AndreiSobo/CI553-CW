package catalogue;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class BasketTest {
    
    private Basket basket;
    
    @Before
    public void setUp() {
        basket = new Basket();
    }
    
    @Test
    public void testSetOrderNum() {
        int orderNum = 123;
        basket.setOrderNum(orderNum);
        assertEquals(orderNum, basket.getOrderNum());
    }
    
    @Test
    public void testAddProduct() {
        Product product = new Product("P001", "Product 1", 10.0, 2);
        boolean result = basket.add(product);
        assertEquals(true, result);
        assertEquals(1, basket.size());
        assertEquals(product, basket.get(0));
    }
    
    @Test
public void testGetDetails() {
    Product product1 = new Product("P001","Product 1",10.0, 2);
    basket.add(product1);

    String expectedDetails = "P001 Product 1 (2) £20.0\n----------------------------\nTotal £20.0\n";

    assertEquals(expectedDetails, basket.getDetails());
}

    
    @Test
    public void testEmptyBasket() {
        assertEquals(0, basket.size());
        assertEquals("", basket.getDetails());
    }
    
    @Test
    public void testAddMultipleProducts() {
        Product product1 = new Product("P001","Product 1",10.0, 2);
        Product product2 = new Product("P002","Product 2",5.0, 3);
        Product product3 = new Product("P003","Product 3",8.0, 1);
        
        basket.add(product1);
        basket.add(product2);
        basket.add(product3);
        
        assertEquals(3, basket.size());
        assertEquals(product1, basket.get(0));
        assertEquals(product2, basket.get(1));
        assertEquals(product3, basket.get(2));
    }
    
    @Test
    public void testRemoveProduct() {
        Product product1 = new Product("P001","Product 1",10.0, 2);
        Product product2 = new Product("P002","Product 2",5.0, 3);
        
        basket.add(product1);
        basket.add(product2);
        
        boolean result = basket.remove(product1);
        
        assertEquals(true, result);
        assertEquals(1, basket.size());
        assertEquals(product2, basket.get(0));
    }
    
    // Add more test cases as needed
    
}
