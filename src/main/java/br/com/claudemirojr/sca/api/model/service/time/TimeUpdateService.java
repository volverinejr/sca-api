package br.com.claudemirojr.sca.api.model.service.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.converter.DozerConverter;
import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.repository.TimeRepository;
import br.com.claudemirojr.sca.api.model.vo.TimeVO;

@Service
public class TimeUpdateService implements ITimeUpdateService {
	
	@Autowired
	private TimeRepository timeRepository;

	@Override
	@Transactional(readOnly = false)
	public TimeVO update(TimeVO timeVo) {
		var entity = timeRepository.findById(timeVo.getKey()).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Time não encontrado para id %d", timeVo.getKey())));

		entity.Atualizar(timeVo.getNome(), timeVo.getAtivo());

		return DozerConverter.parseObject(timeRepository.save(entity), TimeVO.class);
	}

}
