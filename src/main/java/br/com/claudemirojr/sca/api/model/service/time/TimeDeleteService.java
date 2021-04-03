package br.com.claudemirojr.sca.api.model.service.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.entity.Time;
import br.com.claudemirojr.sca.api.model.repository.TimeRepository;

@Service
public class TimeDeleteService implements ITimeDeleteService {

	@Autowired
	private TimeRepository timeRepository;

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		Time entity = timeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Time não encontrado para id %d", id)));
		timeRepository.delete(entity);
	}

}
