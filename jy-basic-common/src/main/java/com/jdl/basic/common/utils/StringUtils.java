package com.jdl.basic.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Describe:
 * User: BjYangKai
 * Date: 2010-6-3
 * Time: 11:29:33
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

    /**
     * ȥ���ַ���ǰ��ȫ�ǺͰ�ǿո�
     *
     * @param tarStr
     * @return
     */
    public static String trimSbc(String tarStr) {
        if (isEmpty(tarStr)) return tarStr;
        tarStr = tarStr.trim();
        while (tarStr.startsWith("��")) {
            tarStr = tarStr.substring(1, tarStr.length()).trim();
        }
        while (tarStr.endsWith("��")) {
            tarStr = tarStr.substring(0, tarStr.length() - 1).trim();
        }
        return tarStr;
    }


    /**
     * Method change ...
     * <p/>
     * ȥ���ı��е�������js�ű���<object>,<frame><iframe>
     *
     * @param htmlStr of type String
     * @return String
     */
    public static String change(String htmlStr) {
        if(StringUtils.isBlank(htmlStr)){
            return htmlStr;
        }

        String temp = htmlStr;
        String temp2 = "";
        String regEx_a = "(<\\s*?(a|A)\\s*?>?[\\s\\S]*?<?[\\s]*?\\/[\\s]*?(a|A)[\\s]*?>)";
//       String regEx_img="(<\\s*img\\s+([^>]*)(\\/)?\\s*>)";
//       String regEx_src="(src\\s*=\\s*(\"|'|)http://\\w+\\.360buy\\.com[\\s\\S]*?)";
        String regEx_href = "((href|HREF)\\s*=\\s*(\"|'|)(http://)?\\w+\\.360buy\\.com[\\s\\S]*?)";
//       String regEx_href_content="(href=[\\\"\\'](http:\\/\\/|\\.\\/|\\/)?\\w+(\\.\\w+)*(\\/\\w+(\\.\\w+)?)*(\\/|\\?\\w*=\\w*(&\\w*=\\w*)*)?(#\\w*)?[\\\"\\'])";
//       String regEx_href_content = "(href=[\\\"\\'](http:\\/\\/|\\.\\/|\\/)?[\\s\\S]*?[\\\"\\'])";
        String regEx_href_content = "(href=(\"(http:\\/\\/|\\.\\/|\\/)?[\\s\\S]*?\")|href=(\'(http:\\/\\/|\\.\\/|\\/)?[\\s\\S]*?\'))";
        String regEx_script = "(<[\\s]*?(script|SCRIPT)[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?(script|SCRIPT)[\\s]*?>)";
        String regEx_object = "(<[\\s]*?(object|OBJECT)[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?(object|OBJECT)[\\s]*?>)";
        String regEx_frame = "(<[\\s]*?(frame|FRAME)[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?(frame|FRAME)[\\s]*?>)";
        String regEx_iframe = "(<[\\s]*?(iframe|IFRAME)[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?(iframe|IFRAME)[\\s]*?>)";
        String regEx_form = "(<[\\s]*?\\/?[\\s]*?form[^>]*?>)";
        String regEx_input = "(<[\\s]*?\\/?[\\s]*?input[^>]*?>)";
        String regEx_textarea = "(<[\\s]*?\\/?[\\s]*?textarea[^>]*?>)";
        String regEx_select = "(<[\\s]*?\\/?[\\s]*?select[^>]*?>)";
        String regEx_option = "(<[\\s]*?\\/?[\\s]*?option[^>]*?>)";
        String regEx_button = "(<[\\s]*?\\/?[\\s]*?button[^>]*?>)";
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(temp);
        temp = m_script.replaceAll(" "); //����script��ǩ ����""
        Pattern p_object = Pattern.compile(regEx_object, Pattern.CASE_INSENSITIVE);
        Matcher m_object = p_object.matcher(temp);
        temp = m_object.replaceAll(" "); //����object��ǩ
        Pattern p_frame = Pattern.compile(regEx_frame, Pattern.CASE_INSENSITIVE);
        Matcher m_frame = p_frame.matcher(temp);
        temp = m_frame.replaceAll(" "); //����frame��ǩ
        Pattern p_iframe = Pattern.compile(regEx_iframe, Pattern.CASE_INSENSITIVE);
        Matcher m_iframe = p_iframe.matcher(temp);
        temp = m_iframe.replaceAll(" "); //����iframe��ǩ
        Pattern p_a = Pattern.compile(regEx_a, Pattern.CASE_INSENSITIVE);
        Matcher m_a = p_a.matcher(temp);
        Pattern p_form = Pattern.compile(regEx_form, Pattern.CASE_INSENSITIVE);
        Matcher m_form = p_form.matcher(temp);
        temp = m_form.replaceAll(" ");
        Pattern p_input = Pattern.compile(regEx_input, Pattern.CASE_INSENSITIVE);
        Matcher m_input = p_input.matcher(temp);
        temp = m_input.replaceAll(" ");
        Pattern p_textarea = Pattern.compile(regEx_textarea, Pattern.CASE_INSENSITIVE);
        Matcher m_textarea = p_textarea.matcher(temp);
        temp = m_textarea.replaceAll(" ");
        Pattern p_select = Pattern.compile(regEx_select, Pattern.CASE_INSENSITIVE);
        Matcher m_select = p_select.matcher(temp);
        temp = m_select.replaceAll(" ");
        Pattern p_option = Pattern.compile(regEx_option, Pattern.CASE_INSENSITIVE);
        Matcher m_option = p_option.matcher(temp);
        temp = m_option.replaceAll(" ");
        Pattern p_button = Pattern.compile(regEx_button, Pattern.CASE_INSENSITIVE);
        Matcher m_button = p_button.matcher(temp);
        temp = m_button.replaceAll(" ");
        temp2 = temp;
        Pattern p_href = Pattern.compile(regEx_href, Pattern.CASE_INSENSITIVE);
        while (m_a.find()) {
            String sb = null;
            sb = m_a.group(0);
            Matcher m_href = p_href.matcher(sb);
            if (!m_href.find()) {
                //�滻��Ҫ����������
                Pattern p_content = Pattern.compile(regEx_href_content, Pattern.CASE_INSENSITIVE);
                Matcher m_content = p_content.matcher(sb);
                while (m_content.find()) {      //ֻ�滻href������
                    String change = null;
                    change = m_content.group(0);
                    temp2 = stringReplace(temp2, change, "href=\"#\"");
                }
            }
        }

//       Pattern p_img=Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
//       Matcher m_img=p_img.matcher(temp);                             // ����ͼƬ
//       Pattern p_src=Pattern.compile(regEx_src,Pattern.CASE_INSENSITIVE);
//       while(m_img.find()){
//           String sb=null;
//           sb=m_img.group(0);
//           Matcher m_src=p_src.matcher(sb);
//           if(m_src.find()){
//               temp=stringReplace(temp,sb,"##");
//           }
//       }
        return temp2;
    }


    /**
     * Method stringReplace ...
     * �滻�ַ���
     *
     * @param sourceString    of type String
     * @param toReplaceString of type String
     * @param replaceString   of type String
     * @return String
     */
    public static String stringReplace(String sourceString, String toReplaceString, String replaceString) {
        String returnString = sourceString;
        int stringLength = 0;
        if (toReplaceString != null) {
            stringLength = toReplaceString.length();
        }
        if (returnString != null && returnString.length() >= stringLength) {
            int max = 0;
            String S4 = " ";
            for (int i = 0; i < sourceString.length(); i++) {
                max = i + toReplaceString.length() > sourceString.length() ? sourceString.length() : i + stringLength;
                String S3 = sourceString.substring(i, max);
                if (!S3.equals(toReplaceString)) {
                    S4 += S3.substring(0, 1);
                } else {
                    S4 += replaceString;
                    i += stringLength - 1;
                }
            }
            returnString = S4;
        }
        return returnString;
    }



}
