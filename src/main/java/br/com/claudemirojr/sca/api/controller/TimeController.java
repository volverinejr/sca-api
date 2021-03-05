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
import br.com.claudemirojr.sca.api.model.service.ITimeService;
import br.com.claudemirojr.sca.api.model.vo.TimeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "TimeEndpoint")
@RestController
@RequestMapping("/api/time/v1")
public class TimeController {

	@Autowired
	private ITimeService service;

	@Operation(summary = "Criar um novo Time")
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'TIME_ALL', 'TIME_NEW')")
	public ResponseEntity<TimeVO> create(@RequestBody @Valid TimeVO time) {

		TimeVO timeVO = service.create(time);

		timeVO.add(linkTo(methodOn(TimeController.class).findById(timeVO.getKey())).withSelfRel());

		return new ResponseEntity<>(timeVO, HttpStatus.CREATED);
	}

	@Operation(summary = "Atualizar um time específico")
	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'TIME_ALL', 'TIME_UPDATE')")
	public ResponseEntity<TimeVO> update(@RequestBody @Valid TimeVO time) {

		TimeVO timeVO = service.update(time);

		timeVO.add(linkTo(methodOn(TimeController.class).findById(timeVO.getKey())).withSelfRel());

		return new ResponseEntity<>(timeVO, HttpStatus.OK);
	}

	@Operation(summary = "Deletar um time específico pelo seu ID")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'TIME_ALL', 'TIME_DELETE')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Find all times, por páginas")
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'TIME_ALL', 'TIME_LIST')")
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<TimeVO> times = service.findAll(prm);

		times.stream().forEach(p -> p.add(linkTo(methodOn(TimeController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(times);
	}

	@Operation(summary = "Find all times, cujo {id} seja maior ou igual, por páginas")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'TIME_ALL', 'TIME_LIST')")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<TimeVO> times = service.findByIdGreaterThanEqual(id, prm);

		times.stream().forEach(p -> p.add(linkTo(methodOn(TimeController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(times);
	}

	@Operation(summary = "Find all times, cujo {nome} esteja contido, por páginas")
	@GetMapping("/findByNome/{nome}")
	@PreAuthorize("hasAnyRole('ADMIN', 'TIME_ALL', 'TIME_LIST')")
	public ResponseEntity<?> findByNome(@PathVariable String nome, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<TimeVO> times = service.findByNome(nome, prm);

		times.stream().forEach(p -> p.add(linkTo(methodOn(TimeController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(times);
	}

	@Operation(summary = "procurar um time específico pelo seu ID")
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'TIME_ALL', 'TIME_DETALHE', 'TIME_UPDATE')")
	public TimeVO findById(@PathVariable("id") Long id) {
		TimeVO timeVO = service.findById(id);
		timeVO.add(linkTo(methodOn(TimeController.class).findById(id)).withSelfRel());
		return timeVO;
	}

}
