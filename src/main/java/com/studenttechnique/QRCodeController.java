package com.studenttechnique;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QRCodeController {
	@PostMapping("/qrcode")
    public ResponseEntity<ByteArrayResource> generateQRCode(@RequestBody String data) {
        // Create the QR code image
        byte[] qrCode = QRCode.from(data).to(ImageType.PNG).withSize(250, 250).stream().toByteArray();
        // Prepare the response entity
        ByteArrayResource resource = new ByteArrayResource(qrCode);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"QRCode-" + data + ".png\"");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE);
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
        return ResponseEntity.ok()
        		.headers(headers)
        		.contentLength(qrCode.length)
        		.body(resource);
    }
}
