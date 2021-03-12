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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.service.IFaseService;
import br.com.claudemirojr.sca.api.model.vo.FaseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "FaseEndpoint")
@RestController
@RequestMapping("/api/fase/v1")
public class FaseController {

	@Autowired
	private IFaseService service;

	@Operation(summary = "Criar uma nova Fase")
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'FASE_ALL')")
	public ResponseEntity<FaseVO> create(@RequestBody @Valid FaseVO fase) {

		FaseVO faseVO = service.create(fase);

		faseVO.add(linkTo(methodOn(FaseController.class).findById(faseVO.getKey())).withSelfRel());

		return new ResponseEntity<>(faseVO, HttpStatus.CREATED);
	}

	@Operation(summary = "Atualizar uma fase específica")
	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'FASE_ALL')")
	public ResponseEntity<FaseVO> update(@RequestBody @Valid FaseVO fase) {

		FaseVO faseVO = service.update(fase);

		faseVO.add(linkTo(methodOn(FaseController.class).findById(faseVO.getKey())).withSelfRel());

		return new ResponseEntity<>(faseVO, HttpStatus.OK);
	}

	@Operation(summary = "Deletar uma fase específico pelo seu ID")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'FASE_ALL')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Find all fases, por páginas")
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'FASE_ALL', 'FABRICA_ALL')")
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<FaseVO> fases = service.findAll(prm);

		fases.stream().forEach(p -> p.add(linkTo(methodOn(FaseController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(fases);
	}

	@Operation(summary = "Find all fases, cujo {id} seja maior ou igual, por páginas")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'FASE_ALL')")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<FaseVO> fases = service.findByIdGreaterThanEqual(id, prm);

		fases.stream().forEach(p -> p.add(linkTo(methodOn(FaseController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(fases);
	}

	@Operation(summary = "Find all fases, cujo {nome} esteja contido, por páginas")
	@GetMapping("/findByNome/{nome}")
	@PreAuthorize("hasAnyRole('ADMIN', 'FASE_ALL')")
	public ResponseEntity<?> findByNome(@PathVariable String nome, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<FaseVO> fases = service.findByNome(nome, prm);

		fases.stream().forEach(p -> p.add(linkTo(methodOn(FaseController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(fases);
	}

	@Operation(summary = "procurar uma fase específica pelo seu ID")
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'FASE_ALL')")
	public FaseVO findById(@PathVariable("id") Long id) {
		FaseVO faseVO = service.findById(id);
		faseVO.add(linkTo(methodOn(FaseController.class).findById(id)).withSelfRel());
		return faseVO;
	}

}
