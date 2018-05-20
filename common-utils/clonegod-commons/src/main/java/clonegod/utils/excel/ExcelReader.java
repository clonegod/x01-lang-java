package clonegod.utils.excel;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import clonegod.uitls.DateTimeUtil;

public class ExcelReader {
	private static Logger logger = Logger.getLogger(ExcelWriter.class.getName());
	private static final String dateFmtPattern = "yyyy-MM-dd";

    /**
     * 读取excel文件（同时支持2003和2007格式）
     *
     * @param fis
     *            文件输入流
     * @param extension
     *            文件名扩展名: xls 或 xlsx 不区分大小写
     * @return list中的map的key是列的序号
     * @throws Exception
     *             io异常等
     */
    public static List<Map<String, String>> read(FileInputStream fis, String extension)
            throws Exception {
        Workbook wb = null;
        List<Map<String, String>> list = null;
        try {
            if ("xls".equalsIgnoreCase(extension)) {
                wb = new HSSFWorkbook(fis);
            } else if ("xlsx".equalsIgnoreCase(extension)) {
                wb = new XSSFWorkbook(fis);
            } else {
                throw new IllegalArgumentException("file is not office excel");
            }
            list = readWorkbook(wb);
            return list;
        } finally {
        	wb.close();
        }
    }

    /**
     * 读取excel文件（同时支持2003和2007格式）
     *
     * @param fileName
     *            文件名，绝对路径
     * @return list中的map的key是列的序号
     * @throws Exception
     *             io异常等
     */
    public static List<Map<String, String>> read(String fileName) throws Exception {
        FileInputStream fis = null;
        Workbook wb = null;
        List<Map<String, String>> list = null;
        try {
            String extension = FilenameUtils.getExtension(fileName);
            fis = new FileInputStream(fileName);
            list = read(fis, extension);
            return list;
        } finally {
        	if(wb != null) wb.close();
        	if(fis != null) fis.close();
        }
    }

    private static String getCellValue(Cell cell) {
        String value = null;
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_FORMULA: // 公式
        case Cell.CELL_TYPE_NUMERIC: // 数字
            double doubleVal = cell.getNumericCellValue();
            short format = cell.getCellStyle().getDataFormat();
            String formatString = cell.getCellStyle().getDataFormatString();

            if (format > 0) {
				logger.info("format: " + format + "　formatString: " + formatString);
            }
            if (format == 14 || format == 31 || format == 57 || format == 58
                    || (format >= 176 && format <= 183)) {
                Date date = DateUtil.getJavaDate(doubleVal);
                value = DateTimeUtil.formatDate(date, dateFmtPattern);
            } else if (format == 20 || format == 32 || (format >= 184 && format <= 187)) {
                Date date = DateUtil.getJavaDate(doubleVal);
                value = DateTimeUtil.formatDate(date, "HH:mm");
            } else {
                value = String.valueOf(doubleVal);
            }
            break;
        case Cell.CELL_TYPE_STRING: // 字符串
            value = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_BLANK: // 空白
            value = "";
            break;
        case Cell.CELL_TYPE_BOOLEAN: // Boolean
            value = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_ERROR: // Error，返回错误码
            value = String.valueOf(cell.getErrorCellValue());
            break;
        default:
            value = "";
            break;
        }
        return value;
    }

    private static List<Map<String, String>> readWorkbook(Workbook wb) throws Exception {
        List<Map<String, String>> list = new LinkedList<Map<String, String>>();
        for (int k = 0; k < wb.getNumberOfSheets(); k++) {
            Sheet sheet = wb.getSheetAt(k);
            int rows = sheet.getPhysicalNumberOfRows();
            for (int r = 0; r < rows; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                Map<String, String> map = new HashMap<String, String>();
                int cells = row.getPhysicalNumberOfCells();
                for (int c = 0; c < cells; c++) {
                    Cell cell = row.getCell(c);
                    if (cell == null) {
                        continue;
                    }
                    String value = getCellValue(cell);
                    map.put(String.valueOf(cell.getColumnIndex() + 1), value);
                }
                list.add(map);
            }
        }
        return list;
    }
    
    
    public static void main(String[] args) throws Exception {
        List<Map<String, String>> list1 = read("src/test/resources/test.xls");
        System.out.println("list:\n" + list1);
        
        List<Map<String, String>> list2 = read("src/test/resources/op.xls");
        System.out.println("list:\n" + list2);
    }
}
