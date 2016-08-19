package com.snap2buy.webservice.util;

import com.snap2buy.webservice.model.ImageStore;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by sachin on 2/4/16.
 */
public class ShellUtil {
    private static Logger LOGGER = Logger.getLogger("s2b");

    public static String executeCommand(String imageFilePath, String category, String uuid, String retailer, String store, String userId, String projectTypeId, String hostId) {
        String response = "";
        Boolean waitForResponse = true;
        String command = "invoke_image_analysis.sh";
        File f = new File("/root");
        LOGGER.info("---------------ShellUtil imageFilePath=" + imageFilePath + ", category=" + category + ", uuid=" + uuid + ", retailer=" + retailer + ", store=" + store + "userId= "+userId+"projectTypeId= "+projectTypeId+"hostId="+hostId+"----------------\n");

        ProcessBuilder pb = new ProcessBuilder("/bin/bash", command);
        pb.environment().put("Image_File_Path", imageFilePath);
        pb.environment().put("Category", category);
        pb.environment().put("Uuid", uuid);
        pb.environment().put("Retailer_Code", retailer);
        pb.environment().put("Store_Id", store);
        pb.environment().put("User_Id", userId);
        pb.environment().put("Project_type_id", projectTypeId);
        pb.environment().put("hostId", hostId);
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
        LOGGER.info("---------------ShellUtil response=" + response);
        return response;
    }

    public static String readResult(String imageUUID) {
        String response = "";
        Boolean waitForResponse = true;
        String command = "read_result.sh";
        File f = new File("/root");
        LOGGER.info("---------------ShellUtil imageUUID=" + imageUUID+"----------------\n");

        ProcessBuilder pb = new ProcessBuilder("/bin/bash", command);

        pb.environment().put("imageUUID", imageUUID);
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
        LOGGER.info("---------------ShellUtil response=" + response);
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
        } else {
            return "null";
        }
    }

    public static void createThumbnail(ImageStore imageStore) {
        String response = "";
        Boolean waitForResponse = true;
        String command = "createThumbnail.sh";
        File f = new File("/root");
        LOGGER.info("---------------ShellUtil filepath=" + imageStore.getImageFilePath()+" thumbnailPath="+imageStore.getThumbnailPath()+"imageRotation="+imageStore.getImageRotation()+"----------------\n");

        ProcessBuilder pb = new ProcessBuilder("/bin/bash", command);

        pb.environment().put("filepath", imageStore.getImageFilePath());
        pb.environment().put("thumbnailPath", imageStore.getThumbnailPath());
        pb.environment().put("imageRotation", imageStore.getImageRotation());

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
        LOGGER.info("---------------ShellUtil response=" + response);

        String values[]=response.split(",");

        imageStore.setOrigWidth(values[0].replace("\r","").replace("\n","").trim());
        imageStore.setOrigHeight(values[1].replace("\r","").replace("\n","").trim());
        imageStore.setNewWidth(values[2].replace("\r","").replace("\n","").trim());
        imageStore.setNewHeight(values[3].replace("\r","").replace("\n","").trim());
    }
}
