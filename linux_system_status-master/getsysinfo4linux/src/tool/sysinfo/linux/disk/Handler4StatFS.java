package tool.sysinfo.linux.disk;

public class Handler4StatFS {
	private StatFS statFS = new StatFS("/");	
	
	/**
	 * willInit is true - initialize once new a instance
	 * willInit is false - manually invoke {@link #setDiskInfo()}
	 * @param willInit
	 */
	public Handler4StatFS(boolean willInit) {
		if (willInit) {
			setDiskInfo();
		}
	}
	
	/**
	 * Get and set disk information once be called
	 */
	public void setDiskInfo() {
		/*
		 *  TODO �˴���ʱֻ��ȡ�˹��ص���Ŀ¼���ļ�ϵͳ�Ļ�����Ϣ��������ò��Ҫ��ȡȫ�����ص�Ȼ����ͲŶ�
		 *       ��Ϊ�����Ѿ���ʱ�������ҵ������ݲ�������һ���������ԣ�����
		 *       ��ȡ���й��ص�ǵ����Ŀ��������Դ��ļ���ȡ
		 *       ����df����ò���ձ��ǽ�һ������Ľ��
		 *       �ٴ��⣬�ٷֱ�Ϊʲô��ƥ�䣬df�İٷֱ���ʲô��
		 */
		statFS.setAll();
	}
	
	
	/**
	 * Total size of all file systems, include whole mount points
	 * Unit: MB
	 * @param path
	 * @return
	 */
	public long getTotalDisk() {
		return (statFS.getF_bsize() * statFS.getF_blocks()) >> 20;
	}
	
	/**
	 * Total free size of all file systems, include whole mount points
	 * Unit: MB
	 * @param path
	 * @return
	 */
	public long getFreeDisk() {
		return (statFS.getF_bsize() * statFS.getF_bfree()) >> 20;
	}
}
