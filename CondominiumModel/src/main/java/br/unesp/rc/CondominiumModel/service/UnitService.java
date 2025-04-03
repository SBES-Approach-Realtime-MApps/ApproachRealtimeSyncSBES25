package br.unesp.rc.CondominiumModel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unesp.rc.CondominiumModel.model.Unit;
import br.unesp.rc.CondominiumModel.repository.UnitRepository;



@Service
public class UnitService {
    
    @Autowired
    UnitRepository unitRepository;

   public List<Unit> findAll() {
        List<Unit> units = unitRepository.findAll();
        return units;
    }
    
    public Unit findById(Long id) {
        Optional<Unit> unit = unitRepository.findById(id);
        if(unit.isEmpty()){
            throw new RuntimeException("Unit not found");
        } 
        return unit.get();
    }

    public Unit save(Unit unit) {
        Unit newUnit = unitRepository.save(unit); 
        return newUnit;
    }

    public void delete(long id) {
        unitRepository.deleteById(id);
    }


    public Unit update(Unit unit) {
        Unit oldUnit = findById(unit.getId());
        updateUnit(oldUnit, unit);
        Unit updatedUnit = unitRepository.save(oldUnit);
        return updatedUnit;
    }

    public void updateUnit(Unit oldUnit, Unit newUnit){

        if (newUnit.getSizeSM() != 0) {
            oldUnit.setSizeSM(newUnit.getSizeSM());
        }

        if (newUnit.getCondominium() != null) {
            oldUnit.setCondominium(newUnit.getCondominium());
        }

        if (newUnit.getLocation() != null) {
            oldUnit.setLocation(newUnit.getLocation());
        }

        if (newUnit.getResident() != null) {
            oldUnit.setResident(newUnit.getResident());
        }

    }
}
