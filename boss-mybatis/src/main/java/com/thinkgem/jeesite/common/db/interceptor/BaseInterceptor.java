/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.db.interceptor;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.db.Page;
import com.thinkgem.jeesite.common.db.dialect.DB2Dialect;
import com.thinkgem.jeesite.common.db.dialect.DerbyDialect;
import com.thinkgem.jeesite.common.db.dialect.Dialect;
import com.thinkgem.jeesite.common.db.dialect.H2Dialect;
import com.thinkgem.jeesite.common.db.dialect.HSQLDialect;
import com.thinkgem.jeesite.common.db.dialect.MySQLDialect;
import com.thinkgem.jeesite.common.db.dialect.OracleDialect;
import com.thinkgem.jeesite.common.db.dialect.PostgreSQLDialect;
import com.thinkgem.jeesite.common.db.dialect.SQLServer2005Dialect;
import com.thinkgem.jeesite.common.db.dialect.SybaseDialect;
import com.thinkgem.jeesite.common.utils.Reflections;

/**
 * Mybatis分页拦截器基类
 * @author poplar.yfyang / thinkgem
 * @version 2013-8-28
 */
public abstract class BaseInterceptor implements Interceptor, Serializable {
	
	private static final long serialVersionUID = 1L;

    protected static final String PAGE = "page";
    
    protected static final String DELEGATE = "delegate";

    protected static final String MAPPED_STATEMENT = "mappedStatement";

    protected Log log = LogFactory.getLog(this.getClass());

    protected Dialect DIALECT;

//    /**
//     * 拦截的ID，在mapper中的id，可以匹配正则
//     */
//    protected String _SQL_PATTERN = "";

    /**
     * 对参数进行转换和检查
     * @param parameterObject 参数对象
     * @param page            分页对象
     * @return 分页对象
     * @throws NoSuchFieldException 无法找到参数
     */
	protected static Page<?> convertParameter(Object parameterObject, Page<?> page) {
    	try{
            if (parameterObject instanceof Page) {
                return (Page<?>) parameterObject;
            } else {
                return (Page<?>)Reflections.getFieldValue(parameterObject, PAGE);
            }
    	}catch (Exception e) {
			return null;
		}
    }
    
    @SuppressWarnings({ "rawtypes"})
    protected Page<?> seekPage(Object parameterObject) {
        Page<?> page = null;
        if (parameterObject == null) {
            return null;
        }
        if (parameterObject instanceof Page) {
            page = (Page<?>) parameterObject;
        } else if (parameterObject instanceof Map) {
            Map map = (Map) parameterObject;
            for (Object arg : map.values()) {
                if (arg instanceof Page) {
                    page = (Page<?>) arg;
                }
            }
        }
        
        return page;
    }
 

    /**
     * 设置属性，支持自定义方言类和制定数据库的方式
     * <code>dialectClass</code>,自定义方言类。可以不配置这项
     * <ode>dbms</ode> 数据库类型，插件支持的数据库
     * <code>sqlPattern</code> 需要拦截的SQL ID
     * @param p 属性
     */
    protected void initProperties(Properties p) {
    	Dialect dialect = null;
        String dbType = Global.getConfig("jdbc.type");
        if ("db2".equals(dbType)){
        	dialect = new DB2Dialect();
        }else if("derby".equals(dbType)){
        	dialect = new DerbyDialect();
        }else if("h2".equals(dbType)){
        	dialect = new H2Dialect();
        }else if("hsql".equals(dbType)){
        	dialect = new HSQLDialect();
        }else if("mysql".equals(dbType)){
        	dialect = new MySQLDialect();
        }else if("oracle".equals(dbType)){
        	dialect = new OracleDialect();
        }else if("postgre".equals(dbType)){
        	dialect = new PostgreSQLDialect();
        }else if("mssql".equals(dbType) || "sqlserver".equals(dbType)){
        	dialect = new SQLServer2005Dialect();
        }else if("sybase".equals(dbType)){
        	dialect = new SybaseDialect();
        }
        if (dialect == null) {
            throw new RuntimeException("mybatis dialect error.");
        }
        DIALECT = dialect;
//        _SQL_PATTERN = p.getProperty("sqlPattern");
//        _SQL_PATTERN = Global.getConfig("mybatis.pagePattern");
//        if (StringUtils.isEmpty(_SQL_PATTERN)) {
//            throw new RuntimeException("sqlPattern property is not found!");
//        }
    }
}
