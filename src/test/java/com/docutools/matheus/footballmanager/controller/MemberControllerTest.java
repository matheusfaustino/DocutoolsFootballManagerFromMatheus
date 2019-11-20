package com.docutools.matheus.footballmanager.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

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
