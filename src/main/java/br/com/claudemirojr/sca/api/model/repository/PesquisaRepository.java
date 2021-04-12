package br.com.claudemirojr.sca.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.sca.api.model.entity.Pesquisa;

public interface PesquisaRepository extends JpaRepository<Pesquisa, Long> {

}
