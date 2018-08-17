package com.xht.cmsdk.utils;

import com.xht.cmsdk.enums.ChannelType;
import com.xht.cmsdk.enums.ErrorCodes;
import com.xht.cmsdk.enums.OperateType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by XIE on 2018/8/13.
 */

public class CMUtil {

    /**
     * map转成字符串
     * @param map
     * @param encode
     * @return
     */
    public static String map_to_string(Map<String, String> map, String encode){
        StringBuffer sb = new StringBuffer();
        if (map != null && !map.isEmpty()){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 使用map，生成xml字符串
     * @param params
     * @return
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public static String mapToXmlUseDocument(Map<String, String> params) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        //documentbuilder用于管理解析或者生产XML
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        document.setXmlVersion("1.0");

        Element rootNode = document.createElement("xml");
        document.appendChild(rootNode);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            Element childNode = document.createElement(entry.getKey());
            Text childText = document.createTextNode(entry.getValue());
//            CDATASection section = document.createCDATASection(childText.getTextContent());
//            childNode.appendChild(section);
            childNode.appendChild(childText);

            rootNode.appendChild(childNode);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(out));

        return out.toString();
    }

    /**
     * xml转成json字符串
     * @param is
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws JSONException
     */
    public static JSONObject parseXMLIntoJson(InputStream is) throws ParserConfigurationException, IOException, SAXException, JSONException {
        //新建一个集合，用于存放解析后的对象
        JSONObject jsonObject = new JSONObject();
        //得到Dom解析对象工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //通过工厂创建Dom解析对象实例
        DocumentBuilder db = factory.newDocumentBuilder();
        //将xml文件的输入流交给Dom解析对象进行解析，并将Dom树返回
        Document document = db.parse(is);
        //通过Dom树接收到根元素
        Element rootElement = document.getDocumentElement();
        //得到根元素标签的下属所节点
        NodeList nodeList = rootElement.getChildNodes();
        for(int i=0; i< nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            //如果此循环出来的元素是Element对象，即标签元素，那么执行以下代码
            if(Node.ELEMENT_NODE == node.getNodeType()){
                jsonObject.put(node.getNodeName(), node.getFirstChild().getNodeValue());
            }
        }
        //返回xml解析后得到的对象集合
        return jsonObject;
    }

    /**
     * 获取当前时间秒数字符串
     * @return
     */
    public static String currDataToStamp(){
        long currTime = System.currentTimeMillis();
        Date currDate = new Date(currTime);
        long millTime = currDate.getTime();
        return String.valueOf(millTime / 1000);
    }

    /**
     * 如str不为空，添加
     * @param queryParams
     * @param tag
     * @param str
     */
    public static void choseInput(Map<String, String> queryParams, String tag, String str){
        if (str != null){
            queryParams.put(tag, str);
        }
    }

    /**
     * 获取渠道名称
     * @param type
     * @return
     */
    public static String getChannelName(final ChannelType type){
        switch (type){
            case TypeWeChat:
                return "微信";
            case TypeQQ:
                return "QQ";
            case TypeAli:
                return "支付宝";
            case TypeWeiBo:
                return "微博";
            case TypeUP:
                return "银联";
            default:
                return "未知渠道";
        }
    }

    /**
     * 获取操作名称
     * @param type
     * @return
     */
    public static String getOperateName(final OperateType type){
        switch (type){
            case TypeLogin:
                return "登陆";
            case TypePay:
                return "支付";
            case TypeShare:
                return "分享";
            default:
                return "未知操作";
        }
    }

    public static String getErrorMessage(final ErrorCodes codes){
        switch (codes){
            case Error_Code_DEFAULT:
                return "未知错误";
            case Error_Code_UnRegister:
                return "SDK未注册";
            case Error_Code_UnInstall:
                return "应用未安装";
            case Error_Code_Action_Success:
                return "成功";
            case Error_Code_Action_Failed:
                return "失败";
            case Error_Code_Action_Cancel:
                return "取消";
            case Error_Code_GetUserInfo_Success:
                return "获取用户信息成功";
            case Error_Code_GetUserInfo_Failed:
                return "获取用户信息失败";
            case Error_Code_GetToken_Failed:
                return "获取token失败";
            default:
                return "";
        }
    }
}
