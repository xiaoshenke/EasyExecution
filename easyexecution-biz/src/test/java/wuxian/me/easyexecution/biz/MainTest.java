package wuxian.me.easyexecution.biz;

import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 5/12/2017.
 */
public class MainTest {

    @Test
    public void testCrawler() {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            public void run() {

                HttpURLConnection httpURLConnection = null;
                String url = "https://www.baidu.com";

                try {
                    httpURLConnection = (HttpURLConnection) (new URL(url)).openConnection();
                    httpURLConnection.setRequestMethod("GET");

                    int status = httpURLConnection.getResponseCode();
                    byte[] responseBytes = IOUtils.toByteArray(httpURLConnection.getInputStream());

                    System.out.println("status code: " + status);
                } catch (Exception e) {

                    System.out.println("exception: " + e.getMessage());

                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }

            }
        });

        while (true) {

        }

    }

}