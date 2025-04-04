package br.unesp.rc.MSReplicator.domain.mapper;

import br.unesp.rc.CondominiumModel.model.RentableArea;

public class RentableAreaMapper {
    public static br.unesp.rc.ReservationModel.model.RentableArea toRentableArea(RentableArea condominiumRentableArea) {
        br.unesp.rc.ReservationModel.model.RentableArea rentableArea = new br.unesp.rc.ReservationModel.model.RentableArea();
        rentableArea.setId(condominiumRentableArea.getId());
        rentableArea.setName(condominiumRentableArea.getName());
        rentableArea.setSize(condominiumRentableArea.getSize());
        rentableArea.setValue(condominiumRentableArea.getValue());
        rentableArea.setCapacity(condominiumRentableArea.getCapacity());
        return rentableArea;
    }
}
