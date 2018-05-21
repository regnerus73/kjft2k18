package de.regnerus.kjft;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.regnerus.kjft.team.TeamRepository;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(Application.class);
		try {
			Server.createWebServer("-webPort", "8081").start();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Bean
	public CommandLineRunner loadData(TeamRepository repository) {
		return (args) -> {
		};
	}

}
