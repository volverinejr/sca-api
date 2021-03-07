package br.com.claudemirojr.sca.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.service.IFabricaService;
import br.com.claudemirojr.sca.api.model.vo.SprintVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "FabricaEndpoint")
@RestController
@RequestMapping("/api/fabrica/v1")
@PreAuthorize("hasAnyRole('ADMIN', 'FABRICA_ALL')")
public class FabricaController {

	@Autowired
	private IFabricaService service;

	@Operation(summary = "Find all sprints do time, por páginas")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<SprintVO> sptints = service.FindBySprintDoTime(prm);

		sptints.stream()
				.forEach(p -> p.add(linkTo(methodOn(FabricaController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(sptints);
	}

	@Operation(summary = "Find all sprints do time, cujo {id} seja maior ou igual, por páginas")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<SprintVO> sptints = service.findByIdGreaterThanEqualSprintDoTime(id, prm);

		sptints.stream()
				.forEach(p -> p.add(linkTo(methodOn(FabricaController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(sptints);
	}

	@Operation(summary = "procurar um sprint, que pertença ao time, específico pelo seu ID")
	@GetMapping(value = "/{id}")
	public SprintVO findById(@PathVariable("id") Long id) {
		SprintVO sprintVO = service.findById(id);
		sprintVO.add(linkTo(methodOn(FabricaController.class).findById(id)).withSelfRel());
		return sprintVO;
	}

}
