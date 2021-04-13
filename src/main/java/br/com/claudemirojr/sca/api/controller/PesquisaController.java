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
import br.com.claudemirojr.sca.api.model.service.IPesquisaService;
import br.com.claudemirojr.sca.api.model.vo.PesquisaVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PesquisaEndpoint")
@RestController
@RequestMapping("/api/pesquisa/v1")
@PreAuthorize("hasAnyRole('ADMIN')")
public class PesquisaController {

	@Autowired
	private IPesquisaService service;

	@Operation(summary = "Find all pesquisas, por páginas")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<PesquisaVO> pesquisas = service.findAll(prm);

		pesquisas.stream()
				.forEach(p -> p.add(linkTo(methodOn(PesquisaController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(pesquisas);
	}

	@Operation(summary = "Find all pesquisas, cujo {id} seja maior ou igual, por páginas")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<PesquisaVO> pesquisas = service.findByIdGreaterThanEqual(id, prm);

		pesquisas.stream()
				.forEach(p -> p.add(linkTo(methodOn(PesquisaController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(pesquisas);
	}

	@Operation(summary = "Find all pesquisas, cujo {className} esteja contido, por páginas")
	@GetMapping("/findByClassName/{className}")
	public ResponseEntity<?> findByClassName(@PathVariable String className, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<PesquisaVO> pesquisas = service.findByClassName(className, prm);

		pesquisas.stream()
				.forEach(p -> p.add(linkTo(methodOn(PesquisaController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(pesquisas);
	}

	@Operation(summary = "Find all pesquisas, cujo {methodName} esteja contido, por páginas")
	@GetMapping("/findByMethodName/{methodName}")
	public ResponseEntity<?> findByMethodName(@PathVariable String methodName,
			@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<PesquisaVO> pesquisas = service.findByMethodName(methodName, prm);

		pesquisas.stream()
				.forEach(p -> p.add(linkTo(methodOn(PesquisaController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(pesquisas);
	}

	@Operation(summary = "Find all pesquisas, cujo {argumento} esteja contido, por páginas")
	@GetMapping("/findByArgumento/{argumento}")
	public ResponseEntity<?> findByArgumento(@PathVariable String argumento, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<PesquisaVO> pesquisas = service.findByArgumento(argumento, prm);

		pesquisas.stream()
				.forEach(p -> p.add(linkTo(methodOn(PesquisaController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(pesquisas);
	}

	@Operation(summary = "Find all pesquisas, cujo {retorno} esteja contido, por páginas")
	@GetMapping("/findByRetorno/{retorno}")
	public ResponseEntity<?> findByRetorno(@PathVariable String retorno, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<PesquisaVO> pesquisas = service.findByRetorno(retorno, prm);

		pesquisas.stream()
				.forEach(p -> p.add(linkTo(methodOn(PesquisaController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(pesquisas);
	}

	@Operation(summary = "Find all pesquisas, cujo {createdBy} esteja contido, por páginas")
	@GetMapping("/findByCreatedBy/{createdBy}")
	public ResponseEntity<?> findByCreatedBy(@PathVariable String createdBy, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<PesquisaVO> pesquisas = service.findByCreatedBy(createdBy, prm);

		pesquisas.stream()
				.forEach(p -> p.add(linkTo(methodOn(PesquisaController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(pesquisas);
	}

	@Operation(summary = "procurar uma pesquisa específica pelo seu ID")
	@GetMapping(value = "/{id}")
	public PesquisaVO findById(@PathVariable("id") Long id) {
		PesquisaVO pesquisaVO = service.findById(id);
		pesquisaVO.add(linkTo(methodOn(PesquisaController.class).findById(id)).withSelfRel());
		return pesquisaVO;
	}

}
