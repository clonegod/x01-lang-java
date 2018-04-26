/**
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package clonegod.rocketmq.transaction;

import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;


/**
 * 执行本地事务，由客户端回调
 */
public class TransactionExecuterImpl implements LocalTransactionExecuter {

    @Override
    public LocalTransactionState executeLocalTransactionBranch(final Message msg, final Object arg) {
    	System.out.println("msg=" + new String(msg.getBody()));
    	System.out.println("arg=" + arg);
    	
    	System.out.println("处理业务逻辑，执行本地事务，比如操作数据库。msg key=" + msg.getKeys());
    	if(msg.getTags().equals("Transaction1")) {
    		// 失败的情况下返回ROLLBACK
    		System.err.println("本地事务执行失败，回滚事务，返回ROLLBACK_MESSAGE");
    		return LocalTransactionState.ROLLBACK_MESSAGE;
    	}
    	
    	return LocalTransactionState.COMMIT_MESSAGE; // 本地事务成功提交
    	// return LocalTransactionState.ROLLBACK_MESSAGE; // 本地事务失败回滚
        // return LocalTransactionState.UNKNOW; // 未决事务 
    }
}
