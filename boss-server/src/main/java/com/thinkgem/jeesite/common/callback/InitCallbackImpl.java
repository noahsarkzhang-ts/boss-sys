/**
 * 
 */
package com.thinkgem.jeesite.common.callback;

import org.apache.commons.lang3.StringUtils;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity.IInitCallback;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * @author hadoop
 *
 */
public class InitCallbackImpl implements IInitCallback {

	@Override
	public void preInsert(DataEntity entity) {
		
		User user = UserUtils.getUser();
		
		entity.setCreateBy(user);
		entity.setUpdateBy(user);
		
	}

	@Override
	public void preUpdate(DataEntity entity) {
		
		User user = UserUtils.getUser();
		
		entity.setUpdateBy(user);
		
	}

}
