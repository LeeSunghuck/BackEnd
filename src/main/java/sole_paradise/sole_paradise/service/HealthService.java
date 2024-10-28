package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.Health;
import sole_paradise.sole_paradise.domain.Pet;
import sole_paradise.sole_paradise.model.HealthDTO;
import sole_paradise.sole_paradise.repos.HealthRepository;
import sole_paradise.sole_paradise.repos.PetRepository;
import sole_paradise.sole_paradise.util.NotFoundException;


@Service
public class HealthService {

    private final HealthRepository healthRepository;
    private final PetRepository petRepository;

    public HealthService(final HealthRepository healthRepository,
            final PetRepository petRepository) {
        this.healthRepository = healthRepository;
        this.petRepository = petRepository;
    }

    public List<HealthDTO> findAll() {
        final List<Health> healths = healthRepository.findAll(Sort.by("healthId"));
        return healths.stream()
                .map(health -> mapToDTO(health, new HealthDTO()))
                .toList();
    }

    public HealthDTO get(final Integer healthId) {
        return healthRepository.findById(healthId)
                .map(health -> mapToDTO(health, new HealthDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final HealthDTO healthDTO) {
        final Health health = new Health();
        mapToEntity(healthDTO, health);
        return healthRepository.save(health).getHealthId();
    }

    public void update(final Integer healthId, final HealthDTO healthDTO) {
        final Health health = healthRepository.findById(healthId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(healthDTO, health);
        healthRepository.save(health);
    }

    public void delete(final Integer healthId) {
        healthRepository.deleteById(healthId);
    }

    private HealthDTO mapToDTO(final Health health, final HealthDTO healthDTO) {
        healthDTO.setHealthId(health.getHealthId());
        healthDTO.setVisitedDate(health.getVisitedDate());
        healthDTO.setNotes(health.getNotes());
        healthDTO.setHealthDate(health.getHealthDate());
        healthDTO.setNextCheckupDate(health.getNextCheckupDate());
        healthDTO.setCreatedAt(health.getCreatedAt());
        healthDTO.setUpdatedAt(health.getUpdatedAt());
        healthDTO.setAlarmStatus(health.getAlarmStatus());
        healthDTO.setPet(health.getPet() == null ? null : health.getPet().getPetId());
        return healthDTO;
    }

    private Health mapToEntity(final HealthDTO healthDTO, final Health health) {
        health.setVisitedDate(healthDTO.getVisitedDate());
        health.setNotes(healthDTO.getNotes());
        health.setHealthDate(healthDTO.getHealthDate());
        health.setNextCheckupDate(healthDTO.getNextCheckupDate());
        health.setCreatedAt(healthDTO.getCreatedAt());
        health.setUpdatedAt(healthDTO.getUpdatedAt());
        health.setAlarmStatus(healthDTO.getAlarmStatus());
        final Pet pet = healthDTO.getPet() == null ? null : petRepository.findById(healthDTO.getPet())
                .orElseThrow(() -> new NotFoundException("pet not found"));
        health.setPet(pet);
        return health;
    }

}
