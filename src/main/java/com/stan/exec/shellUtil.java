package com.stan.exec;

import java.io.*;


 /**
  * @apiNote cmd: String to be executed
  */
public class shellUtil{
    public static void exec(String cmd){
            try {

                Process process = Runtime.getRuntime().exec(cmd);
                printResults(process);
            } catch (IOException e) {
                e.printStackTrace();
            }       
    }

    private static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}

