package com.server.priend.stt.controller;

import com.server.priend.stt.domain.SttService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("stt")
public class SttController {
    private SttService sttService;
    public SttController(SttauService sttService){
        this.sttService = sttService;
    }

    @PostMapping(value = "/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleAudioMessage(@RequestParam("audioFile") MultipartFile audioFile) throws IOException {

        String transcribe = sttService.transcribe(audioFile);

        return ResponseEntity.ok().body(transcribe);
    }
}
