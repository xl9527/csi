package edu.team.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import edu.team.entity.Dept;
import edu.team.entity.Job;
import edu.team.service.DeptService;
import edu.team.service.JobService;
import edu.team.util.JacksonUtil;


/**
 * 项目初始control层示例
 */
@RestController
public class JobController {
	
	@Autowired
    private JobService jobService;
	
	@RequestMapping(value="/selectJob",method=RequestMethod.POST)
	public PageInfo<Job> selectJob(String name, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		 PageHelper.startPage(pageNum, 10);
	       if ("".equals(name) || null == name) {
	           return new PageInfo<Job>(jobService.selectJobAll());
	       } else {
	           return new PageInfo<Job>(jobService.selectJob(name));
	       }
	   
	}
	
	@RequestMapping(value="/selectAllJob",method=RequestMethod.POST)
	public PageInfo<Job> selectAllJob(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
		PageHelper.startPage(pageNum, 10);
        PageInfo<Job> pageInfo = new PageInfo<Job>(jobService.selectJobAll());
        return pageInfo;
	}
	
	@RequestMapping(value="/insertJob",method=RequestMethod.POST)
	public String insertJob(String name,String remake) {
		Job job=new Job();
		job.setName(name);
		job.setRemake(remake);
	    String result=jobService.insertJob(job);
	    if ("FAIL".equals(result)) {
            return JacksonUtil.objectToJson("FAIL");
        }else {
           return JacksonUtil.objectToJson("SUCCESS");
        }
	}
	
	@RequestMapping(value="/deleteJob",method=RequestMethod.POST)
	public String deleteJob(String ids) {
	    String result;
        String[] id = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            result = jobService.deleteJob(Integer.valueOf(id[i]));
            if ("FAIL".equals(result)) {
                return JacksonUtil.objectToJson("FAIL");
            }
        }
        return JacksonUtil.objectToJson("SUCCESS");
	}
	
	@RequestMapping(value="/updateJob",method=RequestMethod.POST)
	public String updateJob(int id,String name,String remake) {
		Job job=new Job();
		job.setId(id);
		job.setName(name);
		job.setRemake(remake);
	    String result=jobService.updateJob(job);
	    if ("FAIL".equals(result)) {
            return JacksonUtil.objectToJson("FAIL");
        }else {
           return JacksonUtil.objectToJson("SUCCESS");
        }
	}

}
