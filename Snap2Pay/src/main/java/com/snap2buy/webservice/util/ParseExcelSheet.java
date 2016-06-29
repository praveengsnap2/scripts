package com.snap2buy.webservice.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.snap2buy.webservice.model.InputObject;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sachin on 6/28/16.
 */
public class ParseExcelSheet {


    private static Logger LOGGER = Logger.getLogger("s2b");


    public static List<InputObject> parseCsv(InputObject inputObject1) throws IOException {
        List<LinkedHashMap<String, String>> rowSet = readCsvConvertMap(inputObject1.getCsvFilePath());
        List<InputObject> inputObjectList = new ArrayList<InputObject>();

        for(LinkedHashMap<String, String> row :rowSet) {
            if (!row.get("Job Id").isEmpty()) //eleminate empty rows
            {
                InputObject inputObject = new InputObject();
                UUID uniqueKey = UUID.randomUUID();
                String filenamePath = "/usr/share/s2pImages/" + inputObject1.getVisitDate() + "/" + uniqueKey.toString() + ".jpg";
                String thumbnailPath  = "/usr/share/s2pImages/" + inputObject.getVisitDate() + "/" + uniqueKey.toString() + "-thm.jpg";

                inputObject.setImageUUID(uniqueKey.toString().trim());
                inputObject.setImageFilePath(filenamePath);
                inputObject.setThumbnailPath(thumbnailPath);
                inputObject.setVisitDate(inputObject1.getVisitDate());
//                inputObject.setHostId("1");
//                inputObject.setCategoryId(categoryId.trim());
//                inputObject.setTimeStamp(timeStamp.trim());
//                inputObject.setUserId(userId.trim());
//                inputObject.setSync(sync);
//                inputObject.setAgentId(agentId);
//                inputObject.setCustomerCode(customerCode);
//                inputObject.setCustomerProjectId(customerProjectId);
//                inputObject.setTaskId(taskId);

                if (inputObject1.getCustomerCode().equalsIgnoreCase("FAG")) {
                    ExtractFieldAgentFields(inputObject, row);
                    ExtractFieldAgentImageUrl(inputObject);

                } else if (inputObject1.getCustomerCode().equalsIgnoreCase("CMK")) {
                    ExtractCrossmarkFields(inputObject, row);
                  //  ExtractCrossmarkImageUrl(inputObject);

                }

                saveImage(inputObject);
                creteThumbnail(inputObject);

                inputObjectList.add(inputObject);
            }
        }
        return inputObjectList;
    }

    public static List<LinkedHashMap<String, String>> readCsvConvertMap(String CsvFilePath) throws IOException {
        BufferedReader br = null;
        String line = "";
        List<LinkedHashMap<String, String>> result = new ArrayList<LinkedHashMap<String, String>>();
           br = new BufferedReader(new FileReader(CsvFilePath));
            //assumption first line is headers
            String headers = br.readLine();
            String[] cols = headers.split(",");
            while ((line = br.readLine()) != null) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                String[] value = line.split(",");
                for (int i = 0; (( i < value.length)&&(i < cols.length)); i++) {
                    map.put(cols[i], value[i]);
                }
                result.add(map);
            }
        LOGGER.info("---------------no of lines parsed from file="+result.size()+"---------------\n");
            return result;
    }


    public static void ExtractFieldAgentFields(InputObject inputObject, LinkedHashMap<String, String> row){
        String AGENT_ID="Agent Id";
        String TASK_ID = "Objective Id";
        String LAT = "Latitude";
        String LONG = "Longitude";
        String DATE="Agent Date & Timestamp - Local Time";
        String IMAGE_URL="Q1: Take a CLEAR photo of the Bluetooth Jam Wireless Audio display.";

        LOGGER.info("---------------row="+row+"---------------\n");
        LOGGER.info("---------------AGENT_ID="+row.get(AGENT_ID)+"---------------\n");
        LOGGER.info("---------------TASK_ID="+row.get(TASK_ID)+"---------------\n");
        LOGGER.info("---------------LAT="+row.get(LAT)+"---------------\n");
        LOGGER.info("---------------LAT="+row.get(LAT)+"---------------\n");
        LOGGER.info("---------------LONG="+row.get(LONG)+"---------------\n");
        LOGGER.info("---------------DATE="+row.get(DATE)+"---------------\n");
        LOGGER.info("---------------IMAGE_URL="+row.get(IMAGE_URL)+"---------------\n");
        inputObject.setAgentId(row.get(AGENT_ID));
        inputObject.setTaskId(row.get(TASK_ID));
        inputObject.setLatitude(row.get(LAT));
        inputObject.setLongitude(row.get(LONG));
        inputObject.setVisitDate(row.get(DATE));
        inputObject.setWebPageLink(row.get(IMAGE_URL).replace("=HYPERLINK(\"","").replace("\"\"",""));

    }


    public static void ExtractCrossmarkFields(InputObject inputObject, LinkedHashMap<String, String> row){
//        String AGENT_ID="Agent Id";
//        String TASK_ID = "Objective Id";
//        String LAT = "Latitude";
//        String LONG = "Longitude";
//        String DATE="Agent Date & Timestamp - Local Time";
//        String IMAGE_URL="Q1: Take a CLEAR photo of the Bluetooth Jam Wireless Audio display.";
//
//        inputObject.setAgentId(row.get(AGENT_ID));
//        inputObject.setTaskId(TASK_ID);
//        inputObject.setLatitude(LAT);
//        inputObject.setLongitude(LONG);
//        inputObject.setVisitDate(DATE);
//        inputObject.setWebPageLink(IMAGE_URL);
//
    }

    public static void ExtractFieldAgentImageUrl(InputObject inputObject) throws IOException {
        String webPageLink = inputObject.getWebPageLink();
        String mediaUrl =null;
        LOGGER.info("---------------webPageLink= "+webPageLink+"---------------\n");
        Document docNew =Jsoup.connect(webPageLink).timeout(10000).get();

        Elements scripts = docNew.getElementsByTag("script");
        for (Element script : scripts) {
            Pattern p = Pattern.compile("(.*?) media = (.+?);"); // Regex for the value of the key
            Matcher m = p.matcher(script.html()); // you have to use html here and NOT text! Text will drop the 'key' part
            while( m.find() ) {
                String jsonString = m.group(2);
                //System.out.println(jsonString);
                JsonElement obj = new JsonParser().parse(jsonString).getAsJsonArray().get(0);
                mediaUrl = obj.getAsJsonObject().get("mediaUrl").getAsString();
                System.out.println(mediaUrl);
            }
        }
        inputObject.setDownloadImageUrl(mediaUrl);
    }

    public static void ExtractCrossmarkImageUrl(InputObject inputObject) throws IOException {
        //
    }

        public static void saveImage(InputObject inputObject) throws IOException {
        URL url = new URL(inputObject.getDownloadImageUrl());
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(inputObject.getImageFilePath());

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    public static void creteThumbnail(InputObject inputObject){
        String csv=ShellUtil.createThumbnail(inputObject.getImageFilePath(),inputObject.getThumbnailPath());
        String values[]=csv.split(",");

        inputObject.setOrigWidth(values[0].replace("\r","").replace("\n","").trim());
        inputObject.setOrigHeight(values[1].replace("\r","").replace("\n","").trim());
        inputObject.setNewWidth(values[2].replace("\r","").replace("\n","").trim());
        inputObject.setNewHeight(values[3].replace("\r","").replace("\n","").trim());
    }

    public static void main (String args[]) throws IOException {
        InputObject inputObject = new InputObject();
        inputObject.setCustomerCode("FAG");
        inputObject.setImageFilePath("/Users/sachin/Desktop/testImage.jpg");
        inputObject.setCsvFilePath("/Users/sachin/Desktop/FieldAgent.csv");
        inputObject.setThumbnailPath("/Users/sachin/Desktop/testImage-thm.jpg");

        parseCsv(inputObject);

    }
//        String csvImageFetchUrl="https://my.fieldagent.net/client/lightbox/?public_id=4aec3f57-19dc-5450-b1b4-c433cc442fdb&mt=7&g=6994190&q=2245379";
//
//        Document docNew =Jsoup.connect(csvImageFetchUrl).timeout(10000).get();
//        Elements scriptElements = docNew.getElementsByTag("script");
//
//
//        for (Element element :scriptElements ){
//            for (DataNode node : element.dataNodes()) {
//                // System.out.println(node.getWholeData());
//            }
//            System.out.println("-------------------");
//        }
//        Elements scripts = docNew.getElementsByTag("script");
//        for (Element script : scripts) {
//            Pattern p = Pattern.compile("(.*?) media = (.+?);"); // Regex for the value of the key
//            Matcher m = p.matcher(script.html()); // you have to use html here and NOT text! Text will drop the 'key' part
//            while( m.find() ) {
//                String jsonString = m.group(2);
//                //System.out.println(jsonString);
//                JsonElement obj = new JsonParser().parse(jsonString).getAsJsonArray().get(0);
//                String mediaUrl = obj.getAsJsonObject().get("mediaUrl").getAsString();
//                System.out.println(mediaUrl);
//            }
//            // String text = script.dataNodes().get(0).toString();
//            if (script.dataNodes().toString().contains("mediaUrl")){
//                // DataNode s = script.dataNodes().size();
//                //  System.out.println(script.dataNodes().get(0).getWholeData());
//            }
//        }
//    }
}

