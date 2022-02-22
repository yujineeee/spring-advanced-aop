package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {
    //대안3 - 구조 변경 (스프링에서 가장 권장)

    private final InternalService internalService;

    public void external() {
        log.info("call external");
        internalService.internal();
    }

}
