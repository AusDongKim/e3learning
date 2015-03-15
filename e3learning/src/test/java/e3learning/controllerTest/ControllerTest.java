package e3learning.controllerTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import e3learning.domain.AccountVO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.Matchers.*;

@WebAppConfiguration
@ContextConfiguration(locations={"file:WebContent/WEB-INF/spring/spring-config.xml","file:WebContent/WEB-INF/spring/spring-servlet.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@EnableAsync
//@Transactional
public class ControllerTest {
	
	@Autowired
	WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
	
	
	@Before
	public void accountTest(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void indexTest() throws Exception {
		this.mockMvc.perform(get("/index").accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
		.andExpect(status().isOk());
	}
	
	@Test
	public void addAccount() throws Exception{
		Gson gson = new Gson();
		AccountVO accountVO = new AccountVO();
		String json = gson.toJson(accountVO);
		System.out.println(json);
		
		String email = System.currentTimeMillis()+"@test2.net";
		
		
		
		this.mockMvc.perform(post("/account")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest())
				.andDo(print())
				//.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.transactionStatus",is(false)))
				.andExpect(jsonPath("$.errorMsg", hasSize(2)))
				.andExpect(jsonPath("$.errorMsg[0]",containsString("may not be null")))
				.andExpect(jsonPath("$.body",nullValue()));
		
		
		accountVO.setFirstName("Stven");
		accountVO.setLastName("Harrison");
		accountVO.setEmail(email);
		accountVO.setAddressPostCode("2127");
		accountVO.setAddressState("NSW");
		accountVO.setAddressStreet("6 Taradale Street");
		accountVO.setAddressSuburb("Wentworth Point");
		accountVO.setAddressCountry("Australia");
		json = gson.toJson(accountVO);
		MvcResult mvcResult = this.mockMvc.perform(post("/account")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("$.transactionStatus", is(true)))
				.andExpect(jsonPath("$.body.firstName",is("Stven")))
				.andExpect(jsonPath("$body.idAccount",notNullValue()))
				.andReturn();
		
		this.mockMvc.perform(get("/account/{id}","1"))
					.andDo(print())
					.andExpect(jsonPath("$.transactionStatus", is(true)))
					.andExpect(jsonPath("$body.idAccount",is(1)));

		accountVO.setIdAccount(1);
		accountVO.setActiveState("P");	
		json = gson.toJson(accountVO);
		this.mockMvc.perform(post("/account/{id}","1").contentType(MediaType.APPLICATION_JSON).content(json))		
		.andDo(print())
		.andExpect(jsonPath("$.transactionStatus", is(true)))
		.andExpect(jsonPath("$.body.firstName",is("Stven")))
		.andExpect(jsonPath("$body.activeState",is("P")));
		
		this.mockMvc.perform(get("/accounts"))
		.andDo(print())
		.andExpect(jsonPath("$.transactionStatus", is(true)));
		
	}
	
	@Test
	public void trainingTest() throws Exception{
		//this.mockMvc.perform(get("/training/{accountId}","1"))			
		//		.andExpect(status().isOk())
		//		.andDo(print());
	}
	
	@Test
	public void getCourseInfo() throws Exception {
		//String courseTitle = System.currentTimeMillis()+"";
		//this.mockMvc.perform(get("/courses","1"))			
		//		.andExpect(status().isOk())
		//		.andDo(print());
	}
	
	
}
