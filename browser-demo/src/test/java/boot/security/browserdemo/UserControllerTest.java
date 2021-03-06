package boot.security.browserdemo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
                get("/user")
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
            .perform(get("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
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
        .perform(get("/user/a").contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void whenCreateSuccess() throws Exception {

    String content = formulateJsonToCreate("password");
    String result =
        mockMvc
            .perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andReturn()
            .getResponse()
            .getContentAsString();

    System.out.println(result);
  }

  @Test
  public void whenCreateWithEmptyPassword() throws Exception {

    String content = formulateJsonToCreate(null);
    String result =
        mockMvc
            .perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andReturn()
            .getResponse()
            .getContentAsString();

    System.out.println(result);
  }

  private String formulateJsonToCreate(String password) {
    Date date = new Date();
    System.out.println(date.getTime());
    String content =
        "{\"username\":\"john\",\"password\":null,\"birthday\":" + date.getTime() + "}";

    if (password != null) {
      content =
          "{\"username\":\"john\",\"password\":\""
              + password
              + "\",\"birthday\":"
              + date.getTime()
              + "}";
    }

    System.out.println(content);
    return content;
  }

  @Test
  public void whenUpdateSuccess() throws Exception {

    Date date =
        new Date(
            LocalDateTime.now()
                .plusYears(1)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli());
    System.out.println(date.getTime());
    String content =
        "{\"username\":\"john\",\"password\":\"password\",\"birthday\":" + date.getTime() + "}";
    String result =
        mockMvc
            .perform(put("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andReturn()
            .getResponse()
            .getContentAsString();

    System.out.println(result);
  }

  @Test
  public void whenDeleteSuccess() throws Exception {
    mockMvc
        .perform(delete("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
  }
}
