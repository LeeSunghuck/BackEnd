package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.Health;
import sole_paradise.sole_paradise.domain.Missing;
import sole_paradise.sole_paradise.domain.Pet;
import sole_paradise.sole_paradise.domain.User;
import sole_paradise.sole_paradise.model.PetDTO;
import sole_paradise.sole_paradise.repos.HealthRepository;
import sole_paradise.sole_paradise.repos.MissingRepository;
import sole_paradise.sole_paradise.repos.PetRepository;
import sole_paradise.sole_paradise.repos.UserRepository;
import sole_paradise.sole_paradise.util.NotFoundException;
import sole_paradise.sole_paradise.util.ReferencedWarning;


@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final MissingRepository missingRepository;
    private final HealthRepository healthRepository;

    public PetService(final PetRepository petRepository, final UserRepository userRepository,
            final MissingRepository missingRepository, final HealthRepository healthRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.missingRepository = missingRepository;
        this.healthRepository = healthRepository;
    }

    public List<PetDTO> findAll() {
        final List<Pet> pets = petRepository.findAll(Sort.by("petId"));
        return pets.stream()
                .map(pet -> mapToDTO(pet, new PetDTO()))
                .toList();
    }

    public PetDTO get(final Integer petId) {
        return petRepository.findById(petId)
                .map(pet -> mapToDTO(pet, new PetDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PetDTO petDTO) {
        final Pet pet = new Pet();
        mapToEntity(petDTO, pet);
        return petRepository.save(pet).getPetId();
    }

    public void update(final Integer petId, final PetDTO petDTO) {
        final Pet pet = petRepository.findById(petId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(petDTO, pet);
        petRepository.save(pet);
    }

    public void delete(final Integer petId) {
        petRepository.deleteById(petId);
    }

    private PetDTO mapToDTO(final Pet pet, final PetDTO petDTO) {
        petDTO.setPetId(pet.getPetId());
        petDTO.setPetName(pet.getPetName());
        petDTO.setPetAge(pet.getPetAge());
        petDTO.setDogOrCat(pet.getDogOrCat());
        petDTO.setPetType(pet.getPetType());
        petDTO.setPetPicture(pet.getPetPicture());
        petDTO.setPetWeight(pet.getPetWeight());
        petDTO.setNeutering(pet.getNeutering());
        petDTO.setPetAllergy(pet.getPetAllergy());
        petDTO.setCreatedAt(pet.getCreatedAt());
        petDTO.setUpdatedAt(pet.getUpdatedAt());
        petDTO.setGender(pet.getGender());
        petDTO.setUser(pet.getUser() == null ? null : pet.getUser().getUserId());
        return petDTO;
    }

    private Pet mapToEntity(final PetDTO petDTO, final Pet pet) {
        pet.setPetName(petDTO.getPetName());
        pet.setPetAge(petDTO.getPetAge());
        pet.setDogOrCat(petDTO.getDogOrCat());
        pet.setPetType(petDTO.getPetType());
        pet.setPetPicture(petDTO.getPetPicture());
        pet.setPetWeight(petDTO.getPetWeight());
        pet.setNeutering(petDTO.getNeutering());
        pet.setPetAllergy(petDTO.getPetAllergy());
        pet.setCreatedAt(petDTO.getCreatedAt());
        pet.setUpdatedAt(petDTO.getUpdatedAt());
        pet.setGender(petDTO.getGender());
        final User user = petDTO.getUser() == null ? null : userRepository.findById(petDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        pet.setUser(user);
        return pet;
    }

    public ReferencedWarning getReferencedWarning(final Integer petId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Pet pet = petRepository.findById(petId)
                .orElseThrow(NotFoundException::new);
        final Missing petMissing = missingRepository.findFirstByPet(pet);
        if (petMissing != null) {
            referencedWarning.setKey("pet.missing.pet.referenced");
            referencedWarning.addParam(petMissing.getMissingId());
            return referencedWarning;
        }
        final Health petHealth = healthRepository.findFirstByPet(pet);
        if (petHealth != null) {
            referencedWarning.setKey("pet.health.pet.referenced");
            referencedWarning.addParam(petHealth.getHealthId());
            return referencedWarning;
        }
        return null;
    }

}
