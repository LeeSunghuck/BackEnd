package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.Missing;
import sole_paradise.sole_paradise.domain.Pet;
import sole_paradise.sole_paradise.domain.WalkRoute;
import sole_paradise.sole_paradise.model.MissingDTO;
import sole_paradise.sole_paradise.repos.MissingRepository;
import sole_paradise.sole_paradise.repos.PetRepository;
import sole_paradise.sole_paradise.repos.WalkRouteRepository;
import sole_paradise.sole_paradise.util.NotFoundException;


@Service
public class MissingService {

    private final MissingRepository missingRepository;
    private final PetRepository petRepository;
    private final WalkRouteRepository walkRouteRepository;

    public MissingService(final MissingRepository missingRepository,
            final PetRepository petRepository, final WalkRouteRepository walkRouteRepository) {
        this.missingRepository = missingRepository;
        this.petRepository = petRepository;
        this.walkRouteRepository = walkRouteRepository;
    }

    public List<MissingDTO> findAll() {
        final List<Missing> missings = missingRepository.findAll(Sort.by("missingId"));
        return missings.stream()
                .map(missing -> mapToDTO(missing, new MissingDTO()))
                .toList();
    }

    public MissingDTO get(final Integer missingId) {
        return missingRepository.findById(missingId)
                .map(missing -> mapToDTO(missing, new MissingDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MissingDTO missingDTO) {
        final Missing missing = new Missing();
        mapToEntity(missingDTO, missing);
        return missingRepository.save(missing).getMissingId();
    }

    public void update(final Integer missingId, final MissingDTO missingDTO) {
        final Missing missing = missingRepository.findById(missingId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(missingDTO, missing);
        missingRepository.save(missing);
    }

    public void delete(final Integer missingId) {
        missingRepository.deleteById(missingId);
    }

    private MissingDTO mapToDTO(final Missing missing, final MissingDTO missingDTO) {
        missingDTO.setMissingId(missing.getMissingId());
        missingDTO.setAlarmName(missing.getAlarmName());
        missingDTO.setLocation(missing.getLocation());
        missingDTO.setAlertRadiusKm(missing.getAlertRadiusKm());
        missingDTO.setMissingDate(missing.getMissingDate());
        missingDTO.setCreatedAt(missing.getCreatedAt());
        missingDTO.setUpdatedAt(missing.getUpdatedAt());
        missingDTO.setMissingDetails(missing.getMissingDetails());
        missingDTO.setMissingStatus(missing.getMissingStatus());
        missingDTO.setPet(missing.getPet() == null ? null : missing.getPet().getPetId());
        missingDTO.setWalkroute(missing.getWalkroute() == null ? null : missing.getWalkroute().getWalkrouteId());
        return missingDTO;
    }

    private Missing mapToEntity(final MissingDTO missingDTO, final Missing missing) {
        missing.setAlarmName(missingDTO.getAlarmName());
        missing.setLocation(missingDTO.getLocation());
        missing.setAlertRadiusKm(missingDTO.getAlertRadiusKm());
        missing.setMissingDate(missingDTO.getMissingDate());
        missing.setCreatedAt(missingDTO.getCreatedAt());
        missing.setUpdatedAt(missingDTO.getUpdatedAt());
        missing.setMissingDetails(missingDTO.getMissingDetails());
        missing.setMissingStatus(missingDTO.getMissingStatus());
        final Pet pet = missingDTO.getPet() == null ? null : petRepository.findById(missingDTO.getPet())
                .orElseThrow(() -> new NotFoundException("pet not found"));
        missing.setPet(pet);
        final WalkRoute walkroute = missingDTO.getWalkroute() == null ? null : walkRouteRepository.findById(missingDTO.getWalkroute())
                .orElseThrow(() -> new NotFoundException("walkroute not found"));
        missing.setWalkroute(walkroute);
        return missing;
    }

}
