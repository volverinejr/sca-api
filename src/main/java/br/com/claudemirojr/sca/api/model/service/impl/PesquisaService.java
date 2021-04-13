package br.com.claudemirojr.sca.api.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.converter.DozerConverter;
import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.Pesquisa;
import br.com.claudemirojr.sca.api.model.repository.PesquisaRepository;
import br.com.claudemirojr.sca.api.model.service.IPesquisaService;
import br.com.claudemirojr.sca.api.model.vo.PesquisaVO;

@Service
public class PesquisaService implements IPesquisaService {

	@Autowired
	private PesquisaRepository pesquisaRepository;

	private PesquisaVO convertToPesquisaVO(Pesquisa entity) {
		return DozerConverter.parseObject(entity, PesquisaVO.class);
	}

	@Override
	@Transactional(readOnly = false)
	public void create(Pesquisa pesquisa) {
		pesquisaRepository.save(pesquisa);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PesquisaVO> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = pesquisaRepository.findAll(pageable);

		return page.map(this::convertToPesquisaVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PesquisaVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = pesquisaRepository.findByIdGreaterThanEqual(id, pageable);

		return page.map(this::convertToPesquisaVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PesquisaVO> findByClassName(String className, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = pesquisaRepository.findByClassNameIgnoreCaseContaining(className, pageable);

		return page.map(this::convertToPesquisaVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PesquisaVO> findByMethodName(String methodName, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = pesquisaRepository.findByMethodNameIgnoreCaseContaining(methodName, pageable);

		return page.map(this::convertToPesquisaVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PesquisaVO> findByArgumento(String argumento, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = pesquisaRepository.findByArgumentoIgnoreCaseContaining(argumento, pageable);

		return page.map(this::convertToPesquisaVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PesquisaVO> findByRetorno(String retorno, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = pesquisaRepository.findByRetornoIgnoreCaseContaining(retorno, pageable);

		return page.map(this::convertToPesquisaVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PesquisaVO> findByCreatedBy(String createdBy, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = pesquisaRepository.findByCreatedByIgnoreCaseContaining(createdBy, pageable);

		return page.map(this::convertToPesquisaVO);
	}

	@Override
	@Transactional(readOnly = true)
	public PesquisaVO findById(Long id) {
		var entity = pesquisaRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Pesquisa não encontrado para id %d", id)));
		return DozerConverter.parseObject(entity, PesquisaVO.class);
	}

}
