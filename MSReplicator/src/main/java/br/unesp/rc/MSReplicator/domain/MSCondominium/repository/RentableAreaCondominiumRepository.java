package br.unesp.rc.MSReplicator.domain.MSCondominium.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.MSReplicator.domain.MSCondominium.model.RentableArea;

public interface RentableAreaCondominiumRepository extends JpaRepository<RentableArea, Long> {
    
}
