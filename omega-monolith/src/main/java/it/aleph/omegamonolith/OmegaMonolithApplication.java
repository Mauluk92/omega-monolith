package it.aleph.omegamonolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication()
public class OmegaMonolithApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmegaMonolithApplication.class, args);
	}

}
