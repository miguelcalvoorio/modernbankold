package org.modernbank.savings.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ContractApplicationTests {

	@Autowired
    private ApplicationContext applicationContext;
    
	@Test
	public void contextLoads() {
		assertNotNull(applicationContext);
	}

}
