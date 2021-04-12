package br.com.claudemirojr.sca.api.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.claudemirojr.sca.api.model.entity.Pesquisa;
import br.com.claudemirojr.sca.api.model.repository.PesquisaRepository;
import br.com.claudemirojr.sca.api.model.service.IPesquisaService;

@Service
public class PesquisaService implements IPesquisaService {

	@Autowired
	private PesquisaRepository pesquisaRepository;

	@Override
	public void create(Pesquisa pesquisa) {
		pesquisaRepository.save(pesquisa);
	}

}
