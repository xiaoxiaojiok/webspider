package com.coderxiao.webspider.monitor;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.JMException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coderxiao.webspider.Request;
import com.coderxiao.webspider.Spider;
import com.coderxiao.webspider.SpiderListener;
import com.coderxiao.webspider.util.Experimental;

/***
 * 爬虫监控类 </br>
 * 
 * @author XiaoJian
 *
 */
@Experimental
public class SpiderMonitor {

    private AtomicBoolean started = new AtomicBoolean(false);

    private Logger logger = LoggerFactory.getLogger(getClass());

    private MBeanServer mbeanServer;

    private String jmxServerName;

    private Map<Long,SpiderStatusMXBean> spiderStatuses = new HashMap<Long,SpiderStatusMXBean>();
    
    private WorkerStatusMXBean workerStatus = new WorkerStatus();

    private SpiderMonitor() {
        jmxServerName = "Spider";
        mbeanServer = ManagementFactory.getPlatformMBeanServer();
        initRegister();
    }
    
	private static class SpiderMonitorInstanceGet{
		private static final SpiderMonitor INSTANCE = new SpiderMonitor();
	}

	private void initRegister(){
	    ObjectName objName;
		try {
			objName = new ObjectName("Worker" + ":name=webservice");
			mbeanServer.registerMBean(workerStatus, objName);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
        
	}
	
    /**
     * Register spider for monitor.
     *
     * @param spiders
     * @return
     */
    public synchronized SpiderMonitor register(Spider... spiders) throws JMException {
        for (Spider spider : spiders) {
        	logger.info("[Monitor register] : UUID = "+spider.getUUID());
            MonitorSpiderListener monitorSpiderListener = new MonitorSpiderListener();
            if (spider.getSpiderListeners() == null) {
                List<SpiderListener> spiderListeners = new ArrayList<SpiderListener>();
                spiderListeners.add(monitorSpiderListener);
                spider.setSpiderListeners(spiderListeners);
            } else {
                spider.getSpiderListeners().add(monitorSpiderListener);
            }
            SpiderStatusMXBean spiderStatusMBean = getSpiderStatusMBean(spider, monitorSpiderListener);
            registerMBean(spiderStatusMBean);
            spiderStatuses.put(spider.getSite().getId(),spiderStatusMBean);
        }
        return this;
    }
    
    /**
     * UnRegister spider for monitor.
     *
     * @param spiders
     * @return
     */
    public synchronized SpiderMonitor unregister(Spider... spiders) throws JMException {
        for (Spider spider : spiders) {
        	SpiderStatusMXBean spiderStatus = spiderStatuses.get(spider.getSite().getId());
        	if(spiderStatus != null){
        		unregisterMBean(spiderStatus);
        	}
            spiderStatuses.remove(spider.getSite().getId());
        }
        return this;
    }

    protected SpiderStatusMXBean getSpiderStatusMBean(Spider spider, MonitorSpiderListener monitorSpiderListener) {
        return new SpiderStatus(spider, monitorSpiderListener);
    }

    public static SpiderMonitor instance() {
        return SpiderMonitorInstanceGet.INSTANCE;
    }

    protected void registerMBean(SpiderStatusMXBean spiderStatus) throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        ObjectName objName = new ObjectName(jmxServerName + ":name=" + spiderStatus.getName());
        mbeanServer.registerMBean(spiderStatus, objName);
    }
    
    protected void unregisterMBean(SpiderStatusMXBean spiderStatus) throws InstanceNotFoundException, MalformedObjectNameException, MBeanRegistrationException {
        ObjectName objName = new ObjectName(jmxServerName + ":name=" + spiderStatus.getName());
        mbeanServer.unregisterMBean(objName);
    }
    
    public Map<Long, SpiderStatusMXBean> getSpiderStatuses() {
		return spiderStatuses;
	}

	public void setSpiderStatuses(Map<Long, SpiderStatusMXBean> spiderStatuses) {
		this.spiderStatuses = spiderStatuses;
	}

	public WorkerStatusMXBean getWorkerStatus() {
		return workerStatus;
	}

	public void setWorkerStatus(WorkerStatusMXBean workerStatus) {
		this.workerStatus = workerStatus;
	}

	public class MonitorSpiderListener implements SpiderListener {

        private final AtomicLong successCount = new AtomicLong(0);

        private final AtomicLong errorCount = new AtomicLong(0);

        private List<String> errorUrls = Collections.synchronizedList(new ArrayList<String>());

        @Override
        public void onSuccess(Request request) {
            successCount.incrementAndGet();
        }

        @Override
        public void onError(Request request) {
            errorUrls.add(request.getUrl());
            errorCount.incrementAndGet();
        }

        public AtomicLong getSuccessCount() {
            return successCount;
        }

        public AtomicLong getErrorCount() {
            return errorCount;
        }

        public List<String> getErrorUrls() {
            return errorUrls;
        }
    }
}
