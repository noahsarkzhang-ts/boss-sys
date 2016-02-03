/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gen.service;

import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.callback.InitCallbackImpl;
import com.thinkgem.jeesite.common.db.Page;
import com.thinkgem.jeesite.common.persistence.DataEntity.IInitCallback;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.gen.dao.GenTemplateDao;
import com.thinkgem.jeesite.modules.gen.entity.GenTemplate;

/**
 * 代码模板Service
 * @author ThinkGem
 * @version 2013-10-15
 */
@Service
@Transactional(readOnly = true)
public class GenTemplateService extends BaseService {

	@Autowired
	private GenTemplateDao genTemplateDao;
	
	public GenTemplate get(Integer id) {
		return genTemplateDao.get(id);
	}
	
	public Page<GenTemplate> find(Page<GenTemplate> page, GenTemplate genTemplate) {
		Map<String,Object> params = Maps.newHashMap();
		params.put("entity", genTemplate);
		params.put("page", page);
		
//		genTemplate.setPage(page);
		page.setList(genTemplateDao.findList(params));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(GenTemplate genTemplate) {
		if (genTemplate.getContent()!=null){
			genTemplate.setContent(StringEscapeUtils.unescapeHtml4(genTemplate.getContent()));
		}
		
		IInitCallback callback = new InitCallbackImpl();
		
		if (genTemplate.getId() == null){
			genTemplate.preInsert(callback);
			genTemplateDao.insert(genTemplate);
		}else{
			genTemplate.preUpdate(callback);
			genTemplateDao.update(genTemplate);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(GenTemplate genTemplate) {
		genTemplateDao.delete(genTemplate);
	}
	
}
