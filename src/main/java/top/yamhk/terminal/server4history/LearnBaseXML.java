package top.yamhk.terminal.server4history;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class LearnBaseXML {

    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("a.xml");

            Element root = doc.getDocumentElement(); // niit节点

            // 读取属性
            // String className = root.getAttribute("userMsg");
            // System.out.println(className);

            // 读取节点名
            System.out.println("root NodeName:" + root.getNodeName());

            // 读取子节点
            NodeList nl = root.getChildNodes(); // 节点类型 element; Node是一个父类
            // 可以指向子类的对象.
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                if (node instanceof Element) {
                    Element e = (Element) node;
                    // 读取节点名
                    System.out.println("NodeName:" + e.getNodeName()
                            + "| userMsg:" + e.getAttribute("name"));

                    // 读取内容
                    // System.out.println("TextContent:"+e.getTextContent());
                    NodeList cnl = e.getChildNodes();
                    for (int j = 0; j < cnl.getLength(); j++) {
                        Node cnode = cnl.item(j); // ｓｔｕ节点
                        if (cnode instanceof Element) {
                            Element ce = (Element) cnode;
                            System.out.print("NodeName:" + ce.getNodeName());
                            String id = ce.getAttribute("id");
                            System.out.print(",id:" + id);

                            if (id.equals("5")) {
                                System.out.print(",userMsg:"
                                        + ce.getAttribute("name"));
                                NodeList ccnl = ce.getChildNodes();
                                for (int k = 0; k < ccnl.getLength(); k++) {
                                    Node ccn = ccnl.item(k);
                                    if (ccn instanceof Element) {
                                        System.out.print(",childNodeName:"
                                                + ccn.getNodeName());
                                        System.out.println(",TextContent:"
                                                + ccn.getTextContent());
                                    }
                                }
                            } else {
                                System.out.print(",age:"
                                        + ce.getAttribute("age"));
                                System.out.println(",TextContent:"
                                        + ce.getTextContent());
                            }
                        }
                    }
                }
            }

            // 添加节点
            Element c3 = doc.createElement("class");
            c3.setAttribute("name", "php");
            root.appendChild(c3);

            // 保存ｘｍｌ
            TransformerFactory tfactory = TransformerFactory.newInstance();
            try {
                Transformer transformer = tfactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("b.xml"));
                transformer.setOutputProperty("encoding", "utf-8");
                transformer.transform(source, result);
            } catch (TransformerConfigurationException e) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}