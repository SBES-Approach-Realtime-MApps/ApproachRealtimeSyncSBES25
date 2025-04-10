package br.unesp.rc.MSReplicator.domain.mapper;

import br.unesp.rc.CondominiumModel.model.ResidentType;

public class ResidentMapper {

    public static br.unesp.rc.CondominiumModel.model.Resident toCondominiumResident(br.unesp.rc.ResidentModel.model.Resident resident) {
        br.unesp.rc.CondominiumModel.model.Resident condominiumResident = new br.unesp.rc.CondominiumModel.model.Resident();
        condominiumResident.setId(resident.getId());
        condominiumResident.setName(resident.getName());
        condominiumResident.setBirthDate(resident.getBirthDate());
        condominiumResident.setResidentType(ResidentType.valueOf(resident.getResidentType().toString()));
        return condominiumResident;
    }

    public static br.unesp.rc.ReservationModel.model.Resident toReservationResident(br.unesp.rc.ResidentModel.model.Resident resident) {
        br.unesp.rc.ReservationModel.model.Resident reservationResident = new br.unesp.rc.ReservationModel.model.Resident();
        reservationResident.setId(resident.getId());
        reservationResident.setName(resident.getName());
        reservationResident.setBirthDate(resident.getBirthDate());
        reservationResident.setResidentType(
            br.unesp.rc.ReservationModel.model.ResidentType.valueOf(resident.getResidentType().toString())
        );
        return reservationResident;
    }

    public static br.unesp.rc.CondominiumModel.model.Resident toCondominiumResident(br.unesp.rc.ReservationModel.model.Resident resident) {
        br.unesp.rc.CondominiumModel.model.Resident condominiumResident = new br.unesp.rc.CondominiumModel.model.Resident();
        condominiumResident.setId(resident.getId());
        condominiumResident.setName(resident.getName());
        condominiumResident.setBirthDate(resident.getBirthDate());
        condominiumResident.setResidentType(
            br.unesp.rc.CondominiumModel.model.ResidentType.valueOf(resident.getResidentType().toString())
        );
        return condominiumResident;
    }



}
