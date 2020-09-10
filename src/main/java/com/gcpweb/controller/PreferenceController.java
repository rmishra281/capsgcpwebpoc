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
	public static final String URL="http://34.87.179.242/pref/";
	
	RestTemplate restclient = new RestTemplate();
	
	@PostMapping("/getpref")
	public String getPref(@ModelAttribute PreferenceSpec preferencespec, Model model) {
		System.out.println(preferencespec.getPrefkey());
		String url1=URL+preferencespec.getPrefkey();
		System.out.println(url1);
		PreferenceSpec obj= restclient.getForObject(url1, PreferenceSpec.class);
		System.out.println(obj);
		model.addAttribute("pref", obj);
		model.addAttribute("key", obj.getPrefkey());
		model.addAttribute("value", obj.getPrefvalue());
		model.addAttribute("desc", obj.getDescdetails());
		return "result";
	}

	
	@GetMapping("/prefview")
	public String getPref(Model model) {
		model.addAttribute("preferencespec", new PreferenceSpec());
		return "index";
	}
	@GetMapping("/createview")
	public String createPref(Model model) {
		model.addAttribute("preferencespecrequest", new PreferenceSpecRequest());
		return "create";
	}
	
	
	@PostMapping("/createpref")
	public String createPref(@ModelAttribute PreferenceSpecRequest preferenceSpecRequest, Model model) {
		System.out.println(preferenceSpecRequest.getKey());
		PreferenceSpec obj = restclient.postForObject(URL, preferenceSpecRequest, PreferenceSpec.class);
		System.out.println(URL);
		model.addAttribute("pref", obj);
		model.addAttribute("key", obj.getPrefkey());
		model.addAttribute("value", obj.getPrefvalue());
		model.addAttribute("desc", obj.getDescdetails());
		return "result";
	}

}
