package com.ht.mapper;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author swt
 */
public interface BaseMapper<T> {

	/**
	 * 保存
	 * @param entity	实体
	 * @return			0：操作失败 1：操作成功
	 */
	 int insert(T entity);

	/**
	 * 更新
	 * @param entity	实体
	 * @return			0：操作失败 1：操作成功
	 */
	 int update(T entity);

	/**
	 * 更新指定属性
	 * @param params			查询参数
	 * @return					当前页记录数
	 */
	int updateByPrimaryKeySelective(Map<String, Object> params);
	
	/**
	 * 删除
	 * @param id	ID
	 * @return		操作影响的行数
	 */
	 int delete(String id);
	
	/**
	 * 删除
	 * @param id	ID数组
	 * @return		操作影响的行数
	 */
	 int deleteByIds(@Param(value = "id") String[] id);

	/**
	 * 根据ID查找
	 * @param id	ID
	 * @return		实体
	 */
	 T findById(@Param(value = "id") String id);

	/**
	 * 分页查询
	 * @param params			查询参数
	 * @return					当前页记录数
	 */
	 List<T> query(Map<String, Object> params);

	/**
	 * 唯一性验证
	 * @param params			查询参数
	 * @return					总记录数
	 */
	int exists(Map<String, Object> params);
}