package br.unesp.rc.MSCondominium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
	"br.unesp.rc.CondominiumModel.model",
	"br.unesp.rc.CondominiumModel.service",
	"br.unesp.rc.CondominiumModel.repository",
	"br.unesp.rc.MSCondominium"
})
@EntityScan("br.unesp.rc.CondominiumModel.model")
@EnableJpaRepositories("br.unesp.rc.CondominiumModel.repository")
public class MsCondominiumApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCondominiumApplication.class, args);
	}

}
