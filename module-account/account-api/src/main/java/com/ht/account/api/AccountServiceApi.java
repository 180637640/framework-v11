package com.ht.account.api;

import com.ht.account.bean.AccountBean;
import com.ht.bean.base.DataTable;
import com.ht.bean.base.SerializeObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RequestMapping("accounts")
public interface AccountServiceApi {

    /**
     * 根据ID查找信息
     * @param accessToken	登录成功后分配的Key
     * @param id		    ID数组
     * @return			    记录集
     */
    @RequestMapping(value = "{id}", method = GET)
    SerializeObject<AccountBean> find(
            @RequestHeader("accessToken") String accessToken,
            @PathVariable("id") String id);

    /**
     * 新增信息（id为null、''）或修改信息（id不为空）
     * @param accessToken       登录成功后分配的Key
     * @param param             参数集合
     * @return				    是否操作成功
     */
    @RequestMapping(method = POST)
    SerializeObject update(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam Map<String, Object> param);

    /**
     * 删除信息
     * @param accessToken		登录成功后分配的Key
     * @param id			    ID数组
     * @return				    是否操作成功
     */
    @RequestMapping(method = DELETE)
    SerializeObject delete(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam("id") String[] id);

    /**
     * 删除信息(逻辑删除)
     * @param accessToken		登录成功后分配的Key
     * @param id			    ID数组
     * @return				    是否操作成功
     */
    @RequestMapping(value = "remove", method = PUT)
    SerializeObject remove(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam("id") String[] id);

    /**
     * 重置密码
     * @param accessToken		登录成功后分配的Key
     * @param id			    ID数组
     * @return				    是否操作成功
     */
    @RequestMapping(value = "passwordReset", method = PUT)
    SerializeObject passwordReset(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam("id") String[] id);

    /**
     * 修改密码
     * @param accessToken		登录成功后分配的Key
     * @param id			    用户ID
     * @param password		    原密码
     * @param newPassword       新密码
     * @return				    是否操作成功
     */
    @RequestMapping(value = "updatePassword", method = PUT)
    SerializeObject updatePassword(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam("id") String id,
            @RequestParam("password") String password,
            @RequestParam("newPassword") String newPassword);

    /**
     * 启用账号信息
     * @param accessToken		登录成功后分配的Key
     * @param id			    ID数组
     * @return				    是否操作成功
     */
    @RequestMapping(value = "enable", method = PUT)
    SerializeObject enable(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam("id") String[] id);

    /**
     * 禁用账号信息
     * @param accessToken		登录成功后分配的Key
     * @param id			    ID数组
     * @return				    是否操作成功
     */
    @RequestMapping(value = "disable", method = PUT)
    SerializeObject disable(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam("id") String[] id);

    /**
     * 根据登录名查找信息
     * @param loginName	登录名
     * @return			记录集
     */
    @RequestMapping(value = "loginName", method = GET)
    SerializeObject<AccountBean> loginName(
            @RequestParam("loginName") String loginName);

    /**
     * 查询列表
     * @param accessToken	登录成功后分配的Key
     * @param order		    排序字段
     * @param sort		    排序方式 desc或asc
     * @param status	    状态	 0：全部 1：正常 2：被锁定
     * @param keyword	    关键字
     * @return			    分页数据
     */
    @RequestMapping(value = "list" , method = GET)
    SerializeObject<List<AccountBean>> list(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam("order") String order,
            @RequestParam("sort") String sort,
            @RequestParam("status") Integer status,
            @RequestParam("keyword") String keyword);

    /**
     * 查询数据
     * @param accessToken	登录成功后分配的Key
     * @param order		    排序字段
     * @param sort		    排序方式 desc或asc
     * @param pageNum	    查询页数
     * @param pageSize	    每页记录数
     * @param status	    状态	 0：全部 1：正常 2：被锁定
     * @param keyword	    关键字
     * @return			    分页数据
     */
    @RequestMapping(method = GET)
    SerializeObject<DataTable<AccountBean>> query(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam("order") String order,
            @RequestParam("sort") String sort,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("status") Integer status,
            @RequestParam("keyword") String keyword);

    /**
     * 验证是否存在
     * @param accessToken	登录成功后分配的Key
     * @param param         参数集合
     * @return			    是否验证通过
     */
    @RequestMapping(value = "verify",method = GET)
    SerializeObject verify(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam Map<String, Object> param);

}
