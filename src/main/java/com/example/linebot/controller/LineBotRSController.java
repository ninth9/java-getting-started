package com.example.linebot.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.linebot.model.Event;
import com.example.linebot.model.EventWrapper;

@RestController
public class LineBotRSController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String[] responses = new String[] { "別亂講話", "今天在幹嘛?", "去睡覺了哦", "去幫忙洗碗了哦", "洗澡了沒", "頭髮要梳哦", "還在玩", "去幫忙折衣服",
			"很晚了哦", "不用念書嗎", "功課寫完了嗎", "有幫忙作家事嗎?" };

	@RequestMapping(value = "/callback")
	public void callback(@RequestBody String message) {
		System.out.println(message);
	}

	@RequestMapping(value = "/con", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void conversation(@RequestBody EventWrapper events, @RequestHeader HttpHeaders headers,
			HttpServletRequest httpRequest) {
		for (Event event : events.getEvents()) {
			String replyToken = event.getReplyToken();
			int index = (int) (Math.random() * responses.length);
			sendResponseMessages(replyToken, responses[index]);
		}
	}

	private void sendResponseMessages(String replyToken, String message) {
		String accessToken = "e1galgPnyWCksZvpGpd7g4IT7bDaQD11AYtdnSyhkPGi8+Vb/nG0X4pb2890rIkbrIGt2exQN1+l+G0BjM++1afhMqRGDbfD/6NftDa4MbHXJIVF3g2zuJBIEZglVsmW7PsBZe4o6CMwPh2U+J4HOgdB04t89/1O/w1cDnyilFU=";
		try {
			message = "{\"replyToken\":\"" + replyToken + "\",\"messages\":[{\"type\":\"text\",\"text\":\"" + message
					+ "\"}]}";
			System.out.println(message);
			URL myurl = new URL("https://api.line.me/v2/bot/message/reply");
			HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			con.setRequestProperty("Authorization", "Bearer " + accessToken);
			con.setDoOutput(true);
			con.setDoInput(true);
			DataOutputStream output = new DataOutputStream(con.getOutputStream());
			output.write(message.getBytes(Charset.forName("utf-8")));
			output.close();
			System.out.println("Resp Code:" + con.getResponseCode() + "; Resp Message:" + con.getResponseMessage());
		} catch (MalformedURLException e) {
			System.out.println("Message: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Message: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Message: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
