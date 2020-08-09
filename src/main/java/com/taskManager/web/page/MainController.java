package com.taskManager.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/* Vue 라우터와 연결 : URL 등록 */
@Controller
public class MainController {

  @GetMapping(value = { "/", "/login" , "/join","/boards/*", "/card/**" })
  public String entry() {
    return "index";
  }

}
