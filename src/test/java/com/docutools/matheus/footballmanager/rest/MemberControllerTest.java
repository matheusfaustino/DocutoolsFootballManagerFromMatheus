package com.docutools.matheus.footballmanager.rest;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.docutools.matheus.footballmanager.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers = MemberController.class)
public class MemberControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void testListAllMembers() throws Exception {
		this.mockMvc.perform(
				get("/members/")
				.contentType("application/json")
		).andDo(print()).andExpect(status().isOk());
	}
}
