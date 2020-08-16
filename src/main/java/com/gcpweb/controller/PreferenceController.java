package com.gcpweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@Controller
public class PreferenceController {
	
	
	RestTemplate restclient = new RestTemplate();
	
	@PostMapping("/prefsubmit")
	public String getPref(@ModelAttribute PreferenceSpec preferencespec, Model model) {
		System.out.println(preferencespec.getPrefkey());
		String url="http://35.196.239.217/pref/"+preferencespec.getPrefkey();
		System.out.println(url);
		PreferenceSpec obj= restclient.getForObject(url, PreferenceSpec.class);
		System.out.println(obj);
		model.addAttribute("pref", obj);
		model.addAttribute("value", obj.getPrefvalue());
		return "result";
	}

	
	@GetMapping("/prefv281")
	public String getPref(Model model) {
		model.addAttribute("preferencespec", new PreferenceSpec());
		return "index";
	}
}
