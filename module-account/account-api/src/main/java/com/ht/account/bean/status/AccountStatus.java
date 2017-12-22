package com.ht.account.bean.status;

import com.ht.bean.status.BaseStatus;

/**
 * 状态类型
 * @author swt
 */
public class AccountStatus extends BaseStatus {
    /** 正常(未锁定) */
    public static final int UNLOCK 	= 1;
    /** 锁定 */
    public static final int LOCK	= 2;
}
