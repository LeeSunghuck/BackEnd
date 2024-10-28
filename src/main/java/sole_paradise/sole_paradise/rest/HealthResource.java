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
import sole_paradise.sole_paradise.model.HealthDTO;
import sole_paradise.sole_paradise.service.HealthService;


@RestController
@RequestMapping(value = "/api/healths", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthResource {

    private final HealthService healthService;

    public HealthResource(final HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping
    public ResponseEntity<List<HealthDTO>> getAllHealths() {
        return ResponseEntity.ok(healthService.findAll());
    }

    @GetMapping("/{healthId}")
    public ResponseEntity<HealthDTO> getHealth(
            @PathVariable(name = "healthId") final Integer healthId) {
        return ResponseEntity.ok(healthService.get(healthId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createHealth(@RequestBody @Valid final HealthDTO healthDTO) {
        final Integer createdHealthId = healthService.create(healthDTO);
        return new ResponseEntity<>(createdHealthId, HttpStatus.CREATED);
    }

    @PutMapping("/{healthId}")
    public ResponseEntity<Integer> updateHealth(
            @PathVariable(name = "healthId") final Integer healthId,
            @RequestBody @Valid final HealthDTO healthDTO) {
        healthService.update(healthId, healthDTO);
        return ResponseEntity.ok(healthId);
    }

    @DeleteMapping("/{healthId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteHealth(
            @PathVariable(name = "healthId") final Integer healthId) {
        healthService.delete(healthId);
        return ResponseEntity.noContent().build();
    }

}
