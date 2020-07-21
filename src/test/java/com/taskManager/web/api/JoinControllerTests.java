package com.taskManager.web.api;


import com.taskManager.domin.application.service.UserService;
import com.taskManager.domin.model.user.exception.EmailAddressExistsException;
import com.taskManager.domin.model.user.exception.UsernameExistsException;
import com.taskManager.utils.JsonUtils;
import com.taskManager.web.payload.JoinPayload;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* Unable to find a @SpringBootConfiguration ... error 문제일 경우 == main 위치 확인  */

@RunWith(SpringRunner.class)
@WebMvcTest(JoinController.class)
public class JoinControllerTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService serviceMock;

  @Test
  public void join_blankPayload_failAndReturn400() throws Exception{
    mvc.perform(post("/api/join")).andExpect(status().is(400));
  }
  @Test
  public void join_existedUsername_failAndReturn400() throws Exception {
    JoinPayload payload = new JoinPayload();
    payload.setUsername("exist");
    payload.setEmailAddress("test@taskagile.com");
    payload.setPassword("MyPassword!");

    doThrow(UsernameExistsException.class).when(serviceMock).join(payload.toCommand());

    mvc.perform(
      post("/api/join")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtils.toJson(payload)))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.message").value("Username 존재하지 않음")); //Contorller 에서 정의한 Error 메시지 확인 같은지 확인
  }

  @Test
  public void register_existedEmailAddress_shouldFailAndReturn400() throws Exception {
    JoinPayload payload = new JoinPayload();
    payload.setUsername("test");
    payload.setEmailAddress("exist@taskagile.com");
    payload.setPassword("MyPassword!");

    doThrow(EmailAddressExistsException.class)
      .when(serviceMock)
      .join(payload.toCommand());

    mvc.perform(
      post("/api/join")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtils.toJson(payload)))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.message").value("Email address 존재하지 않음")); //Contorller 에서 정의한 Error 메시지 확인 같은지 확인
  }

  @Test
  public void register_validPayload_shouldSucceedAndReturn201() throws Exception {
    JoinPayload payload = new JoinPayload();
    payload.setUsername("sunny");
    payload.setEmailAddress("sunny@taskagile.com");
    payload.setPassword("MyPassword!");

    doNothing().when(serviceMock)
      .join(payload.toCommand());

    mvc.perform(
      post("/api/join")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtils.toJson(payload)))
      .andExpect(status().is(201));
  }
}
