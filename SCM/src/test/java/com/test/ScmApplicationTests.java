package com.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.services.Impl.EmailServiceImpl;


@SpringBootTest
class ScmApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@Test
	void sendEmail()
	{
		emailServiceImpl.sendEmail("r77999884@gmail.com","testing","testing");
	}

}
