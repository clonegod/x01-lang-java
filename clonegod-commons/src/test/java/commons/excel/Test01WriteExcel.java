package commons.excel;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class Test01WriteExcel {

	@Test
	public void test01_xls() throws Exception {
		Workbook wb = new HSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream("target/workbook-xls.xls");
		wb.write(fileOut);
		fileOut.close();

	}

	@Test
	public void test01_xlsx() throws Exception {
		Workbook wb = new XSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream("target/workbook-xlsx.xlsx");
		wb.write(fileOut);
		fileOut.close();
	}

	@Test
	public void test02_NewSheet() throws Exception {
		Workbook wb = new HSSFWorkbook(); // or new XSSFWorkbook();
		Sheet sheet1 = wb.createSheet("new sheet");
		Sheet sheet2 = wb.createSheet("second sheet");

		// Note that sheet name is Excel must not exceed 31 characters
		// and must not contain any of the any of the following characters:
		// 0x0000
		// 0x0003
		// colon (:)
		// backslash (\)
		// asterisk (*)
		// question mark (?)
		// forward slash (/)
		// opening square bracket ([)
		// closing square bracket (])

		// You can use
		// org.apache.poi.ss.util.WorkbookUtil#createSafeSheetName(String
		// nameProposal)}
		// for a safe way to create valid names, this utility replaces invalid
		// characters with a space (' ')
		String safeName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]"); // returns
																					// "
																					// O'Brien's
																					// sales
																					// "
		Sheet sheet3 = wb.createSheet(safeName);

		FileOutputStream fileOut = new FileOutputStream("target/workbook-sheets.xls");
		wb.write(fileOut);
		fileOut.close();
	}

	@Test
	public void test03_CreateCell() throws Exception {
		Workbook wb = new HSSFWorkbook();
		// Workbook wb = new XSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("new sheet");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow((short) 0);
		// Create a cell and put a value in it.
		Cell cell = row.createCell(0);
		cell.setCellValue(1);

		// Or do it on one line.
		row.createCell(1).setCellValue(1.2);
		row.createCell(2).setCellValue(createHelper.createRichTextString("This is a string"));
		row.createCell(3).setCellValue(true);

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("target/workbook-cell.xls");
		wb.write(fileOut);
		fileOut.close();
	}

	@Test
	public void test04_CreateDateCell() throws Exception {
		Workbook wb = new HSSFWorkbook();
		// Workbook wb = new XSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("new sheet");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow(0);

		// Create a cell and put a date value in it. The first cell is not
		// styled
		// as a date.
		Cell cell = row.createCell(0);
		cell.setCellValue(new Date());

		// we style the second cell as a date (and time). It is important to
		// create a new cell style from the workbook otherwise you can end up
		// modifying the built in style and effecting not only this cell but
		// other cells.
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
		cell = row.createCell(1);
		cell.setCellValue(new Date());
		cell.setCellStyle(cellStyle);

		// you can also set date as java.util.Calendar
		cell = row.createCell(2);
		cell.setCellValue(Calendar.getInstance());
		cell.setCellStyle(cellStyle);

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("target/workbook-date.xls");
		wb.write(fileOut);
		fileOut.close();
	}

	/**
	 * 单元格中的常见数据类型：数字，日期，字符串
	 */
	@Test
	public void test05_CellTyepsDefault() throws Exception {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");
		
		Row row = sheet.createRow(2);
		row.createCell(0).setCellValue(1.1);
		row.createCell(1).setCellValue(new Date());
		row.createCell(2).setCellValue(Calendar.getInstance());
		row.createCell(3).setCellValue("a string");
		row.createCell(4).setCellValue(true);
		row.createCell(5).setCellType(Cell.CELL_TYPE_ERROR);

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("target/workbook-celltypes01.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	/**
	 * 1. 设置列的宽度
	 * 2. 设置单元格的数据格式：数字，保留小数位；日期；货币；百分比
	 * @throws Exception
	 */
	@Test
	public void test05_CellTyepsCustomer() throws Exception {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");
		sheet.setColumnWidth(0, 4000);
		sheet.autoSizeColumn(1);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 2000);
		sheet.setColumnWidth(4, 6000);
		sheet.autoSizeColumn(5);
		
		Row row = sheet.createRow(2);
		Cell cell = null;
		
		DataFormat format = wb.createDataFormat();
		// 日期格式
		cell = row.createCell(0);
		cell.setCellValue(Calendar.getInstance());
		CellStyle style_0 = wb.createCellStyle();
		style_0.setDataFormat(format.getFormat("yyyy年MM月dd日"));
		cell.setCellStyle(style_0);
		
		// 小数格式
		cell = row.createCell(1);
		cell.setCellValue(1000.2567d);
		CellStyle style_1 = wb.createCellStyle();
		style_1.setDataFormat(format.getFormat("0.00"));
		cell.setCellStyle(style_1);
		
		
		// 货币格式
		cell = row.createCell(2);
		cell.setCellValue(123456.789d);
		CellStyle style_2 = wb.createCellStyle();
		style_2.setDataFormat(format.getFormat("¥#,##0.00"));
		cell.setCellStyle(style_2);
		
		// 百分比格式
		cell = row.createCell(3);
		cell.setCellValue(0.6);
		CellStyle style_3 = wb.createCellStyle();
		style_3.setDataFormat(format.getFormat("0.00%"));
		cell.setCellStyle(style_3);
		
		// 中文大写格式
		cell = row.createCell(4);
		cell.setCellValue(123456789.88);
		CellStyle style_4 = wb.createCellStyle();
		style_4.setDataFormat(format.getFormat("[DbNum2][$-804]0.00"));
		cell.setCellStyle(style_4);
		
		// 科学计数格式
		cell = row.createCell(5);
		cell.setCellValue(100000);
		CellStyle style_5 = wb.createCellStyle();
		style_5.setDataFormat(format.getFormat("0.00E+00"));
		cell.setCellStyle(style_5);
		
//		row.createCell(0).setCellValue(1.1);
//		row.createCell(1).setCellValue(new Date());
//		row.createCell(2).setCellValue(Calendar.getInstance());
//		row.createCell(3).setCellValue("a string");
//		row.createCell(4).setCellValue(true);
//		row.createCell(5).setCellType(Cell.CELL_TYPE_ERROR);
		
		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("target/workbook-celltypes02.xls");
		wb.write(fileOut);
		fileOut.close();
	}

	/**
	 * 单元格对齐方式：左、右、上、下、居中
	 */
	@Test
	public void test06_Alignments() throws Exception {
		Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();

        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow((short) 2);
        row.setHeightInPoints(30);

        createCell(wb, row, (short) 0, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_CENTER);
        createCell(wb, row, (short) 1, CellStyle.ALIGN_CENTER_SELECTION, CellStyle.VERTICAL_BOTTOM);
        createCell(wb, row, (short) 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER);
        createCell(wb, row, (short) 3, CellStyle.ALIGN_GENERAL, CellStyle.VERTICAL_CENTER);
        createCell(wb, row, (short) 4, CellStyle.ALIGN_JUSTIFY, CellStyle.VERTICAL_JUSTIFY);
        createCell(wb, row, (short) 5, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_TOP);
        createCell(wb, row, (short) 6, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_TOP);

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("target/xssf-align.xlsx");
        wb.write(fileOut);
        fileOut.close();

	}
	
	/**
     * Creates a cell and aligns it a certain way.
     *
     * @param wb     the workbook
     * @param row    the row to create the cell in
     * @param column the column number to create the cell in
     * @param halign the horizontal alignment for the cell.
     */
    private static void createCell(Workbook wb, Row row, short column, short halign, short valign) {
        Cell cell = row.createCell(column);
        cell.setCellValue("居中Align It");
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
    }
    
    /**
     * 设置单元格边框
     */
    @Test
    public void test07_cellBorder() throws Exception {
    	Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow(1);

        // Create a cell and put a value in it.
        Cell cell = row.createCell(1);
        cell.setCellValue(4);

        // Style the cell with borders all around.
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.GREEN.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLUE.getIndex());
        style.setBorderTop(CellStyle.BORDER_MEDIUM_DASHED);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cell.setCellStyle(style);

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("target/workbook-border.xls");
        wb.write(fileOut);
        fileOut.close();
    	
    }
    
    /**
     * 单元格背景颜色
     */
    @Test
    public void test08_FillColor() throws Exception {
    	Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow((short) 1);

        // Aqua background
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(CellStyle.BIG_SPOTS);
        Cell cell = row.createCell((short) 1);
        cell.setCellValue("X");
        cell.setCellStyle(style);

        // Orange "foreground", foreground being the fill foreground not the font color.
        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cell = row.createCell((short) 2);
        cell.setCellValue("X");
        cell.setCellStyle(style);

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("target/workbook-colors.xls");
        wb.write(fileOut);
        fileOut.close();
    }
    
    /**
     * 合并单元格
     */
    @Test
    public void test09_MergeCell() throws Exception {
    	Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");

        Row row = sheet.createRow((short) 1);
        Cell cell = row.createCell((short) 1);
        cell.setCellValue("This is a test of merging");

        sheet.addMergedRegion(new CellRangeAddress(
                1, //first row (0-based)
                1, //last row  (0-based)
                1, //first column (0-based)
                2  //last column  (0-based)
        ));

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("target/workbook-merge.xls");
        wb.write(fileOut);
        fileOut.close();
    }
    
    /**
     * 字体: 加粗
     */
    @Test
    public void test10_fonts() throws Exception {
    	Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow(1);

        // Create a new font and alter it.
        Font font = wb.createFont();
        font.setFontHeightInPoints((short)24);
        font.setFontName("Courier New");
        font.setItalic(true);
        font.setStrikeout(true);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // Fonts are set into a style so create a new one to use.
        CellStyle style = wb.createCellStyle();
        style.setFont(font);

        // Create a cell and put a value in it.
        Cell cell = row.createCell(1);
        cell.setCellValue("This is a test of fonts");
        cell.setCellStyle(style);

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("target/workbook-fonts.xls");
        wb.write(fileOut);
        fileOut.close();
    }
    
    @Test
    public void test11_NewLinesInCell() throws Exception {
    	Workbook wb = new XSSFWorkbook();   //or new HSSFWorkbook();
        Sheet sheet = wb.createSheet();

        Row row = sheet.createRow(2);
        Cell cell = row.createCell(2);
        cell.setCellValue("Use \n with word wrap on to create a new line");

        //to enable newlines you need set a cell styles with wrap=true
        CellStyle cs = wb.createCellStyle();
        cs.setWrapText(true);
        cell.setCellStyle(cs);

        //increase row height to accomodate two lines of text
        row.setHeightInPoints((3*sheet.getDefaultRowHeightInPoints()));

        //adjust column width to fit the content
        sheet.autoSizeColumn((short)2);

        FileOutputStream fileOut = new FileOutputStream("target/ooxml-newlines.xlsx");
        wb.write(fileOut);
        fileOut.close();
    }
    
    @Test
    public void test12_DateFormat() throws Exception {
    	Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("format sheet");
        CellStyle style;
        DataFormat format = wb.createDataFormat();
        Row row;
        Cell cell;
        short rowNum = 0;
        short colNum = 0;
        
        double value = 11111.25;

        row = sheet.createRow(rowNum++);
        cell = row.createCell(colNum);
        cell.setCellValue(value);
        style = wb.createCellStyle();
        style.setDataFormat(format.getFormat("0.0"));
        cell.setCellStyle(style);

        row = sheet.createRow(rowNum++);
        cell = row.createCell(colNum);
        cell.setCellValue(value);
        style = wb.createCellStyle();
        style.setDataFormat(format.getFormat("#,##0.0000"));
        cell.setCellStyle(style);

        FileOutputStream fileOut = new FileOutputStream("target/workbook-dataformat.xls");
        wb.write(fileOut);
        fileOut.close();
    }
    
    /**
     * 利用工具类：合并单元格、设置边框、设置样式
     * @throws Exception
     */
    @Test
    public void test13_useConvenienceFunc() throws Exception {
    	Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook()
        Sheet sheet1 = wb.createSheet( "new sheet" );

        // Create a merged region
        Row row = sheet1.createRow( 1 );
        Row row2 = sheet1.createRow( 2 );
        Cell cell = row.createCell( 1 );
        cell.setCellValue( "This is a test of merging" );
        CellRangeAddress region = CellRangeAddress.valueOf("B2:E5");
        sheet1.addMergedRegion( region );

        // Set the border and border colors.
        final short borderMediumDashed = CellStyle.BORDER_MEDIUM_DASHED;
        RegionUtil.setBorderBottom( borderMediumDashed,
            region, sheet1, wb );
        RegionUtil.setBorderTop( borderMediumDashed,
            region, sheet1, wb );
        RegionUtil.setBorderLeft( borderMediumDashed,
            region, sheet1, wb );
        RegionUtil.setBorderRight( borderMediumDashed,
            region, sheet1, wb );
        RegionUtil.setBottomBorderColor(IndexedColors.AQUA.getIndex(), region, sheet1, wb);
        RegionUtil.setTopBorderColor(IndexedColors.AQUA.getIndex(), region, sheet1, wb);
        RegionUtil.setLeftBorderColor(IndexedColors.AQUA.getIndex(), region, sheet1, wb);
        RegionUtil.setRightBorderColor(IndexedColors.AQUA.getIndex(), region, sheet1, wb);

        // Shows some usages of HSSFCellUtil
        CellStyle style = wb.createCellStyle();
        style.setIndention((short)40);
        CellUtil.createCell(row, 8, "This is the value of the cell", style);
        Cell cell2 = CellUtil.createCell( row2, 8, "This is the value of the cell");
        CellUtil.setAlignment(cell2, wb, CellStyle.ALIGN_CENTER);

        // Write out the workbook
        FileOutputStream fileOut = new FileOutputStream( "target/workbook-utils.xls" );
        wb.write( fileOut );
        fileOut.close();
    }
    
    /**
     * 固定行与列
     * 分割sheet为多个区域
     */
    @Test
    public void test14_Freeze() throws Exception{
    	Workbook wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("new sheet");
        Sheet sheet2 = wb.createSheet("second sheet");
        Sheet sheet3 = wb.createSheet("third sheet");
        Sheet sheet4 = wb.createSheet("fourth sheet");

        // Freeze just one row - 固定第一行
        sheet1.createFreezePane( 0, 1, 0, 1 );
        addRows(sheet1);
        // Freeze just one column - 固定第一列
        sheet2.createFreezePane( 1, 0, 1, 0 );
        addRows(sheet2);
        // Freeze the columns and rows (forget about scrolling position of the lower right quadrant).
        sheet3.createFreezePane( 2, 2 ); // 固定前2行不动
        addRows(sheet3);
        // Create a split with the lower left side being the active quadrant - 分割sheet为多个区域
        sheet4.createSplitPane( 2000, 2000, 0, 0, Sheet.PANE_LOWER_LEFT );
        addRows(sheet4);
        
        FileOutputStream fileOut = new FileOutputStream("target/workbook-freeze.xls");
        wb.write(fileOut);
        fileOut.close();
        wb.close();
    }

	private void addRows(Sheet sheet) {
		Workbook wb = sheet.getWorkbook();
		for(int i=0; i<50; i++) {
			Row row = sheet.createRow(i+1);
			Cell cell = null;
			
			DataFormat format = wb.createDataFormat();
			// 日期格式
			cell = row.createCell(0);
			cell.setCellValue(Calendar.getInstance());
			CellStyle style_0 = wb.createCellStyle();
			style_0.setDataFormat(format.getFormat("yyyy年MM月dd日"));
			cell.setCellStyle(style_0);
			
			// 小数格式
			cell = row.createCell(1);
			cell.setCellValue(1000.2567d);
			CellStyle style_1 = wb.createCellStyle();
			style_1.setDataFormat(format.getFormat("0.00"));
			cell.setCellStyle(style_1);
			
			
			// 货币格式
			cell = row.createCell(2);
			cell.setCellValue(123456.789d);
			CellStyle style_2 = wb.createCellStyle();
			style_2.setDataFormat(format.getFormat("¥#,##0.00"));
			cell.setCellStyle(style_2);
			
			// 百分比格式
			cell = row.createCell(3);
			cell.setCellValue(0.6);
			CellStyle style_3 = wb.createCellStyle();
			style_3.setDataFormat(format.getFormat("0.00%"));
			cell.setCellStyle(style_3);
			
			// 中文大写格式
			cell = row.createCell(4);
			cell.setCellValue(123456789.88);
			CellStyle style_4 = wb.createCellStyle();
			style_4.setDataFormat(format.getFormat("[DbNum2][$-804]0.00"));
			cell.setCellStyle(style_4);
			
			// 科学计数格式
			cell = row.createCell(5);
			cell.setCellValue(100000);
			CellStyle style_5 = wb.createCellStyle();
			style_5.setDataFormat(format.getFormat("0.00E+00"));
			cell.setCellStyle(style_5);
		}
	}
	
	@Test
	public void test15_header() throws Exception {
		Workbook wb = new HSSFWorkbook();
	    Sheet sheet = wb.createSheet("new sheet");

	    Header header = sheet.getHeader();
	    header.setCenter("Center Header");
	    header.setLeft("Left Header");
	    header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
	                    HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");

	    addRows(sheet);
	    
	    FileOutputStream fileOut = new FileOutputStream("target/workbook-header.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}
	
	/**
	 * 使用Map封装cellStyle-使用通用样式，减少设置样式的代码
	 * 对于个别特殊cell，则单独进行样式的设置
	 */
	@Test
	public void test16_SetCellProperties() throws Exception {
		Workbook workbook = new XSSFWorkbook(); // OR new HSSFWorkbook()
		Sheet sheet = workbook.createSheet("Sheet1");
		
		
		Map<String, Object> properties = new HashMap<String, Object>();

		// border around a cell
		properties.put(CellUtil.BORDER_TOP, CellStyle.BORDER_MEDIUM);
		properties.put(CellUtil.BORDER_BOTTOM, CellStyle.BORDER_MEDIUM);
		properties.put(CellUtil.BORDER_LEFT, CellStyle.BORDER_MEDIUM);
		properties.put(CellUtil.BORDER_RIGHT, CellStyle.BORDER_MEDIUM);

		// Give it a color (RED)
		properties.put(CellUtil.TOP_BORDER_COLOR, IndexedColors.RED.getIndex());
		properties.put(CellUtil.BOTTOM_BORDER_COLOR, IndexedColors.RED.getIndex());
		properties.put(CellUtil.LEFT_BORDER_COLOR, IndexedColors.RED.getIndex());
		properties.put(CellUtil.RIGHT_BORDER_COLOR, IndexedColors.RED.getIndex());

		// Apply the borders to the cell at B2
		Row row = sheet.createRow(1);
		Cell cell = row.createCell(1);
		CellUtil.setCellStyleProperties(cell, properties);

		// Apply the borders to a 3x3 region starting at D4
		for (int ix = 3; ix <= 5; ix++) {
			row = sheet.createRow(ix);
			for (int iy = 3; iy <= 5; iy++) {
				cell = row.createCell(iy);
				CellUtil.setCellStyleProperties(cell, properties);
			}
		}

		FileOutputStream fileOut = new FileOutputStream("target/workbook-cellproperties.xls");
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
	}
}

/**
 * 单元格宽度问题-width
 * 数据不是数字格式的问题-dataFormat
 * 
 * 单元格边框-border
 * 单元格合并-merge
 * 单元格数据对齐方式-align
 * 单元格数据字体-font
 */
