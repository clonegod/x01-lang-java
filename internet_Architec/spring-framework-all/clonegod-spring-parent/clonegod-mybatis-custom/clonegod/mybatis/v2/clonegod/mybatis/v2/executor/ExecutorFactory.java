package clonegod.mybatis.v2.executor;

import clonegod.mybatis.v2.config.CGConfiguration;

public class ExecutorFactory {

    public static CGExecutor DEFAULT(CGConfiguration configuration) {
        return get(ExecutorType.SIMPLE, configuration);
    }

    public static CGExecutor get(ExecutorType type, CGConfiguration configuration) {
        if (ExecutorType.SIMPLE == type) {
            return new SimpleExecutor(configuration);
        }
        if (ExecutorType.CACHING == type) {
            return new CachingExecutor(new SimpleExecutor(configuration));
        }
        throw new RuntimeException("no executor found");
    }

}