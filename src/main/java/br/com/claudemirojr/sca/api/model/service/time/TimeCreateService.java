package br.com.claudemirojr.sca.api.model.service.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.converter.DozerConverter;
import br.com.claudemirojr.sca.api.model.entity.Time;
import br.com.claudemirojr.sca.api.model.repository.TimeRepository;
import br.com.claudemirojr.sca.api.model.vo.TimeVO;

@Service
public class TimeCreateService implements ITimeCreateService {

	@Autowired
	private TimeRepository timeRepository;

	@Override
	@Transactional(readOnly = false)
	public TimeVO create(TimeVO timeVO) {
		var entity = DozerConverter.parseObject(timeVO, Time.class);
		entity.setId(null);
		
		return DozerConverter.parseObject(timeRepository.save(entity), TimeVO.class);
	}

}
