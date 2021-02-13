package com.webserver.core;

import java.util.List;

public class MonitorInfoBean {


    //    可使用内存
    private long totalMemory;

    //    剩余内存
    private long freeMemory;

    //    最大可使用内存
    private long maxMemory;

    //    操作系统
    private String osName;

    //    总的物理内存
    private long totalMemorySize;

    //    剩余的物理内存
    private long freePhysicalMemorySize;

    //    已使用的物理内存
    private long usedMemory;

    //    线程总数
    private int totalThread;

    //    cpu使用率
    private double cpuRatio;

    private String time;

    private int totalProcess;

    private List processDetail;

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public long getTotalMemorySize() {
        return totalMemorySize;
    }

    public void setTotalMemorySize(long totalMemorySize) {
        this.totalMemorySize = totalMemorySize;
    }

    public long getFreePhysicalMemorySize() {
        return freePhysicalMemorySize;
    }

    public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
        this.freePhysicalMemorySize = freePhysicalMemorySize;
    }

    public long getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

    public int getTotalThread() {
        return totalThread;
    }

    public void setTotalThread(int totalThread) {
        this.totalThread = totalThread;
    }

    public double getCpuRatio() {
        return cpuRatio;
    }

    public void setCpuRatio(double cpuRatio) {
        this.cpuRatio = cpuRatio;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotalProcess() {
        return totalProcess;
    }

    public void setTotalProcess(int totalProcess) {
        this.totalProcess = totalProcess;
    }

    public List getProcessDetail() {
        return processDetail;
    }

    public void setProcessDetail(List processDetail) {
        this.processDetail = processDetail;
    }



}
