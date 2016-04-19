/**   
 * @Title: FileUtil.java
 * @Package com.fsmeeting.util
 * @Description: TODO(用一句话描述该文件做什么)
 * @author zhangxt  
 * @date 2016-4-15 下午4:26:17
 */
package com.thinkgem.jeesite.common.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName FileUtil
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author zhangxt
 * @Date 2016-4-15 下午4:26:17
 */
public class FileLoaderUtil {
	private static final Logger LOG = LoggerFactory.getLogger(FileLoaderUtil.class);

	public static String getJarDir() {
		String path = getDirFromClassLoader();
		if (path == null) {
			path = System.getProperty("user.dir");
		}
		return path;
	}

	/**
	 * 从通过Class Loading计算路径： 1 class文件通过jar包加载： 如果为jar包，该包为d:/test/myProj.jar
	 * 该方法返回d:/test这个目录（不受用户工作目录影响） 提示：在jar包加载
	 * 的时候，通过指定加载FileUtil的class资源得到jar:<url>!/{entry}计算出加载路径
	 *  2 class文件直接被加载：
	 * 如果是web工程
	 * ,class文件放在D:\tools\apache-tomcat-5.5.27\webapps\webProj\WEB-INF\classes
	 * 该方法返回D:\tools\apache-tomcat-5.5.27\webapps\webProj\WEB-INF
	 * 即返回放class文件夹的上一层目录。
	 * */
	private static String getDirFromClassLoader() {
		try {
			String path = FileLoaderUtil.class.getName().replace(".", "/");
			LOG.info("class path:" + path);
			
			path = "/" + path + ".class";
			URL url = FileLoaderUtil.class.getResource(path);
			String jarUrl = url.getPath();
			
			LOG.info("class url:" + jarUrl);
			if (jarUrl.startsWith("file:")) {
				if (jarUrl.length() > 5) {
					jarUrl = jarUrl.substring(5);
				}
				
				LOG.info("class url:" + jarUrl);
				
				jarUrl = jarUrl.split("!")[0];

			} else {
				jarUrl = FileLoaderUtil.class.getResource("/").toString()
						.substring(5);
			}
			File file = new File(jarUrl);
			return file.getParent();

		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 找出指定目录及其子目录下，满足指定后缀的文件的绝对路径。 提示：方法中出现异常被内部捕获。
	 * 
	 * @param dir
	 *            指定目录
	 * @param suffix
	 *            文件名后缀
	 * 
	 * @throws IllegalArgumentException
	 * */
	public static List<String> find(String dir, String suffix) {
		List<String> list = new ArrayList<String>();
		try {
			File file = new File(dir);
			if (file.exists() && file.isDirectory()) {
				find(file, suffix, list);
			} else {
				throw new IllegalArgumentException(
						"param \"dir\" must be an existing directory .dir = "
								+ dir);
			}
		} catch (Exception e) {
			LOG.error("gcw:find(dir,suffix) - " + e.getMessage());
		}
		return list;
	}

	/**
	 * 递归遍历，查找满足后缀的文件
	 * 
	 * @param dirFile
	 *            必须为一个存在的目录.不能为null
	 * @param suffix
	 * @param list
	 *            递归遍历目录记录满足后缀的文件的绝对路径。
	 * */
	private static void find(File dirFile, String suffix, List<String> list) {
		if (dirFile.exists() && dirFile.isDirectory()) {
			File[] subFiles = dirFile.listFiles();
			for (File subFile : subFiles) {
				if (subFile.isDirectory()) {
					find(subFile, suffix, list);
				} else {
					String path = subFile.getAbsolutePath();
					if (path.endsWith(suffix)) {
						list.add(path);
					}
				}
			}
		} else {
			throw new IllegalArgumentException(
					"param \"dir\" must be an existing directory .dir = "
							+ dirFile.getAbsolutePath());
		}
	}
}
