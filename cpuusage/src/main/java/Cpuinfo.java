import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

import static java.lang.Thread.sleep;


public class Cpuinfo {
    private static OperatingSystemMXBean operatingSystemMXBean;

    static {
        operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    }

    /**
     * 获取cpu使用率
     * @return
     */
    public static int getSystemCpuLoad() {
        double cpuLoad = operatingSystemMXBean.getSystemCpuLoad();
        int percentCpuLoad = (int) (cpuLoad * 100);
        return percentCpuLoad;
    }

    /**
     * 获取cpu数量
     * @return
     */
    public static int getSystemCpuCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static void main(String[] args) throws InterruptedException {
       //循环输出100s内CPU数量与利用率
        for(int i=0;i<100;i++) {
            System.out.println("cpu usage:" + Cpuinfo.getSystemCpuLoad()+","+
                    "cpu count:" + Cpuinfo.getSystemCpuCount()        );
            sleep(1000);
        }
        ;


    }
}
