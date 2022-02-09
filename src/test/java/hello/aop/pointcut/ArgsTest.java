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
public class ArgsTest {

    AspectJExpressionPointcut pointCut = new AspectJExpressionPointcut();
    Method helloMethod;

    //선생님은 복사하시는데 저는 치기 싫어서 그냥 열심히 들었스빈다..
}
