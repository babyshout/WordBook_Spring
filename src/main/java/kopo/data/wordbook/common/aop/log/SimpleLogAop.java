package kopo.data.wordbook.common.aop.log;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class SimpleLogAop {

    // TODO Pointcut execution 찾아서 바꿔주기
    @Pointcut("execution(* kopo.data.wordbook.app.*.*.*(..))")
    private void cut() {
    }

    // Pointcut 에 의해 필터링된 경로로 들어오는 경우 메서드 호출 전에 적용
    @Before(value = "cut()")
    public void beforeParameterLog(JoinPoint joinPoint) {
//        Method method = getMethod(joinPoint);

        log.info("===========" + joinPoint.getTarget() + " START!!!!");

        // 파라미터 받아오기
//        Object[] args = joinPoint.getArgs();
//        if (args.length == 0) {
//            log.debug("no Parameter");
//            return;
//        }
//        for (Object arg : args) {
//            log.debug("parameter type = {} ", arg.getClass().getSimpleName());
//            log.info("parameter type = {}", arg);
//        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterReturnLog(JoinPoint joinPoint, @Nullable Object returnObj) {
        // 메서드 정보 받아오기
//        Method method = getMethod(joinPoint);

        Optional<Object> optionalObject = Optional.ofNullable(returnObj);
        if (optionalObject.isEmpty()) {
            return;
        }


        log.info("return type = {}", returnObj.getClass().getSimpleName());
        if (returnObj instanceof Collection<?>) {
            ((Collection<?>) returnObj).parallelStream().limit(1)
                    .forEach(value -> log.debug("returning Object : " + value));
        } else {
            log.debug("returning Object : " + returnObj);
        }
        log.info("===========" + joinPoint.getSourceLocation() + " END!!!!");
    }

    private Method getMethod(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
