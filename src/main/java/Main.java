import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.imageio.ImageIO;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();
            CloseableHttpClient client = HttpClientBuilder.create()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setConnectTimeout(5000)
                            .setSocketTimeout(30000)
                            .setRedirectsEnabled(false)
                            .build())
                    .build();
            HttpGet infoGet = new HttpGet("https://api.nasa.gov/planetary/apod?api_key=TMFgGOItXmWfGaLLSOxBlHs5XHFgi9JYkYAKe91U");
            CloseableHttpResponse response1 = client.execute(infoGet);
            NasaData info = objectMapper.readValue(response1.getEntity().getContent(),NasaData.class);
            String imageUrl = info.getUrl();
            String [] arrImageUrl = imageUrl.split("/");
            File file = new File(arrImageUrl[arrImageUrl.length-1]);
            HttpGet imageGet = new HttpGet(imageUrl);
            CloseableHttpResponse response2 = client.execute(imageGet);
            ImageIO.write(ImageIO.read(response2.getEntity().getContent()),"jpg",file);

    }
}