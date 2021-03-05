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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.service.ISolicitacaoService;
import br.com.claudemirojr.sca.api.model.vo.ClienteSistemaVO;
import br.com.claudemirojr.sca.api.model.vo.ClienteVO;
import br.com.claudemirojr.sca.api.model.vo.SolicitacaoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "SolicitacaoEndpoint")
@RestController
@RequestMapping("/api/solicitacao/v1")
@PreAuthorize("hasAnyRole('ADMIN', 'SOLICITACAO_ALL')")
public class SolicitacaoController {

	@Autowired
	private ISolicitacaoService service;

	@Operation(summary = "Criar uma nova Solicitação")
	@PostMapping
	public ResponseEntity<SolicitacaoVO> create(@RequestBody @Valid SolicitacaoVO solicitacao) {

		SolicitacaoVO solicitacaoVO = service.create(solicitacao);

		solicitacaoVO.add(linkTo(methodOn(SolicitacaoController.class).findById(solicitacaoVO.getKey())).withSelfRel());

		return new ResponseEntity<>(solicitacaoVO, HttpStatus.CREATED);
	}

	@Operation(summary = "Atualizar uma nova Solicitação")
	@PutMapping
	public ResponseEntity<SolicitacaoVO> update(@RequestBody @Valid SolicitacaoVO solicitacao) {

		SolicitacaoVO solicitacaoVO = service.update(solicitacao);

		solicitacaoVO.add(linkTo(methodOn(SolicitacaoController.class).findById(solicitacaoVO.getKey())).withSelfRel());

		return new ResponseEntity<>(solicitacaoVO, HttpStatus.OK);
	}

	@Operation(summary = "procurar um solicitação específico pelo seu ID")
	@GetMapping("/{id}")
	public SolicitacaoVO findById(@PathVariable("id") Long id) {
		SolicitacaoVO solicitacaoVO = service.findById(id);

		solicitacaoVO.add(linkTo(methodOn(SolicitacaoController.class).findById(id)).withSelfRel());

		return solicitacaoVO;
	}

	@Operation(summary = "Find all Solicitações, por páginas")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<SolicitacaoVO> solicitacoes = service.findAll(prm);

		solicitacoes.stream()
				.forEach(p -> p.add(linkTo(methodOn(SolicitacaoController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(solicitacoes);
	}

	@Operation(summary = "Find all Clientes do Usuário logado, por páginas")
	@GetMapping("/clientes")
	public ResponseEntity<?> FindByClientesDoUsuario(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<ClienteVO> clientes = service.FindByClientesDoUsuario(prm);

		return ResponseEntity.ok(clientes);
	}

	@Operation(summary = "Find all Clientes do Usuário logado, por páginas")
	@GetMapping("/cliente/{id}/sistemas")
	public ResponseEntity<?> FindBySistemasDoCliente(@PathVariable("id") Long id,
			@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<ClienteSistemaVO> clientes = service.FindBySistemasDoCliente(id, prm);

		return ResponseEntity.ok(clientes);
	}

}
