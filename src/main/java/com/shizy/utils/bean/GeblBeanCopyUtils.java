package com.shizy.utils.bean;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * 拷贝工具
 * <p>Project:			<p>
 * <p>Module:			<p>
 * <p>Description:		<p>
 *
 * @author ZHANGYZ10
 * @date 2018年4月28日 下午9:17:18
 */
public class GeblBeanCopyUtils {
	

	/**
	 * 获取值为空的字段
	 * @param source
	 * @return
	 */
	public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

	/**
	 * 将src不为空的值，覆盖到target
	 * @param src
	 * @param target
	 */
    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
    
   
}
