package com.tnnowu.android.generalmethod.util;

import java.util.List;

/**
 * 在html中引入外部css,js文件   常规拼接顺序css->html->js
 *
 * Created by Tnno Wu on 2017/04/27.
 */

public class HtmlUtil {

    // Css样式,隐藏header
    private static final String HIDE_HEADER_STYLE = "<style>div.headline{display:none;}</style>";

    // Css style tag,需要格式化
    private static final String NEEDED_FORMAT_CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";

    // JavaScript tag,需要格式化
    private static final String NEEDED_FORMAT_JS_TAG = "<script src=\"%s\"></script>";

    public static final String MIME_TYPE = "text/html; charset=utf-8";

    public static final String ENCODING = "utf-8";

    private HtmlUtil() {
    }

    /**
     * 根据css链接生成Link标签
     * Create css tag
     *
     * e,g, TODO
     */
    public static String createCssTag(String url) {
        return String.format(NEEDED_FORMAT_CSS_TAG, url);
    }

    /**
     * 根据多个css链接生成Link标签
     * Create multi css tag
     *
     * e.g. TODO
     */
    public static String createMultiCssTag(List<String> urls) {
        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(createCssTag(url));
        }
        return sb.toString();
    }

    /**
     * 根据JS链接生成Script标签
     * Create js tag
     *
     * e.g. TODO
     */
    public static String createJsTag(String url) {
        return String.format(NEEDED_FORMAT_JS_TAG, url);
    }

    /**
     * 根据多个js链接生成Script标签
     * Create multi js tag
     *
     * e.g. TODO
     */
    public static String createMultiJsTag(List<String> urls) {
        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(createJsTag(url));
        }
        return sb.toString();
    }

    /**
     * 根据样式标签,html字符串,js标签,生成完整的HTML文档
     * Create html data
     *
     * e.g. TODO
     */
    public static String createHtmlData(String html, List<String> cssList, List<String> jsList) {
        final String css = HtmlUtil.createMultiCssTag(cssList);
        final String js = HtmlUtil.createMultiJsTag(jsList);
        return css.concat(HIDE_HEADER_STYLE).concat(html).concat(js);
    }

}
