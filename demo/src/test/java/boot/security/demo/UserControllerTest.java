package boot.security.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

  @Autowired private WebApplicationContext context;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = webAppContextSetup(context).build();
  }

  @Test
  public void whenQuerySuccess() throws Exception {
    String result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/user")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .param("username", "me")
                    .param("age", "18")
                    .param("ageTo", "26")
                    .param("size", "15")
                    .param("page", "3")
                    .param("sort", "age,desc") // cannot have space between age and desc
                )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(3))
            .andReturn()
            .getResponse()
            .getContentAsString();

    System.out.println(result);
  }

  @Test
  public void whenGetInfoSuccess() throws Exception {
    String result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("tom"))
            .andReturn()
            .getResponse()
            .getContentAsString();

    System.out.println(result);
  }

  @Test
  public void whenGetInfoFail() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/user/a").contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().is4xxClientError());
  }
}
