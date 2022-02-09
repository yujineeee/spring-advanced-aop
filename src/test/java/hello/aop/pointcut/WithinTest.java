package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.assertThat;

import hello.aop.member.MemberServiceImpl;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WithinTest {

    AspectJExpressionPointcut pointCut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void withinExact() {
        pointCut.setExpression("within(hello.aop.member.MemberServiceImpl)");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinStar() {
        pointCut.setExpression("within(hello.aop.member.*Service*)");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinSubPackage() {
        pointCut.setExpression("within(hello.aop..*)");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타켓의 타입에만 직접 적용, 인터페이스를 선정하면 안됨")
    void withinSuperTypeFalse() {
        pointCut.setExpression("within(hello.aop.member.MemberService)");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("execution은 타입 기반, 인터페이스를 선정 가능...")
    void executionSuperTypeTrue() {
        pointCut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        assertThat(pointCut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
