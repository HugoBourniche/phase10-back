package fr.bugo.games.tool.Phase10;

import org.springframework.boot.SpringApplication;

public class TestPhase10Application {

	public static void main(String[] args) {
		SpringApplication.from(Phase10Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
