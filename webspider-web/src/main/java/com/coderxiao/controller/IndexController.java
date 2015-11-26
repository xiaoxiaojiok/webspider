package com.coderxiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping("hello")
	public ModelAndView hello(){
		ModelAndView mv =new ModelAndView();
        mv.addObject("spring", "spring mvc");
        mv.setViewName("index");
        return mv;
	}

}
