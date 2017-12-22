package com.ht.account.web.controller;

import com.ht.account.bean.AccountBean;
import com.ht.account.bean.status.AccountStatus;
import com.ht.account.service.AccountService;
import com.ht.bean.base.DataTable;
import com.ht.bean.base.Order;
import com.ht.bean.base.Page;
import com.ht.bean.base.SerializeObject;
import com.ht.bean.type.ResultType;
import com.ht.config.Config;
import com.ht.util.UF;
import com.ht.web.controller.BaseController;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * 系统账号相关服务
 * @author swt
 */
@RestController
@RequestMapping("accounts")
public class AccountController extends BaseController {

    @Resource
    private AccountService accountService;

    @Resource
    private Config config;

    /**
     * 根据ID查找信息
     * @param accessToken	登录成功后分配的Key
     * @param id		    ID
     * @return			    记录集
     */
    @RequestMapping(value = "{id}", method = GET)
    public SerializeObject find(String accessToken, @PathVariable String id) {
        if(StringUtils.isBlank(id)) {
            return new SerializeObject<>(ResultType.ERROR, "请求参数有误");
        }
        AccountBean entity = accountService.findById(id);
        if(null == entity || StringUtils.isBlank(entity.getId())) {
            return new SerializeObject<>(ResultType.ERROR, "请求的数据不存在");
        }
        return new SerializeObject<>(ResultType.NORMAL, entity);
    }

    /**
     * 新增信息（id为null、''）或修改信息（id不为空）
     * @param accessToken		登录成功后分配的Key
     * @param entity		    记录集
     * @return				    是否操作成功
     */
    @RequestMapping(method = POST)
    public SerializeObject update(String accessToken, AccountBean entity) {
        if(null == entity || StringUtils.isBlank(entity.getLoginName())) {
            return new SerializeObject<>(ResultType.ERROR, "登录名不能为空");
        }
        int val;
        if(StringUtils.isBlank(entity.getId())) {
            val = accountService.save(entity);
        } else {
            val = accountService.update(entity.getId(), entity);
        }

        if(val == 1) {
            return new SerializeObject<>(ResultType.ERROR, "登录名不能为空");
        } else if(val == 2) {
            return new SerializeObject<>(ResultType.ERROR, "提交的数据已存在");
        }
        return new SerializeObject<>(ResultType.NORMAL, "操作成功");
    }

    /**
     * 删除信息
     * @param accessToken		登录成功后分配的Key
     * @param id			    ID数组
     * @return				    是否操作成功
     */
    @RequestMapping(method = DELETE)
    public SerializeObject delete(String accessToken, String[] id) {
        if(null == id || id.length <= 0) {
            return new SerializeObject<>(ResultType.ERROR, "请求参数有误");
        }

        int rows = accountService.deleteByIds(id);
        return new SerializeObject<>(ResultType.NORMAL, "共删除 " + rows + " 条记录");
    }

    /**
     * 删除信息(逻辑删除)
     * @param accessToken		登录成功后分配的Key
     * @param id			    ID数组
     * @return				    是否操作成功
     */
    @RequestMapping(value = "remove", method = PUT)
    public SerializeObject remove(String accessToken, String[] id) {
        if(null == id || id.length <= 0) {
            return new SerializeObject<>(ResultType.ERROR, "请求参数有误");
        }

        int rows = accountService.removeByIds(id);
        return new SerializeObject<>(ResultType.NORMAL, "共删除 " + rows + " 条记录");
    }

    /**
     * 重置密码
     * @param accessToken		登录成功后分配的Key
     * @param id			    ID数组
     * @return				    是否操作成功
     */
    @RequestMapping(value = "passwordReset", method = PUT)
    public SerializeObject passwordReset(String accessToken, String[] id) {
        if(null == id || id.length <= 0) {
            return new SerializeObject<>(ResultType.ERROR, "请求参数有误");
        }

        int rows = accountService.resetPassword(id, config.getDefaultPassword());
        return new SerializeObject<>(ResultType.NORMAL, "共重置 " + rows + " 条记录");
    }

    /**
     * 修改密码
     * @param accessToken		登录成功后分配的Key
     * @param id			    用户ID
     * @param password		    原密码
     * @param newPassword       新密码
     * @return				    是否操作成功
     */
    @RequestMapping(value = "updatePassword", method = PUT)
    public SerializeObject updatePassword(String accessToken, String id, String password, String newPassword) {
        if(StringUtils.isBlank(accessToken) && StringUtils.isBlank(id)) {
            return new SerializeObject<>(ResultType.UNLOGIN);
        }
        if(StringUtils.isBlank(password)) {
            return new SerializeObject<>(ResultType.ERROR, "原密码不能为空");
        }
        if(StringUtils.isBlank(newPassword)) {
            return new SerializeObject<>(ResultType.ERROR, "新密码不能为空");
        }

        AccountBean accountBean = accountService.findById(id);
        password = DigestUtils.md5Hex(password);
        if(!StringUtils.equalsIgnoreCase(password, accountBean.getPassword())) {
            return new SerializeObject<>(ResultType.ERROR, "原密码错误");
        }

        String[] ids = {id};
        accountService.resetPassword(ids, newPassword);
        return new SerializeObject<>(ResultType.NORMAL, "操作成功");
    }

    /**
     * 启用账号信息
     * @param accessToken		登录成功后分配的Key
     * @param id			    ID数组
     * @return				    是否操作成功
     */
    @RequestMapping(value = "enable", method = PUT)
    public SerializeObject enable(String accessToken, String[] id) {
        if(null == id || id.length <= 0) {
            return new SerializeObject<>(ResultType.ERROR, "请求参数有误");
        }
        int rows = accountService.updateStatus(id, AccountStatus.UNLOCK);
        return new SerializeObject<>(ResultType.NORMAL, "共启用 " + rows + " 条记录");
    }

    /**
     * 禁用账号信息
     * @param accessToken		登录成功后分配的Key
     * @param id			    ID数组
     * @return				    是否操作成功
     */
    @RequestMapping(value = "disable", method = PUT)
    public SerializeObject disable(String accessToken, String[] id) {
        if(null == id || id.length <= 0) {
            return new SerializeObject<>(ResultType.ERROR, "请求参数有误");
        }
        int rows = accountService.updateStatus(id, AccountStatus.LOCK);
        return new SerializeObject<>(ResultType.NORMAL, "共禁用 " + rows + " 条记录");
    }

    /**
     * 根据登录名查找信息
     * @param loginName	        登录名
     * @return			        记录集
     */
    @RequestMapping(value = "loginName", method = GET)
    public SerializeObject<AccountBean> loginName(String loginName) {
        if(StringUtils.isBlank(loginName)) {
            return new SerializeObject<>(ResultType.ERROR, "请求参数有误");
        }
        AccountBean entity = accountService.findByLoginName(loginName);
        if(null == entity || StringUtils.isBlank(entity.getId())) {
            return new SerializeObject<>(ResultType.ERROR, "请求的数据不存在");
        }
        return new SerializeObject<>(ResultType.NORMAL, entity);
    }

    /**
     * 查询列表
     * @param accessToken	    登录成功后分配的Key
     * @param order		        排序
     * @param status	        状态	 0：全部 1：正常 2：被锁定
     * @param keyword	        关键字
     * @return			        分页数据
     */
    @RequestMapping(value = "list" , method = GET)
    public SerializeObject<List<AccountBean>> list(String accessToken, Order order, Integer status, String keyword) {
        keyword = UF.toString(keyword);
        List<AccountBean> list = accountService.findList(order.getOrder(), order.getSort(), status, keyword);
        return new SerializeObject<>(ResultType.NORMAL, list);
    }

    /**
     * 查询数据
     * @param accessToken	    登录成功后分配的Key
     * @param order		        排序
     * @param page		        分页
     * @param status	        状态	 0：全部 1：正常 2：被锁定
     * @param keyword	        关键字
     * @return			        分页数据
     */
    @RequestMapping(method = GET)
    public SerializeObject<DataTable<AccountBean>> query(String accessToken, Order order, Page page, Integer status, String keyword) {
        if(page.getPageSize() <= 0) {
            page.setPageSize(config.getPageSize());
        }
        if(null == status || status < 0) {
            status = 0;
        }
        keyword = UF.toString(keyword);

        DataTable<AccountBean> dataTable = accountService.query(order.getOrder(), order.getSort(), page.getPageNum(), page.getPageSize(), status, keyword);
        return new SerializeObject<>(ResultType.NORMAL, dataTable);
    }

    /**
     * 验证是否存在
     * @param accessToken	    登录成功后分配的Key
     * @param entity	        验证参数
     * @return			        是否验证通过
     */
    @RequestMapping(value = "verify",method = GET)
    public SerializeObject verify(String accessToken, AccountBean entity) {
        if(accountService.exists(entity, entity.getId())) {
            return new SerializeObject<>(ResultType.ERROR, "信息已存在");
        }
        return new SerializeObject<>(ResultType.NORMAL, "验证成功");
    }
}
