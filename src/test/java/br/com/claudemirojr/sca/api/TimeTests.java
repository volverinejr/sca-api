package br.com.claudemirojr.sca.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.claudemirojr.sca.api.model.vo.TimeVO;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test Time")
@WithMockUser(username = "Miro", authorities = { "ROLE_ADMIN" })
@ActiveProfiles("test")
public class TimeTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("Post Time Sucesso")
	@Order(1)
	void Post_Time_Created_Sucesso() throws Exception {
		TimeVO timeVO = new TimeVO();
		timeVO.setNome("Time de teste");
		timeVO.setAtivo(true);

		mockMvc.perform(
				post("/api/time/v1").contentType("application/json").content(objectMapper.writeValueAsString(timeVO)))
				.andExpect(status().isCreated());

		// TimeVO result = timeServices.create(timeVO);

		// Assertions.assertEquals(result.getNome(), "Time de teste");

	}
	

	@Test
	@DisplayName("Post Time Fail - Nome Curto")
	@Order(2)
	void Post_Time_Created_Fail_Nome_Curto() throws Exception {
		TimeVO timeVO = new TimeVO();
		timeVO.setNome("Tim");
		timeVO.setAtivo(true);

		mockMvc.perform(
				post("/api/time/v1").contentType("application/json").content(objectMapper.writeValueAsString(timeVO)))
				.andExpect(status().isBadRequest());
	}
	

}
