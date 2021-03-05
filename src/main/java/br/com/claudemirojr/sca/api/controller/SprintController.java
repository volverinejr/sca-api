package br.com.claudemirojr.sca.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.service.ISprintService;
import br.com.claudemirojr.sca.api.model.service.ISprintSolicitacaoService;
import br.com.claudemirojr.sca.api.model.vo.SprintSolicitacaoInput;
import br.com.claudemirojr.sca.api.model.vo.SprintVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "SprintEndpoint")
@RestController
@RequestMapping("/api/sprint/v1")
@PreAuthorize("hasAnyRole('ADMIN', 'SPRINT_ALL')")
public class SprintController {

	@Autowired
	private ISprintService service;

	@Autowired
	private ISprintSolicitacaoService sprintSolicitacao;

	@Operation(summary = "Criar um novo sprint")
	@PostMapping
	public ResponseEntity<SprintVO> create(@RequestBody @Valid SprintVO sprint) {

		SprintVO sprintVO = service.create(sprint);

		sprintVO.add(linkTo(methodOn(SprintController.class).findById(sprintVO.getKey())).withSelfRel());

		return new ResponseEntity<>(sprintVO, HttpStatus.CREATED);
	}

	@Operation(summary = "Atualizar um sprint específico")
	@PutMapping
	public ResponseEntity<SprintVO> update(@RequestBody @Valid SprintVO sprint) {

		SprintVO sprintVO = service.update(sprint);

		sprintVO.add(linkTo(methodOn(SprintController.class).findById(sprintVO.getKey())).withSelfRel());

		return new ResponseEntity<>(sprintVO, HttpStatus.OK);
	}

	@Operation(summary = "Deletar um sprint específico pelo seu ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Find all sprints, por páginas")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<SprintVO> sprints = service.findAll(prm);

		sprints.stream()
				.forEach(p -> p.add(linkTo(methodOn(SprintController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(sprints);
	}

	@Operation(summary = "Find all sprints, cujo {id} seja maior ou igual, por páginas")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<SprintVO> sprints = service.findByIdGreaterThanEqual(id, prm);

		sprints.stream()
				.forEach(p -> p.add(linkTo(methodOn(SprintController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(sprints);
	}

	@Operation(summary = "procurar um sprint específico pelo seu ID")
	@GetMapping(value = "/{id}")
	public SprintVO findById(@PathVariable("id") Long id) {
		SprintVO sprintVO = service.findById(id);
		sprintVO.add(linkTo(methodOn(SprintController.class).findById(id)).withSelfRel());
		return sprintVO;
	}

	@Operation(summary = "Dado um {id} da sprint, Listar todos as solicitações ( ANALISADA/PLANEJADA ) sinalizando qual está vinculado a ele")
	@GetMapping("/{id}/solicitacao")
	public ResponseEntity<?> findBySprintSolicitacao(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		return new ResponseEntity<>(sprintSolicitacao.findBySprintSolicitacao(id, prm), HttpStatus.OK);
	}

	@Operation(summary = "Vincular solicitação à sprint")
	@PostMapping("/solicitacao")
	public ResponseEntity<?> addSolicitacaoASprint(@RequestBody @Valid SprintSolicitacaoInput sprintSolicitacaoInput) {
		sprintSolicitacao.addSolicitacaoASprint(sprintSolicitacaoInput.getSolicitacaoId(),
				sprintSolicitacaoInput.getSprintId());

		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	@Operation(summary = "Remover vinculo da solicitaçãocom com a sprint")
	@DeleteMapping("/{id}/solicitacao/{solicitacaoId}")
	public ResponseEntity<?> deleteSistemaDoCliente(@PathVariable Long id, @PathVariable Long solicitacaoId) {

		sprintSolicitacao.deleteSistemaDoCliente(solicitacaoId, id);

		return ResponseEntity.ok().build();
	}
	
	
	@Operation(summary = "Encaminhar sprint ao time")
	@PatchMapping("/{id}")
	public ResponseEntity<?> encaminarAoTime(@PathVariable Long id) {

		service.encaminarAoTime(id);

		return ResponseEntity.ok().build();
	}	

}
