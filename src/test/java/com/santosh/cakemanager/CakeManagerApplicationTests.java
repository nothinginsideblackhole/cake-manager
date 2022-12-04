package com.santosh.cakemanager;

import com.santosh.cakemanager.controller.CakeController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CakeManagerApplicationTests {

	@Autowired
	private CakeController cakeController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(cakeController);
	}

}
