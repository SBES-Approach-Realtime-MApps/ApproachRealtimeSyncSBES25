package br.unesp.rc.MSReplicator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(
	scanBasePackages = {
		"br.unesp.rc.MSReplicator.*",
		"br.unesp.rc.ReservationModel.*",
		"br.unesp.rc.ResidentModel.model",
		"br.unesp.rc.CondominiumModel.*"
	}
)
public class MsReplicatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsReplicatorApplication.class, args);
	}

}
