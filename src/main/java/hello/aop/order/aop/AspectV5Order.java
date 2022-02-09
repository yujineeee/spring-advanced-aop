package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {

    @Aspect
    @Order(2)
    public static class LogAspect {
        //@order는 여기에해봤자 순서대로 안됨. 클래스 단위로 밖에 못함
        @Around("hello.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint jointPoint) throws Throwable {
            log.info("[log] {}", jointPoint.getSignature());
            return jointPoint.proceed();
        }
    }


    @Aspect
    @Order(1)
    public static class TxAspect {

        //hello.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *써비스
        @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
        public Object doTransaction(ProceedingJoinPoint jointPoint) throws Throwable {
            try {
                log.info("[트랜잭션 시작] {}", jointPoint.getSignature());
                Object result = jointPoint.proceed();
                log.info("[트랜잭션 커밋] {}", jointPoint.getSignature());
                return result;
            } catch (Exception e) {
                log.info("[트랜잭션 롤백 {}]", jointPoint.getSignature());
                throw e;
            } finally {
                log.info("[리소스 릴리즈 {}]", jointPoint.getSignature());
            }
        }
    }


}
