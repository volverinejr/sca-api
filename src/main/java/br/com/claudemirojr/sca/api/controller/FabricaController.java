package br.com.claudemirojr.sca.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
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
import br.com.claudemirojr.sca.api.model.service.IFabricaService;
import br.com.claudemirojr.sca.api.model.service.IUserTimeService;
import br.com.claudemirojr.sca.api.model.vo.AnaliseVO;
import br.com.claudemirojr.sca.api.model.vo.SolicitacaoFaseVO;
import br.com.claudemirojr.sca.api.model.vo.SolicitacaoVO;
import br.com.claudemirojr.sca.api.model.vo.SprintVO;
import br.com.claudemirojr.sca.api.security.model.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "FabricaEndpoint")
@RestController
@RequestMapping("/api/fabrica/v1")
@PreAuthorize("hasAnyRole('ADMIN', 'FABRICA_ALL')")
public class FabricaController {

	@Autowired
	private IFabricaService service;

	@Autowired
	private IUserTimeService userTimeService;

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

	@Operation(summary = "Find all solicitações da sprint do time, por páginas")
	@GetMapping("/{id}/solicitacao")
	public ResponseEntity<?> FindBySolicitacaoDaSprint(@PathVariable("id") Long id,
			@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<AnaliseVO> solicitacoes = service.FindBySolicitacaoDaSprint(id, prm);

		solicitacoes.stream()
				.forEach(p -> p.add(linkTo(methodOn(SolicitacaoController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(solicitacoes);
	}

	@Operation(summary = "Find all fases da solicitação do sprint do time, por páginas")
	@GetMapping("/{id}/solicitacao/{idSolicitacao}")
	public ResponseEntity<?> FindBySprintSolicitacaoFase(
			@PathVariable("id") Long id,
			@PathVariable("idSolicitacao") Long idSolicitacao, 
			@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<SolicitacaoFaseVO> fases = service.FindBySprintSolicitacaoFase(id, idSolicitacao, prm);

		fases.stream().forEach(p -> p.add(linkTo(methodOn(FaseController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(fases);
	}

	@Operation(summary = "procurar uma solicitação, que pertença ao time, específico pelo seu ID")
	@GetMapping(value = "/sprint/{idSprint}/solicitacao/{idSolicitacao}")
	public SolicitacaoVO findBySolicitacaoDaSprint(@PathVariable("idSprint") Long idSprint,
			@PathVariable("idSolicitacao") Long idSolicitacao) {
		SolicitacaoVO solicitacaoVO = service.findBySolicitacaoPorId(idSprint, idSolicitacao);

		return solicitacaoVO;
	}

	@Operation(summary = "Criar uma nova fase da Solicitação")
	@PostMapping("/sprint/{idSprint}/solicitacao/{idSolicitacao}")
	public ResponseEntity<SolicitacaoFaseVO> createFase(@PathVariable("idSprint") Long idSprint,
			@PathVariable("idSolicitacao") Long idSolicitacao, @RequestBody @Valid SolicitacaoFaseVO solicitacaoFase) {
		service.createFase(idSprint, idSolicitacao, solicitacaoFase);

		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	@Operation(summary = "procurar uma fase da solicitação específica pelo seu ID")
	@GetMapping("/sprint/{idSprint}/solicitacao/{idSolicitacao}/fase/{idFase}")
	public SolicitacaoFaseVO FindByFase(@PathVariable("idSprint") Long idSprint,
			@PathVariable("idSolicitacao") Long idSolicitacao, @PathVariable("idFase") Long idFase) {

		SolicitacaoFaseVO solicitacaoFaseVO = service.FindByFase(idSprint, idSolicitacao, idFase);

		return solicitacaoFaseVO;
	}

	@Operation(summary = "Atualizar uma fase específica da solicitação")
	@PutMapping("/sprint/{idSprint}/solicitacao/{idSolicitacao}")
	public ResponseEntity<SolicitacaoFaseVO> updateFase(@PathVariable("idSprint") Long idSprint,
			@PathVariable("idSolicitacao") Long idSolicitacao, @RequestBody @Valid SolicitacaoFaseVO solicitacaoFase) {

		service.updateFase(idSprint, idSolicitacao, solicitacaoFase);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@Operation(summary = "Deletar uma fase específica da solicitação")
	@DeleteMapping("/sprint/{idSprint}/solicitacao/{idSolicitacao}/fase/{idFase}")
	public ResponseEntity<?> deleteFase(@PathVariable("idSprint") Long idSprint,
			@PathVariable("idSolicitacao") Long idSolicitacao, @PathVariable("idFase") Long idFase) {
		service.deleteFase(idSprint, idSolicitacao, idFase);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "listar os usuários que podem ser responsaveis pela solicitação")
	@GetMapping("/sprint/{idSprint}/solicitacao/{idSolicitacao}/responsavel")
	public List<UserVO> findByMovimentacao(@PathVariable("idSprint") Long idSprint,
			@PathVariable("idSolicitacao") Long idSolicitacao) {
		List<UserVO> userTime = userTimeService.findByUsuariosDoTimeDaSolicitacao(idSolicitacao);

		return userTime;
	}

}
