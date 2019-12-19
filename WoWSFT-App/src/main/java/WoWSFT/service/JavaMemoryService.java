package WoWSFT.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Slf4j
@Service
public class JavaMemoryService
{
    private static Runtime r = Runtime.getRuntime();

    public String showMemory()
    {
        final double gigabyte = 1024 * 1024 * 1024;

        DecimalFormat format = new DecimalFormat("##0.######");
        //JVM이 현재 시스템에 요구 가능한 최대 메모리량, 이 값을 넘으면 OutOfMemory 오류가 발생 합니다.
        double max = r.maxMemory() / gigabyte;
        //JVM이 현재 시스템에 얻어 쓴 메모리의 총량
        double total = r.totalMemory() / gigabyte;
        //JVM이 현재 시스템에 청구하여 사용중인 최대 메모리(total)중에서 사용 가능한 메모리
        double free = r.freeMemory() / gigabyte;

        return ("Max: " + format.format(max) + " GB, Total: " + format.format(total) + " GB, Free: "+format.format(free) + " GB");
    }
}
