package com.howaboutliving.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@CrossOrigin(origins="*") // 개발 서버 간에 api콜 허락하기 위한 CORS 설정
public class CommonController {
	
	@RequestMapping("/geocoding")
	String geocoding(@RequestParam(value = "latitude")double latitude,
			@RequestParam(value = "longitude")double longitude) {
		
		RestTemplate restTemplate = new RestTemplate(); // RestTemplate 사용 (피드백 적용)
		String reverseGeoURI = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?"
				+ "request=coordsToaddr&coords=" + longitude + "," + latitude + "&orders=legalcode,admcode,addr,roadaddr&output=json";
		URI uri;
		System.out.println("reverseURI : " + reverseGeoURI);
		
		/** 리버스 지오코딩 api 인증하기 위한 헤더 생성 **/
		Map<String, String> map = new HashMap<>();
		map.put("X-NCP-APIGW-API-KEY-ID", "49db75h9vw");
		map.put("X-NCP-APIGW-API-KEY", "7WS9EaGvmNvgx2Xw90KoY3KDpKOXZZ8e2YIK91lM");
		HttpEntity<String> entity = new HttpEntity<String>(makeHeaders(map));
		
		ResponseEntity<String> responseString = null;
		try {
			uri = new URI(reverseGeoURI);
			responseString = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		} catch (URISyntaxException e) {
			System.out.println("URI 문법 에러");
			e.printStackTrace();
		}
		
		return reverseGeocodingParse(responseString.getBody());
	}
	
	/** header 생성 함수 */
	private HttpHeaders makeHeaders(Map<String, String> map) {
		HttpHeaders headers = new HttpHeaders();
		
		for(Map.Entry<String, String> elem : map.entrySet()) {
			headers.set(elem.getKey(), elem.getValue());
		}
		
		headers.set("X-NCP-APIGW-API-KEY-ID", "49db75h9vw");
		headers.set("X-NCP-APIGW-API-KEY", "7WS9EaGvmNvgx2Xw90KoY3KDpKOXZZ8e2YIK91lM");
		
		return headers;
	}
	
	private String reverseGeocodingParse(String readData) {
		JsonObject parseJson = JsonParser.parseString(readData).getAsJsonObject();
		JsonObject parseStatus = parseJson.get("status").getAsJsonObject();
		if(parseStatus.get("code").getAsInt() != 0) {
			System.out.println("리버스 지오코딩 에러");
			return "리버스 지오코딩 에러";
		}
		JsonArray parseResults = parseJson.get("results").getAsJsonArray();
		JsonObject parseResult = parseResults.get(0).getAsJsonObject();
		JsonObject parseRegion = parseResult.get("region").getAsJsonObject();
		
		String sido = parseRegion.get("area1").getAsJsonObject().get("name").getAsString();
		String sigungu = parseRegion.get("area2").getAsJsonObject().get("name").getAsString();
		
		return sido + " " + sigungu;
	}
	
}
