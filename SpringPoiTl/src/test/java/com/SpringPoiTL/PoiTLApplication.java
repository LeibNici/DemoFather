package com.SpringPoiTL;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperlinkTextRenderData;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.Texts;
import org.apache.poi.xwpf.model.XWPFCommentsDecorator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

/**
 * @author chenming
 * @description
 * @create: 2022-05-16
 */
@SpringBootTest
public class PoiTLApplication {

    @Test
    void test1() throws IOException {
        File templateFile = new File("D:\\test.docx");
        XWPFTemplate template = XWPFTemplate.compile(templateFile).render(
                new HashMap<String, Object>() {{
                    put("title", "Hi, poi-tl Word模板引擎");
                    put("name", new TextRenderData("000000", "sayHi"));
                    put("author", new HyperlinkTextRenderData("website", "http://www.baidu.com"));
                    put("link", new HyperlinkTextRenderData("anchortxt", "anchor:appendix1"));
                }});
        File outFile = new File("D:\\test1.docx");
        template.writeAndClose(new FileOutputStream(outFile));
    }

    @Test
    void test2() throws IOException {
        File templateFile = new File("D:\\test.docx");
        XWPFTemplate template = XWPFTemplate.compile(templateFile).render(new HashMap<String, Object>() {{
            put("title", Texts.of("Hi, poi-tl Word模板引擎").create());
            put("name", Texts.of("sayHi").color("000000").create());
            put("website", Texts.of("website").link("http://www.baidu.com").create());
            put("link", Texts.of("link").anchor("anchor:appendix1").create());
            put("image", Pictures.of("http://deepoove.com/images/icecream.png").create());

        }});
        File outFile = new File("D:\\test1.docx");
        template.writeAndClose(new FileOutputStream(outFile));
    }

}
