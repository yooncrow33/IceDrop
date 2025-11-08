package base;

import view.ISystemMonitor;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class SystemMonitor implements ISystemMonitor {
    Runtime run = Runtime.getRuntime();
    private final OperatingSystemMXBean mxbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    private long totalMemory;
    private long freeMemory;
    private long usedMemory;
    private double jvmCpuLoad;
    private int cpuPercentage;

    public SystemMonitor() {

    }

    public void updateMetrics() {
        Runtime runtime = Runtime.getRuntime();
        long totalBytes = runtime.totalMemory();
        long freeBytes = runtime.freeMemory();

        // MB 단위로 계산하여 저장
        this.totalMemory = totalBytes / (1024 * 1024);
        this.freeMemory = freeBytes / (1024 * 1024);
        this.usedMemory = this.totalMemory - this.freeMemory;

        // CPU 사용률 계산 (Java의 표준 방식은 복잡하여 간단한 예시로 대체)
        double processCpuLoad = mxbean.getSystemCpuLoad(); // 시스템 전체 부하
        this.cpuPercentage = (int) (processCpuLoad * 100);
    }

    @Override public long getTotalMemory() { return totalMemory; }
    @Override public long getFreeMemory() { return freeMemory; }
    @Override public long getUsedMemory() { return usedMemory; }
    @Override public int getCpuPercentage() { return cpuPercentage; }
}
