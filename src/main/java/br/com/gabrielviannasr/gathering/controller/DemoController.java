package br.com.gabrielviannasr.gathering.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

	@RequestMapping("/hello")
	@ResponseBody
	public String helloWord() {
		return "Hello World!";
	}
	
}
