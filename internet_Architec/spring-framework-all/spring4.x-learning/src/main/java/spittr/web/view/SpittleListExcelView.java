package spittr.web.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import spittr.domain.Spittle;
import spittr.util.FormatUtil;

/**
 * 使用一个Bean作为特殊类型的视图解析对象
 *
 */
@Component
public class SpittleListExcelView extends AbstractXlsView {

	public static final String BANE_NAME = StringUtils.uncapitalize(SpittleListExcelView.class.getSimpleName());
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
									  Workbook workbook , HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		
		response.setHeader("Content-Disposition", "inline; filename="+ 
				new String("消息列表.xls".getBytes(), "iso8859-1"));
		
		List<Spittle> spittleList = (List<Spittle>) model.get("data");
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet("spittles");
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Message");
		header.createCell(1).setCellValue("Date");

		int rowNum = 1;
		for (Spittle spittle : spittleList) {
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(spittle.getMessage());
			row.createCell(1).setCellValue(FormatUtil.formatDate(spittle.getTime()));
		}
	}

}
