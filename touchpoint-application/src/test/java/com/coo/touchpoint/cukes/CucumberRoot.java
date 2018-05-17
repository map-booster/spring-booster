package com.coo.touchpoint.cukes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.coo.touchpoint.Application;
import com.coo.touchpoint.cukes.util.TestContext;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class CucumberRoot {

	@Autowired
	protected TestRestTemplate template;

	@Autowired
	protected TestContext context;

}
