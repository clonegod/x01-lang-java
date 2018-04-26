package spittr.web.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import spittr.domain.Spittle;
import spittr.util.FormatUtil;

@Component
public class SpittleListPdfView extends AbstractPdfView {
	
	public static final String BANE_NAME = StringUtils.uncapitalize(SpittleListPdfView.class.getSimpleName());
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setHeader("Content-Disposition", "inline; filename="+ 
				new String("消息列表.pdf".getBytes(), "iso8859-1"));  
		
		
		BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\comicbd.ttf", BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		Font cnFont = new Font(bfComic, 10, Font.NORMAL, Color.BLUE);
		
		Table table = new Table(3);
		table.setWidth(80);
		table.setBorder(1);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(buildFontCell("id", cnFont));
		table.addCell(buildFontCell("username", cnFont));
		table.addCell(buildFontCell("date", cnFont));
		
		List<Spittle> spittleList = (List<Spittle>) model.get("data");
		for (Spittle spittle : spittleList) {
			table.addCell(spittle.getId()+"");
			table.addCell(spittle.getMessage());
			table.addCell(FormatUtil.formatDate(spittle.getTime()));
		}
		document.add(table);
		
	}
	
	private Cell buildFontCell(String content,Font font) throws RuntimeException{ 
		try {
			 Phrase phrase = new Phrase(content, font);
			 return new Cell(phrase);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}	
}
