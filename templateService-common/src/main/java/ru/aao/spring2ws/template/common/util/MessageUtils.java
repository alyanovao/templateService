package ru.aao.spring2ws.template.common.util;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MessageUtils {
    public static String getStringFromInputStream(InputStream is) throws Exception {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            throw new Exception("Couldn't read from Input Stream : " + e.getLocalizedMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new Exception("Couldn't close BufferedReader : " + e.getLocalizedMessage());
                }
            }
        }
        return sb.toString();

    }
    
    public static boolean isJSONValid(String jsonInString) {
	      try {
	    	  Gson gson = new Gson();
              gson.fromJson(jsonInString, String.class);
	          return true;
	      } catch(com.google.gson.JsonSyntaxException ex) { 
	          return false;
	      }
	  }


}
