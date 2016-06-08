package com.xiu.search.web.listener;

import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.log4j.MDC;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.IntrospectorCleanupListener;

import com.jolbox.bonecp.BoneCPDataSource;
import com.xiu.search.dao.cache.CacheManage;

public class XiuCleanupListener extends IntrospectorCleanupListener {

	private DataSource dataSource;
	
	private static final String FINALIZER_CLASS = "com.google.common.base.internal.Finalizer";
	private static final String GUICE_FINALIZER_CLASS = "com.google.inject.internal.Finalizer";
    private static final Field accessControlContext = getThreadAccField();
    private static final Field urlClassLoaderAccessControlContext = getUrlClAccField();
    // this is only for Guice Finalizer (it has an older copy of Finalizer from GCollect)
    private static final Field inheritableThreadLocals = getInheritableThreadLocalsField();
    private static final Logger logger = Logger.getLogger(XiuCleanupListener.class.getName());
	
	public void contextInitialized(ServletContextEvent sce) {
		// Get data source
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		if(null != context)
			dataSource = (DataSource)context.getBean("dataSources_goods");
		// Start cache clean thread
		CacheManage.startCleanExpiredThread();
		super.contextInitialized(sce);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
		// Shutdown http connection manager
		MultiThreadedHttpConnectionManager.shutdownAll();
		
		// Close data source
		if(null != dataSource){
			try {
				((BoneCPDataSource)dataSource).close();
			} catch (Exception e) { // ignore
			}
		}
		
		// Deregister jdbc drivers
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		Driver d = null;
		while(drivers.hasMoreElements()){
			d = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(d);
			} catch (SQLException e) {// ignore
			}
		}
		
		// google guava clean
		this.finalizerGoogleLeak();
		
		// log4j mdc lear
		MDC.clear();
		
		// Shut down cache clean thread
		CacheManage.shutdownCleanExpiredThread();
		super.contextInitialized(sce);
	}

//	protected SqlMapClientImpl[] getSqlMapClientImplInstances(FilterConfig filterConfig) throws Exception {
//        // find them in a ContextLoaderListener-loaded Spring WebApplicationContext
//        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
//        Map clientsMap = ctx.getBeansOfType(SqlMapClientImpl.class);
//        return (SqlMapClientImpl[])clientsMap.values().toArray(new SqlMapClientImpl[] { });
//    }
	
	
	
	
	
	
	
	///////////////////////////////////////////////////
	// This is for bonecp google guava gc
	///////////////////////////////////////////////////
	private void finalizerGoogleLeak(){
		AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                try {
                    visitAll(new ThreadVisitor() {
                        public void visit(Thread thread) {
                            String name = thread.getClass().getName();
                            if (FINALIZER_CLASS.equals(name) || GUICE_FINALIZER_CLASS.equals(name)) {

                               /* Strike one
                                * Get rid of ContextClassLoader. Just in case it was set by some odd coincidence.
                                * Should be unnecessary.
                                */
                                thread.setContextClassLoader(null);

                                /* Strike two
                                * Get rid of AccessControlContext
                                */
                                try {
                                    if (accessControlContext != null) {
                                        accessControlContext.set(thread, null);
                                    }
                                }
                                catch (Throwable t) {
                                    logger.log(Level.SEVERE, "Failed to clear thread access control context."
                                            + "Memory leak is still present.", t);
                                }

                               /* Strike three
                                * Get rid of AccessControlContext on URLClassLoader
                                * todo If this was loaded in DirectLoader maybe we can try and interrupt the thread?
                                * The finalizer doesn't care about interrupts
                                * If using Thread#Stop God would surely kill a Kitten.
                                */
                                ClassLoader threadClassLoader = thread.getClass().getClassLoader();
                                if (threadClassLoader instanceof URLClassLoader) {
                                    try {
                                        if (urlClassLoaderAccessControlContext != null) {
                                            urlClassLoaderAccessControlContext.set(threadClassLoader, null);
                                        }
                                    }
                                    catch (Throwable t) {
                                        logger.log(Level.SEVERE, "Failed to clear url class loader access control context."
                                                + "Memory leak is still present.", t);
                                    }
                                }

                               /* Strike three and a half
                                * For Guice's Finalizer
                                */
                                try {
                                    if (inheritableThreadLocals != null) {
                                        inheritableThreadLocals.set(thread, null);
                                    }
                                }
                                catch (Throwable t) {
                                    logger.log(Level.SEVERE, "Failed to clear thread local values inherited"
                                            + " Memory leak is still present.", t);
                                }

                                /* And you're out! */
                            }

                        }
                    });
                } catch (SecurityException ex) {
                    logger.log(Level.SEVERE, "Failed to patch any Finalizer threads due to Security restrictions.",
                            ex);
                }
                return null;
            }
        });
	}
	
	private static Field getThreadAccField() {
        Field[] declaredFields = Thread.class.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (AccessControlContext.class == declaredField.getType()) {
                declaredField.setAccessible(true);
                return declaredField;
            }
        }
        logger.log(Level.SEVERE, "Failed to get Thread#AccessControlerField field. Patcher is useless.");
        return null;
    }

    private static Field getUrlClAccField() {
        try {
            Field field = URLClassLoader.class.getDeclaredField("acc");
            field.setAccessible(true);
            return field;
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Failed to get URLClassLoader Acc field. Patcher is useless.");
        }
        return null;
    }

    private static Field getInheritableThreadLocalsField() {
        try {
            Field inheritableThreadLocals = Thread.class
                    .getDeclaredField("inheritableThreadLocals");
            inheritableThreadLocals.setAccessible(true);
            return inheritableThreadLocals;
        }
        catch (Throwable t) {
            logger.log(Level.SEVERE, "Couldn't access Thread.inheritableThreadLocals."
                    + " Patcher is useless.");
            return null;
        }
    }


    /* Cut and paste with slight modification from http://www.exampledepot.com/egs/java.lang/ListThreads.html */
    private static void visitAll(ThreadVisitor visitor) throws SecurityException {
        ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
        while (root.getParent() != null) {
            root = root.getParent();
        }

        visit(root, visitor);
    }

    private interface ThreadVisitor {
        void visit(Thread thread);
    }

    private static void visit(ThreadGroup group, ThreadVisitor visitor) {
        int numThreads = group.activeCount();
        Thread[] threads = new Thread[numThreads * 2];
        numThreads = group.enumerate(threads, false);

        for (int i = 0; i < numThreads; i++) {
            visitor.visit(threads[i]);
        }

        int numGroups = group.activeGroupCount();
        ThreadGroup[] groups = new ThreadGroup[numGroups * 2];
        numGroups = group.enumerate(groups, false);

        for (int i = 0; i < numGroups; i++) {
            visit(groups[i], visitor);
        }
    }
	
	
	
	
	
	
	
}
