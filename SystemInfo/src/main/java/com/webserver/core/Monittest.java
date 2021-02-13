package com.webserver.core;

public class Monittest {
    //测试代码
    public static void main(String[] args) throws Exception {
        MonitorInfoBean monitorInfo = new MonitorServiceImpl().getMonitorInfoBean();
        System.out.println(monitorInfo.getProcessDetail());
        System.out.println("cpu占有率=" + monitorInfo.getCpuRatio());
        System.out.println("总的物理内存=" + monitorInfo.getTotalMemorySize() + "MB");
        System.out.println("已使用的物理内存=" + monitorInfo.getUsedMemory() + "MB");
        System.out.println("进程总数=" + monitorInfo.getTotalProcess());
        System.out.println("OS name: "+monitorInfo.getOsName());
    }

}
