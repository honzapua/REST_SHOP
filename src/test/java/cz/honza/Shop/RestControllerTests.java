package cz.honza.Shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.honza.Shop.domain.Customer;
import cz.honza.Shop.domain.Merchant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
// Test se spusti v kontexte spustene hlavni aplikace, tj. beans & services ready and running
@SpringBootTest     // je to jako by byla spustena cela aplikace ne jen, izolovane Unit testy
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)  // vycisti kontext nez ho zapneme vcetne h2db
@AutoConfigureMockMvc   //  Anotation needed because private MockMvc mockMvc;
// without this: No qualifying bean of type 'org.springframework.test.web.servlet.MockMvc' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)/
public class RestControllerTests {
    @Autowired
    private MockMvc mockMvc;    //  simulate client calls of application
    @Autowired  //without this: java.lang.IllegalArgumentException: WebApplicationContext is required
    private WebApplicationContext webApplicationContext;

    /*@Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }*/

    /*@Autowired*//*(required = true)*//*  //bean initialization
    private MockMvc mockMvc;*/
    private final ObjectMapper objectMapper = new ObjectMapper();   // transforms object to JSON

    @Test
    public void customer() throws Exception {   // avoid all methods which throws others exceptions
        //  test s prazdnou h2 db, nutno vytvorit customera
        Customer customer = new Customer("Pepa", "Vonasek", "guserp@mail.cz", "Naplavka", 40, "777123456");

        // Add customer
        String id = mockMvc.perform(post("/customer")   // POST call of REST endpoint, thanks to mockMvc
                        .contentType(MediaType.APPLICATION_JSON)    //  Json Header
                        .content(objectMapper.writeValueAsString(customer)))    // request closed ))) sends to body, converse to string representation to JSON object mapper
                        .andExpect(status().isCreated())    // expect 201 status    // isOk() is 200
                        .andReturn().getResponse().getContentAsString();    // finally store String to id variable

        System.out.println(id);     // expected id == 1
        customer.setId(objectMapper.readValue(id, Integer.class));  // newly created id load from z DB and cast by objectMapper

        // Get customer
        String customerJson = mockMvc.perform(get("/customer/" + customer.getId())  // GET call of REST
                        .contentType(MediaType.APPLICATION_JSON))   // do body neodesilame uz nic!
                        .andExpect(status().isOk())     // isOK() 200 only GET
                        .andReturn().getResponse().getContentAsString();
        customer.setId(objectMapper.readValue(id, Integer.class));   // finally store String to customerJson variable

        Customer returnedCustomer = objectMapper.readValue(customerJson, Customer.class);   // Create new instance and cast to Customer.class
        Assert.assertEquals(customer, returnedCustomer);    //  both Object should be same to green test


        //  Get all customers
        String listJson = mockMvc.perform(get("/customer")     //   List<Customer> response from getAll
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();

//        System.out.println(listJson);   //  try to cast to list of Customers
        List<Customer> customers = objectMapper.readValue(listJson, new TypeReference<List<Customer>>(){}); //remap JSON to a Customer list
        assert customers.size() == 1;   // if !=1 then test fails!
        Assert.assertEquals(customer, customers.get(0)); // what I expect as value, what really is value first and only element of List
    }

    @Test
    public void merchant() throws Exception {
        //  Add merchant
        Merchant merchant = new Merchant("name", "email", "address");

        String id = mockMvc.perform(post("/merchant")   //  POST
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(merchant))) // insert merchant in JSON form
                        .andExpect(status().isCreated())
                        .andReturn().getResponse().getContentAsString();

        merchant.setId(objectMapper.readValue(id, Integer.class));  // remap id from String to Integer

        //  Get merchant
        String merchantJson = mockMvc.perform(get("/merchant/" + merchant.getId())  //  GET
                        .contentType(MediaType.APPLICATION_JSON))   // perform
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();

        Merchant returnedMerchant = objectMapper.readValue(merchantJson, Merchant.class);   // Cast to merchant
        Assert.assertEquals(merchant, returnedMerchant);    //  add and later get same merchant again!

        //  Get all merchants
        String listJson = mockMvc.perform(get("/merchant")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();
        List<Merchant> merchants = objectMapper.readValue(listJson, new TypeReference<List<Merchant>>(){});
        assert merchants.size() == 1;   //  should be only 1 merchant in list
        Assert.assertEquals(merchant, merchants.get(0));    //  first merchant is same as in the List<Merchant>
    }
}
