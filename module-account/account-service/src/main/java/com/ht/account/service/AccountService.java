package com.ht.account.service;


import com.ht.account.bean.AccountBean;
import com.ht.bean.base.DataTable;

import java.util.List;

/**
 * 系统账号管理
 * @author swt
 */
public interface AccountService {
	
	/**
	 * 添加
	 * @param entityBean	实体
	 * @return 				0:操作成功 1：不能为空 2：数据已存在
	 */
	int save(AccountBean entityBean);
	
	/**
	 * 编辑
	 * @param id			ID
	 * @param entityBean	实体
	 * @return 				0:操作成功 1：不能为空 2：数据已存在
	 */
	int update(String id, AccountBean entityBean);
	
	/**
	 * 根据ID删除
	 * @param id	ID
	 * @return		操作影响的记录数
	 */
	int delete(String id);
	
	/**
	 * 删除对象
	 * @param id	ID数组
	 * @return		操作影响的记录数	0：请求参数有误 >=1：操作成功
	 */
	int deleteByIds(String[] id);

	/**
	 * 删除对象(逻辑删除)
	 * @param id	ID数组
	 * @return		操作影响的记录数	0：请求参数有误 >=1：操作成功
	 */
	int removeByIds(String[] id);

	/**
     * 判断数据是否存在
     * @param entityBean	数据对象
     * @param id			例外ID
     * @return				True 是 | False 否
     */
    boolean exists(AccountBean entityBean, String id);
	
	/**
	 * 根据ID, 查找对象
	 * @param id	ID
	 * @return		实例
	 */
	AccountBean findById(String id);

	/**
	 * 获取列表
	 * @param order			排序字段
	 * @param sort			排序方式 desc或asc
	 * @param status		状态	 0：全部 1：正常 2：被锁定
	 * @param keyword		关键字
	 * @return				分页数据
	 */
	List<AccountBean> findList(String order, String sort, int status, String keyword);

	/**
	 * 搜索
	 * @param order			排序字段
	 * @param sort			排序方式 desc或asc
	 * @param pageNum		当前页1..N
	 * @param pageSize		每页记录数
	 * @param status		状态	 0：全部 1：正常 2：被锁定
	 * @param keyword		关键字
	 * @return				分页数据
	 */
	DataTable<AccountBean> query(String order, String sort, int pageNum, int pageSize, int status, String keyword);

	/**
	 * 根据LoginName, 查找对象
	 * @param loginName	登录名
	 * @return			实体对象
	 */
	AccountBean findByLoginName(String loginName);

	/**
	 * 重置密码
	 * @param id			用户ID
	 * @param newPassword	新密码 null:重置为默认密码
	 * @return				操作影响的行数
	 */
	int resetPassword(String[] id, String newPassword);

	/**
	 * 更新状态
	 * @param id			用户ID
	 * @param status		用户状态
	 * @return				操作影响的记录数
	 */
	int updateStatus(String[] id, int status);
}
