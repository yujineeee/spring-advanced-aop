package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(CallLogAspect.class)
public class CallServiceVOTest {

    @Autowired
    CallServiceVO callServiceVO;

    @Test
    void external(){
        //internal에 AOP 적용되지 않음
        callServiceVO.external();
    }

    @Test
    void internal(){
        callServiceVO.internal();
    }

}
