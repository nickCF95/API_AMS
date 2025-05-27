package com.TestAPI_AMS.API_AMS.web.controllers;

import com.TestAPI_AMS.API_AMS.domain.service.AprovisionamientoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/aprovisionamiento")
public class AprovisionamientoController {

    @Autowired
    private AprovisionamientoService aprovisionamientoService;

    @PostMapping(path = "/uploadExcelFile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
        public ResponseEntity<Object> handlePost(@RequestParam(name = "file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName.substring(fileName.length() - 5, fileName.length()).equals(".xlsx")) {
            try (InputStream excelIs = file.getInputStream()) {
                // create the Workbook using the InputStream returned by
                // MultipartFile#getInputStream()
                Workbook wb = WorkbookFactory.create(excelIs);
                aprovisionamientoService.aprovisionarMasivo(wb);
                // get the first sheet of the Workbook
            } catch(IOException e) {
                log.info("Failed to process");
            }
        } else {
            log.info("The file should be a .xlsx");
        }

        return ResponseEntity.ok(""); // your return
    }
}
