package com.myapp.spring.web.api;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.spring.model.Product;
import com.myapp.spring.repository.ProductRepository;

@SpringBootTest

@AutoConfigureMockMvc

public class ProductAPITest {

	@MockBean
	private ProductRepository repository;
	
	

//	@Autowired
//	private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	@DisplayName("Test Product by ID - GET products/id")
	public void testGetProductsById() throws Exception {

		// Prepare Mock Product
		Product product = new Product("mobile", "Oneplus", "OnePlus9Pro", 60000.00, 4);
		product.setProductId(1L);

		// Prepare Mock Service Method

		doReturn(Optional.of(product)).when(repository).findById(product.getProductId());

		// Perform GET Request

		mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}",1L))
				// Validate Status should be 200 ok and json response recived

				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

				// Validate Response body

				.andExpect(jsonPath("$.productId", is(1))).andExpect(jsonPath("$.productName", is("Oneplus")))
				.andExpect(jsonPath("$.description", is("OnePlus9Pro"))).andExpect(jsonPath("$.price", is(60000.00)))
				.andExpect(jsonPath("$.quantity", is(4)));

	}

	@Test
	@DisplayName("Test All Product - GET products/id")
	public void testGetAllProductsById() throws Exception {

		// Prepare Mock Product
		Product product1 = new Product("mobile", "Oneplus", "OnePlus9Pro", 70000.00, 4);
		product1.setProductId(25L);

		Product product2 = new Product("mobile", "Oneplus", "OnePlus8Pro", 60000.00, 35);
		product2.setProductId(26L);

		List<Product> products = new ArrayList<>();
		products.add(product1);
		products.add(product2);

		// Prepare Mock Service Method

		doReturn(products).when(repository).findAll();

		// Perform GET Request

		mockMvc.perform(MockMvcRequestBuilders.get("/products"))
				// Validate Status should be 200 ok and json response recived

				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

				// Validate Response body

				// Object1
				.andExpect(jsonPath("$[0].productId", is(25))).andExpect(jsonPath("$[0].productName", is("Oneplus")))
				.andExpect(jsonPath("$[0].description", is("OnePlus9Pro")))
				.andExpect(jsonPath("$[0].price", is(70000.00))).andExpect(jsonPath("$[0].quantity", is(4)))

				// Object2
				.andExpect(jsonPath("$[1].productId", is(26))).andExpect(jsonPath("$[1].productName", is("Oneplus")))
				.andExpect(jsonPath("$[1].description", is("OnePlus8Pro")))
				.andExpect(jsonPath("$[1].price", is(60000.00))).andExpect(jsonPath("$[1].quantity", is(35)));

	}

	@Test
	@DisplayName("Test Add New Product")
	@WithMockUser(username="admin",password="password",roles="ADMIN")
	public void testAddNewProducts() throws Exception {

		// Prepare Mock Product
		Product newproduct = new Product("mobiles", "Oneplus", "OnePlus9Pro", 70000.00, 45);
		newproduct.setProductId(28);
		Product mockproduct = new Product("mobiles", "Oneplus", "OnePlus9Pro", 70000.00, 45);
		mockproduct.setProductId(28);

		// Prepare Mock Service Method

		doReturn(mockproduct).when(repository).save(ArgumentMatchers.any());

		// Perform GET Request

		mockMvc.perform(post("/admin/saveNewProduct")
				// Validate Status should be 200 ok and json response recived
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(newproduct)))

				// Validate Response body

				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.productId", is(28))).andExpect(jsonPath("$.productName", is("Oneplus")))
				.andExpect(jsonPath("$.description", is("OnePlus9Pro"))).andExpect(jsonPath("$.price", is(70000.00)))
				.andExpect(jsonPath("$.quantity", is(45)));

	}

	@Test
	@DisplayName("Test All Product by price - GET /products/findByPrice")
	public void testGetAllProductsByPrice() throws Exception {

		// Prepare Mock Product
		Product product1 = new Product("mobiles", "Oneplus", "OnePlus9Pro", 70000.00, 4);
		product1.setProductId(1L);

		Product product2 = new Product("mobiles", "Oneplus", "OnePlus8Pro", 60000.00, 35);
		product2.setProductId(2L);

		Product product3 = new Product("mobiles", "Oneplus", "OnePlus7Pro", 40000.00, 25);
		product3.setProductId(3L);

		List<Product> products = new ArrayList<>();
		products.add(product1);
		products.add(product2);
		products.add(product3);

		double price = 50000;
		// Prepare Mock Service Method

		doReturn(Optional.of(products)).when(repository).findByPriceGreaterThanEqual(price);

		// Perform GET Request

		mockMvc.perform(MockMvcRequestBuilders.get("/products/findByPrice/{price}", price))
				// Validate Status should be 200 ok and json response recived

				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

				// Validate Response body

				// Object1
				.andExpect(jsonPath("$[0].productId", is(1))).andExpect(jsonPath("$[0].productName", is("Oneplus")))
				.andExpect(jsonPath("$[0].description", is("OnePlus9Pro")))
				.andExpect(jsonPath("$[0].price", is(70000.00))).andExpect(jsonPath("$[0].quantity", is(4)))

				// Object2
				.andExpect(jsonPath("$[1].productId", is(2))).andExpect(jsonPath("$[1].productName", is("Oneplus")))
				.andExpect(jsonPath("$[1].description", is("OnePlus8Pro")))
				.andExpect(jsonPath("$[1].price", is(60000.00))).andExpect(jsonPath("$[1].quantity", is(35)));

		
	

	}
	@Test
    @DisplayName("Test Delete Product")
	@WithMockUser(username="admin",password="password",roles="ADMIN")
    public void testDeleteProducts() throws Exception {    
    
    Product product3 = new Product("Oneplus","OnePlus8Pro","Mobiles",40000.00,4);
    product3.setProductId(3);
    doReturn(Optional.of(product3)).when(repository).findById(product3.getProductId());
    
    mockMvc.perform(MockMvcRequestBuilders.delete("/admin/{id}",3))
    
    
    .andExpect(status().isOk())
    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    .andExpect(jsonPath("$.deleted",is(true)));
    
           }
	
	@Test
    @DisplayName("Test Update Product")
	@WithMockUser(username="admin",password="password",roles="ADMIN")
    public void testUpdateProduct() throws Exception {    
		Product newproduct = new Product("mobiles", "Oneplus", "OnePlus9", 70000.00, 45);
		newproduct.setProductId(1L);
		Product mockproduct = new Product("mobiles", "Oneplus", "OnePlus9Pro", 70000.00, 45);
		mockproduct.setProductId(1L);
		
		doReturn(Optional.of(mockproduct)).when(repository).findById(1L);
		doReturn(mockproduct).when(repository).save(ArgumentMatchers.any());
	
//		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(mockproduct);
		mockMvc.perform(put("/admin/{id}",1L)
				// Validate Status should be 200 ok and json response recived
				.contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
				.content(new ObjectMapper().writeValueAsString(newproduct)).accept(MediaType.APPLICATION_JSON))

				// Validate Response body

				.andExpect(status().isOk())//.andExpect(content().contentTypeCompatibleWith("application/json"))
				
				.andExpect(jsonPath("$.productId", is(1))).andExpect(jsonPath("$.productName", is("Oneplus")))
				.andExpect(jsonPath("$.description", is("OnePlus9Pro"))).andExpect(jsonPath("$.price", is(70000.00)))
				.andExpect(jsonPath("$.quantity", is(45)));
           }
	
	@Test
	@DisplayName("Test Product by Name - GET /products/findByName")
	public void testGetProductsByName() throws Exception {

		// Prepare Mock Product
		Product product = new Product("mobile", "Oneplus", "OnePlus9Pro", 60000.00, 4);
		product.setProductId(1L);
		
		List<Product> list = new ArrayList<>();
		list.add(product);
		
		// Prepare Mock Service Method


		doReturn(Optional.of(list)).when(repository).findByProductNameLike(product.getProductName());

		// Perform GET Request

		mockMvc.perform(MockMvcRequestBuilders.get("/products/findByName")
				.queryParam("productName", "Oneplus"))
				// Validate Status should be 200 ok and json response recived

				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

				// Validate Response body

				.andExpect(jsonPath("$[0].productId", is(1)))
				.andExpect(jsonPath("$[0].productName", is("Oneplus")))
				.andExpect(jsonPath("$[0].category", is("mobile")))
				.andExpect(jsonPath("$[0].description", is("OnePlus9Pro")))
				.andExpect(jsonPath("$[0].price", is(60000.00)))
				.andExpect(jsonPath("$[0].quantity", is(4)));

	}
	
	@Test
	@DisplayName("Test Product by Category - GET /products/findByCategory")
	public void testGetProductsByCategory() throws Exception {

		// Prepare Mock Product
		Product product = new Product("mobile", "Oneplus", "OnePlus9Pro", 60000.00, 4);
		product.setProductId(1L);
		
		List<Product> list = new ArrayList<>();
		list.add(product);
		
		// Prepare Mock Service Method


		doReturn(Optional.of(list)).when(repository).findByCategoryLike(product.getCategory());

		// Perform GET Request

		mockMvc.perform(MockMvcRequestBuilders.get("/products/findByCategory")
				.queryParam("category", "mobile"))
				// Validate Status should be 200 ok and json response recived

				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

				// Validate Response body
				.andExpect(jsonPath("$[0].productId", is(1)))
				.andExpect(jsonPath("$[0].productName", is("Oneplus")))
				.andExpect(jsonPath("$[0].category", is("mobile")))
				.andExpect(jsonPath("$[0].description", is("OnePlus9Pro")))
				.andExpect(jsonPath("$[0].price", is(60000.00)))
				.andExpect(jsonPath("$[0].quantity", is(4)));

	}
}
