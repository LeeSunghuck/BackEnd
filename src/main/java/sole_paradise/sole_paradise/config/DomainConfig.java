package sole_paradise.sole_paradise.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("sole_paradise.sole_paradise.domain")
@EnableJpaRepositories("sole_paradise.sole_paradise.repos")
@EnableTransactionManagement
public class DomainConfig {
}
