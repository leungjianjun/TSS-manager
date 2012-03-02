package nju.software.tss.system;

import java.sql.Timestamp;

/**
 * 采集系统存取数据JavaBean
 * @author ljj09
 * @deprecated 暂时先不用，这个类获得详细的内存，cpu信息等，不符合这个软件的需求
 *
 */
public class MonitorInfoBean implements Comparable<MonitorInfoBean> {

	/** 操作系统. */
	private String osName;

	/** 总的物理内存. */
	private float totalMemorySize;

	/** 已使用的物理内存. */
	private float usedMemory;

	/** cpu使用率. */
	private double cpuRatio;

	/** 主机IP地址 */
	private String mIpAddress;
	/** 数据存储时间 */
	private String dDateTime;

	/** 内存使用率 */
	private float memoryRatio;

	/** linux下Buffers内存 */
	private float buffersMemory;

	/** linux下Cached内存 */
	private float cachedMemory;

	public float getBuffersMemory() {
		return buffersMemory;
	}

	public float getCachedMemory() {
		return cachedMemory;
	}

	public String getDDateTime() {
		return dDateTime;
	}

	public void setDDateTime(String dateTime) {
		dDateTime = dateTime;
	}

	public String getMIpAddress() {
		return mIpAddress;
	}

	public void setMIpAddress(String ipAddress) {
		mIpAddress = ipAddress;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public float getTotalMemorySize() {
		return totalMemorySize;
	}

	public void setTotalMemorySize(float totalMemorySize) {
		this.totalMemorySize = totalMemorySize;
	}

	public float getUsedMemory() {
		return usedMemory;
	}

	public void setUsedMemory(long usedMemory) {
		this.usedMemory = usedMemory;
	}

	public double getCpuRatio() {
		return cpuRatio;
	}

	public void setCpuRatio(double cpuRatio) {
		this.cpuRatio = cpuRatio;
	}

	public int compareTo(MonitorInfoBean m) {
		String stra = this.getDDateTime();
		String strb = m.getDDateTime();
		Timestamp a = Timestamp.valueOf(stra);
		Timestamp b = Timestamp.valueOf(strb);
		if (a.before(b)) {
			return -1;
		} else if (a.after(b)) {
			return 1;
		} else {
			return 0;
		}

	}

	public float getMemoryRatio() {
		return memoryRatio;
	}

	public void setMemoryRatio(float memoryRatio) {
		this.memoryRatio = memoryRatio;
	}

	public void setUsedMemory(float usedMemory) {
		this.usedMemory = usedMemory;
	}

	public void setBuffersMemory(float buffersMemory) {
		this.buffersMemory = buffersMemory;
	}

	public void setCachedMemory(float cachedMemory) {
		this.cachedMemory = cachedMemory;
	}

}
