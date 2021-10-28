package com.myapp.spring.web.api;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.myapp.spring.model.Product;
import com.myapp.spring.repository.ProductRepository;
import com.myapp.spring.service.ShoppingCartService;

@SpringBootTest
@AutoConfigureMockMvc   //(addFilters = false)

public class ShoppingCartAPITest {

	@MockBean
	private ShoppingCartService service;
	
	@MockBean
	private ProductRepository repository;
	
	@Autowired
	private MockMvc mockMvc;



	@Test
	@DisplayName("Test get all products in cart -")
	@WithMockUser(username="himalaya001",password="password",roles="USER")
	public void testGetProductsInCart() throws Exception {

		// Prepare Mock Product
		Product product = new Product("mobile", "Oneplus", "OnePlus9Pro", 60000.00, 4);
		product.setProductId(1L);
		Integer quantity=2;
		HashMap<Product,Integer> cartItem1=new HashMap<Product,Integer>();
		cartItem1.put(product,quantity);
		
//		doReturn(Optional.of(product)).when(repository).findById(1);
//		doReturn(product).when(repository).save(ArgumentMatchers.any());
	
		// Prepare Mock Service Method

		doReturn(cartItem1).when(service).getProductsInCart();

		// Perform GET Request

		MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/shoppingCart"))
				// Validate Status should be 200 ok and json response recived

				.andExpect(status().isOk())
				.andReturn();
		String content = result.getResponse().getContentAsString();
		String expectedContent="{\""+String.valueOf(product)+"\""+":"+String.valueOf(quantity)+"}";
		System.out.println(expectedContent);
		Assertions.assertEquals(content,expectedContent);
	}
	
	@Test
	@DisplayName("Test add product in cart ")
	@WithMockUser(username="himalaya001",password="password",roles="USER")
	public void testAddProductInCart() throws Exception {

		// Prepare Mock Product
		Product product = new Product("mobile", "Oneplus", "OnePlus9Pro", 60000.00, 4);
		product.setProductId(1L);
		Integer quantity=2;
		Map<Product,Integer> cartItem1=new HashMap<Product,Integer>();
		cartItem1.put(product,quantity);
			
		// Prepare Mock Service Method
		doReturn(Optional.of(product)).when(repository).findById(1L);
		doReturn(product).when(repository).save(ArgumentMatchers.any());
		
		doReturn(cartItem1).when(service).getProductsInCart();
		
		doReturn(cartItem1).when(service).addProduct(product,quantity);

		// Perform GET Request

		MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/shoppingCart/addProduct/{productId}/{quantity}",1,2))
				// Validate Status should be 200 ok and json response recived

				.andExpect(status().isOk())
				.andReturn();
		String content = result.getResponse().getContentAsString();
		String expectedContent="{\""+String.valueOf(product)+"\""+":"+String.valueOf(quantity)+"}";
		Assertions.assertEquals(expectedContent,content);
	}
	@Test
	@DisplayName("Test remove product from cart ")
	@WithMockUser(username="himalaya001",password="password",roles="USER")
	public void testRemoveProductFromCart() throws Exception {

		// Prepare Mock Product
		Product product = new Product("mobile", "Oneplus", "OnePlus9Pro", 60000.00, 4);
		product.setProductId(1L);
		Integer quantity=2;
		HashMap<Product,Integer> cartItem1=new HashMap<Product,Integer>();
		cartItem1.put(product,quantity);
		
	
		// Prepare Mock Service Method
		doReturn(Optional.of(product)).when(repository).findById(1L);
		doReturn(product).when(repository).save(ArgumentMatchers.any());
		doReturn(cartItem1).when(service).getProductsInCart();
		doReturn(cartItem1).when(service).removeProduct(product,quantity);
		service.addProduct(product, quantity);

		// Perform GET Request

		MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/shoppingCart/removeProduct/{productId}/{quantity}",1,2))
				// Validate Status should be 200 ok and json response recived

				.andExpect(status().isOk())
				.andReturn();
		String content = result.getResponse().getContentAsString();
		String expectedContent="{\""+String.valueOf(product)+"\""+":"+String.valueOf(quantity)+"}";
		Assertions.assertEquals(expectedContent,content);
	}
	
	@Test
	@DisplayName("Test Checkout from cart ")
	@WithMockUser(username="himalaya001",password="password",roles="USER")
	public void testCheckoutFromCart() throws Exception {

		// Prepare Mock Product
		
		HashMap<Product,Integer> cartItem1=new HashMap<Product,Integer>();
	
		// Prepare Mock Service Method

		doReturn(cartItem1).when(service).checkout();

		// Perform GET Request

		MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/shoppingCart/checkout"))
				// Validate Status should be 200 ok and json response recived

				.andExpect(status().isOk())
				.andReturn();
		String content = result.getResponse().getContentAsString();
		String expectedContent="{}";
		Assertions.assertEquals(expectedContent,content);
	}
	
	@Test
    @DisplayName("Test remove product from checkout as registered user ")
    @WithMockUser(username="himalaya001",password="password",roles="USER")
    public void testRemoveProductFromCheckoutasRegisteredUser() throws Exception {
        
        // Prepare Mock Product
        Product product = new Product("mobile", "Oneplus", "OnePlus9Pro", 60000.00, 4);
        product.setProductId(1L);
        Integer quantity=2;
        HashMap<Product,Integer> checkoutitem=new HashMap<Product,Integer>();
        checkoutitem.put(product,quantity);
        
        doReturn(Optional.of(product)).when(repository).findById(1L);
        doReturn(product).when(repository).save(ArgumentMatchers.any());
        doReturn(checkoutitem).when(service).checkout();
        doReturn(checkoutitem).when(service).checkoutremove(product);
        service.addProduct(product, quantity);
    
         Mockito.when(service.checkoutremove(product)).thenReturn(null);
            mockMvc.perform(MockMvcRequestBuilders.post("/registereduser/checkout/removeProduct/{productId}",1L))
                    .andExpect(status().isOk());
        
            
    }
	
	@Test
    @DisplayName("Test remove product from checkout as guest user ")
    @WithMockUser(username="himalaya001",password="password",roles="USER")
    public void testRemoveProductFromCheckoutasGuestUser() throws Exception {
        
        // Prepare Mock Product
        Product product = new Product("mobile", "Oneplus", "OnePlus9Pro", 60000.00, 4);
        product.setProductId(1L);
        Integer quantity=2;
        HashMap<Product,Integer> checkoutItem1=new HashMap<Product,Integer>();
        checkoutItem1.put(product,quantity);
        
        doReturn(Optional.of(product)).when(repository).findById(1L);
        doReturn(product).when(repository).save(ArgumentMatchers.any());
        doReturn(checkoutItem1).when(service).checkout();
        doReturn(checkoutItem1).when(service).checkoutremove(product);
        service.addProduct(product, quantity);
    
         Mockito.when(service.checkoutremove(product)).thenReturn(null);
            mockMvc.perform(MockMvcRequestBuilders.post("/guestuser/checkout/removeProduct/{productId}",1L))
                    .andExpect(status().isOk());
        
            
    }
	
	

}
