package com.kais.controllers.utils.qrcode;

import com.kais.app.AppMain;
import com.kais.core.OcrClient;

import java.io.File;

/**
 * *.png, *.jpg格式图片二维码识别内容
 * @author QiuKai
 */
public class QrCodeParse implements FileParse<String> {

    private File file;

    @Override
    public String parse() {

        OcrClient ocrClient = AppMain.getBean(OcrClient.class);
        return ocrClient.qrCode(new OcrClient.BasicOcrBuilder().setFilePath(file.getAbsolutePath()));
    }

    @Override
    public boolean filter(File file) {

        this.file = file;
        return true;
    }
}
