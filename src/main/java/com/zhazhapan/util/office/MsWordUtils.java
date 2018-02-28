package com.zhazhapan.util.office;

import com.zhazhapan.util.Checker;
import com.zhazhapan.util.ReflectUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author pantao
 * @since 2018/2/28
 */
public class MsWordUtils {

    private static Logger logger = Logger.getLogger(MsWordUtils.class);

    private MsWordUtils() {}

    /**
     * 添加一行
     *
     * @param document 文档对象
     */
    public static void appendLine(XWPFDocument document) {
        document.createParagraph().createRun();
    }

    /**
     * 添加一张图片
     *
     * @param document 文档对象
     * @param imagePath 图片路径
     * @param pictureType 图片类型
     * @param width 宽度
     * @param height 长度
     * @param alignment 对齐方式
     *
     * @throws IOException 异常
     * @throws InvalidFormatException 异常
     */
    public static void appendImage(XWPFDocument document, String imagePath, int pictureType, int width, int height,
                                   ParagraphAlignment alignment) throws IOException, InvalidFormatException {
        appendImage(getNewRun(document, alignment), imagePath, pictureType, width, height);
    }

    /**
     * 添加一张图片
     *
     * @param run {@link XWPFRun}
     * @param imagePath 图片路径
     * @param pictureType 图片类型
     * @param width 宽度
     * @param height 长度
     *
     * @throws IOException 异常
     * @throws InvalidFormatException 异常
     */
    public static void appendImage(XWPFRun run, String imagePath, int pictureType, int width, int height) throws
            IOException, InvalidFormatException {
        InputStream input = new FileInputStream(imagePath);
        run.addPicture(input, pictureType, imagePath, Units.toEMU(width), Units.toEMU(height));
    }

    /**
     * 获取一个新的XWPFRun对象
     *
     * @param document 文档对象
     * @param alignment 对齐方式
     *
     * @return {@link XWPFRun}
     */
    public static XWPFRun getNewRun(XWPFDocument document, ParagraphAlignment alignment) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(alignment);
        return paragraph.createRun();
    }

    /**
     * 从XWPFParagraph获取一个新的XWPFRun对象
     *
     * @param document 文档对象
     * @param paragraphIndex XWPFParagraph位置
     *
     * @return {@link XWPFRun}
     */
    public static XWPFRun getNewRunOfParagraph(XWPFDocument document, int paragraphIndex) {
        return getParagraph(document, paragraphIndex).createRun();
    }

    /**
     * 获取一个XWPFRun对象
     *
     * @param document 文档对象
     * @param paragraphIndex XWPFParagraph位置
     * @param runIndex XWPFRun位置
     *
     * @return {@link XWPFRun}
     */
    public static XWPFRun getRun(XWPFDocument document, int paragraphIndex, int runIndex) {
        return document.getParagraphs().get(paragraphIndex).getRuns().get(runIndex);
    }

    /**
     * 获取XWPFParagraph中XWPFRun的大小
     *
     * @param document 文档对象
     * @param paragraphIndex XWPFParagraph位置
     *
     * @return {@link Integer}
     */
    public static int getRunSize(XWPFDocument document, int paragraphIndex) {
        return getParagraph(document, paragraphIndex).getRuns().size();
    }

    /**
     * 获取一个XWPFParagraph对象
     *
     * @param document 文档对象
     * @param paragraphIndex XWPFParagraph位置
     *
     * @return {@link XWPFParagraph}
     */
    public static XWPFParagraph getParagraph(XWPFDocument document, int paragraphIndex) {
        return document.getParagraphs().get(paragraphIndex);
    }

    /**
     * 获取XWPFParagraph长度
     *
     * @param document 文档对象
     *
     * @return {@link Integer}
     */
    public static int getParagraphSize(XWPFDocument document) {
        return document.getParagraphs().size();
    }

    /**
     * 添加一张表格
     *
     * @param document 文档对象
     * @param alignment 对齐方式
     * @param rows 行数
     * @param cols 列数
     * @param values 所有值，包括标题
     * @param rowHeight 行高
     * @param colWidth 列宽
     * @param cellMargins 单元格边缘
     * @param styles 样式
     */
    public static void appendTable(XWPFDocument document, ParagraphAlignment[] alignment, int rows, int cols,
                                   String[][] values, int[] rowHeight, int[] colWidth, Map<String, Integer>
                                           cellMargins, Map<String, Object> styles) {
        document.createParagraph();
        XWPFTable table = document.createTable(rows, cols);
        XWPFParagraph paragraph;
        XWPFTableRow row;
        XWPFTableCell cell;
        CTTcPr cellPr;
        XWPFRun run;
        if (Checker.isNotEmpty(cellMargins)) {
            int top = MsUtils.checkInteger(cellMargins.get("top"));
            int left = MsUtils.checkInteger(cellMargins.get("left"));
            int bottom = MsUtils.checkInteger(cellMargins.get("bottom"));
            int right = MsUtils.checkInteger(cellMargins.get("right"));
            table.setCellMargins(top, left, bottom, right);
        }
        int minRow = Math.min(rows, values.length);
        for (int i = 0; i < minRow; i++) {
            row = table.getRow(i);
            int height = i < rowHeight.length ? rowHeight[i] : rowHeight[rowHeight.length - 1];
            row.setHeight(height);
            String[] value = values[i];
            int minCol = Math.min(cols, value.length);
            for (int j = 0; j < minCol; j++) {
                cell = row.getCell(j);
                cellPr = cell.getCTTc().addNewTcPr();
                int width = j < colWidth.length ? colWidth[j] : colWidth[colWidth.length - 1];
                cellPr.addNewTcW().setW(BigInteger.valueOf(width));
                paragraph = cell.getParagraphs().get(0);
                int idx = i * cols + j;
                ParagraphAlignment align = idx < alignment.length ? alignment[idx] : alignment[alignment.length - 1];
                paragraph.setAlignment(align);
                run = paragraph.createRun();
                run.setText(value[j]);
                setStyle(run, styles);
            }
        }
    }

    /**
     * 添加一张表格
     *
     * @param document 文档对象
     * @param alignment 对齐方式
     * @param rows 行数
     * @param cols 列数
     * @param values 所有值，包括标题
     * @param rowHeight 行高
     * @param colWidth 列宽
     * @param cellMargins 单元格边缘
     * @param styles 样式
     */
    public static void appendTable(XWPFDocument document, ParagraphAlignment alignment, int rows, int cols,
                                   String[][] values, int rowHeight, int colWidth, Map<String, Integer> cellMargins,
                                   Map<String, Object> styles) {
        appendTable(document, new ParagraphAlignment[]{alignment}, rows, cols, values, new int[]{rowHeight}, new
                int[]{colWidth}, cellMargins, styles);
    }

    /**
     * 设置XWPFRun样式
     *
     * @param run XWPFRun对象
     * @param styles 样式
     */
    public static void setStyle(XWPFRun run, Map<String, Object> styles) {
        if (Checker.isNotEmpty(styles)) {
            styles.forEach((key, value) -> {
                String methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
                try {
                    ReflectUtils.invokeMethodUseBasicType(run, methodName, new Object[]{value});
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    logger.error("set property " + key + " failed use method " + methodName + ", " + e.getMessage());
                }
            });
        }
    }
}