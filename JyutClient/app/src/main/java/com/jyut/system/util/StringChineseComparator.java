/**
 * 
 */
package com.jyut.system.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * 针对中文的排序类
 * @author wztscau
 * @date Sep 29, 2016
 * @project 粤盟管理系统客户端
 */
public class StringChineseComparator implements Comparator<String>{

	@Override
	public int compare(String lhs, String rhs) {
		return Collator.getInstance(Locale.CHINA).compare(lhs, rhs);
	}

}
