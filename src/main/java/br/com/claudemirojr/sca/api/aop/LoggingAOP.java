package br.com.claudemirojr.sca.api.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.claudemirojr.sca.api.model.entity.Pesquisa;
import br.com.claudemirojr.sca.api.model.service.IPesquisaService;
import br.com.claudemirojr.sca.api.security.service.IUserService;

@Aspect
@Component
public class LoggingAOP {

	Logger log = LoggerFactory.getLogger(LoggingAOP.class);

	@Autowired
	private IUserService userService;

	@Autowired
	private IPesquisaService pesquisaService;

	@Pointcut(value = "execution(* br.com.claudemirojr.sca.api.controller.*.findBy*(..) )")
	public void myPointcut() {

	}

	@Around("myPointcut()")
	public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();

		String className = pjp.getTarget().getClass().toString();
		String methodName = pjp.getSignature().getName();
		Object[] array = pjp.getArgs();
		String argumento = mapper.writeValueAsString(array);

		if (!className.contains("PesquisaController")) {
			// log.info("Método chamado: " + className + "." + methodName + "()" + "
			// argumentos: " + argumento);

			Object object = pjp.proceed();

			String retorno = mapper.writeValueAsString(object);

			// log.info(className + "." + methodName + "()" + " Response: " + retorno);

			Pesquisa pesquisa = new Pesquisa();
			pesquisa.Novo(className, methodName, argumento, retorno, userService.getUsuarioLogado());
			pesquisaService.create(pesquisa);

			return object;
		} else {
			return null;
		}

	}
}
