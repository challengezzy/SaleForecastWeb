package dmdd.dmddjava.dataaccess.hibernate;

import java.io.File;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import com.cool.common.constant.SysConst;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;

/**
 * Configures and provides access to Hibernate sessions, tied to the
 * current thread of execution.  Follows the Thread Local Session
 * pattern, see {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

    /** 
     * Location of hibernate.cfg.xml file.
     * Location should be on the classpath as Hibernate uses  
     * #resourceAsStream style lookup for its configuration file. 
     * The default classpath location of the hibernate config file is 
     * in the default package. Use #setConfigFile() to update 
     * the location of the configuration file for the current session.   
     */
//    private static String CONFIG_FILE_LOCATION_ORACLE = "/dmdd/configure/oracle/hibernate.cfg.xml";
//    private static String CONFIG_FILE_LOCATION_SQLSREVER = "/dmdd/configure/sqlserver/hibernate.cfg.xml";
//    private static String CONFIG_FILE_LOCATION_DB2 = "/dmdd/configure/db2/hibernate.cfg.xml";
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private  static Configuration configuration = new Configuration();
    private static org.hibernate.SessionFactory sessionFactory;
    private static String configFileUrl = "";//默认为oracle

	static {
    	try {
    		/* 配置文件在classpath目录下，编译后不方便修改，转移到WEB-INF目录下
    		Properties config = new Properties();
    		config.load(HibernateSessionFactory.class.getClassLoader().getResourceAsStream("dmdd.properties"));
    		String database_type=config.getProperty("database_type");   		
			if(database_type.equals("Microsoft SQL Server"))
			{
				configFile = CONFIG_FILE_LOCATION_SQLSREVER;
			}
			else if(database_type.equals("Oracle"))
			{
				configFile = CONFIG_FILE_LOCATION_ORACLE;
			}
			else if(database_type.equals("db2"))
			{
				configFile = CONFIG_FILE_LOCATION_DB2;
			}
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
			*/
    		
    		String confFileSubPath = "oracle";
    		String database_type = ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE);
    		if(database_type.equalsIgnoreCase(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER))
			{
    			confFileSubPath = "sqlserver";
			}
			else if(database_type.equalsIgnoreCase(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
			{
				confFileSubPath = "oracle";
			}
			else if(database_type.equalsIgnoreCase(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
			{
				confFileSubPath = "db2";
			}else{
				System.err.println("***** 不支持的数据库类型["+database_type+"] *****");
			}
    		
    		configFileUrl = SysConst.WEBROOT_PATH+"WEB-INF/config/"+confFileSubPath+"/hibernate.cfg.xml";
    		File configFile = new File(configFileUrl);
    		configuration.configure(configFile);
    		sessionFactory = configuration.buildSessionFactory();
    		
    		System.out.println("Hibernate连接构建成功!");
    		
		}catch (Exception e) {
			System.err.println("%%%% Error Creating Hibernate SessionFactory %%%%");
			e.printStackTrace();
		}
    }
	
	
    private HibernateSessionFactory() {
    	System.out.println("HibernateSessionFactory私有构造函数");
    }
	
	/**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

        return session;
    }

	/**
     *  Rebuild hibernate session factory
     *
     */
	public static void rebuildSessionFactory() {
		try {
			configuration.configure(configFileUrl);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);

        if (session != null) {
            session.close();
        }
    }

	/**
     *  return session factory
     *
     */
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
     *  return session factory
     *
     *	session factory will be rebuilded in the next call
     */
	public static void setConfigFile(String configFile) {
		HibernateSessionFactory.configFileUrl = configFile;
		sessionFactory = null;
	}

	/**
     *  return hibernate configuration
     *
     */
	public static Configuration getConfiguration() {
		return configuration;
	}

}