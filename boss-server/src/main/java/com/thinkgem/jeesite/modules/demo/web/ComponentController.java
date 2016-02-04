/**
 * 
 */
package com.thinkgem.jeesite.modules.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/demo")
public class ComponentController extends BaseController {
	
	@Autowired
	private SystemService systemService;
	
	@RequestMapping(value = "tree")
	public String index(Model model)
	{
		model.addAttribute("menuList", systemService.findAllMenu());
		
		return "modules/demo/tree";
	}
	
	
	@RequestMapping(value = "form")
	public String form(Model model)
	{
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("allRoles", systemService.findAllRole());
		
		return "modules/demo/form";
	}

}
