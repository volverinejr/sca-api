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
import br.com.claudemirojr.sca.api.model.service.IClienteService;
import br.com.claudemirojr.sca.api.model.service.IClienteSistemaService;
import br.com.claudemirojr.sca.api.model.vo.ClienteSistemaInput;
import br.com.claudemirojr.sca.api.model.vo.ClienteVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ClienteEndpoint")
@RestController
@RequestMapping("/api/cliente/v1")
public class ClienteController {

	@Autowired
	private IClienteService service;

	@Autowired
	private IClienteSistemaService serviceClienteSistema;

	@Operation(summary = "Criar um novo cliente")
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE_ALL', 'CLIENTE_NEW')")
	public ResponseEntity<ClienteVO> create(@RequestBody @Valid ClienteVO cliente) {

		ClienteVO clienteVO = service.create(cliente);

		clienteVO.add(linkTo(methodOn(ClienteController.class).findById(clienteVO.getKey())).withSelfRel());

		return new ResponseEntity<>(clienteVO, HttpStatus.CREATED);
	}

	@Operation(summary = "Atualizar um cliente específico")
	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE_ALL', 'CLIENTE_UPDATE')")
	public ResponseEntity<ClienteVO> update(@RequestBody @Valid ClienteVO cliente) {

		ClienteVO clienteVO = service.update(cliente);

		clienteVO.add(linkTo(methodOn(ClienteController.class).findById(clienteVO.getKey())).withSelfRel());

		return new ResponseEntity<>(clienteVO, HttpStatus.OK);
	}

	@Operation(summary = "Deletar um cliente específico pelo seu ID")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE_ALL', 'CLIENTE_DELETE')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Find all clientes, por páginas")
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE_ALL', 'CLIENTE_LIST')")
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<ClienteVO> clientes = service.findAll(prm);

		clientes.stream()
				.forEach(p -> p.add(linkTo(methodOn(ClienteController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(clientes);
	}

	@Operation(summary = "Find all clientes, cujo {id} seja maior ou igual, por páginas")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE_ALL', 'CLIENTE_LIST')")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<ClienteVO> clientes = service.findByIdGreaterThanEqual(id, prm);

		clientes.stream()
				.forEach(p -> p.add(linkTo(methodOn(ClienteController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(clientes);
	}

	@Operation(summary = "Find all clientes, cujo {nome} esteja contido, por páginas")
	@GetMapping("/findByNome/{nome}")
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE_ALL', 'CLIENTE_LIST')")
	public ResponseEntity<?> findByNome(@PathVariable String nome, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<ClienteVO> clientes = service.findByNome(nome, prm);

		clientes.stream()
				.forEach(p -> p.add(linkTo(methodOn(ClienteController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(clientes);
	}

	@Operation(summary = "procurar um cliente específico pelo seu ID")
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE_ALL', 'CLIENTE_DETALHE', 'CLIENTE_UPDATE')")
	public ClienteVO findById(@PathVariable("id") Long id) {
		ClienteVO clienteVO = service.findById(id);
		clienteVO.add(linkTo(methodOn(ClienteController.class).findById(id)).withSelfRel());
		return clienteVO;
	}

	@Operation(summary = "Dado um {id} do cliente, Listar todos os sistemas sinalizando qual está vinculado a ele")
	@GetMapping("/{id}/sistemas")
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE_ALL', 'CLIENTE_DETALHE', 'CLIENTE_UPDATE')")
	public ResponseEntity<?> FindBySistemasDoCliente(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		return new ResponseEntity<>(serviceClienteSistema.FindBySistemasDoCliente(id, prm), HttpStatus.OK);
	}

	@Operation(summary = "Vincular sistema ao cliente")
	@PostMapping("/sistemas")
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE_ALL', 'CLIENTE_DETALHE', 'CLIENTE_UPDATE')")
	public ResponseEntity<?> addSistemaAoCliente(@RequestBody @Valid ClienteSistemaInput clienteSistemaInput) {
		serviceClienteSistema.addSistemaAoCliente(clienteSistemaInput.getClienteId(), clienteSistemaInput.getSistemaId());

		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Remover vinculo do sistema com o cliente")
	@DeleteMapping("/{id}/sistema/{sistemaId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE_ALL', 'CLIENTE_DETALHE', 'CLIENTE_UPDATE')")
	public ResponseEntity<?> deleteSistemaDoCliente(@PathVariable Long id, @PathVariable Long sistemaId) {
		
		serviceClienteSistema.deleteSistemaDoCliente(id, sistemaId);

		return ResponseEntity.ok().build();
	}
	

}
