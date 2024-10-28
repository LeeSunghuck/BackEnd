package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.Missing;
import sole_paradise.sole_paradise.domain.User;
import sole_paradise.sole_paradise.domain.WalkRoute;
import sole_paradise.sole_paradise.model.WalkRouteDTO;
import sole_paradise.sole_paradise.repos.MissingRepository;
import sole_paradise.sole_paradise.repos.UserRepository;
import sole_paradise.sole_paradise.repos.WalkRouteRepository;
import sole_paradise.sole_paradise.util.NotFoundException;
import sole_paradise.sole_paradise.util.ReferencedWarning;


@Service
public class WalkRouteService {

    private final WalkRouteRepository walkRouteRepository;
    private final UserRepository userRepository;
    private final MissingRepository missingRepository;

    public WalkRouteService(final WalkRouteRepository walkRouteRepository,
            final UserRepository userRepository, final MissingRepository missingRepository) {
        this.walkRouteRepository = walkRouteRepository;
        this.userRepository = userRepository;
        this.missingRepository = missingRepository;
    }

    public List<WalkRouteDTO> findAll() {
        final List<WalkRoute> walkRoutes = walkRouteRepository.findAll(Sort.by("walkrouteId"));
        return walkRoutes.stream()
                .map(walkRoute -> mapToDTO(walkRoute, new WalkRouteDTO()))
                .toList();
    }

    public WalkRouteDTO get(final Integer walkrouteId) {
        return walkRouteRepository.findById(walkrouteId)
                .map(walkRoute -> mapToDTO(walkRoute, new WalkRouteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final WalkRouteDTO walkRouteDTO) {
        final WalkRoute walkRoute = new WalkRoute();
        mapToEntity(walkRouteDTO, walkRoute);
        return walkRouteRepository.save(walkRoute).getWalkrouteId();
    }

    public void update(final Integer walkrouteId, final WalkRouteDTO walkRouteDTO) {
        final WalkRoute walkRoute = walkRouteRepository.findById(walkrouteId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(walkRouteDTO, walkRoute);
        walkRouteRepository.save(walkRoute);
    }

    public void delete(final Integer walkrouteId) {
        walkRouteRepository.deleteById(walkrouteId);
    }

    private WalkRouteDTO mapToDTO(final WalkRoute walkRoute, final WalkRouteDTO walkRouteDTO) {
        walkRouteDTO.setWalkrouteId(walkRoute.getWalkrouteId());
        walkRouteDTO.setWalkrouteName(walkRoute.getWalkrouteName());
        walkRouteDTO.setLocation(walkRoute.getLocation());
        walkRouteDTO.setDistanceKm(walkRoute.getDistanceKm());
        walkRouteDTO.setPopularity(walkRoute.getPopularity());
        walkRouteDTO.setCreatedAt(walkRoute.getCreatedAt());
        walkRouteDTO.setUpdatedAt(walkRoute.getUpdatedAt());
        walkRouteDTO.setUser(walkRoute.getUser() == null ? null : walkRoute.getUser().getUserId());
        return walkRouteDTO;
    }

    private WalkRoute mapToEntity(final WalkRouteDTO walkRouteDTO, final WalkRoute walkRoute) {
        walkRoute.setWalkrouteName(walkRouteDTO.getWalkrouteName());
        walkRoute.setLocation(walkRouteDTO.getLocation());
        walkRoute.setDistanceKm(walkRouteDTO.getDistanceKm());
        walkRoute.setPopularity(walkRouteDTO.getPopularity());
        walkRoute.setCreatedAt(walkRouteDTO.getCreatedAt());
        walkRoute.setUpdatedAt(walkRouteDTO.getUpdatedAt());
        final User user = walkRouteDTO.getUser() == null ? null : userRepository.findById(walkRouteDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        walkRoute.setUser(user);
        return walkRoute;
    }

    public ReferencedWarning getReferencedWarning(final Integer walkrouteId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final WalkRoute walkRoute = walkRouteRepository.findById(walkrouteId)
                .orElseThrow(NotFoundException::new);
        final Missing walkrouteMissing = missingRepository.findFirstByWalkroute(walkRoute);
        if (walkrouteMissing != null) {
            referencedWarning.setKey("walkRoute.missing.walkroute.referenced");
            referencedWarning.addParam(walkrouteMissing.getMissingId());
            return referencedWarning;
        }
        return null;
    }

}
