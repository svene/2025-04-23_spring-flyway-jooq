package org.svenehrke.demo.springflywayjooq;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		int containerPort = 5432 ;
		int localPort = 5432 ;
		var container = new PostgreSQLContainer<>("postgres:latest")
			.withUsername("postgres")
			.withPassword("postgres")
			.withReuse(false)
			.withExposedPorts(5432, 5432)
			.withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
				new HostConfig()
					.withPortBindings(
						new PortBinding(
							Ports.Binding.bindPort(localPort),
							new ExposedPort(containerPort))
					))
			);
		return container;
	}
}
