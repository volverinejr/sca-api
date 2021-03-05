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
import br.com.claudemirojr.sca.api.model.service.ISistemaService;
import br.com.claudemirojr.sca.api.model.vo.SistemaVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "SistemaEndpoint")
@RestController
@RequestMapping("/api/sistema/v1")
public class SistemaController {

	@Autowired
	private ISistemaService service;

	@Operation(summary = "Criar um novo sistema")
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'SISTEMA_ALL', 'SISTEMA_NEW')")
	public ResponseEntity<SistemaVO> create(@RequestBody @Valid SistemaVO sistema) {

		SistemaVO sistemaVO = service.create(sistema);

		sistemaVO.add(linkTo(methodOn(SistemaController.class).findById(sistemaVO.getKey())).withSelfRel());

		return new ResponseEntity<>(sistemaVO, HttpStatus.CREATED);
	}

	@Operation(summary = "Atualizar um sistema específico")
	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'SISTEMA_ALL', 'SISTEMA_UPDATE')")
	public ResponseEntity<SistemaVO> update(@RequestBody @Valid SistemaVO sistema) {

		SistemaVO sistemaVO = service.update(sistema);

		sistemaVO.add(linkTo(methodOn(SistemaController.class).findById(sistemaVO.getKey())).withSelfRel());

		return new ResponseEntity<>(sistemaVO, HttpStatus.OK);
	}

	@Operation(summary = "Deletar um sistema específico pelo seu ID")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'SISTEMA_ALL', 'SISTEMA_DELETE')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Find all sistemas, por páginas")
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'SISTEMA_ALL', 'SISTEMA_LIST')")
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<SistemaVO> sistemas = service.findAll(prm);

		sistemas.stream()
				.forEach(p -> p.add(linkTo(methodOn(SistemaController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(sistemas);
	}

	@Operation(summary = "Find all sistemas, cujo {id} seja maior ou igual, por páginas")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'SISTEMA_ALL', 'SISTEMA_LIST')")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<SistemaVO> sistemas = service.findByIdGreaterThanEqual(id, prm);

		sistemas.stream()
				.forEach(p -> p.add(linkTo(methodOn(SistemaController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(sistemas);
	}

	@Operation(summary = "Find all sistemas, cujo {nome} esteja contido, por páginas")
	@GetMapping("/findByNome/{nome}")
	@PreAuthorize("hasAnyRole('ADMIN', 'SISTEMA_ALL', 'SISTEMA_LIST')")
	public ResponseEntity<?> findByNome(@PathVariable String nome,
			@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<SistemaVO> sistemas = service.findByNome(nome, prm);

		sistemas.stream()
				.forEach(p -> p.add(linkTo(methodOn(SistemaController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(sistemas);
	}

	@Operation(summary = "procurar um sistema específico pelo seu ID")
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'SISTEMA_ALL', 'SISTEMA_DETALHE', 'SISTEMA_UPDATE')")
	public SistemaVO findById(@PathVariable("id") Long id) {
		SistemaVO sistemaVO = service.findById(id);
		sistemaVO.add(linkTo(methodOn(SistemaController.class).findById(id)).withSelfRel());
		return sistemaVO;
	}

}
