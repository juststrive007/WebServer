package tool.sysinfo.linux.memory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Create one snapshot once new a instance or invoking {@link #setSnapshot()}, 
 * <br>And you could get informations from given methods
 * @author ZHJIE
 *
 */
public class Handler4meminfo {
	private Snapshot4meminfo shot = new Snapshot4meminfo();
	private final static String meminfoPath = "/proc/meminfo";
	
	public Handler4meminfo(boolean canInit) {
		if (canInit) {
			setSnapshot();
		}
	}
	
	/**
	 * Read file /proc/meminfo, then get and set useful information
	 */
	public void setSnapshot() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(meminfoPath)));
			String line;
			int hitCount = 0;
			while ((line=br.readLine().trim()) != null) {
				if (line.startsWith("MemTotal:")) {
					hitCount ++;
					String[] infoArray = line.split("\\s+");
					shot.setMemTotal(Long.valueOf(infoArray[1]));
				} else if (line.startsWith("MemFree:")) {
					hitCount ++;
					String[] infoArray = line.split("\\s+");
					shot.setMemFree(Long.valueOf(infoArray[1]));
				} else if (line.startsWith("Buffers:")) {
					hitCount ++;
					String[] infoArray = line.split("\\s+");
					shot.setBuffers(Long.valueOf(infoArray[1]));
				} else if (line.startsWith("Cached:")) {
					hitCount ++;
					String[] infoArray = line.split("\\s+");
					shot.setCached(Long.valueOf(infoArray[1]));
				}
				
				if (hitCount >= 4)
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Unit: kB
	 * @return
	 */
	public long getMemTotal() {
		return shot.getMemTotal();
	}
	
	/**
	 * Unit: kB
	 * <ul>
	 * �˴������ּ��㷽ʽ
	 * <li>��Ϊcached�ڴ��buffers�ڴ����ڱ�ռ�õ��ڴ棬�����ڴ�ֱ�ӷ���{@link Snapshot4meminfo#getMemFree()}</li>
	 * <li>��Ϊ���������ڴ����ڿ����ڴ棬�����ڴ淵��{@link Snapshot4meminfo#getMemFree()}�� {@link Snapshot4meminfo#getBuffers()}�� {@link Snapshot4meminfo#getCached()}֮��
	 * <br><b>XXX -->�˴�ʹ���˵�һ�ּ��㷽ʽ</b>
	 * </ul>
	 * @return
	 */
	public long getMemFree() {
		return shot.getMemFree();
	}
}
