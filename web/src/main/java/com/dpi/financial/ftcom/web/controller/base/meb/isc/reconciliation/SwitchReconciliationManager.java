package com.dpi.financial.ftcom.web.controller.base.meb.isc.reconciliation;

import com.dpi.financial.ftcom.api.GeneralServiceApi;
import com.dpi.financial.ftcom.api.base.atm.TerminalService;
import com.dpi.financial.ftcom.api.base.meb.atm.transaction.TerminalTransactionService;
import com.dpi.financial.ftcom.api.base.meb.isc.reconciliation.SwitchReconciliationService;
import com.dpi.financial.ftcom.api.base.meb.isc.reconciliation.SynchronizeStatisticsService;
import com.dpi.financial.ftcom.api.base.meb.isc.transaction.SwitchTransactionService;
import com.dpi.financial.ftcom.model.to.atm.Terminal;
import com.dpi.financial.ftcom.model.to.meb.atm.transaction.TerminalTransaction;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.MiddleEastBankSwitchTransaction;
import com.dpi.financial.ftcom.model.to.meb.isc.transaction.SynchronizeStatistics;
import com.dpi.financial.ftcom.web.controller.base.ControllerManagerBase;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Named("middleEastBankSwitchReconciliationManager")
@ViewScoped
public class SwitchReconciliationManager extends ControllerManagerBase<MiddleEastBankSwitchTransaction>
        implements Serializable {
    Logger logger = LoggerFactory.getLogger(SwitchReconciliationManager.class);

    private static final long serialVersionUID = 680449549876328399L;

    @EJB
    private SwitchReconciliationService service;

    @EJB
    private SynchronizeStatisticsService synchronizeStatisticsService;

    @EJB
    private TerminalService terminalService;

    @EJB
    private SwitchTransactionService switchTransactionService;

    @EJB
    private TerminalTransactionService terminalTransactionService;

    private Terminal terminal;
    private List<String> cardNumbers;
    private String selectedCardNumber;
    Date switchTransactionDateFrom;
    Date switchTransactionDateTo;

    private List<MiddleEastBankSwitchTransaction> switchTransactionList;
    private List<TerminalTransaction> terminalTransactionList;

    public SwitchReconciliationManager() {
        super(MiddleEastBankSwitchTransaction.class);

        terminal = new Terminal();

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        switchTransactionDateFrom = cal.getTime();

        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        switchTransactionDateTo = cal.getTime();
    }

    @Override
    public GeneralServiceApi<MiddleEastBankSwitchTransaction> getGeneralServiceApi() {
        return service;
    }

    /**
     * This method initialize list not depend on the request parameter
     */
    @Override
    public void init() {

    }

    public void onLoad() {
    }

    /**
     * This method prepare ATM transactions based on journal content for specified terminal
     *
     * @param event
     * @since ver 1.0.0 modified by Hossein Mohammadi w.r.t Issue #1 as on Tuesday, December 13, 2016 3:10:02 PM
     * <li>Synchronize ATM transactions based on switch transactions</li>
     */
    public void lunoValueChange(AjaxBehaviorEvent event) {
        String luno = ((UIInput) event.getComponent()).getValue().toString();
        logger.info("Loading unbound card number for terminal: {}", luno);

        try {
            // Terminal terminal = terminalService.findByLuno(luno);
            cardNumbers = service.findAllCard(luno, switchTransactionDateFrom, switchTransactionDateTo);
            logger.info("{} card is found.", cardNumbers.size());
            if (cardNumbers.size() < 100)
                return;

            cardNumbers = new ArrayList<String>(cardNumbers.subList(0, 99));
            logger.info("Load finished.");
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    /**
     * This method prepare ATM transactions based on journal content for specified terminal
     *
     * @param event
     * @since ver 1.0.0 modified by Hossein Mohammadi w.r.t Issue #1 as on Tuesday, December 13, 2016 3:10:02 PM
     * <li>Synchronize ATM transactions based on switch transactions</li>
     */
    public void cardValueChange(AjaxBehaviorEvent event) {
        Object value = ((UIInput) event.getComponent()).getValue();
        if (value == null) {
            switchTransactionList = null;
            terminalTransactionList = null;
            return;
        }
        String cardNumber = value.toString();
        try {
            switchTransactionList = switchTransactionService.findAllByLunoCardNumber(terminal.getLuno(), cardNumber);
            terminalTransactionList = terminalTransactionService.findAllByLunoCardNumber(terminal.getLuno(), cardNumber);
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    /**
     * This method prepare ATM transactions based on journal content for specified terminal
     *
     * @param event
     * @since ver 1.0.0 modified by Hossein Mohammadi w.r.t Issue #1 as on Tuesday, December 13, 2016 3:10:02 PM
     * <li>Synchronize ATM transactions based on switch transactions</li>
     */
    public void synchronizeSwitchTransactions(AjaxBehaviorEvent event) {
        String luno = terminal.getLuno();
        try {
            if (selectedCardNumber != null) {
                service.synchronizeAtmTransactions(luno, selectedCardNumber);
                switchTransactionList = switchTransactionService.findAllByLunoCardNumber(terminal.getLuno(), selectedCardNumber);
                terminalTransactionList = terminalTransactionService.findAllByLunoCardNumber(terminal.getLuno(), selectedCardNumber);
            } else {
                List<String> allUnbound = service.findAllCard(luno, switchTransactionDateFrom, switchTransactionDateTo);
                allUnbound.forEach(cardNo -> {
                    logger.info("Synchronizing started for {}/{}", luno, cardNo);
                    service.synchronizeAtmTransactions(luno, cardNo);
                    logger.info("Synchronize finished for {}/{}", luno, cardNo);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void synchronizeSwitchTransactionsForSelectedTerminal() {
        String luno = terminal.getLuno();
        List<SynchronizeStatistics> synchronizeStatisticsList = synchronizeStatisticsService.findAllCashWithdrawalByTerminal(luno);
        if (synchronizeStatisticsList == null || synchronizeStatisticsList.size() == 0)
            logger.info("There is nothing to synchronize");

        for (SynchronizeStatistics synchronizeStatistics : synchronizeStatisticsList) {
            int countOfRemainSwitchTransaction;

            try {
                countOfRemainSwitchTransaction = service.synchronize(luno, synchronizeStatistics.getCardNumber());
            } catch (Exception e) {
                countOfRemainSwitchTransaction = -1;
                logger.error("Error in synchronizeSwitchTransactionsForSelectedTerminal", e);
            }

            synchronizeStatistics.setRetryCount(synchronizeStatistics.getRetryCount() + 1);
            synchronizeStatistics.setRemainNo(countOfRemainSwitchTransaction);
            synchronizeStatisticsService.update(synchronizeStatistics);
        }
    }

    public void synchronizeSwitchTransactionsForSelectedCard() {
        if (terminal == null || terminal.getLuno() == null)
            return;
        if (selectedCardNumber == null || selectedCardNumber.isEmpty())
            return;

        String luno = terminal.getLuno();

        SynchronizeStatistics synchronizeStatistics = synchronizeStatisticsService.findAllCashWithdrawalByLunoCardNumber(luno, selectedCardNumber);

        int countOfRemainSwitchTransaction;
        try {
            countOfRemainSwitchTransaction = service.synchronize(luno, synchronizeStatistics.getCardNumber());
        } catch (Exception e) {
            countOfRemainSwitchTransaction = -1;
            logger.error("Error in synchronizeSwitchTransactionsForSelectedTerminal", e);
        }

        synchronizeStatistics.setRetryCount(synchronizeStatistics.getRetryCount() + 1);
        synchronizeStatistics.setRemainNo(countOfRemainSwitchTransaction);
        synchronizeStatisticsService.update(synchronizeStatistics);
    }

    private static void WriteRow(HSSFSheet sheet, int rownum, Object[] values) {
        Row row = sheet.createRow(rownum);
        int cellnum = 0;
        for (Object obj : values) {
            Cell cell = row.createCell(cellnum);
            if (obj instanceof String)
                cell.setCellValue((String) obj);
            else if (obj instanceof Integer)
                cell.setCellValue((Integer) obj);
            else if (obj instanceof Date)
                cell.setCellValue((Date) obj);
            cellnum++;
        }
    }

    public void showXLS() {
        try {
            List<MiddleEastBankSwitchTransaction> inconsistentSwitchTransactions = service.findInconsistentTransactions(terminal.getLuno(), switchTransactionDateFrom, switchTransactionDateTo);

            HttpServletResponse response = getHttpServletResponse();
            String fileName = "switch-transactions.xls";
            response.setContentType("application/xls");
            response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");
            BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream());

            HSSFWorkbook workbook = new HSSFWorkbook(); // entityAttributeReportXLS(baseValueObjectArrayList, EntityNameEn.Station, EntityAttributeGroupEn.Utility, reservedColumns);

            // Create a blank sheet
            HSSFSheet sheet = workbook.createSheet("transactions");

            int rownum = 0;


            for (MiddleEastBankSwitchTransaction transaction : inconsistentSwitchTransactions) {
                if (rownum == 0) {
                    WriteRow(sheet, rownum++, getFieldNames(transaction));
                }

                WriteRow(sheet, rownum++, getFieldValues(transaction));
            }

            /*
            // --------------- create font and styles
            HSSFFont entityHeaderHssfFont = workbook.createFont();
            entityHeaderHssfFont.setFontName("Tahoma");
            entityHeaderHssfFont.setFontHeightInPoints((short) 12);
            entityHeaderHssfFont.setColor(HSSFFont.COLOR_NORMAL);
            entityHeaderHssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            entityHeaderHssfFont.setStrikeout(false);

            HSSFFont entitySubHeaderHssfFont = workbook.createFont();
            entitySubHeaderHssfFont.setFontName("Tahoma");
            entitySubHeaderHssfFont.setFontHeightInPoints((short) 10);
            entitySubHeaderHssfFont.setColor(HSSFFont.COLOR_NORMAL);
            entitySubHeaderHssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            entitySubHeaderHssfFont.setStrikeout(false);

            HSSFFont entityHssfFont = workbook.createFont();
            entityHssfFont.setFontName("Tahoma");
            entityHssfFont.setFontHeightInPoints((short) 10);
            entityHssfFont.setColor(HSSFFont.COLOR_NORMAL);
            entityHssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            entityHssfFont.setStrikeout(false);

            HSSFCellStyle entityHeaderCellStyle = workbook.createCellStyle();
            entityHeaderCellStyle.setFont(entityHeaderHssfFont);
            entityHeaderCellStyle.setAlignment((short) HSSFCellStyle.ALIGN_CENTER);
            entityHeaderCellStyle.setVerticalAlignment((short) HSSFCellStyle.VERTICAL_CENTER);
            entityHeaderCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            entityHeaderCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

            HSSFCellStyle entitySubHeaderCellStyle = workbook.createCellStyle();
            entitySubHeaderCellStyle.setFont(entitySubHeaderHssfFont);
            entitySubHeaderCellStyle.setAlignment((short) HSSFCellStyle.ALIGN_CENTER);
            entitySubHeaderCellStyle.setVerticalAlignment((short) HSSFCellStyle.VERTICAL_CENTER);
            entitySubHeaderCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

            HSSFCellStyle entitySubHeaderCellStyle2 = workbook.createCellStyle();
            entitySubHeaderCellStyle2.setFont(entitySubHeaderHssfFont);
            entitySubHeaderCellStyle2.setAlignment((short) HSSFCellStyle.ALIGN_CENTER);
            entitySubHeaderCellStyle2.setVerticalAlignment((short) HSSFCellStyle.VERTICAL_CENTER);
            entitySubHeaderCellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
            entitySubHeaderCellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);

            HSSFCellStyle entityCellStyle = workbook.createCellStyle();
            entityCellStyle.setFont(entityHssfFont);
            entityCellStyle.setVerticalAlignment((short) HSSFCellStyle.VERTICAL_CENTER);

            HSSFCellStyle entityCellStyle2 = workbook.createCellStyle();
            entityCellStyle2.setFont(entityHssfFont);
            entityCellStyle2.setVerticalAlignment((short) HSSFCellStyle.VERTICAL_CENTER);
            entityCellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);

            HSSFSheet sheet = workbook.getSheet(EntityNameEn.Station.name());

            if (sheet != null) {
                int rowIndex = 0;
                int colIndex = 0;

                // --------- create header row
                HSSFRow headerRow = sheet.getRow(rowIndex);
                HSSFCell cell = headerRow.createCell(colIndex);
                cell.setCellValue(new HSSFRichTextString(getMessage(EntityNameEn.Station.getFullName())));
                cell.setCellStyle(entityHeaderCellStyle);
                colIndex++;

                cell = headerRow.createCell(colIndex);
                cell.setCellStyle(entityHeaderCellStyle);
                colIndex++;

                colIndex = 0;
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex, colIndex + reservedColumns - 1));
                rowIndex++;

                // --------- create sub header
                colIndex = 0;
                HSSFRow subHeaderRow = sheet.getRow(rowIndex);
                cell = subHeaderRow.createCell(colIndex);
                cell.setCellValue(new HSSFRichTextString(getMessage("station.name")));
                cell.setCellStyle(entitySubHeaderCellStyle);
                colIndex++;

                subHeaderRow = sheet.getRow(rowIndex);
                cell = subHeaderRow.createCell(colIndex);
                cell.setCellValue(new HSSFRichTextString(getMessage("form.general.status")));
                cell.setCellStyle(entitySubHeaderCellStyle2);
                colIndex++;

                rowIndex++;

                // fill values for entity propery
                for (StationVO station : stationVOList) {
                    colIndex = 0;

                    HSSFRow row = sheet.getRow(rowIndex);
                    cell = row.createCell(colIndex);
                    if (station != null) {
                        cell.setCellValue(new HSSFRichTextString(station.getName()));
                    }
                    cell.setCellStyle(entityCellStyle);
                    sheet.setColumnWidth(colIndex, 24 * 256); // set column width to 30 character
                    colIndex++;

                    row = sheet.getRow(rowIndex);
                    cell = row.createCell(colIndex);
                    if (station != null && station.getStatus() != null) {
                        cell.setCellValue(new HSSFRichTextString(getMessage(station.getStatus().getFullName())));
                    }
                    cell.setCellStyle(entityCellStyle2);
                    sheet.setColumnWidth(colIndex, 16 * 256); // set column width to 30 character
                    colIndex++;

                    rowIndex++;
                }
            }
            */

            // write the workbook to the output stream
            // close our file (don't blow out our file handles
            workbook.write(output);
            output.close();

            // response.flushBuffer();
            // FacesContext.getCurrentInstance().responseComplete();

        } catch (IOException e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
    }

    private String[] getFieldNames(MiddleEastBankSwitchTransaction switchTransaction) {
        List<String> fieldNameList = new ArrayList<String>();

        for (String fieldName : switchTransaction.getFieldNames()) {
            fieldNameList.add(switchTransaction.getFieldName(fieldName, getLabelBundle()));
        }

        TerminalTransaction terminalTransaction = switchTransaction.getTerminalTransaction();
        for (String fieldName : terminalTransaction.getFieldNames()) {
            fieldNameList.add(terminalTransaction.getFieldName(fieldName, getLabelBundle()));
        }

        return fieldNameList.toArray(new String[fieldNameList.size()]);
    }

    private Object[] getFieldValues(MiddleEastBankSwitchTransaction switchTransaction) {
        List<Object> fieldNameList = new ArrayList<Object>();

        for (String fieldName : switchTransaction.getFieldNames()) {
            fieldNameList.add(switchTransaction.getFieldValue(fieldName, getLabelBundle()));
        }

        TerminalTransaction terminalTransaction = switchTransaction.getTerminalTransaction();
        for (String fieldName : terminalTransaction.getFieldNames()) {
            fieldNameList.add(terminalTransaction.getFieldValue(fieldName, getLabelBundle()));
        }

        return fieldNameList.toArray(new Object[fieldNameList.size()]);
    }

    public void showPDF() {

        /*
        try {
            //---- initial pdf structure
            HttpServletResponse response = getHttpServletResponse();
            BufferedOutputStream output = null;
            String fileName = lineVO.getCodeLast() + ".pdf";
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");
            output = new BufferedOutputStream(response.getOutputStream());

            Rectangle documentPageSize = PageSize.A4.rotate();
            Document document = new Document(documentPageSize, 10f, 10f, 10f, 10f);

            document.addCreationDate();
            PdfWriter pw = PdfWriter.getInstance(document, output);
            pw.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

            document.addAuthor("GAP Company");
            document.addCreator("BIS");
            document.addCreationDate();
            document.addTitle("Report by BIS");

            // document.addProducer();
            // document.setPageSize(PageSize.LETTER);

            document.open();

            String fontDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/css/fonts");
            String imageDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/images");
            Font mitra16 = new Font(BaseFont.createFont(fontDir + "/BMITRA.TTF", BaseFont.IDENTITY_H, false), 16);
            Font mitraBold16 = new Font(BaseFont.createFont(fontDir + "/BMITRABD.TTF", BaseFont.IDENTITY_H, false), 16);
            Font mitra14 = new Font(BaseFont.createFont(fontDir + "/BMITRA.TTF", BaseFont.IDENTITY_H, false), 14);
            Font mitraBold14 = new Font(BaseFont.createFont(fontDir + "/BMITRABD.TTF", BaseFont.IDENTITY_H, false), 14);
            Font mitra12 = new Font(BaseFont.createFont(fontDir + "/BMITRA.TTF", BaseFont.IDENTITY_H, false), 12);
            Font mitraBold12 = new Font(BaseFont.createFont(fontDir + "/BMITRABD.TTF", BaseFont.IDENTITY_H, false), 12);
            Font mitra10 = new Font(BaseFont.createFont(fontDir + "/BMITRA.TTF", BaseFont.IDENTITY_H, false), 10);
            Font mitraBold10 = new Font(BaseFont.createFont(fontDir + "/BMITRABD.TTF", BaseFont.IDENTITY_H, false), 10);
            Font timesBold10 = new Font(BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 10);

            float[] widthsCarUsage = new float[]{260f, 40f, 10f};
            float[] widthsCarOptions = new float[Constants.LINE_SHIFT_HALF_PATH_HOURS[lineShiftType.ordinal()].length + 2];

            int iIndex;
            for (iIndex = 0; iIndex < Constants.LINE_SHIFT_HALF_PATH_HOURS[lineShiftType.ordinal()].length; iIndex++) {
                widthsCarOptions[iIndex] = 15f;
            }
            widthsCarOptions[iIndex] = 15f;
            iIndex++;
            widthsCarOptions[iIndex] = 50f;

            PdfPTable table = new PdfPTable(widthsCarUsage);
            table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table.setWidthPercentage(100);

            // header row
            PdfPTable headerTable = new PdfPTable(new float[]{100f, 400f, 100f});
            headerTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            headerTable.setWidthPercentage(100);

            Image image = Image.getInstance(imageDir + "/appPic/uiPic/bus.gif");
            image.setAlignment(Element.ALIGN_LEFT);
            image.scalePercent(25);

            PdfPCell cell = new PdfPCell(image, false);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(3f);
            cell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(cell);

            cell = TextCell(getMessage("title.lineDailyReportDetail.privateCo"), mitraBold16, Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(cell);

            // report date & time
            PdfPTable dateTimeNestedTable = new PdfPTable(new float[]{2f, 10f});
            dateTimeNestedTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            dateTimeNestedTable.setWidthPercentage(100);

            cell = new PdfPCell();
            cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPadding(0f);
            cell.setPaddingRight(4f);
            cell.setPaddingLeft(4f);
            cell.setArabicOptions(ArabicLigaturizer.ar_composedtashkeel);
            cell.setColspan(dateTimeNestedTable.getNumberOfColumns());
            String hejriCurrentDate = HejriUtil.chrisToHejri(new Date());
            String reportDateStr = getMessage("label.general.report.print.date") + ": " + HejriUtil.rotateDate(hejriCurrentDate);
            Phrase phrase = new Phrase(reportDateStr, mitra12);
            phrase.add("\n");
            SimpleDateFormat reportTime = new SimpleDateFormat("HH:mm");
            phrase.add(getMessage("label.general.report.print.time") + ": " + reportTime.format(new Date()));
            cell.setPhrase(phrase);
            cell.setBorder(Rectangle.NO_BORDER);

            dateTimeNestedTable.addCell(cell);
            dateTimeNestedTable.completeRow();

            cell = new PdfPCell();
            cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPadding(0f);
            cell.setPaddingRight(4f);
            cell.setPaddingBottom(8f);
            cell.setArabicOptions(ArabicLigaturizer.ar_composedtashkeel);
            phrase = new Phrase(getMessage("title.general.report.bis"), mitraBold12);
            cell.setPhrase(phrase);
            cell.setBorder(Rectangle.NO_BORDER);
            dateTimeNestedTable.addCell(cell);

            cell = new PdfPCell();
            phrase = new Phrase("BIS", timesBold10);
            cell.setPhrase(phrase);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingBottom(8f);
            dateTimeNestedTable.addCell(cell);

            dateTimeNestedTable.completeRow();

            cell = new PdfPCell(dateTimeNestedTable);
            cell.setBorder(Rectangle.NO_BORDER);

            headerTable.addCell(cell);
            headerTable.completeRow();

            cell = new PdfPCell(headerTable);
            cell.setColspan(widthsCarUsage.length);
            table.addCell(cell);
            table.completeRow();

            // sub header first row
            headerTable = new PdfPTable(new float[]{30f, 100f, 150f, 130f});
            headerTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            headerTable.setWidthPercentage(100);

            // headerTable.resetColumnCount(5);
            // headerTable.setWidths(new float[] {50f, 50f, 50f, 50f, 50f});

            String s = new String(getMessage("line.code") + ": ");
            s += lineVO.getCodeLast();
            s += " (" + lineVO.getName() + ")";
            // cell.setFixedHeight(50f);
            headerTable.addCell(TextCell(s, mitraBold14));

            //------------------
            PdfPTable companyPdfPTable = new PdfPTable(new float[]{100f});
            companyPdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            companyPdfPTable.setWidthPercentage(100);

            s = new String(getMessage("lineCompany.companyController") + ": ");
            if (lineVO.getLineCompanyController().getCompany().getCompanyType() == CompanyType.CityRegion) {
                s += lineVO.getCompanyControllerMasterFS().getName() + " - ";
            }
            s += lineVO.getLineCompanyController().getCompany().getName();
            cell = TextCell(s, mitra14);
            cell.setBorderWidth(0f);
            cell.setPaddingBottom(0f);
            companyPdfPTable.addCell(cell);
            companyPdfPTable.completeRow();

            s = new String(getMessage("label.line.companyProfit") + ": ");

            int capacity = 0;

            tmpLineCompanyFSIN = new LineCompanyVO(GeneralStatus.Active);
            tmpLineCompanyFSIN.setLine(new LineVO(lineVO.getId()));
            tmpLineCompanyFSIN.setCompanyType(LineCompanyType.Profit);
            lineCompanyVOListIN = searchLineCompany(tmpLineCompanyFSIN);
            String lineCompanyProfitName = null;
            for (LineCompanyVO lineCompanyVO : lineCompanyVOListIN) {
                if (lineCompanyProfitName != null) {
                    lineCompanyProfitName += " - ";
                }
                lineCompanyProfitName += lineCompanyVO.getCompany().getName();
                capacity += lineCompanyVO.getCapacityProfitNo();
                // s = s.substring(0, s.length() - 3);
            }
            s += lineCompanyProfitName;
            cell = TextCell(s, mitra14);
            cell.setBorderWidth(0f);
            companyPdfPTable.addCell(cell);
            companyPdfPTable.completeRow();
            headerTable.addCell(companyPdfPTable);

            //-----------------------
            PdfPTable calendarDataPdfPTable = new PdfPTable(new float[]{100f});
            calendarDataPdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            calendarDataPdfPTable.setWidthPercentage(100);

            s = new String(getMessage("lineDailyReport.date") + ": ");
            s += getMessage(calenderVO.getDayWeek().getDayWeek());
            s += " " + HejriUtil.rotateDate(hejriLineDailyReportDateFS);
            s += " - " + getMessage(lineShiftType.getFullName());
            cell = TextCell(s, mitraBold12);
            cell.setBorderWidth(0);
            cell.setPaddingBottom(0f);
            calendarDataPdfPTable.addCell(cell);
            calendarDataPdfPTable.completeRow();

            s = new String(getMessage("timeLine.pathTypeEn") + ": ");
            s += getMessage(timeLineVO.getPathTypeEn().getFullName());
            s += " - " + getMessage("timeLine.dayTypeOnHolidayEn") + ": ";
            s += getMessage(timeLineVO.getDayTypeOnHolidayEn().getFullName());
            cell = TextCell(s, mitra12);
            cell.setBorderWidth(0);
            calendarDataPdfPTable.addCell(cell);
            calendarDataPdfPTable.completeRow();
            headerTable.addCell(calendarDataPdfPTable);

            s = new String(getMessage("line.capacity") + ": " + capacity);

            headerTable.addCell(TextCell(s, mitra12));
            headerTable.completeRow();

            cell = new PdfPCell(headerTable);
            cell.setGrayFill(0.9f);
            cell.setColspan(widthsCarUsage.length);
            table.addCell(cell);
            table.completeRow();

            // sub header second row
            cell = TextCell(getMessage("form.general.no"), mitra12, Element.ALIGN_CENTER);
            cell.setRotation(90);
            cell.setGrayFill(0.9f);
            table.addCell(cell);

            cell = TextCell(getMessage("car.plateText"), mitraBold14, Element.ALIGN_CENTER);
            cell.setGrayFill(0.9f);
            table.addCell(cell);

            headerTable = new PdfPTable(widthsCarOptions);
            headerTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            headerTable.setWidthPercentage(100);

            cell = TextCell(getMessage("label.driver.description"), mitraBold14, Element.ALIGN_CENTER);
            cell.setColspan(2);
            headerTable.addCell(cell);

            cell = TextCell(getMessage("label.lineDailyReportDetail.halfPath.hours"), mitraBold14, Element.ALIGN_CENTER);
            cell.setColspan(widthsCarOptions.length - 2);
            headerTable.addCell(cell);
            headerTable.completeRow();

            headerTable.addCell(TextCell(getMessage("label.driver.nameFamily"), mitraBold14, Element.ALIGN_CENTER));
            headerTable.addCell(TextCell(getMessage("label.driverJob.type"), mitraBold12, Element.ALIGN_CENTER));

            for (String lineDailyReportDetailHalfPathHour : Constants.LINE_SHIFT_HALF_PATH_HOURS[lineShiftType.ordinal()]) {
                Font font = mitraBold12;
                if (Constants.LINE_SHIFT_HALF_PATH_HOURS[lineShiftType.ordinal()].length > 18) {
                    font = mitra10;
                }
                headerTable.addCell(TextCell(lineDailyReportDetailHalfPathHour, font, Element.ALIGN_CENTER));
            }

            headerTable.completeRow();

            cell = new PdfPCell(headerTable);
            cell.setGrayFill(0.9f);
            table.addCell(cell);
            table.completeRow();

            // footer row
            cell = TextCell(getMessage("report.lineDailyReportDetail.footer.notification"), mitra12);
            cell.setColspan(widthsCarUsage.length);
            cell.setBorder(0);
            table.addCell(cell);
            table.completeRow();

            table.setHeaderRows(4); // hear we set count header and footer rows
            table.setFooterRows(1); // hear wo only set count footer rows

            // main table
            for (CarUsageVO carUsageVO : carUsageVOList) {

                if (carUsageVO.getNoFL() != null) {
                    cell = TextCell(carUsageVO.getNoFL().toString(), mitra14);
                } else {
                    cell = new PdfPCell();
                }
                table.addCell(cell);

                if (carUsageVO.getCar() != null && carUsageVO.getCar().getPlateText() != null) {
                    cell = TextCell(carUsageVO.getCar().getPlateText(), mitra14);
                } else {
                    cell = TextCell("plateText is null", mitra14);
                }
                table.addCell(cell);

                PdfPTable headerTableCarOptions = new PdfPTable(widthsCarOptions);
                headerTableCarOptions.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                headerTableCarOptions.setWidthPercentage(100);

                if (carUsageVO.getCar().getActivityStatus().equals(GeneralStatus.Inactive)) {
                    String reasonInactive = new String(getMessage("car.activityStatus.equals.inactive"));
                    if (carUsageVO.getCar().getLastHLDeactivationCarUsageFV() != null &&
                            carUsageVO.getCar().getLastHLDeactivationCarUsageFV().getSystemParameter() != null &&
                            carUsageVO.getCar().getLastHLDeactivationCarUsageFV().getSystemParameter().getParamValue() != null) {
                        reasonInactive += " " + getMessage("car.lastHLDeactivationCarUsageFV.reason");
                        reasonInactive += ": " + carUsageVO.getCar().getLastHLDeactivationCarUsageFV().getSystemParameter().getParamValue();
                    }
                    cell = TextCell(reasonInactive, mitra12);
                    cell.setColspan(widthsCarOptions.length);
                    GrayColor color = new GrayColor(0.9f);
                    cell.setBackgroundColor(color);
                    headerTableCarOptions.addCell(cell);
                    headerTableCarOptions.completeRow();
                } else {
                    if (carUsageVO.getCarOptionVOList() != null && carUsageVO.getCarOptionVOListSize() > 0) {
                        boolean isExistsActivityLicence = false;

                        for (CarOptionVO carOptionVO : carUsageVO.getCarOptionVOList()) {
                            if (carOptionVO.getExpireDate().equals(CalenderUtils.midnight(reportDate))
                                    || carOptionVO.getExpireDate().after(CalenderUtils.midnight(reportDate))
                                    && carOptionVO.getStartDate().before(CalenderUtils.addDay(reportDate, 1))) {

                                isExistsActivityLicence = true;

                                if (carOptionVO.getPerson() != null) {
                                    Font font = mitra14;
                                    if (Constants.LINE_SHIFT_HALF_PATH_HOURS[lineShiftType.ordinal()].length > 18) {
                                        font = mitra12;
                                    }
                                    headerTableCarOptions.addCell(TextCell(carOptionVO.getPerson().getNameFv(), font));

                                    if (carOptionVO.getDriverJob() != null && carOptionVO.getDriverJob().getType() != null)
                                        headerTableCarOptions.addCell(TextCell(getMessage(carOptionVO.getDriverJob().getType().getFullName("summary")), mitra12));
                                }

                                headerTableCarOptions.completeRow();
                            }
                        }

                        if (!isExistsActivityLicence) {
                            cell = TextCell(getMessage("carUsage.carOptionVO.activityLicence.not.active.found.in.date.0", new String[]{HejriUtil.rotateDate(HejriUtil.chrisToHejri(reportDate))}), mitra12);
                            cell.setColspan(widthsCarOptions.length);
                            GrayColor color = new GrayColor(0.9f);
                            cell.setBackgroundColor(color);
                            headerTableCarOptions.addCell(cell);
                            headerTableCarOptions.completeRow();
                        }

                    } else {
                        cell = TextCell(getMessage("carUsage.carOptionVO.activityLicence.not.active.found"), mitra12);
                        cell.setColspan(widthsCarOptions.length);
                        // BaseColor color = new BaseColor( red, green blue ); // or red, green, blue, alpha
                        // CYMKColor cmyk = new CMYKColor( cyan, yellow, magenta, black ); // no alpha
                        // BaseColor color = WebColors.getRGBColor("#A00000");
                        GrayColor color = new GrayColor(0.9f);
                        cell.setBackgroundColor(color);
                        headerTableCarOptions.addCell(cell);
                        headerTableCarOptions.completeRow();
                    }
                }

                cell = new PdfPCell(headerTableCarOptions);
                table.addCell(cell);
                table.completeRow();
            }

            document.add(table);

            MultiColumnText multiColumnText = new MultiColumnText();
            multiColumnText.addSimpleColumn(10, documentPageSize.getWidth() - 10);
            multiColumnText.setArabicOptions(ArabicLigaturizer.ar_composedtashkeel);
            multiColumnText.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            multiColumnText.setColumnsRightToLeft(true);

            //////////////////////////////////////////////////////////////////////////////
            Paragraph p = null;
            p = new Paragraph(getMessage("title.timeLineDetail.plan.table"), mitraBold16);
            p.setAlignment(Element.ALIGN_LEFT);
            // p.setIndentationLeft(450);
            // p.setIndentationRight(0);
            multiColumnText.addElement(p);
            // document.add(multiColumnText);
            //////////////////////////////////////////////////////////////////////////////

            //-------- timeLineDetailTable ----------------------------------------------------

            List<PdfPCell> pdfPCells = new ArrayList<PdfPCell>();
            List<Float> widthsList = new ArrayList<Float>();

            PdfPTable nestedTimeLineDetailTable = new PdfPTable(1);
            nestedTimeLineDetailTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            nestedTimeLineDetailTable.setWidthPercentage(100);
            nestedTimeLineDetailTable.setKeepTogether(true);
            nestedTimeLineDetailTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            float rowHeight = 28f;

            cell = TextCell(getMessage("timeLineDetail.startTime2") + " - " + getMessage("timeLineDetail.endTime2"), mitraBold14, 0.9f);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setFixedHeight(rowHeight);
            nestedTimeLineDetailTable.addCell(cell);
            cell = TextCell(getMessage("label.timeLineDetail.moveDistanceOnMin"), mitraBold14);
            cell.setFixedHeight(rowHeight);
            nestedTimeLineDetailTable.addCell(cell);
            cell = TextCell(getMessage("label.timeLineDetail.desire.halfPath"), mitraBold14);
            cell.setFixedHeight(rowHeight);
            nestedTimeLineDetailTable.addCell(cell);
            cell = TextCell(getMessage("label.timeLineDetail.real.halfPath"), mitraBold14);
            cell.setFixedHeight(rowHeight);
            nestedTimeLineDetailTable.addCell(cell);

            cell = new PdfPCell(nestedTimeLineDetailTable);
            pdfPCells.add(cell);
            widthsList.add(0.2f);
            float sum = 0.2f;

            int sumDesireHalfPath = 0;

            boolean hasTitle = true;

            for (TimeLineDetailVO timeLineDetailVO : timeLineVO.getTimeLineDetailVOList()) {
                nestedTimeLineDetailTable = new PdfPTable(1);
                nestedTimeLineDetailTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                nestedTimeLineDetailTable.setWidthPercentage(100);
                nestedTimeLineDetailTable.setKeepTogether(true);
                nestedTimeLineDetailTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

                cell = TextCell(timeLineDetailVO.getStartTime() + "-" + timeLineDetailVO.getEndTime(), mitraBold14, 0.9f);
                cell.setFixedHeight(rowHeight);
                nestedTimeLineDetailTable.addCell(cell);
                Integer moveDistanceOnMin = timeLineDetailVO.getMoveDistanceOnSec() / 60;
                cell = TextCell(moveDistanceOnMin.toString(), mitra16, Element.ALIGN_CENTER);
                cell.setFixedHeight(rowHeight);
                nestedTimeLineDetailTable.addCell(cell);
                Integer desireHalfPath = (CalenderUtils.hourStrToMinute(timeLineDetailVO.getEndTime()) - CalenderUtils.hourStrToMinute(timeLineDetailVO.getStartTime())) * 60 / timeLineDetailVO.getMoveDistanceOnSec();
                sumDesireHalfPath += desireHalfPath;
                cell = TextCell(desireHalfPath.toString(), mitra16, Element.ALIGN_CENTER);
                cell.setFixedHeight(rowHeight);
                nestedTimeLineDetailTable.addCell(cell);
                cell = new PdfPCell();
                cell.setFixedHeight(rowHeight);
                nestedTimeLineDetailTable.addCell(cell);

                sum += 0.1f;
                System.out.println(">>>>> sum : " + sum);

                if (sum > 1) {
                    Collections.reverse(widthsList);
                    float widths[] = ArrayUtils.toPrimitive(widthsList.toArray(new Float[widthsList.size()]));

                    // ---- TimeLineDetailPdfTable
                    PdfPTable timeLineDetailTable = new PdfPTable(widths);
                    timeLineDetailTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    timeLineDetailTable.setWidthPercentage(sum * 100);
                    timeLineDetailTable.setKeepTogether(true);
                    timeLineDetailTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

                    if (hasTitle) {
                        cell = TextCell(getMessage("title.timeLineDetail.plan.table"), mitraBold16);
                        cell.setBorder(Rectangle.NO_BORDER);
                        cell.setColspan(widths.length);
                        timeLineDetailTable.addCell(cell);
                    }

                    for (PdfPCell pdfPCell : pdfPCells) {
                        timeLineDetailTable.addCell(pdfPCell);
                    }

                    timeLineDetailTable.completeRow();
                    document.add(timeLineDetailTable);
                    document.add(new Paragraph("\n"));

                    pdfPCells = new ArrayList<PdfPCell>();
                    widthsList = new ArrayList<Float>();
                    sum = 0.1f;
                    hasTitle = false;
                }

                cell = new PdfPCell(nestedTimeLineDetailTable);
                pdfPCells.add(cell);
                widthsList.add(0.1f);
            }

            nestedTimeLineDetailTable = new PdfPTable(1);
            nestedTimeLineDetailTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            nestedTimeLineDetailTable.setWidthPercentage(100);
            nestedTimeLineDetailTable.setKeepTogether(true);
            nestedTimeLineDetailTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            cell = TextCell(getMessage("label.general.sum"), mitraBold14, 0.9f);
            cell.setFixedHeight(rowHeight);
            nestedTimeLineDetailTable.addCell(cell);
            cell = new PdfPCell();
            cell.setFixedHeight(rowHeight);
            nestedTimeLineDetailTable.addCell(cell);
            cell = TextCell(String.valueOf(sumDesireHalfPath), mitra16, Element.ALIGN_CENTER);
            cell.setFixedHeight(rowHeight);
            nestedTimeLineDetailTable.addCell(cell);
            cell = new PdfPCell();
            cell.setFixedHeight(rowHeight);
            nestedTimeLineDetailTable.addCell(cell);

            cell = new PdfPCell(nestedTimeLineDetailTable);
            pdfPCells.add(cell);
            widthsList.add(0.1f);
            sum += 0.1f;

            Collections.reverse(widthsList);
            float widths[] = ArrayUtils.toPrimitive(widthsList.toArray(new Float[widthsList.size()]));

            // ---- TimeLineDetailPdfTable
            PdfPTable timeLineDetailTable = new PdfPTable(widths);
            timeLineDetailTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            timeLineDetailTable.setWidthPercentage(sum * 100);
            timeLineDetailTable.setKeepTogether(true);
            timeLineDetailTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            if (hasTitle) {
                cell = TextCell(getMessage("title.timeLineDetail.plan.table"), mitraBold16);
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setColspan(widths.length);
                timeLineDetailTable.addCell(cell);
            }

            for (PdfPCell pdfPCell : pdfPCells) {
                timeLineDetailTable.addCell(pdfPCell);
            }

            timeLineDetailTable.completeRow();
            document.add(timeLineDetailTable);


            // --- search employeeDetails for this line
            EmployeeDetailVO tmpEmployeeDetailFS = new EmployeeDetailVO(GeneralStatus.Active, true);
            tmpEmployeeDetailFS.setProcessStatus(ProcessStatus.Confirm1);
            tmpEmployeeDetailFS.setEntityNameEn(EntityNameEn.Line);
            tmpEmployeeDetailFS.setEntityId(lineVO.getId());
            super.setTmpEmployeeDetailFS(tmpEmployeeDetailFS);
            super.searchEmployeeDetail();

            if (!super.getEmployeeDetailVOList().isEmpty()) {

                int nCols = super.getEmployeeDetailVOList().size();
                float tableWidthPercentage = nCols * 0.25f;
                float colWidth[] = new float[nCols];
                for (int i = 0; i < nCols; i++) {
                    colWidth[i] = 0.25f;
                }

                PdfPTable employeeDetailPdfPTable = new PdfPTable(nCols);
                employeeDetailPdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                employeeDetailPdfPTable.setWidthPercentage(tableWidthPercentage * 100);
                employeeDetailPdfPTable.setKeepTogether(true);
                employeeDetailPdfPTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

                cell = TextCell(getMessage("title.employeeDetail.LineSupervisor"), mitraBold16);
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setColspan(colWidth.length);
                employeeDetailPdfPTable.addCell(cell);

                for (EmployeeDetailVO employeeDetailVO : super.getEmployeeDetailVOList()) {
                    PdfPTable nestedTable = new PdfPTable(1);
                    nestedTable.setWidthPercentage(100);

                    PdfPCell imageCell = TextCell("");
                    imageCell.setPadding(4f);
                    imageCell.setBorder(Rectangle.NO_BORDER);
                    if (employeeDetailVO.getEmployee().getPerson().getPictureAF() != null) {
                        AttachFileVO attachFileVO = new AttachFileVO(employeeDetailVO.getEmployee().getPerson().getPictureAF().getId());
                        attachFileVO = ((CoreService) getServiceLocatorBean().getService("coreService")).getAttachFileById(attachFileVO);
                        File file = new File(attachFileVO.getPathUrl());
                        if (file.exists()) {
                            Image personImage = Image.getInstance(attachFileVO.getPathUrl());
                            personImage.setAlignment(Element.ALIGN_CENTER);
                            float ratio = personImage.getWidth() / personImage.getHeight();
                            personImage.scaleAbsoluteHeight(70f);
                            personImage.scaleAbsoluteWidth(70f * ratio);

                            imageCell = new PdfPCell(personImage);
                            imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            imageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            imageCell.setPadding(4f);
                            imageCell.setBorder(Rectangle.NO_BORDER);
                        }
                    }
                    imageCell.setFixedHeight(70f);
                    nestedTable.addCell(imageCell);

                    cell = TextCell(employeeDetailVO.getEmployee().getPerson().getName() + " " + employeeDetailVO.getEmployee().getPerson().getFamily(), mitra14);
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    nestedTable.addCell(cell);

                    cell = TextCell(getMessage("person.signature"), mitra14);
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    nestedTable.addCell(cell);

                    employeeDetailPdfPTable.addCell(nestedTable);
                }
                employeeDetailPdfPTable.completeRow();

                // multiColumnText.addElement(employeeDetailPdfPTable);
                document.add(employeeDetailPdfPTable);
            } // end of if (!employeeDetailVOList.isEmpty())

            // document.add(multiColumnText);
            document.close();

            // response.flushBuffer();
            // FacesContext.getCurrentInstance().responseComplete();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            printErrorMessage(e);
        }
        */
    }

    /*
    public HSSFWorkbook entityAttributeReportXLS(List<BaseValueObject> valueObjectArrayList, EntityNameEn entityName, EntityAttributeGroupEn entityAttributeGroup, int countReservedColumns) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        try {
            // create a new file
            // FileOutputStream out = new FileOutputStream("workbook.xls");

            HSSFSheet sheet = workbook.createSheet();
            HSSFRow row = null;
            HSSFCell cell = null;

            HSSFCellStyle attrCellStyle = workbook.createCellStyle();
            HSSFCellStyle paramCellStyle = workbook.createCellStyle();
            HSSFCellStyle valueCellStyle = workbook.createCellStyle();

            // HSSFDataFormat df = workbook.createDataFormat();
            HSSFFont attrHssfFont = workbook.createFont();
            HSSFFont paramHssfFont = workbook.createFont();
            HSSFFont valueHssfFont = workbook.createFont();

            attrHssfFont.setFontHeightInPoints((short) 12);
            attrHssfFont.setColor((short) 0xC); // make it blue
            attrHssfFont.setFontName("B Homa"); // arial is the default font
            attrHssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            paramHssfFont.setFontName("B Lotus");
            paramHssfFont.setFontHeightInPoints((short) 12);
            paramHssfFont.setColor(HSSFFont.COLOR_RED);
            paramHssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            paramHssfFont.setStrikeout(false);

            valueHssfFont.setFontName("Tahoma");
            valueHssfFont.setFontHeightInPoints((short) 10);
            valueHssfFont.setColor(HSSFFont.COLOR_NORMAL);
            valueHssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            valueHssfFont.setStrikeout(false);

            attrCellStyle.setFont(attrHssfFont);
            // set the cell format
            // attrCellStyle.setDataFormat(df.getFormat("#,##0.0"));
            attrCellStyle.setAlignment((short) HSSFCellStyle.ALIGN_CENTER);
            attrCellStyle.setVerticalAlignment((short) HSSFCellStyle.VERTICAL_CENTER);

            // set a thin border
            paramCellStyle.setBorderBottom(paramCellStyle.BORDER_THIN);
            // fill w fg fill color
            // paramCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            // set the cell format to text see HSSFDataFormat for a full list
            paramCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
            paramCellStyle.setAlignment((short) HSSFCellStyle.ALIGN_CENTER);
            paramCellStyle.setVerticalAlignment((short) HSSFCellStyle.VERTICAL_CENTER);
            paramCellStyle.setFont(paramHssfFont); // Set the font

            valueCellStyle.setFont(valueHssfFont);
            valueCellStyle.setAlignment((short) HSSFCellStyle.ALIGN_CENTER);
            valueCellStyle.setVerticalAlignment((short) HSSFCellStyle.VERTICAL_CENTER);

            workbook.setSheetName(0, entityName.name()); // set the sheet name in Unicode

            // in case of compressed Unicode
            // workbook.setSheetName(0, "HSSF Test", HSSFWorkbook.ENCODING_COMPRESSED_UNICODE );

            // ------ Get dynamic header values
            EntityAttributeVO tmpEntityAttributeFS = new EntityAttributeVO();
            tmpEntityAttributeFS.setEntityNameEn(entityName);
            tmpEntityAttributeFS.setEntityAttributeGroupEn(entityAttributeGroup);
            List<EntityAttributeVO> entityAttributeVOList = getServiceLocatorBean().getCoreService().getEntityAttributeListByParam(tmpEntityAttributeFS);

            int rowIndex = 0;
            int colIndex = countReservedColumns;

            // --------- Create header row
            HSSFRow headerRow = sheet.createRow(rowIndex);
            headerRow.setHeight((short) (30 * 20)); // set row height to 30 point
            rowIndex++;

            // --------- Create sub header row
            HSSFRow subHeaderRow = sheet.createRow(rowIndex);
            subHeaderRow.setHeight((short) (30 * 20)); // set row height to 30 point

            // remember index of attribute param column for later
            Map<Long, Map<Long, Integer>> colIndexOfEntityAttributeMap = new HashMap<Long, Map<Long, Integer>>();

            // ------------------ Fill header and sub header of sheet
            for (EntityAttributeVO curEntityAttributeVO : entityAttributeVOList) {
                Map<Long, Integer> colIndexOfEntityAttributeParamMap = new HashMap<Long, Integer>();

                int curEntityAttributeColIndex = colIndex;

                // create header row of sheet
                cell = headerRow.createCell(colIndex);
                cell.setCellValue(new HSSFRichTextString(curEntityAttributeVO.getName()));
                cell.setCellStyle(attrCellStyle);
                // set column width to 30 character
                sheet.setColumnWidth(colIndex, 20 * 256);

                // create sub header row of sheet
                for (EntityAttributeParamVO curEntityAttributeParamVO : curEntityAttributeVO.getEntityAttributeParamList()) {
                    colIndexOfEntityAttributeParamMap.put(curEntityAttributeParamVO.getId(), colIndex);

                    cell = subHeaderRow.createCell(colIndex);
                    cell.setCellValue(new HSSFRichTextString(curEntityAttributeParamVO.getName()));
                    cell.setCellStyle(paramCellStyle);
                    // set column width to 25 character
                    sheet.setColumnWidth(colIndex, 20 * 256);
                    colIndex++;
                }

                colIndexOfEntityAttributeMap.put(curEntityAttributeVO.getId(), colIndexOfEntityAttributeParamMap);

                if (curEntityAttributeVO.getEntityAttributeParamList() != null && !curEntityAttributeVO.getEntityAttributeParamList().isEmpty()) {
                    if (curEntityAttributeVO.getEntityAttributeParamList().size() > 1) {
                        sheet.addMergedRegion(new CellRangeAddress(0, 0, curEntityAttributeColIndex, curEntityAttributeColIndex + curEntityAttributeVO.getEntityAttributeParamList().size() - 1));
                    }
                    continue;
                }

                colIndex++;
            }

            rowIndex++;

            // ------------------ getting all attributes values and set it for value objects
            Map<Long, Map<Long, EntityAttributeVO>> entityAttributeMap = getServiceLocatorBean().getCoreService().getEntityAttributeReport(entityName, entityAttributeGroup);

            for (BaseValueObject valueObject : valueObjectArrayList) {
                if (entityAttributeMap.containsKey(valueObject.getId())) {
                    valueObject.setEntityAttributeMap(entityAttributeMap.get(valueObject.getId()));
                }
            }

            int curRowIndex = rowIndex; // save current row index for first value of attribute param

            // ------------------ tracing column by column per entityAttributeParam and fill values
            for (EntityAttributeVO entityAttributeVO : entityAttributeVOList) {
                for (EntityAttributeParamVO entityAttributeParamVO : entityAttributeVO.getEntityAttributeParamList()) {
                    rowIndex = curRowIndex; // start filling each column
                    for (BaseValueObject curValueObject : valueObjectArrayList) {
                        row = sheet.getRow(rowIndex);
                        if (row == null) {
                            row = sheet.createRow(rowIndex);
                            row.setHeight((short) (18 * 20)); // set row height to 18 point
                        }
                        if (curValueObject.getEntityAttributeMap() != null && curValueObject.getEntityAttributeMap().containsKey(entityAttributeVO.getId())) {
                            EntityAttributeVO attributeVO = curValueObject.getEntityAttributeMap().get(entityAttributeVO.getId());
                            if (attributeVO.getEntityAttributeParamMap() != null && attributeVO.getEntityAttributeParamMap().containsKey(entityAttributeParamVO.getId())) {
                                EntityAttributeParamVO attributeParamVO = attributeVO.getEntityAttributeParamMap().get(entityAttributeParamVO.getId());

                                colIndex = colIndexOfEntityAttributeMap.get(entityAttributeVO.getId()).get(entityAttributeParamVO.getId());

                                cell = row.createCell(colIndex);
                                if (attributeParamVO.getValue() != null) {
                                    cell.setCellValue(new HSSFRichTextString(attributeParamVO.getValue().toString()));
                                }
                                cell.setCellStyle(valueCellStyle);

                                // set column width to 30 character
                                sheet.setColumnWidth(colIndex, 20 * 256);
                                colIndex++;
                            }
                        }
                        rowIndex++;
                    }
                }
            }

            rowIndex++;

            // demonstrate adding/naming and deleting a sheet
            // ---- create a sheet, set its title then delete it
            // sheet = workbook.createSheet();
            // workbook.setSheetName(1, "DeletedSheet");
            // workbook.removeSheetAt(1);
            //end deleted sheet

        } catch (CoreServiceException e) {
            logger.error(e.getMessage(), e);
        }

        return workbook;
    }
    */

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public List<MiddleEastBankSwitchTransaction> getSwitchTransactionList() {
        return switchTransactionList;
    }

    public void setSwitchTransactionList(List<MiddleEastBankSwitchTransaction> switchTransactionList) {
        this.switchTransactionList = switchTransactionList;
    }

    public List<TerminalTransaction> getTerminalTransactionList() {
        return terminalTransactionList;
    }

    public void setTerminalTransactionList(List<TerminalTransaction> terminalTransactionList) {
        this.terminalTransactionList = terminalTransactionList;
    }

    public List<String> getCardNumbers() {
        return cardNumbers;
    }

    public void setCardNumbers(List<String> cardNumbers) {
        this.cardNumbers = cardNumbers;
    }

    public Date getSwitchTransactionDateFrom() {
        return switchTransactionDateFrom;
    }

    public void setSwitchTransactionDateFrom(Date switchTransactionDateFrom) {
        this.switchTransactionDateFrom = switchTransactionDateFrom;
    }

    public Date getSwitchTransactionDateTo() {
        return switchTransactionDateTo;
    }

    public void setSwitchTransactionDateTo(Date switchTransactionDateTo) {
        this.switchTransactionDateTo = switchTransactionDateTo;
    }

    public String getSelectedCardNumber() {
        return selectedCardNumber;
    }

    public void setSelectedCardNumber(String selectedCardNumber) {
        this.selectedCardNumber = selectedCardNumber;
    }
}
