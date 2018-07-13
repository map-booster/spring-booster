package com.coo.gis.cukes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.coo.gis.Application;
import com.coo.gis.cukes.util.TestContext;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class CucumberRoot {

	@Autowired
	protected TestRestTemplate template;

	@Autowired
	protected TestContext context;

}
