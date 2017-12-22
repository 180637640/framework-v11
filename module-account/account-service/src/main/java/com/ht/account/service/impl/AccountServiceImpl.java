package com.ht.account.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ht.account.bean.AccountBean;
import com.ht.account.mapper.AccountMapper;
import com.ht.account.mapper.entity.Account;
import com.ht.account.service.AccountService;
import com.ht.aop.RemoveCache;
import com.ht.aop.SaveCache;
import com.ht.bean.base.DataTable;
import com.ht.bean.status.DeleteStatus;
import com.ht.config.Config;
import com.ht.service.impl.BaseServiceImpl;
import com.ht.util.ParameterMap;
import com.ht.util.UF;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ht.config.CacheType.OBJECT;
import static com.ht.config.CacheType.SEARCH;

/**
 * @author swt
 */
@Service
public class AccountServiceImpl extends BaseServiceImpl implements AccountService {
	
	@Resource
	private AccountMapper accountMapper;

	@Resource
	private Config config;

	@Resource
	private DozerBeanMapper dozerBeanMapper;

	@Override
	@RemoveCache(cleanSearch = true)
	public int save(AccountBean entityBean) {
		// 数据完整性校验
		if(null == entityBean || StringUtils.isBlank(entityBean.getLoginName())) {
			return 1;
		}
		Account entity = dozerBeanMapper.map(entityBean, Account.class);
		if(exists(entityBean, entityBean.getId())){
            // 该数据已存在
        	return 2;
        }

		if(StringUtils.isBlank(entity.getId())) {
			entity.setId(UF.getRandomUUID());
		}
		if(StringUtils.isBlank(entity.getPassword())) {
			entity.setPassword(DigestUtils.md5Hex(config.getDefaultPassword()));
		}
		accountMapper.insert(entity);

        return 0;
	}

	@Override
	@RemoveCache(cleanSearch = true, cleanObjectByKeyPosition = 0)
	public int update(String id, AccountBean entityBean) {
        // 数据完整性校验
		if(null == entityBean || StringUtils.isBlank(entityBean.getId()) || StringUtils.isBlank(entityBean.getLoginName())) {
			return 1;
		}
		Account entity = accountMapper.findById(entityBean.getId());
		dozerBeanMapper.map(entityBean, entity);

		if(exists(entityBean, entityBean.getId())){
            // 该数据已存在
        	return 2;
        }

		accountMapper.update(entity);
		return 0;
	}

	@Override
	@RemoveCache(cleanSearch = true, cleanObjectByKeyPosition = 0)
	public int delete(String id) {
		if(StringUtils.isBlank(id)) {
			return 0;
		}

		return accountMapper.delete(id);
	}

	@Override
	@RemoveCache(cleanSearch = true, cleanObjectByKeyPosition = 0)
	public int deleteByIds(String[] id){
		if(id == null || id.length<=0){
			return 0;
		}

		return accountMapper.deleteByIds(id);
	}

	@Override
	@RemoveCache(cleanSearch = true, cleanObjectByKeyPosition = 0)
	public int removeByIds(String[] id) {
		if(id == null || id.length<=0){
			return 0;
		}

		return accountMapper.updateByPrimaryKeySelective(
				getParams()
						.put("id", id)
						.put("deleted", DeleteStatus.YES)
						.toMap()
		);
	}

	@Override
	@SaveCache(cacheType = SEARCH)
	public boolean exists(AccountBean entityBean, String id) {
		if(null == entityBean || StringUtils.isBlank(entityBean.getLoginName())) {
			return false;
		}

		int rows = accountMapper.exists(
				getParams()
						.put("loginName", entityBean.getLoginName())
						.put("id", id)
						.toMap()
		);
		return rows > 0;
	}

	@Override
	@SaveCache(cacheType = OBJECT)
	public AccountBean findById(String id) {
		if(StringUtils.isBlank(id)) {
			return null;
		}

		Account entity = accountMapper.findById(id);
		if(null == entity) {
			return null;
		}

		AccountBean entityBean = dozerBeanMapper.map(entity, AccountBean.class);
		entityBean.setAddTimeString(UF.getFormatDateTime(entity.getAddTime()));
		entityBean.setModifyTimeString(UF.getFormatDateTime(entity.getModifyTime()));
		return entityBean;
	}

	@Override
	@SaveCache(cacheType = SEARCH)
	public List<AccountBean> findList(String order, String sort, int status, String keyword) {
		return query(order, sort, null, null, status, keyword);
	}

	@Override
	@SaveCache(cacheType = SEARCH)
	public DataTable<AccountBean> query(String order, String sort, int pageNum, int pageSize, int status, String keyword){
		Page<AccountBean> page = PageHelper.startPage(pageNum, pageSize);
		List<AccountBean> list = query(order, sort, null, null, status, keyword);
		return new DataTable<>(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
	}

	@Override
	@SaveCache(cacheType = SEARCH)
	public AccountBean findByLoginName(String loginName) {
		if(StringUtils.isBlank(loginName)) {
			return null;
		}

		List<AccountBean> list = query(null, null, loginName, null, 0, null);
		if(null == list || list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	@Override
	@RemoveCache(cleanSearch = true, cleanObjectByKeyPosition = 0)
	public int resetPassword(String[] id, String newPassword) {
		if(StringUtils.isBlank(newPassword)) {
			newPassword = config.getDefaultPassword();
		}

		return accountMapper.updateByPrimaryKeySelective(
				getParams()
						.put("id", id)
						.put("password", DigestUtils.md5Hex(newPassword))
						.toMap()
		);
	}

	@Override
	@RemoveCache(cleanSearch = true, cleanObjectByKeyPosition = 0)
	public int updateStatus(String[] id, int status) {
		if(id == null || id.length<=0){
			return 0;
		}

		return accountMapper.updateByPrimaryKeySelective(
				getParams()
						.put("id", id)
						.put("status", status)
						.toMap()
		);
	}

	/**
	 * 获取数据列表
	 * @param order			排序字段
	 * @param sort			排序方式 desc或asc
	 * @param loginName		登录名
	 * @param mobile		手机
	 * @param status		状态	 0：全部 1：正常 2：被锁定
	 * @param keyword		关键字
	 * @return				列表
	 */
	private List<AccountBean> query(String order, String sort, String loginName, String mobile, int status, String keyword){
		List<AccountBean> dataList = new ArrayList<>();
		List<Account> list = accountMapper.query(
				getParams(keyword, order, sort)
						.put("loginName", loginName)
						.put("mobile", mobile)
						.put("status", status)
						.toMap());
		for (Account o : list) {
			AccountBean objBean = dozerBeanMapper.map(o, AccountBean.class);
			objBean.setAddTimeString(UF.getFormatDateTime(o.getAddTime()));
			objBean.setModifyTimeString(UF.getFormatDateTime(o.getModifyTime()));
			dataList.add(objBean);
		}
		return dataList;
	}
}

