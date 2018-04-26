package clonegod.dubbox.restful.utils;

import java.io.IOException;
import java.util.Map;

/**
 * <B>系统名称：</B>通用系统功能<BR>
 * <B>模块名称：</B>网络交互功能<BR>
 * <B>中文类名：</B>HTTP协议调用器辅助类<BR>
 * <B>概要说明：</B><BR>
 */
public final class HttpInvokerUtils {

    /**
     * <B>构造方法</B><BR>
     */
    private HttpInvokerUtils() {
    }

    /**
     * <B>方法名称：</B>默认请求<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param url URL地址
     * @param params 参数
     * @return String 文本数据
     * @throws IOException 输入输出异常
     */
    public static String request(String url, Map<String, String> params) throws IOException {
        HttpInvoker c = new HttpInvoker(url, HttpInvoker.REQUEST_METHOD_POST, params);
        return c.request();
    }

    /**
     * <B>方法名称：</B>GET请求<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param url URL地址
     * @param params 参数
     * @return String 文本数据
     * @throws IOException 输入输出异常
     */
    public static String get(String url, Map<String, String> params) throws IOException {
        HttpInvoker c = new HttpInvoker(url, HttpInvoker.REQUEST_METHOD_GET, params);
        return c.request();
    }

    /**
     * <B>方法名称：</B>POST请求<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param url URL地址
     * @param params 参数
     * @return String 文本数据
     * @throws IOException 输入输出异常
     */
    public static String post(String url, Map<String, String> params) throws IOException {
        HttpInvoker c = new HttpInvoker(url, HttpInvoker.REQUEST_METHOD_POST, params);
        return c.request();
    }

    /**
     * <B>方法名称：</B>PUT请求<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param url URL地址
     * @param params 参数
     * @return String 文本数据
     * @throws IOException 输入输出异常
     */
    public static String put(String url, Map<String, String> params) throws IOException {
        HttpInvoker c = new HttpInvoker(url, HttpInvoker.REQUEST_METHOD_PUT, params);
        return c.request();
    }

    /**
     * <B>方法名称：</B>DELETE请求<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param url URL地址
     * @param params 参数
     * @return String 文本数据
     * @throws IOException 输入输出异常
     */
    public static String delete(String url, Map<String, String> params) throws IOException {
        HttpInvoker c = new HttpInvoker(url, HttpInvoker.REQUEST_METHOD_DELETE, params);
        return c.request();
    }
}
