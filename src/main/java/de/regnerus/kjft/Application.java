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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Bean
	public CommandLineRunner loadData(TeamRepository repository) {
		return (args) -> {
			// // save a couple of customers
			// repository.save(new Team("Hasloh"));
			// repository.save(new Team("Bönningstedt"));
			// repository.save(new Team("Ellerbek"));
			//
			// // fetch all customers
			// log.info("Customers found with findAll():");
			// log.info("-------------------------------");
			// for (Team customer : repository.findAll()) {
			// log.info(customer.toString());
			// }
			// log.info("");
			//
			// // fetch an individual customer by ID
			// Team customer = repository.findOne(1L);
			// log.info("Customer found with findOne(1L):");
			// log.info("--------------------------------");
			// log.info(customer.toString());
			// log.info("");
			//
			// // fetch customers by last name
			// log.info("Customer found with
			// findByLastNameStartsWithIgnoreCase('Bauer'):");
			// log.info("--------------------------------------------");
			// for (Team bauer : repository
			// .findByNameStartsWithIgnoreCase("Bauer")) {
			// log.info(bauer.toString());
			// }
			// log.info("");
		};
	}

}
