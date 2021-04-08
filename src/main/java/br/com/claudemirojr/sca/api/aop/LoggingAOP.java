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

import br.com.claudemirojr.sca.api.security.service.impl.UserServices;

@Aspect
@Component
public class LoggingAOP {

	Logger log = LoggerFactory.getLogger(LoggingAOP.class);
	
	@Autowired
	private UserServices userServices;
	

	@Pointcut(value = "execution(* br.com.claudemirojr.sca.api.controller.*.*(..) )")
	public void myPointcut() {

	}

	@Around("myPointcut()")
	public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();

		String className = pjp.getTarget().getClass().toString();
		String methodName = pjp.getSignature().getName();
		Object[] array = pjp.getArgs();

		log.info("Usuário: " + userServices.getUsuarioLogado());
		
		log.info("Método chamado: " + className + "." + methodName + "()" + " argumentos: "
				+ mapper.writeValueAsString(array));

		Object object = pjp.proceed();
		
		log.info(className + "." + methodName + "()" + " Response: " + mapper.writeValueAsString(object));

		return object;
	}
}
