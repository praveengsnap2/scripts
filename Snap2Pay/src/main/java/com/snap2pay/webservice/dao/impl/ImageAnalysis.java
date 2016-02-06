//package com.snap2pay.webservice.dao.impl;
//
//import org.python.core.PyObject;
//import org.python.core.PyString;
//import org.python.util.PythonInterpreter;
//
//import java.io.*;
//
///**
// * Created by sachin on 2/4/16.
// */
//public class ImageAnalysis {
//    @Override
//    public void invokePythonImageAnalysis() {
//        PythonInterpreter interpreter = new PythonInterpreter();
//        String input = "this is a test";
//        String image = "images/sh01_20150825_144710.jpg";
//        String category = "shampoo";
//        String uuid = "11111";
//        String retailer = "safeway";
//        String store = "01";
//
//        interpreter.exec("import os");
//        interpreter.execfile("/Users/sachin/test/test4.py");
//
//        interpreter.set("input", new PyString(input));
//        interpreter.set("image", new PyString(image));
//        interpreter.set("category", new PyString(category));
//        interpreter.set("uuid", new PyString(uuid));
//        interpreter.set("retailer", new PyString(retailer));
//        interpreter.set("store", new PyString(store));
//
//        PyObject someFunc = interpreter.get("image_analysis");
//        PyObject result = someFunc.__call__();
//        PyObject results = interpreter.get("results");
//        Integer realResult = (Integer) result.__tojava__(Integer.class);
//        System.out.println(results);
//        System.out.println(realResult);
//
//    }
//
//    @Override
//    public String invokeImageAnalysis(String image,String category,String uuid,String retailer,String store) {
//        String command ="/bin/sh -c cd /home/ubuntu/caffe;\n" ;
//        //+
//          //      "/bin/sh -c sh pipeline/shelfC "+image+" "+category+" "+uuid+" "+retailer+" "+store+";";
//        String result =executeCommand(command,true);
//        return result;
//    }
//
//
//    public static void main(String[] args) {
//        String input = "this is a test";
//        String image = "images/sh01_20150825_144710.jpg";
//        String category = "shampoo";
//        String uuid = "11111";
//        String retailer = "safeway";
//        String store = "01";
//        ImageAnalysis imageAnalysis = new ImageAnalysis();
//        System.out.println(imageAnalysis.invokeImageAnalysis(image, category, uuid, retailer, store));
//    }
//
//}
