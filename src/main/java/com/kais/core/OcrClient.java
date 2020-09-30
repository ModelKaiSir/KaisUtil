package com.kais.core;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * 百度OCR文字识别
 *
 * @author QiuKai
 */
public class OcrClient {

    //设置APPID/AK/SK
    public static final String APP_ID = "22695988";
    public static final String API_KEY = "b2DF5Hu2KoqVVstZPAFuaT9Z";
    public static final String SECRET_KEY = "qUpexeiGEvs0G4OVG7SOlq91gkPEXq2u";

    private AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

    @PostConstruct
    public void start() {

        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }

    public String qrCode(BasicOcrBuilder builder) {

        // 调用接口
        JSONObject res = client.qrcode(builder.filePath, builder.parameters);

        try{

            return String.valueOf(res.getJSONArray("codes_result").getJSONObject(0).getJSONArray("text").get(0));
        }catch (Exception e){

            e.printStackTrace();
            return "没有解析成功。";
        }
    }

    public static class BasicOcrBuilder{

        private String filePath;

        private HashMap<String, String> parameters = new HashMap<>();

        public BasicOcrBuilder setFilePath(String filePath) {
            this.filePath = filePath;
            return this;
        }
    }
}
