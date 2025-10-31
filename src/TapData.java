public interface TapData {
    //getters for debug tap
    long getUsedMemory();
    long getCpuPercentage();
    long getTotalMemory();
    long getFreeMemory();
    double jvmCpuLoad();
    int getVirtualMouseX();
    int getVirtualMouseY();
}
