package com.snap2pay.webservice.util;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created by sachin on 2/4/16.
 */
public class ShellUtil {
    private static Logger LOGGER = Logger.getLogger("s2p");

    public static String executeCommand(String image,String category,String uuid,String retailer,String store) {
        String response = "";
        Boolean waitForResponse=true;
        String command ="test.sh";
        File f = new File("/home/ubuntu/caffe");

        LOGGER.info("exist" + f.isDirectory());

        ProcessBuilder pb = new ProcessBuilder("/bin/bash",command);

        pb.environment().put("image",image );
        pb.environment().put("category",category );
        pb.environment().put("uuid",uuid );
        pb.environment().put("retailer",retailer );
        pb.environment().put("store",store );
        pb.directory(f);
        pb.redirectErrorStream(true);

        LOGGER.info("Linux command: " + command);

        try {
            Process shell = pb.start();
            if (waitForResponse) {
                InputStream shellIn = shell.getInputStream();
                int shellExitStatus = shell.waitFor();
                LOGGER.info("Exit status" + shellExitStatus);
                response = convertStreamToStr(shellIn);
                shellIn.close();
            }
        } catch (IOException e) {
            LOGGER.info("Error occured while executing Linux command. Error Description: "
                    + e.getMessage());
        } catch (InterruptedException e) {
            LOGGER.info("Error occured while executing Linux command. Error Description: "
                    + e.getMessage());
        }
        return response;
    }
    public static String convertStreamToStr(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is,
                        "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        }
        else {
            return "null";
        }
    }
}
