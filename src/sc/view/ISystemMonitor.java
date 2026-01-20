package sc.view;

public interface ISystemMonitor {
    long getTotalMemory();
    long getFreeMemory();
    long getUsedMemory();
    int getCpuPercentage();
}
