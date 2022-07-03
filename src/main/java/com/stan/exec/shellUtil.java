package com.stan.exec;

import java.io.*;


 /**
  * @apiNote cmd: String to be executed
  */
public class shellUtil{
    public static String[] exec(String cmd){
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            return getResults(process);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;       
    }

    private static String[] getResults(Process process) throws IOException{
        String[] res = new String[100];
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        int i=0;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            res[i++]=line;
        }
        return res;
    }
}

