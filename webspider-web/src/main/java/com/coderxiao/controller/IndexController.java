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

import java.util.ArrayList;
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
		List<String> allSites = workerService.getAllSites();
		modelAndView.addObject("workerURLs", workerURLs);
		modelAndView.addObject("allSites", allSites);
		modelAndView.setViewName("workerList");
		return modelAndView;
	}

    @RequestMapping("test")
    public ModelAndView spiderStatus(){
        ModelAndView modelAndView = new ModelAndView();
        List<String> workerURLs = workerService.getWorkerURLs();
        List<String> allSites = workerService.getAllSites();
        List<String> spiderURLs = new ArrayList<String>();

        for(int i=0; i<workerURLs.size(); i++){
            for(int j=0; j<allSites.size(); j++){
                String tmp = workerURLs.get(i) + "/spider/" + allSites.get(j);
                spiderURLs.add(tmp);
            }
        }

        modelAndView.addObject("spiderURLs", spiderURLs);
        modelAndView.setViewName("display");
        return modelAndView;
    }

}