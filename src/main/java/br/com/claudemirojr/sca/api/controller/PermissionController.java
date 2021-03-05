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
import br.com.claudemirojr.sca.api.security.model.vo.PermissionVO;
import br.com.claudemirojr.sca.api.security.service.IPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PermissionEndpoint")
@RestController
@RequestMapping("/api/permissao/v1")
public class PermissionController {

	@Autowired
	private IPermissionService service;

	@Operation(summary = "Criar uma nova permissão")
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<PermissionVO> create(@RequestBody @Valid PermissionVO permission) {

		PermissionVO permissionVO = service.create(permission);

		permissionVO.add(linkTo(methodOn(PermissionController.class).findById(permissionVO.getKey())).withSelfRel());

		return new ResponseEntity<>(permissionVO, HttpStatus.CREATED);
	}

	@Operation(summary = "Atualizar uma permissão específica")
	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<PermissionVO> update(@RequestBody @Valid PermissionVO permission) {

		PermissionVO permissionVO = service.update(permission);

		permissionVO.add(linkTo(methodOn(PermissionController.class).findById(permissionVO.getKey())).withSelfRel());

		return new ResponseEntity<>(permissionVO, HttpStatus.OK);
	}

	@Operation(summary = "Deletar uma permissão específica pelo seu ID")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Find all permissões, por páginas")
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<PermissionVO> permissions = service.findAll(prm);

		permissions.stream()
				.forEach(p -> p.add(linkTo(methodOn(PermissionController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(permissions);
	}

	@Operation(summary = "Find all permissões, cujo {id} seja maior ou igual, por páginas")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<PermissionVO> permissions = service.findByIdGreaterThanEqual(id, prm);

		permissions.stream()
				.forEach(p -> p.add(linkTo(methodOn(PermissionController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(permissions);
	}

	@Operation(summary = "Find all permissões, cujo {nome} esteja contido, por páginas")
	@GetMapping("/findByNome/{nome}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> findByNome(@PathVariable String nome, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<PermissionVO> permissions = service.findByNome(nome, prm);

		permissions.stream()
				.forEach(p -> p.add(linkTo(methodOn(PermissionController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(permissions);
	}

	@Operation(summary = "procurar uma permissão específico pelo seu ID")
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PermissionVO findById(@PathVariable("id") Long id) {
		PermissionVO permissionVO = service.findById(id);
		permissionVO.add(linkTo(methodOn(PermissionController.class).findById(id)).withSelfRel());
		return permissionVO;
	}

}
