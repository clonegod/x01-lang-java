package clonegod.myutils.excel;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import clonegod.myutils.DateTimeUtil;

public class ExcelWriter {
    private static Logger logger = Logger.getLogger(ExcelWriter.class.getName());
    private static final String dateTimeFmtPattern = "yyyy-MM-dd HH:mm:ss";

    /**
     *
     * @param sheetMap
     *            DAO层封装返回的数据集合
     * @param headers
     *            表格列名
     * @param columns
     *            查询结果集中Map<key,value>中的key
     * @param fileName
     *            文件名称
     * @return
     * @throws Exception
     */
    public static String write(Map<String, List<Map<String, Object>>> sheetMap, String[] headers,
            String[] columns, String fileName) throws Exception {

        String extension = FilenameUtils.getExtension(fileName);

        Workbook wb = createWorkbook(extension);

        Map<String, CellStyle> styles = createStyles(wb);

        String sheetName = null;
        List<Map<String, Object>> dataList = null;
        for (Map.Entry<String, List<Map<String, Object>>> sheetEntry : sheetMap.entrySet()) {
            sheetName = sheetEntry.getKey();
            dataList = sheetEntry.getValue();

            // 创建Excel的工作sheet,对应到一个excel文档的tab
            Sheet sheet = wb.createSheet(sheetName);
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth((short) 10);
            sheet.createFreezePane(0, 1, 0, 1);

            createHeader(sheet.createRow(0), headers, styles.get("header"));

            Row row = null;
            for (int i = 1; i <= dataList.size(); i++) {
                row = sheet.createRow(i);

                Map<String, Object> map = dataList.get(i - 1);

                Cell cell = null;
                for (int j = 0; j < headers.length; j++) {
                    cell = row.createCell(j);
                    setCellValue(cell, map.get(columns[j]));
                }
            }
        }

        FileOutputStream os = new FileOutputStream(fileName);
        wb.write(os);
        os.close();

        wb.close();

        return fileName;
    }

    private static void createHeader(Row row, String[] headers, CellStyle headerStyle) {
        if (headers != null && headers.length > 0) {
            for (int index = 0; index < headers.length; index++) {
                Cell cell = row.createCell(index);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(headers[index]);
            }
        }
    }

    private static Workbook createWorkbook(String extension) {
        Workbook wb = null;
        if ("xls".equalsIgnoreCase(extension)) {
            wb = new HSSFWorkbook();
        } else if ("xlsx".equalsIgnoreCase(extension)) {
            wb = new XSSFWorkbook();
        } else {
            logger.log(Level.SEVERE, "file is not office excel");
            throw new IllegalArgumentException("file is not office excel");
        }
        return wb;
    }

    private static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        CellStyle style = wb.createCellStyle();
        style.setFont(headerFont);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("header", style);

        return styles;
    }

    private static void setCellValue(Cell cell, Object obj) {

        if (null == obj) {
            cell.setCellValue("");
        } else if (obj instanceof String) {
            cell.setCellValue(obj.toString());
        } else if (obj instanceof Short || obj instanceof Integer || obj instanceof Long) {

            cell.setCellValue(Double.valueOf(String.valueOf(obj)));

        } else if (obj instanceof Double) {

            Double doubleVal = (Double) obj;

            cell.setCellValue(doubleVal);
        } else if (obj instanceof BigDecimal) {

            BigDecimal bdValue = (BigDecimal) obj;

            cell.setCellValue(bdValue.doubleValue());
        } else if (obj instanceof Date) {

            String dateVal = DateTimeUtil.formatDate((Date) obj, dateTimeFmtPattern);
            if (dateVal.endsWith("00:00:00")) {
                dateVal = dateVal.replace("00:00:00", "");
            }
            cell.setCellValue(dateVal);
        } else {
            cell.setCellValue(obj.toString());
        }
    }
    
    public static void main(String[] args) throws Exception {
    	Map<String, List<Map<String, Object>>> sheetMap = 
    			new HashMap<String, List<Map<String,Object>>>();
    	
    	List<Map<String,Object>> colList = new LinkedList<Map<String,Object>>();
    	
    	// first row
    	Map<String,Object> row1 = new HashMap<String, Object>();
    	row1.put("id", "1");
    	row1.put("name", "测试1");
    	colList.add(row1);
    	
    	// second row
    	Map<String,Object> row2 = new HashMap<String, Object>();
    	row2.put("id", "2");
    	row2.put("name", "测试2");
    	colList.add(row2);
    	
    	sheetMap.put("sheet1", colList);
    	
    	// 列名与属性名按数组脚本一一对应
    	String[] headers = {"编号", "姓名"};
    	String[] columns = {"id", "name"};
        write(sheetMap, headers, columns, "src/test/resources/test.xls");

    }
}
