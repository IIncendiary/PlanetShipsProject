package com.example.PlanetShipsProject.service;
import com.example.PlanetShipsProject.Mapper.PlanetMapping;
import com.example.PlanetShipsProject.Mapper.SpaceShipMapping;
import com.example.PlanetShipsProject.dto.PlanetDTO;
import com.example.PlanetShipsProject.dto.SpaceShipDTO;
import com.example.PlanetShipsProject.model.Planet;
import com.example.PlanetShipsProject.repository.PlanetRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@Service
public class PlanetService {
    private final PlanetRepository planetRepository;
    private final PlanetMapping planetMapping;
    private final SpaceShipMapping spaceShipMapping;


    public List<PlanetDTO> findAllPlanets(){
        List<Planet> planetsList = planetRepository.findAll();
        return planetsList.stream().map(planetMapping::planetEntityToDto).toList();
    }

    @Transactional
    public void createPlanet(PlanetDTO planetDTO){
       planetRepository.save(planetMapping.planetDtoToEntity(planetDTO));
    }

    public PlanetDTO getPlanetById(Long id){
        Planet planet = planetRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Нема планеты с индификатором "+id));
        return planetMapping.planetEntityToDto(planet);
    }

    @Transactional
    public void deletePlanet (Long id){
        planetRepository.deleteById(id);
    }

    @Transactional
    public void updatePlanet(Long id, PlanetDTO updatePlanetDTO){
        Planet exsistingPlanet = planetMapping.planetDtoToEntity(getPlanetById(id));
        exsistingPlanet.setName(updatePlanetDTO.getName());
        exsistingPlanet.setPlanetResource(updatePlanetDTO.getPlanetResource());
        exsistingPlanet.setFuelPrice(updatePlanetDTO.getFuelPrice());
        planetRepository.save(exsistingPlanet);
    }

    @Transactional
    public void updatePlanetFuelPrice(Long planetDTOId, Double newFuelPrice){
        Planet exsistingPlanet = planetMapping.planetDtoToEntity(getPlanetById(planetDTOId));
        exsistingPlanet.setFuelPrice(newFuelPrice);
        planetRepository.save(exsistingPlanet);
    }

    public List<SpaceShipDTO> getAllSpaceShipsOnAPlanet(Long planetDTOId){
        Planet exsistingPlanet = planetMapping.planetDtoToEntity(getPlanetById(planetDTOId));
        return spaceShipMapping.spaceShipListEntityToDTO(exsistingPlanet.getListOfShips());
    }
}