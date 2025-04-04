package br.unesp.rc.MSReservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
	scanBasePackages = {
		"br.unesp.rc.MSReservation",
		"br.unesp.rc.ReservationModel.model",
		"br.unesp.rc.ReservationModel.repository",
		"br.unesp.rc.ReservationModel.service",
	}
)
@EntityScan("br.unesp.rc.ReservationModel.model")
@EnableJpaRepositories("br.unesp.rc.ReservationModel.repository")
public class MsReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsReservationApplication.class, args);
	}

}
