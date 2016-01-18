package com.coderxiao.controller;

import com.coderxiao.service.WorkerService;
import com.coderxiao.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.xml.MarshallingView;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WorkerService workerService;

	@RequestMapping("/")
	public String index() {

		return "index";
	}

	@RequestMapping("workers")
	public ModelAndView workers(){
		ModelAndView modelAndView = new ModelAndView();
		List<String> workerURLs = workerService.getWorkerURLs();
		logger.info(workerURLs.toString());
		modelAndView.addObject("workerURLs", workerURLs);
		modelAndView.setViewName("workerList");
		return modelAndView;
	}
}
