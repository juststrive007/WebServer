package tool.sysinfo.linux.disk;

public class SyncDiskInfoJNI {
	static {
		System.loadLibrary("SyncDiskInfoJNI");
	}
	
	/**
	 * ����infoArray����Ӳ�̴�С��ص��ֶε�λΪ���ֽ�
	 * @param path
	 * @param infoArray
	 */
	public native void syncDiskInfo(final String path, long infoArray[]);
}
