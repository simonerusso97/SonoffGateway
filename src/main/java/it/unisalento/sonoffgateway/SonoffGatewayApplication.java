package it.unisalento.sonoffgateway;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;




@SpringBootApplication
public class SonoffGatewayApplication{
	String status = new String();

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SonoffGatewayApplication.class);
        app.setDefaultProperties(Collections
          .singletonMap("server.port", "8081"));
        app.run(args);	
        
        try {
        	
        	FileInputStream serviceAccount =
        			  new FileInputStream("./sonoff-66f45-firebase-adminsdk-uyqwv-6627f88f88.json");
        			
                    FirebaseOptions options = FirebaseOptions.builder()
                   		 .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .build();


        			FirebaseApp.initializeApp(options);
        
        }catch (Exception e) {
			// TODO: handle exception
		}
        

        File file = new File("./tokens.json");  
        JSONParser parser = new JSONParser();
		Reader reader;
		List<String> tokens = new ArrayList<>();
		try {
			file.createNewFile();
			reader = new FileReader("./tokens.json");
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			Set<String> keys = jsonObject.keySet(); 
			System.out.println(keys);
			tokens = (List<String>) jsonObject.get(keys.toArray()[0].toString());
			for(String tok: tokens) {
				System.out.println(tok);
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        connectAndSubscribeMqtt((ArrayList<String>) tokens);
        
        
	}

	private static void connectAndSubscribeMqtt(ArrayList<String> tokens) {
		String broker = "tcp://localhost:1883";
		
		String statTopic = "stat/tasmota_8231A8/POWER1";
		
		MqttMessage message = new MqttMessage();

		String clientId = "notificationChannel";
		
		
        try {
        	MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
    		
    		
    		client.setCallback(new MqttCallback() {
    		
    			@Override
    			public void messageArrived(String topic, MqttMessage message) throws Exception {
    			}
    		
    			@Override
    			public void deliveryComplete(IMqttDeliveryToken token) {
    			}
    		
    			@Override
    			public void connectionLost(Throwable cause) {
    				// TODO Auto-generated method stub
    			
    			}
    		
    		});	
    		
    	
    		MqttConnectOptions opt = new MqttConnectOptions();
    	
    		opt.setCleanSession(true);
    	
    		System.out.println("CONNECTIONG TO BROKER " + broker);
    	
    		client.connect(opt);
    	
    		System.out.println("CONNECT SUCCESSED");

    		
    		

    		
    		client.subscribe(statTopic, new IMqttMessageListener() {
    			
    			//TODO: correggere bug
    			
    			@Override
    			public void messageArrived(String topic, MqttMessage message) throws Exception {
    				String m = new String(message.getPayload(), StandardCharsets.UTF_8);
    				System.out.println("MESSAGE ARRIVED: " + m);
    				Notification.Builder builder = Notification.builder();
    				MulticastMessage notMess = MulticastMessage.builder()
    						.setNotification(builder.build())
    						.putData("title", "Cambio di stato")
    						.putData("body", m)
    				        .addAllTokens(tokens)
    				        .build();
    				System.out.println("SENDING...");

    				BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(notMess);
    				    // See the BatchResponse reference documentation
    				    // for the contents of response.
    				System.out.println(response.getSuccessCount() + " messages were sent successfully");
    				    				
    				
				}
    		});
    		
        }catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}
