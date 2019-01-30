package com.sky.auth.config.provider;

/**
 * 集成认证上下文
 * @author 王帆
 * @date  2019年1月26日 下午3:19:41
 */
public class IntegrationAuthenticationContext {
    private static ThreadLocal<IntegrationAuthentication> holder = new ThreadLocal<>();

    public static void set(IntegrationAuthentication entity){
        holder.set(entity);
    }

    public static IntegrationAuthentication get(){
        return holder.get();
    }

    public static void clear(){
        holder.remove();
    }
}
