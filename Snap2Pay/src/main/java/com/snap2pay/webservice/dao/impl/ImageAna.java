//package com.snap2pay.webservice.dao.impl;
//
//import javax.script.ScriptException;
//import java.io.*;
//
///**
// * Created by sachin on 2/3/16.
// */
//public class ImageAna {
//    public String invokeImageAnalysis(String image,String category,String uuid,String retailer,String store) {
//
//        String result = executeCommand(image, category, uuid, retailer, store);
//        return result;
//    }
//
//    public static String executeCommand(String image,String category,String uuid,String retailer,String store) {
//        String response = "";
//        Boolean waitForResponse=true;
//        String command ="test.sh";
//        File f = new File("/home/ubuntu/caffe");
//        System.out.println("exist" + f.isDirectory());
//
//        ProcessBuilder pb = new ProcessBuilder("/bin/bash",command);
//        pb.environment().put("image",image );
//        pb.environment().put("category",category );
//        pb.environment().put("uuid",uuid );
//        pb.environment().put("retailer",retailer );
//        pb.environment().put("store",store );
//        pb.directory(f);
//        pb.redirectErrorStream(true);
//
//        System.out.println("Linux command: " + command);
//
//        try {
//            Process shell = pb.start();
//            if (waitForResponse) {
//                InputStream shellIn = shell.getInputStream();
//                int shellExitStatus = shell.waitFor();
//                System.out.println("Exit status" + shellExitStatus);
//                response = convertStreamToStr(shellIn);
//                shellIn.close();
//            }
//        } catch (IOException e) {
//            System.out.println("Error occured while executing Linux command. Error Description: "
//                    + e.getMessage());
//        } catch (InterruptedException e) {
//            System.out.println("Error occured while executing Linux command. Error Description: "
//                    + e.getMessage());
//        }
//        return response;
//    }
//    public static String convertStreamToStr(InputStream is) throws IOException {
//        if (is != null) {
//            Writer writer = new StringWriter();
//            char[] buffer = new char[1024];
//            try {
//                Reader reader = new BufferedReader(new InputStreamReader(is,
//                        "UTF-8"));
//                int n;
//                while ((n = reader.read(buffer)) != -1) {
//                    writer.write(buffer, 0, n);
//                }
//            } finally {
//                is.close();
//            }
//            return writer.toString();
//        }
//        else {
//            return "null";
//        }
//    }
//    public static void main(String[] args) throws ScriptException, IOException {
//        String input = "this is a test";
//        String image = "images/sh01_20150825_144710.jpg";
//        String category = "shampoo";
//        String uuid = "11111";
//        String retailer = "safeway";
//        String store = "01";
//        ImageAna ImageAna = new ImageAna();
//        System.out.println(ImageAna.invokeImageAnalysis( image, category, uuid, retailer, store));
//
//        // export LD_LIBRARY_PATH=/home/ubuntu/caffe/build/lib:/usr/local/lib:/usr/local/cuda/lib64:/home/ubuntu/anaconda3/lib/
////        Properties props = new Properties();
////        if we do the next line, then it works, but os import fails
////        props.put("python.import.site","false");
////        Properties preprops = System.getProperties();
////        PythonInterpreter.initialize(preprops, props, new String[0]);
////        PythonInterpreter interpreter = new PythonInterpreter();
////       // PySystemState sys = interpreter.getSystemState();
//////http://blog.smartbear.com/programming/embedding-jython-in-java-applications/
////        String input = "this is a test";
////        String image = "images/sh01_20150825_144710.jpg";
////        String category = "shampoo";
////        String uuid = "11111";
////        String retailer = "safeway";
////        String store = "01";
////        int a =10;
////        int b =10;
////        int i;
////
////        interpreter.execfile("/Users/sachin/test/test4.py");
////
////        interpreter.set("input", new PyString(input));
////        interpreter.set("image", new PyString(image));
////        interpreter.set("category", new PyString(category));
////        interpreter.set("uuid", new PyString(uuid));
////        interpreter.set("retailer", new PyString(retailer));
////        interpreter.set("store", new PyString(store));
////        interpreter.exec("import os");
////
////        PyObject someFunc = interpreter.get("image_analysis");
////        PyObject result = someFunc.__call__();
////        PyObject results = interpreter.get("results");
////        Integer realResult = (Integer) result.__tojava__(Integer.class);
////        System.out.println(results);
////        System.out.println(realResult);
//
//
////        interpreter.exec("# Function definition is here\n"+
////                "for i in range(1,10):\n" +
////                " print(i)\n" +
////                " sum("+a+",i)");
//
//       // interpreter.execfile("wrapper_scrpit.py");
//       // PyObject result = interpreter.get("results");
////        PyObject res_count = (PyInteger) interpreter.get("res_count");
////        System.out.println(result.__getitem__(0).__getattr__("x"));
////        System.out.println(res_count.asInt());
////
////        System.out.println(result.toString());
////        System.out.println(res_count.toString());
//    }
//}
////
////        interpreter.exec("import sys\n" +
////                "from ctypes import *\n" +
////                "import ctypes\n" +
////                "import os\n" +
////                "\n" +
////                "os.environ[\"LD_LIBRARY_PATH\"] = os.environ[\"LD_LIBRARY_PATH\"] + \":/home/ubuntu/caffe/build/lib:/usr/local/lib:/usr/local/cuda/lib64\"\n" +
////                "sys.path.append('')\n" +
////                "import yourModule\n" +
////                "\n" +
////                "prog=cdll.LoadLibrary('pipeline_shared/shelfC.so')\n");
//
////// execute a function that takes a string and returns a string
////        PyObject someFunc = interpreter.get("funcName");
////        PyObject result = someFunc.__call__(new PyString("Test!"));
////        String realResult = (String) result.__tojava__(String.class);
//
//
////    }
//
////        StringWriter writer = new StringWriter(); //ouput will be stored here
////
////        ScriptEngineManager manager = new ScriptEngineManager();
////        ScriptContext context = new SimpleScriptContext();
////
////        context.setWriter(writer); //configures output redirection
////        ScriptEngine engine = manager.getEngineByName("python");
////        engine.eval(new FileReader("/Users/sachin/test/test.py"), context);
////        System.out.println(writer.toString());
//
//
////    public static void main(String[] args)
////    {
////        try
////        {
////            Runtime r = Runtime.getRuntime();
////            Process p = r.exec("python foo.py");
////            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
////            p.waitFor();
////            String line = "";
////            while (br.ready())
////                System.out.println(br.readLine());
////
////        }
////        catch (Exception e)
////        {
////            String cause = e.getMessage();
////            if (cause.equals("python: not found"))
////                System.out.println("No python interpreter found.");
////        }
////    }
////}
