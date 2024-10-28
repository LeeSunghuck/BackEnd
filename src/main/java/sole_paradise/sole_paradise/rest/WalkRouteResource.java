package sole_paradise.sole_paradise.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sole_paradise.sole_paradise.model.WalkRouteDTO;
import sole_paradise.sole_paradise.service.WalkRouteService;
import sole_paradise.sole_paradise.util.ReferencedException;
import sole_paradise.sole_paradise.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/walkRoutes", produces = MediaType.APPLICATION_JSON_VALUE)
public class WalkRouteResource {

    private final WalkRouteService walkRouteService;

    public WalkRouteResource(final WalkRouteService walkRouteService) {
        this.walkRouteService = walkRouteService;
    }

    @GetMapping
    public ResponseEntity<List<WalkRouteDTO>> getAllWalkRoutes() {
        return ResponseEntity.ok(walkRouteService.findAll());
    }

    @GetMapping("/{walkrouteId}")
    public ResponseEntity<WalkRouteDTO> getWalkRoute(
            @PathVariable(name = "walkrouteId") final Integer walkrouteId) {
        return ResponseEntity.ok(walkRouteService.get(walkrouteId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createWalkRoute(
            @RequestBody @Valid final WalkRouteDTO walkRouteDTO) {
        final Integer createdWalkrouteId = walkRouteService.create(walkRouteDTO);
        return new ResponseEntity<>(createdWalkrouteId, HttpStatus.CREATED);
    }

    @PutMapping("/{walkrouteId}")
    public ResponseEntity<Integer> updateWalkRoute(
            @PathVariable(name = "walkrouteId") final Integer walkrouteId,
            @RequestBody @Valid final WalkRouteDTO walkRouteDTO) {
        walkRouteService.update(walkrouteId, walkRouteDTO);
        return ResponseEntity.ok(walkrouteId);
    }

    @DeleteMapping("/{walkrouteId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteWalkRoute(
            @PathVariable(name = "walkrouteId") final Integer walkrouteId) {
        final ReferencedWarning referencedWarning = walkRouteService.getReferencedWarning(walkrouteId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        walkRouteService.delete(walkrouteId);
        return ResponseEntity.noContent().build();
    }

}
