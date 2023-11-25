package com.harmony.payment.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PaymentModule {
	
	private static PaymentModule module = new PaymentModule();
	private PaymentModule() {
		String path = PaymentModule.class.getResource("/common/paymodule.properties").getPath();
		try (FileReader fr = new FileReader(path)){
			settings.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PaymentModule getModule() {
		return PaymentModule.module;
	}
	
	private String accessToken;
	
	private Properties settings = new Properties();
	
	public void checkAcessToken() {
		
		String impKey = settings.getProperty("impKey");
		String impSecret = settings.getProperty("impSecret");
		String requestURL = "https://api.iamport.kr/users/getToken";
		
		try {
		URL url = new URL(requestURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        
        json.addProperty("imp_key", impKey);
        json.addProperty("imp_secret", impSecret);

        // POST 요청에서 필요한 파라미터를 OutputStream을 통해 전송
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bufferedWriter.write(gson.toJson(json));
        bufferedWriter.flush();
        
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);
		
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        StringBuilder result = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        
        JsonElement element = JsonParser.parseString(result.toString());
        JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();
        accessToken = response.getAsJsonObject().get("access_token").getAsString();
        
        bufferedReader.close();
        bufferedWriter.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void preValidate(String payHisCode, int amount) {
		
		checkAcessToken();
		
		String requestURL = "https://api.iamport.kr/payments/prepare";
		String code = "";
		try {
			
			//기존에 등록된 사전 등록 정보가 있는지 확인
			URL url = new URL(requestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-Type", "application/json; utf-8");
	        conn.setRequestProperty("Accept", "application/json");
	        conn.setRequestProperty("Authorization", "Bearer "+accessToken);
	        conn.setDoOutput(true);
	        
	        Gson gson = new Gson();
	        JsonObject json = new JsonObject();
	        
	        json.addProperty("merchant_uid", payHisCode);

	        // POST 요청에서 필요한 파라미터를 OutputStream을 통해 전송
	        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	        bufferedWriter.write(gson.toJson(json));
	        bufferedWriter.flush();
	        
	        
	        int responseCode = conn.getResponseCode();
			System.out.println(responseCode);
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = "";
	        StringBuilder result = new StringBuilder();

	        while ((line = bufferedReader.readLine()) != null) {
	            result.append(line);
	        }
	        
	        JsonElement element = JsonParser.parseString(result.toString());
	        code = element.getAsJsonObject().get("code").getAsString();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
        if(code.equals("-1")) {
        	try {
        	URL url = new URL(requestURL);
        	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        	conn.setRequestMethod("POST");
 	        conn.setRequestProperty("Content-Type", "application/json; utf-8");
 	        conn.setRequestProperty("Accept", "application/json");
 	        conn.setRequestProperty("Authorization", "Bearer "+accessToken);
 	        conn.setDoOutput(true);

 	        Gson gson = new Gson();
 	        JsonObject json = new JsonObject();
 	        
 	        json.addProperty("merchant_uid", payHisCode);
 	        json.addProperty("amount", amount);
 	        
 	        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
 	        bufferedWriter.write(gson.toJson(json));
 	        bufferedWriter.flush();
 	        
 	        int responseCode = conn.getResponseCode();
 	        System.out.println(responseCode);
 			
 	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
 	        String line = "";
 	        StringBuilder result = new StringBuilder();

 	        while ((line = bufferedReader.readLine()) != null) {
 	            result.append(line);
 	        }
 	        System.out.println("Method(Post) result : " + result.toString());
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
        	return;
        }else if(code.equals("0")){
        	try {
            	URL url = new URL(requestURL);
            	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            	conn.setRequestMethod("PUT");
     	        conn.setRequestProperty("Content-Type", "application/json; utf-8");
     	        conn.setRequestProperty("Accept", "application/json");
     	        conn.setRequestProperty("Authorization", "Bearer "+accessToken);
     	        conn.setDoOutput(true);

     	        Gson gson = new Gson();
     	        JsonObject json = new JsonObject();
     	        
     	        json.addProperty("merchant_uid", payHisCode);
     	        json.addProperty("amount", amount);
     	        
     	        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
     	        bufferedWriter.write(gson.toJson(json));
     	        bufferedWriter.flush();
     	        
     	        int responseCode = conn.getResponseCode();
     	        System.out.println("responseCode : " + responseCode);
     			
     	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
     	        String line = "";
     	        StringBuilder result = new StringBuilder();

     	        while ((line = bufferedReader.readLine()) != null) {
     	            result.append(line);
     	        }
     	        
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
        }
	}
	
	
	public boolean refund(String impUid, String payHisCode) {
		
		checkAcessToken();
		
		boolean refundResult = false;
		int code = -1;
		int responseCode =0;
		String requestURL = "https://api.iamport.kr/payments/cancel";
		
		try {
        	URL url = new URL(requestURL);
        	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        	conn.setRequestMethod("POST");
 	        conn.setRequestProperty("Content-Type", "application/json; utf-8");
 	        conn.setRequestProperty("Accept", "application/json");
 	        conn.setRequestProperty("Authorization", "Bearer "+accessToken);
 	        conn.setDoOutput(true);

 	        Gson gson = new Gson();
 	        JsonObject json = new JsonObject();
 	        
 	        json.addProperty("imp_uid", impUid);
 	        json.addProperty("merchant_uid", payHisCode);
 	        
 	        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
 	        bufferedWriter.write(gson.toJson(json));
 	        bufferedWriter.flush();
 	        
 	        responseCode = conn.getResponseCode();
 	        System.out.println("responseCode : " + responseCode);
 			
 	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
 	        String line = "";
 	        StringBuilder result = new StringBuilder();

 	        while ((line = bufferedReader.readLine()) != null) {
 	            result.append(line);
 	        }
 	        	System.out.println("refund result : "+result.toString());
 	        	JsonElement portOneResult = JsonParser.parseString(result.toString());
 	        	code = portOneResult.getAsJsonObject().get("code").getAsInt();
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
		System.out.println("code");
		if(responseCode==200 && code==0) {
			refundResult = true;
		}
		
		return refundResult;
	}
	
}
