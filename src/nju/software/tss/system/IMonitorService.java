package nju.software.tss.system;

/**
 * 
 * @author ljj09
 * @deprecated 暂时先不用，这个类获得详细的内存，cpu信息等，不符合这个软件的需求
 *
 */
public interface IMonitorService {
	public MonitorInfoBean getMonitorInfoBean() throws Exception;
}
