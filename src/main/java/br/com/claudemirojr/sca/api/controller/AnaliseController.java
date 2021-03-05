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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.service.IAnaliseService;
import br.com.claudemirojr.sca.api.model.vo.AnaliseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AnaliseEndpoint")
@RestController
@RequestMapping("/api/analise/v1")
@PreAuthorize("hasAnyRole('ADMIN', 'ANALISE_ALL')")
public class AnaliseController {

	@Autowired
	private IAnaliseService service;

	@Operation(summary = "Find all Solicitações, para/ou em análise, por páginas")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<AnaliseVO> analise = service.FindByAnalise(prm);

		analise.stream()
				.forEach(p -> p.add(linkTo(methodOn(AnaliseController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(analise);
	}

	@Operation(summary = "procurar um Solicitação específico pelo seu ID")
	@GetMapping("/{id}")
	public AnaliseVO findById(@PathVariable("id") Long id) {
		AnaliseVO analiseVO = service.findById(id);

		analiseVO.add(linkTo(methodOn(AnaliseController.class).findById(id)).withSelfRel());

		return analiseVO;
	}

	@Operation(summary = "Atualizar a prioridade de uma solicitação")
	@PutMapping
	public ResponseEntity<AnaliseVO> update(@RequestBody @Valid AnaliseVO analise) {

		AnaliseVO analiseVO = service.update(analise);

		analiseVO.add(linkTo(methodOn(AnaliseController.class).findById(analiseVO.getKey())).withSelfRel());

		return new ResponseEntity<>(analiseVO, HttpStatus.OK);
	}

}
