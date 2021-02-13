package tool.sysinfo.linux.cpu;

/**
 * Use linux file /proc/stat
 * @author ZHJIE
 *
 */
public class CPUInfoImpl implements CPUInfo {
	
	/**
	 * return average CPU usage between given time interval
	 * @param interval seconds
	 * @return
	 * @throws InterruptedException 
	 */
	public double getCPUUsage(int interval) throws InterruptedException {
		Handler4stat handler4stat = new Handler4stat(true);
		long total = handler4stat.getTotalCPUTime();
		long idle = handler4stat.getIdleCPUTime();
		
		Thread.sleep(interval * 1000);
		
		handler4stat.setSnapshot();
		long total2 = handler4stat.getTotalCPUTime();
		long idle2 = handler4stat .getIdleCPUTime();
		
		if (total2 == total) {
			// ��������δ�����仯����Ϊ���ʱ��������Ϊ0����ֹ����Ϊ0
			return 0;
		}
		
		return 1 - (double)(idle2 - idle)/(double)(total2 - total);
	}

}
