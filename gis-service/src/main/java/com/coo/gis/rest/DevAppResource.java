package com.coo.gis.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coo.gis.domain.DevApp;
import com.coo.gis.service.DevAppService;

@RestController
@CrossOrigin
@RequestMapping("/devapps")
public class DevAppResource {

	private DevAppService service;

	@Autowired
	public DevAppResource(DevAppService service) {
		this.service = service;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public DevApp createDevApp(@Valid @RequestBody DevApp devApp) {
		
		return service.createDevApp(devApp);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public List<DevApp> getAll() {
		
		return service.getAll();
	}
	


}
