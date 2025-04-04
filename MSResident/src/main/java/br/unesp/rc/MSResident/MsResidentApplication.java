package br.unesp.rc.MSResident;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(
	scanBasePackages = {
		"br.unesp.rc.MSResident",
		"br.unesp.rc.ResidentModel.model",
		"br.unesp.rc.ResidentModel.repository",
		"br.unesp.rc.ResidentModel.service"
	}
)
@EntityScan("br.unesp.rc.ResidentModel.model")
@EnableMongoRepositories("br.unesp.rc.ResidentModel.repository")
public class MsResidentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsResidentApplication.class, args);
	}

}
