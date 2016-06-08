package com.xiu.search.web.ext.freemarker;

import java.util.List;
import java.util.Random;

import com.xiu.search.web.config.XiuWebConstant;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 随机生成图片url前辍
 * @author dingchenghong
 *
 */
public class RandomZoshowImageUrlMethod implements TemplateMethodModel {

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		Random r = new Random();
		int i = r.nextInt(6);
		return XiuWebConstant.IMAGE_ZOSHOW_URL.get(i) + "/";
	}

	

}
