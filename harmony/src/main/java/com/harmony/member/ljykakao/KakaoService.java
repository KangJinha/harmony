package com.harmony.member.ljykakao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.harmony.common.exception.HarmonyException;

public class KakaoService {
	private static KakaoService kservice = new KakaoService();
	
	private KakaoService() {}
	
	public static KakaoService getKakaoService() {
		return KakaoService.kservice;
	}
	
	public HashMap<String, Object> getKakaoAccessToken (String code, String serverInfo) {
		
		HashMap<String, Object> tokens = new HashMap<>();
	    String accessToken = "";
	    String refreshToken = "";
	    String requestURL = "https://kauth.kakao.com/oauth/token";

	    try {
	        URL url = new URL(requestURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	        conn.setRequestMethod("POST");
	        // setDoOutput()은 OutputStream으로 POST 데이터를 넘겨 주겠다는 옵션이다.
	        // POST 요청을 수행하려면 setDoOutput()을 true로 설정한다.
	        conn.setDoOutput(true);
	        
	        // POST 요청에서 필요한 파라미터를 OutputStream을 통해 전송
	        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	        String sb = "grant_type=authorization_code" +
	                "&client_id=7a71de50fa7db8d6bb2395a8a5fba504" + // REST_API_KEY
	                "&redirect_uri="+serverInfo+"/member/kakaoLogin.do" + // REDIRECT_URI
	                "&code=" + code;
	        bufferedWriter.write(sb);
	        bufferedWriter.flush();

	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        InputStream stream = conn.getErrorStream();
		    if (stream != null) {
		    	String response = "";
			    try (Scanner scanner = new Scanner(stream)) {
			        scanner.useDelimiter("\\Z");
			        response = scanner.next();
			    }
			    throw new HarmonyException("이 메세지가 보이면 재연에게 알려주세요\r\n"+response);
		    }
	        
	        // 요청을 통해 얻은 데이터를 InputStreamReader을 통해 읽어 오기
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = "";
	        StringBuilder result = new StringBuilder();

	        while ((line = bufferedReader.readLine()) != null) {
	            result.append(line);
	        }

	        JsonElement element = JsonParser.parseString(result.toString());

	        accessToken = element.getAsJsonObject().get("access_token").getAsString();
	        refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();
	        tokens.put("accessToken", accessToken);
	        tokens.put("refreshToken", refreshToken);

	        bufferedReader.close();
	        bufferedWriter.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return tokens;
	}
	
	public HashMap<String, Object> getUserInfo(String accessToken) {
	    HashMap<String, Object> userInfo = new HashMap<>();
	    String postURL = "https://kapi.kakao.com/v2/user/me";

	    try {
	        URL url = new URL(postURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");

	        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

	        InputStream stream = conn.getErrorStream();
		    if (stream != null) {
		    	String response = "";
			    try (Scanner scanner = new Scanner(stream)) {
			        scanner.useDelimiter("\\Z");
			        response = scanner.next();
			    }
			    System.out.println("response : "+response);
			    throw new HarmonyException("이 메세지가 보이면 재연에게 알려주세요\r\n"+response);
		    }
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuilder result = new StringBuilder();
	        String line = "";
	        while ((line = br.readLine()) != null) {
	            result.append(line);
	        }

	        JsonElement element = JsonParser.parseString(result.toString());
	        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
	        JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

	        String nickname = properties.getAsJsonObject().get("nickname").getAsString();
	        String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
	        String id = element.getAsJsonObject().get("id").getAsString();
	        
	        
	        userInfo.put("nickname", nickname);
	        userInfo.put("email", email);
	        userInfo.put("id",id);
	    } catch (IOException exception) {
	        exception.printStackTrace();
	    }

	    return userInfo;
	}
}
