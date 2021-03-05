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
import br.com.claudemirojr.sca.api.model.service.IUserClienteSistemaService;
import br.com.claudemirojr.sca.api.model.vo.UserClienteSistemaInput;
import br.com.claudemirojr.sca.api.security.model.vo.UserPermissionInput;
import br.com.claudemirojr.sca.api.security.model.vo.UserVO;
import br.com.claudemirojr.sca.api.security.model.vo.UserVOInput;
import br.com.claudemirojr.sca.api.security.service.IUserPermissionService;
import br.com.claudemirojr.sca.api.security.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserEndpoint")
@RestController
@RequestMapping("/api/user/v1")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

	@Autowired
	private IUserService service;

	@Autowired
	private IUserClienteSistemaService userClienteSistemaService;

	@Autowired
	private IUserPermissionService userPermissionService;

	@Operation(summary = "Criar um novo usuário")
	@PostMapping
	public ResponseEntity<UserVO> create(@RequestBody @Valid UserVOInput user) {
		UserVO userVO = service.create(user);

		userVO.add(linkTo(methodOn(UserController.class).findById(userVO.getKey())).withSelfRel());

		return new ResponseEntity<>(userVO, HttpStatus.CREATED);
	}

	@Operation(summary = "Atualizar um usuário específico")
	@PutMapping
	public ResponseEntity<UserVO> update(@RequestBody @Valid UserVOInput user) {

		UserVO userVO = service.update(user);

		userVO.add(linkTo(methodOn(UserController.class).findById(userVO.getKey())).withSelfRel());

		return new ResponseEntity<>(userVO, HttpStatus.OK);
	}

	@Operation(summary = "Deletar um usuário específica pelo seu ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Find all usuários, por páginas")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<UserVO> users = service.findAll(prm);

		users.stream().forEach(p -> p.add(linkTo(methodOn(UserController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(users);
	}

	@Operation(summary = "Find all usuários, cujo {id} seja maior ou igual, por páginas")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<UserVO> users = service.findByIdGreaterThanEqual(id, prm);

		users.stream().forEach(p -> p.add(linkTo(methodOn(UserController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(users);
	}

	@Operation(summary = "Find all usuários, cujo {nome} esteja contido e, userName, por páginas")
	@GetMapping("/findByNome/{nome}")
	public ResponseEntity<?> findByNome(@PathVariable String nome, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<UserVO> users = service.findByNome(nome, prm);

		users.stream().forEach(p -> p.add(linkTo(methodOn(UserController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(users);
	}

	@Operation(summary = "Find all usuários, cujo {nome} esteja contido em fullName, por páginas")
	@GetMapping("/findByNomeCompleto/{nome}")
	public ResponseEntity<?> findByNomeCompleto(@PathVariable String nome, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<UserVO> users = service.findByNomeCompleto(nome, prm);

		users.stream().forEach(p -> p.add(linkTo(methodOn(UserController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(users);
	}

	@Operation(summary = "procurar um usuário específico pelo seu ID")
	@GetMapping(value = "/{id}")
	public UserVO findById(@PathVariable("id") Long id) {
		UserVO userVO = service.findById(id);
		userVO.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
		return userVO;
	}

	@Operation(summary = "Dado um {id} do usuário, Listar todos os sistemas sinalizando qual está vinculado a ele")
	@GetMapping("/{id}/sistemas")
	public ResponseEntity<?> FindBySistemasDoUser(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		return new ResponseEntity<>(userClienteSistemaService.FindBySistemasDoUser(id, prm), HttpStatus.OK);
	}

	@Operation(summary = "Vincular sistema ao usuário")
	@PostMapping("/sistemas")
	public ResponseEntity<?> addSistemaAoUser(@RequestBody @Valid UserClienteSistemaInput userClienteSistemaInput) {
		userClienteSistemaService.addSistemaAoUser(userClienteSistemaInput.getUserId(),
				userClienteSistemaInput.getClienteSistemaId());

		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	@Operation(summary = "Remover vinculo do sistema com o usuário")
	@DeleteMapping("/{id}/sistema/{sistemaId}")
	public ResponseEntity<?> deleteSistemaDoCliente(@PathVariable Long id, @PathVariable Long sistemaId) {
		userClienteSistemaService.deleteSistemaDoUser(id, sistemaId);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Dado um {id} do usuário, Listar todas as permissões, sinalizando qual está vinculado a ele")
	@GetMapping("/{id}/permissao")
	public ResponseEntity<?> findByPermissaoDoUser(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		return new ResponseEntity<>(userPermissionService.findByPermissaoDoUser(id, prm), HttpStatus.OK);
	}

	@Operation(summary = "Vincular permissão ao usuário")
	@PostMapping("/permissao")
	public ResponseEntity<?> addPermissaoAoUser(@RequestBody @Valid UserPermissionInput userPermissionInput) {
		userPermissionService.addPermissaoAoUser(userPermissionInput.getPermissaoId(), userPermissionInput.getUserId());

		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	@Operation(summary = "Remover vinculo da permissão com o usuário")
	@DeleteMapping("/{id}/permissao/{permissaoId}")
	public ResponseEntity<?> deletePermissaoDoUser(@PathVariable Long id, @PathVariable Long permissaoId) {

		userPermissionService.deletePermissaoDoUser(permissaoId, id);

		return ResponseEntity.ok().build();
	}

}
