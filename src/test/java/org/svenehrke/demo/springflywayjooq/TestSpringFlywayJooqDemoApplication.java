package org.svenehrke.demo.springflywayjooq;

import org.springframework.boot.SpringApplication;

public class TestSpringFlywayJooqDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringFlywayJooqDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
