package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.assertThat;

import hello.aop.member.MemberServiceImpl;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointCut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    void exactMatch() {
        //helloMethod=public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointCut.setExpression(
            "execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch() {
        pointCut.setExpression("execution(* *(..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch() {
        pointCut.setExpression("execution(* hello(..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1() {
        pointCut.setExpression("execution(* hel*(..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar2() {
        pointCut.setExpression("execution(* *el*(..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse() {
        pointCut.setExpression("execution(* nono(..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatch1() {
        pointCut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2() {
        pointCut.setExpression("execution(* hello.aop.member.*.*(..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatchFalse() {
        pointCut.setExpression("execution(* hello.aop.*.*(..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage1() {
        pointCut.setExpression("execution(* hello.aop.member..*.*(..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatchSubPackage2() {
        pointCut.setExpression("execution(* hello.aop..*.*(..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    @Test
    void typeExactMatch() {
        pointCut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        assertThat(pointCut.matches(helloMethod,
            MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchSuperType() { //부모 타입으로 해도 성공함
        pointCut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        pointCut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointCut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        pointCut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointCut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void argsMatch() {
        pointCut.setExpression("execution(* *(String))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsMatchNoArgs() {
        pointCut.setExpression("execution(* *())");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void argsMatchStar() {
        pointCut.setExpression("execution(* *(*))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsMatchAll() {
        pointCut.setExpression("execution(* *(..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsMatchComplex() {
        pointCut.setExpression("execution(* *(String, ..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

}
