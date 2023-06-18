package in.emagna.cachepoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
//@EnableJpaRepositories(basePackages = {"in.emagna.cachepoc.repository"})
public class CachePocApplication {

	public static void main(String[] args) {

		SpringApplication.run(CachePocApplication.class, args);
	}

}
