package project.practice.document.security.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.practice.document.models.Data;
import project.practice.document.models.DataCA;
import project.practice.document.models.Document;
import project.practice.document.payload.response.MessageResponse;
import project.practice.document.repository.DocRepository;

import java.io.*;
import java.util.List;

@Service
public class DocService {
    @Autowired
    private DocRepository docRepository;

    // List Document
    public List<Document> getDocuments() {
        return docRepository.findAll();
    }

    public ResponseEntity<?> saveDocument(Document doc) {
        if (docRepository.existsByTitle(doc.getTitle())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Document title is already taken!"));
        }else{
            docRepository.save(doc);
            return ResponseEntity.ok(new MessageResponse("You have created a new document!"));
        }

    }


    // List Document By User Id
    public List<Document> getDocsByUserId(Long userId) {
        return docRepository.findAllByUserId(userId);
    }

    public List<Document> getDocsByType(Long userId, String type) {
        return docRepository.findAllByUserIdAndType(userId, type);
    }

    // List document by type
    public List<Document> getDocumentsByType(String type) {
        return docRepository.findAllByType(type);
    }

    // Get Document By Document id
    public Document getDocById(Long id) {
        return docRepository.findById(id).orElse(null);
    }

    // Delete Document by id
    public String deleteDocument(Long id) {
        docRepository.deleteById(id);
        return "Document removed !! " + id;
    }

    public ResponseEntity<?> updateDocument(Document doc) {
        Document existingDoc = docRepository.findById(doc.getId()).orElse(null);
        if(existingDoc.getTitle().equals(doc.getTitle())){
            existingDoc.setTitle(doc.getTitle());
            existingDoc.setUpdated(doc.getUpdated());
            existingDoc.setDocData(doc.getDocData());
            docRepository.save(existingDoc);
            return ResponseEntity.ok(new MessageResponse("Document has been updated successfully!"));
        }
        else if(!existingDoc.getTitle().equals(doc.getTitle()) && docRepository.existsByTitle(doc.getTitle())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Document title is already taken!"));
        }else{
            existingDoc.setTitle(doc.getTitle());
            existingDoc.setUpdated(doc.getUpdated());
            existingDoc.setDocData(doc.getDocData());
            docRepository.save(existingDoc);
            return ResponseEntity.ok(new MessageResponse("Document has been updated successfully!"));
        }

    }

    // Update Document Status
    public Document updateDocStatus(Document doc) {
        Document existingDoc = docRepository.findById(doc.getId()).orElse(null);
        existingDoc.setStatus(doc.getStatus());
        return docRepository.save(existingDoc);
    }

    public ByteArrayInputStream exportFile(Data data) {

        try (XWPFDocument doc = new XWPFDocument()) {

            // create a paragraph
            XWPFParagraph p1 = doc.createParagraph();
            p1.setAlignment(ParagraphAlignment.CENTER);

            // set font
            XWPFRun r1 = p1.createRun();
            r1.setBold(true);
            r1.setFontSize(18);
            r1.setText("CAUSAL ANALYSIS & RESOLUTION (CAR) REPORT");

            XWPFParagraph p2 = doc.createParagraph();
            p2.setAlignment(ParagraphAlignment.CENTER);
            //Set color for second paragraph
            XWPFRun r2 = p2.createRun();
            r2.setBold(true);
            r2.setFontSize(13);
            r2.setText("Type of CAR: ");
            r2 = p2.createRun();
            r2.setColor("0000ff");
            r2.setBold(true);
            r2.setText(data.getCarType());
            r2.setFontSize(13);


            XWPFParagraph ptitle = doc.createParagraph();
            XWPFRun rtitle = ptitle.createRun();
            rtitle.setBold(true);
            rtitle.setFontSize(12);
            rtitle.setText("Document title : ");
            rtitle = ptitle.createRun();
            rtitle.setColor("ff0000");
            rtitle.setText(data.getTitle());
            rtitle.setFontSize(12);

            XWPFParagraph p3 = doc.createParagraph();
            XWPFRun r3 = p3.createRun();
            r3.setBold(true);
            r3.setFontSize(12);
            r3.setText("Performed by : ");
            r3 = p3.createRun();
            r3.setColor("ff0000");
            r3.setText(data.getPerformedBy());
            r3.setFontSize(12);

            XWPFParagraph p4 = doc.createParagraph();
            XWPFRun r4 = p4.createRun();
            r4.setBold(true);
            r4.setFontSize(12);
            r4.setText("Discussed with  ");
            r4 = p4.createRun();
            r4.setColor("ff0000");
            r4.setBold(true);
            r4.setText(data.getDiscussedWith());
            r4.setFontSize(12);

            XWPFParagraph p5 = doc.createParagraph();
            XWPFRun r5 = p5.createRun();
            r5.setText("Problem Description ");
            r5.setBold(true);
            r5.setFontSize(12);

            XWPFParagraph p6 = doc.createParagraph();
            XWPFRun r6 = p6.createRun();
            r6.setText(data.getProblemDescription());
            r6.setColor("ff0000");
            r6.setFontSize(12);

            XWPFParagraph p7 = doc.createParagraph();
            XWPFRun r7 = p7.createRun();
            r7.setText("Impact");
            r7.setBold(true);
            r7.setFontSize(12);

            XWPFParagraph p8 = doc.createParagraph();
            XWPFRun r8 = p8.createRun();
            r8.setText(data.getImpact());
            r8.setColor("ff0000");
            r8.setFontSize(12);

            XWPFParagraph p9 = doc.createParagraph();
            XWPFRun r9 = p9.createRun();
            r9.setText("Root Cause");
            r9.setBold(true);
            r9.setFontSize(12);

            XWPFParagraph p10 = doc.createParagraph();
            XWPFRun r10 = p10.createRun();
            r10.setText(data.getRootCause());
            r10.setColor("ff0000");
            r10.setFontSize(12);

            XWPFParagraph p11 = doc.createParagraph();
            XWPFRun r11 = p11.createRun();
            r11.setText("Corrective Actions");
            r11.setBold(true);
            r11.setFontSize(12);

            XWPFParagraph p12 = doc.createParagraph();
            XWPFRun r12 = p12.createRun();
            r12.setText(data.getCorrectiveActions());
            r12.setColor("ff0000");
            r12.setFontSize(12);

            XWPFParagraph p13 = doc.createParagraph();
            XWPFRun r13 = p13.createRun();
            r13.setText("5 WHYs Worksheet");
            r13.setBold(true);
            r13.setFontSize(12);

            XWPFParagraph p14 = doc.createParagraph();
            XWPFRun r14 = p14.createRun();
            r14.setText("Define the Problem:");
            r14.setBold(true);
            r14.setFontSize(12);

            XWPFParagraph p15 = doc.createParagraph();
            XWPFRun r15 = p15.createRun();
            r15.setText(data.getDefineTheProblem());
            r15.setColor("ff0000");
            r15.setFontSize(12);

            XWPFParagraph p16 = doc.createParagraph();
            XWPFRun r16 = p16.createRun();
            r16.setText("Why is it happening? (Identify each as a concern, influence or control.)");
            r16.setBold(true);
            r16.setFontSize(12);

            if(data.getWhy()!= null){
                for (int i = 0; i < data.getWhy().length; i++) {
                    XWPFParagraph p17 = doc.createParagraph();
                    XWPFRun r17 = p17.createRun();
                    r17.setText("Why is that?");
                    r17.setBold(true);
                    r17.setFontSize(12);

                    XWPFParagraph p18 = doc.createParagraph();
                    XWPFRun r18 = p18.createRun();
                    r18.setText(data.getWhy()[i].getWhy());
                    r18.setColor("ff0000");
                    r18.setFontSize(12);
                }
            }


            // save it to .docx file
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            doc.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    // export file and create Report CA
    public ByteArrayInputStream exportFileCA(DataCA dataCA) {

        try (XWPFDocument doc = new XWPFDocument()) {

            // create a paragraph
            XWPFParagraph p1 = doc.createParagraph();
            p1.setAlignment(ParagraphAlignment.CENTER);

            // set font
            XWPFRun r1 = p1.createRun();
            r1.setBold(true);
            r1.setFontSize(18);
            r1.setText("Configuration Audit Report");


            XWPFTable table = doc.createTable();
            table.setCellMargins(200,200,200,200);

            //Creating first Row
            XWPFTableRow row1 = table.getRow(0);

            XWPFTableCell cell1 = row1.getCell(0);
            XWPFParagraph par1 = cell1.addParagraph();
            XWPFRun run1 = par1.createRun();
            run1.setText("No");
            run1.setBold(true);
            run1.setFontSize(12);

            XWPFTableCell cell2 = row1.addNewTableCell();
            XWPFParagraph par2 = cell2.addParagraph();
            XWPFRun run2 = par2.createRun();
            run2.setText("Activity");
            run2.setBold(true);
            run2.setFontSize(12);

            XWPFTableCell cell3 = row1.addNewTableCell();
            XWPFParagraph par3 = cell3.addParagraph();
            XWPFRun run3 = par3.createRun();
            run3.setText("Description");
            run3.setBold(true);
            run3.setFontSize(12);

            XWPFTableCell cell4 = row1.addNewTableCell();
            XWPFParagraph par4 = cell4.addParagraph();
            XWPFRun run4 = par4.createRun();
            run4.setText("Action item suggested");
            run4.setBold(true);
            run4.setFontSize(12);

            XWPFTableCell cell5 = row1.addNewTableCell();
            XWPFParagraph par5 = cell5.addParagraph();
            XWPFRun run5 = par5.createRun();
            run5.setText("Status( Y/N/NA)");
            run5.setBold(true);
            run5.setFontSize(12);


            //Creating second Row
            XWPFTableRow row2 = table.createRow();

            if(dataCA.getDataActivity()!= null) {
                for (int i = 0; i < dataCA.getDataActivity().length; i++) {
                    XWPFTableCell cell6 = row2.getCell(0);
                    XWPFParagraph par6 = cell6.addParagraph();
                    XWPFRun run6 = par6.createRun();
                    run6.setText(String.valueOf(i+1));
                    run6.setColor("ff0000");
                    run6.setFontSize(12);

                    XWPFTableCell cell7 = row2.getCell(1);
                    XWPFParagraph par7 = cell7.addParagraph();
                    XWPFRun run7 = par7.createRun();
                    run7.setText(dataCA.getDataActivity()[i].getActivity());
                    run7.setColor("ff0000");
                    run7.setFontSize(12);

                    XWPFTableCell cell8 = row2.getCell(2);
                    XWPFParagraph par8 = cell8.addParagraph();
                    XWPFRun run8 = par8.createRun();
                    run8.setText(dataCA.getDataActivity()[i].getDescription());
                    run8.setColor("ff0000");
                    run8.setFontSize(12);

                    XWPFTableCell cell9 = row2.getCell(3);
                    XWPFParagraph par9 = cell9.addParagraph();
                    XWPFRun run9 = par9.createRun();
                    run9.setText(dataCA.getDataActivity()[i].getActionItem());
                    run9.setColor("ff0000");
                    run9.setFontSize(12);

                    XWPFTableCell cell10 = row2.getCell(4);
                    XWPFParagraph par10 = cell10.addParagraph();
                    XWPFRun run10 = par10.createRun();
                    run10.setText(dataCA.getDataActivity()[i].getStatus());
                    run10.setColor("0000ff");
                    run10.setFontSize(12);
                }
            }

            XWPFParagraph p3 = doc.createParagraph();
            XWPFRun r3 = p3.createRun();
            r3.setText("");

            XWPFParagraph p4 = doc.createParagraph();
            XWPFRun r4 = p4.createRun();
            r4.setFontSize(12);
            r4.setText("Document Title: ");
            r4 = p4.createRun();
            r4.setColor("ff0000");
            r4.setText(dataCA.getTitle());
            r4.setFontSize(12);

            XWPFParagraph p5 = doc.createParagraph();
            XWPFRun r5 = p5.createRun();
            r5.setFontSize(12);
            r5.setText("Project Name: ");
            r5 = p5.createRun();
            r5.setColor("ff0000");
            r5.setText(dataCA.getProjectName());
            r5.setFontSize(12);

            XWPFParagraph p6 = doc.createParagraph();
            XWPFRun r6 = p6.createRun();
            r6.setFontSize(12);
            r6.setText("Project ID: ");
            r6 = p6.createRun();
            r6.setColor("ff0000");
            r6.setText(dataCA.getProjectId());
            r6.setFontSize(12);

            XWPFParagraph p7 = doc.createParagraph();
            XWPFRun r7 = p7.createRun();
            r7.setFontSize(12);
            r7.setText("Audit date: ");
            r7 = p7.createRun();
            r7.setColor("ff0000");
            r7.setText(dataCA.getAuditDate());
            r7.setFontSize(12);

            XWPFParagraph p8 = doc.createParagraph();
            XWPFRun r8 = p8.createRun();
            r8.setFontSize(12);
            r8.setText("Customer: ");
            r8 = p8.createRun();
            r8.setColor("ff0000");
            r8.setText(dataCA.getCustomer());
            r8.setFontSize(12);

            XWPFParagraph p9 = doc.createParagraph();
            XWPFRun r9 = p9.createRun();
            r9.setFontSize(12);
            r9.setText("Configurable Items: ");
            r9 = p9.createRun();
            r9.setColor("ff0000");
            r9.setText(dataCA.getItems());
            r9.setFontSize(12);

            XWPFParagraph p10 = doc.createParagraph();
            XWPFRun r10 = p10.createRun();
            r10.setFontSize(12);
            r10.setText("Audit Team: ");
            r10 = p10.createRun();
            r10.setColor("ff0000");
            r10.setText(dataCA.getAuditTeam());
            r10.setFontSize(12);


            XWPFParagraph p12 = doc.createParagraph();
            XWPFRun r12 = p12.createRun();
            r12.setText("");

            XWPFParagraph p13 = doc.createParagraph();
            XWPFRun r13 = p13.createRun();
            r13.setText("Remarks: ");
            r13.setBold(true);
            r13.setFontSize(12);

            XWPFParagraph p14 = doc.createParagraph();
            XWPFRun r14 = p14.createRun();
            r14.setText(dataCA.getRemarks());
            r14.setColor("ff0000");
            r14.setFontSize(12);

            XWPFParagraph p15 = doc.createParagraph();
            XWPFRun r15 = p15.createRun();
            r15.setFontSize(12);
            r15.setText("Date: ");
            r15 = p15.createRun();
            r15.setColor("ff0000");
            r15.setText(dataCA.getToday());
            r15.setFontSize(12);

            XWPFParagraph p16 = doc.createParagraph();
            XWPFRun r16 = p16.createRun();
            r16.setText("");

            // save it to .docx file
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            doc.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public ByteArrayInputStream exportFileById(Document dataDocument) {

        String fileName = "d:\\CNTT\\ThucTap\\Angular Final Project\\file\\" + dataDocument.getTitle() + ".docx";
        if(dataDocument.getType().equals("COM-CAR-TPL")){
            try (XWPFDocument doc = new XWPFDocument()) {
                ObjectMapper objectMapper = new ObjectMapper();
                Data docObject = objectMapper.readValue(dataDocument.getDocData(), Data.class);

                // create a paragraph
                XWPFParagraph p1 = doc.createParagraph();
                p1.setAlignment(ParagraphAlignment.CENTER);

                // set font
                XWPFRun r1 = p1.createRun();
                r1.setBold(true);
                r1.setFontSize(18);
                r1.setText("CAUSAL ANALYSIS & RESOLUTION (CAR) REPORT");

                XWPFParagraph p2 = doc.createParagraph();
                p2.setAlignment(ParagraphAlignment.CENTER);
                //Set color for second paragraph
                XWPFRun r2 = p2.createRun();
                r2.setBold(true);
                r2.setFontSize(13);
                r2.setText("Type of CAR: ");
                r2 = p2.createRun();
                r2.setColor("0000ff");
                r2.setBold(true);
                r2.setText(docObject.getCarType());
                r2.setFontSize(13);


                XWPFParagraph ptitle = doc.createParagraph();
                XWPFRun rtitle = ptitle.createRun();
                rtitle.setBold(true);
                rtitle.setFontSize(12);
                rtitle.setText("Document title : ");
                rtitle = ptitle.createRun();
                rtitle.setColor("ff0000");
                rtitle.setText(docObject.getTitle());
                rtitle.setFontSize(12);

                XWPFParagraph p3 = doc.createParagraph();
                XWPFRun r3 = p3.createRun();
                r3.setBold(true);
                r3.setFontSize(12);
                r3.setText("Performed by : ");
                r3 = p3.createRun();
                r3.setColor("ff0000");
                r3.setText(docObject.getPerformedBy());
                r3.setFontSize(12);

                XWPFParagraph p4 = doc.createParagraph();
                XWPFRun r4 = p4.createRun();
                r4.setBold(true);
                r4.setFontSize(12);
                r4.setText("Discussed with  ");
                r4 = p4.createRun();
                r4.setColor("ff0000");
                r4.setBold(true);
                r4.setText(docObject.getDiscussedWith());
                r4.setFontSize(12);

                XWPFParagraph p5 = doc.createParagraph();
                XWPFRun r5 = p5.createRun();
                r5.setText("Problem Description ");
                r5.setBold(true);
                r5.setFontSize(12);

                XWPFParagraph p6 = doc.createParagraph();
                XWPFRun r6 = p6.createRun();
                r6.setText(docObject.getProblemDescription());
                r6.setColor("ff0000");
                r6.setFontSize(12);

                XWPFParagraph p7 = doc.createParagraph();
                XWPFRun r7 = p7.createRun();
                r7.setText("Impact");
                r7.setBold(true);
                r7.setFontSize(12);

                XWPFParagraph p8 = doc.createParagraph();
                XWPFRun r8 = p8.createRun();
                r8.setText(docObject.getImpact());
                r8.setColor("ff0000");
                r8.setFontSize(12);

                XWPFParagraph p9 = doc.createParagraph();
                XWPFRun r9 = p9.createRun();
                r9.setText("Root Cause");
                r9.setBold(true);
                r9.setFontSize(12);

                XWPFParagraph p10 = doc.createParagraph();
                XWPFRun r10 = p10.createRun();
                r10.setText(docObject.getRootCause());
                r10.setColor("ff0000");
                r10.setFontSize(12);

                XWPFParagraph p11 = doc.createParagraph();
                XWPFRun r11 = p11.createRun();
                r11.setText("Corrective Actions");
                r11.setBold(true);
                r11.setFontSize(12);

                XWPFParagraph p12 = doc.createParagraph();
                XWPFRun r12 = p12.createRun();
                r12.setText(docObject.getCorrectiveActions());
                r12.setColor("ff0000");
                r12.setFontSize(12);

                XWPFParagraph p13 = doc.createParagraph();
                XWPFRun r13 = p13.createRun();
                r13.setText("5 WHYs Worksheet");
                r13.setBold(true);
                r13.setFontSize(12);

                XWPFParagraph p14 = doc.createParagraph();
                XWPFRun r14 = p14.createRun();
                r14.setText("Define the Problem:");
                r14.setBold(true);
                r14.setFontSize(12);

                XWPFParagraph p15 = doc.createParagraph();
                XWPFRun r15 = p15.createRun();
                r15.setText(docObject.getDefineTheProblem());
                r15.setColor("ff0000");
                r15.setFontSize(12);

                XWPFParagraph p16 = doc.createParagraph();
                XWPFRun r16 = p16.createRun();
                r16.setText("Why is it happening? (Identify each as a concern, influence or control.)");
                r16.setBold(true);
                r16.setFontSize(12);

                if(docObject.getWhy()!= null) {
                    for (int i = 0; i < docObject.getWhy().length; i++) {
                        XWPFParagraph p17 = doc.createParagraph();
                        XWPFRun r17 = p17.createRun();
                        r17.setText("Why is that?");
                        r17.setBold(true);
                        r17.setFontSize(12);

                        XWPFParagraph p18 = doc.createParagraph();
                        XWPFRun r18 = p18.createRun();
                        r18.setText(docObject.getWhy()[i].getWhy());
                        r18.setColor("ff0000");
                        r18.setFontSize(12);

                    }
                }
                // save it to .docx file
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                doc.write(outputStream);
                return new ByteArrayInputStream(outputStream.toByteArray());

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try (XWPFDocument doc = new XWPFDocument()) {
                ObjectMapper objectMapper = new ObjectMapper();
                DataCA docObject = objectMapper.readValue(dataDocument.getDocData(), DataCA.class);

                // create a paragraph
                XWPFParagraph p1 = doc.createParagraph();
                p1.setAlignment(ParagraphAlignment.CENTER);

                // set font
                XWPFRun r1 = p1.createRun();
                r1.setBold(true);
                r1.setFontSize(18);
                r1.setText("Configuration Audit Report");


                XWPFTable table = doc.createTable();
                table.setCellMargins(200,200,200,200);

                //Creating first Row
                XWPFTableRow row1 = table.getRow(0);

                XWPFTableCell cell1 = row1.getCell(0);
                XWPFParagraph par1 = cell1.addParagraph();
                XWPFRun run1 = par1.createRun();
                run1.setText("No");
                run1.setBold(true);
                run1.setFontSize(12);

                XWPFTableCell cell2 = row1.addNewTableCell();
                XWPFParagraph par2 = cell2.addParagraph();
                XWPFRun run2 = par2.createRun();
                run2.setText("Activity");
                run2.setBold(true);
                run2.setFontSize(12);

                XWPFTableCell cell3 = row1.addNewTableCell();
                XWPFParagraph par3 = cell3.addParagraph();
                XWPFRun run3 = par3.createRun();
                run3.setText("Description");
                run3.setBold(true);
                run3.setFontSize(12);

                XWPFTableCell cell4 = row1.addNewTableCell();
                XWPFParagraph par4 = cell4.addParagraph();
                XWPFRun run4 = par4.createRun();
                run4.setText("Action item suggested");
                run4.setBold(true);
                run4.setFontSize(12);

                XWPFTableCell cell5 = row1.addNewTableCell();
                XWPFParagraph par5 = cell5.addParagraph();
                XWPFRun run5 = par5.createRun();
                run5.setText("Status( Y/N/NA)");
                run5.setBold(true);
                run5.setFontSize(12);

                //Creating second Row
                XWPFTableRow row2 = table.createRow();

                if(docObject.getDataActivity()!= null) {
                    for (int i = 0; i < docObject.getDataActivity().length; i++) {
                        XWPFTableCell cell6 = row2.getCell(0);
                        XWPFParagraph par6 = cell6.addParagraph();
                        XWPFRun run6 = par6.createRun();
                        run6.setText(String.valueOf(i+1));
                        run6.setColor("ff0000");
                        run6.setFontSize(12);

                        XWPFTableCell cell7 = row2.getCell(1);
                        XWPFParagraph par7 = cell7.addParagraph();
                        XWPFRun run7 = par7.createRun();
                        run7.setText(docObject.getDataActivity()[i].getActivity());
                        run7.setColor("ff0000");
                        run7.setFontSize(12);

                        XWPFTableCell cell8 = row2.getCell(2);
                        XWPFParagraph par8 = cell8.addParagraph();
                        XWPFRun run8 = par8.createRun();
                        run8.setText(docObject.getDataActivity()[i].getDescription());
                        run8.setColor("ff0000");
                        run8.setFontSize(12);

                        XWPFTableCell cell9 = row2.getCell(3);
                        XWPFParagraph par9 = cell9.addParagraph();
                        XWPFRun run9 = par9.createRun();
                        run9.setText(docObject.getDataActivity()[i].getActionItem());
                        run9.setColor("ff0000");
                        run9.setFontSize(12);

                        XWPFTableCell cell10 = row2.getCell(4);
                        XWPFParagraph par10 = cell10.addParagraph();
                        XWPFRun run10 = par10.createRun();
                        run10.setText(docObject.getDataActivity()[i].getStatus());
                        run10.setColor("0000ff");
                        run10.setFontSize(12);

                    }
                }

                XWPFParagraph p3 = doc.createParagraph();
                XWPFRun r3 = p3.createRun();
                r3.setText("");

                XWPFParagraph p4 = doc.createParagraph();
                XWPFRun r4 = p4.createRun();
                r4.setFontSize(12);
                r4.setText("Document Title: ");
                r4 = p4.createRun();
                r4.setColor("ff0000");
                r4.setText(docObject.getTitle());
                r4.setFontSize(12);

                XWPFParagraph p5 = doc.createParagraph();
                XWPFRun r5 = p5.createRun();
                r5.setFontSize(12);
                r5.setText("Project Name: ");
                r5 = p5.createRun();
                r5.setColor("ff0000");
                r5.setText(docObject.getProjectName());
                r5.setFontSize(12);

                XWPFParagraph p6 = doc.createParagraph();
                XWPFRun r6 = p6.createRun();
                r6.setFontSize(12);
                r6.setText("Project ID: ");
                r6 = p6.createRun();
                r6.setColor("ff0000");
                r6.setText(docObject.getProjectId());
                r6.setFontSize(12);

                XWPFParagraph p7 = doc.createParagraph();
                XWPFRun r7 = p7.createRun();
                r7.setFontSize(12);
                r7.setText("Audit date: ");
                r7 = p7.createRun();
                r7.setColor("ff0000");
                r7.setText(docObject.getAuditDate());
                r7.setFontSize(12);

                XWPFParagraph p8 = doc.createParagraph();
                XWPFRun r8 = p8.createRun();
                r8.setFontSize(12);
                r8.setText("Customer: ");
                r8 = p8.createRun();
                r8.setColor("ff0000");
                r8.setText(docObject.getCustomer());
                r8.setFontSize(12);

                XWPFParagraph p9 = doc.createParagraph();
                XWPFRun r9 = p9.createRun();
                r9.setFontSize(12);
                r9.setText("Configurable Items: ");
                r9 = p9.createRun();
                r9.setColor("ff0000");
                r9.setText(docObject.getItems());
                r9.setFontSize(12);

                XWPFParagraph p10 = doc.createParagraph();
                XWPFRun r10 = p10.createRun();
                r10.setFontSize(12);
                r10.setText("Audit Team: ");
                r10 = p10.createRun();
                r10.setColor("ff0000");
                r10.setText(docObject.getAuditTeam());
                r10.setFontSize(12);


                XWPFParagraph p12 = doc.createParagraph();
                XWPFRun r12 = p12.createRun();
                r12.setText("");

                XWPFParagraph p13 = doc.createParagraph();
                XWPFRun r13 = p13.createRun();
                r13.setText("Remarks: ");
                r13.setBold(true);
                r13.setFontSize(12);

                XWPFParagraph p14 = doc.createParagraph();
                XWPFRun r14 = p14.createRun();
                r14.setText(docObject.getRemarks());
                r14.setColor("ff0000");
                r14.setFontSize(12);

                XWPFParagraph p15 = doc.createParagraph();
                XWPFRun r15 = p15.createRun();
                r15.setFontSize(12);
                r15.setText("Date: ");
                r15 = p15.createRun();
                r15.setColor("ff0000");
                r15.setText(docObject.getToday());
                r15.setFontSize(12);

                XWPFParagraph p16 = doc.createParagraph();
                XWPFRun r16 = p16.createRun();
                r16.setText("");

                // save it to .docx file
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                doc.write(outputStream);
                return new ByteArrayInputStream(outputStream.toByteArray());

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }



}
