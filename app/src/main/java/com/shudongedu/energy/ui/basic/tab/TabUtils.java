/*
 * 文件名: TabUtils.java
 * 版    权：  Copyright Paitao Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:2014-11-3
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.ui.basic.tab;

import android.os.Message;

/**
 * Tab工具类<BR>
 * @author zhaozeyang
 * @version [Paitao Client V20130911, 2014-11-3] 
 */
public final class TabUtils
{
    public static Message getMessage(int msgType)
    {
        Message msg = new Message();
        msg.what = msgType;
        return msg;
    }
}
