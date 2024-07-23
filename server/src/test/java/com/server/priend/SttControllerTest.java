package com.server.priend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SttControllerTest {

    private final Logger logger = LoggerFactory.getLogger(SttControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("STT CONTROLLER : /stt/audio")
    public void testHandleAudioMessage() throws Exception {
        //given
        // Create a mock audio file
        final String fileName = "test-audio";
        final String fileType = "flac";
        final String filePath = "src/test/resources/audio/" + fileName + "." + fileType;
        var audioFileInputStream = new FileInputStream(filePath);

        MockMultipartFile audioFile = new MockMultipartFile(
                "audioFile",
                fileName + "." + fileType,
                fileType,
                audioFileInputStream);

        // Set userSeq and slideIdx values
        Long userSeq = 1234L;
        Integer slideIdx = 5;

        //when
        //then
        // Call the controller endpoint
        MvcResult result = mockMvc.perform(multipart("/stt/audio")
                        .file(audioFile))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the response
        String responseBody = result.getResponse().getContentAsString();
        logger.info("Response Body: {}", responseBody);
        assertNotNull(responseBody, "Response body should not be null");
    }

}
