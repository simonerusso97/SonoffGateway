package it.unisalento.sonoffgateway.restController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;

@RestController
public class Controller {

	String cmdTopic = "cmnd/tasmota_8231A8/POWER1";
	String reqToipic = "cmnd/tasmota_8231A8/Power1";
	String statTopic = "stat/tasmota_8231A8/POWER1";
	
	String status = new String();
		
	@RequestMapping(value="changeStatusON", method = RequestMethod.GET)
	public ResponseEntity<Boolean> changeStatusON() throws Exception{
		try {
			
			MqttClient client = connectToBroker();
			
			MqttMessage message = new MqttMessage("ON".getBytes());
			
			client.publish(cmdTopic, message);	//BLOCKING

			client.disconnect();
			
			System.out.println(client.getClientId() + ": CLIENT DISCONNESSO");
			
			return new ResponseEntity<Boolean>(HttpStatus.OK);
			
		}catch (Exception e) {
			throw e;
			}
	}
	
	@RequestMapping(value="changeStatusOFF", method = RequestMethod.GET)
	public ResponseEntity<Boolean> changeStatusOFF() throws Exception{
		try {
			MqttClient client = connectToBroker();
			
			MqttMessage message = new MqttMessage("OFF".getBytes());
			
			client.publish(cmdTopic, message);	//BLOCKING

			client.disconnect();
			
			System.out.println(client.getClientId() + ": CLIENT DISCONNESSO");
			
			return new ResponseEntity<Boolean>(HttpStatus.OK);
			
		}catch (Exception e) {
			throw e;
			}
	}
	

	
	@RequestMapping(value="getStatus", method = RequestMethod.GET)
	public String getStatus() throws Exception{
		try {
			status = "";
			MqttClient client = connectToBroker();;

			
			//TODO: correggere bug

			client.subscribe(statTopic, new IMqttMessageListener() {
				
				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					String m = new String(message.getPayload(), StandardCharsets.UTF_8);
					System.out.println("MESSAGE ARRIVED: " + m);
					status = m;
					client.disconnect();	
					System.out.println(client.getClientId() + ": CLIENT DISCONNESSO");
				}
			});
			
			
			MqttMessage message = new MqttMessage();
			
			client.publish(reqToipic, message);	//BLOCKING
			
			while(client.isConnected()) {
				
			}

			return status;
		}catch (Exception e) {
			throw e;
			}
		
	}
	
	private MqttClient connectToBroker() throws MqttException {
		
		String broker = "tcp://localhost:1883";
		String clientId = "raspberrypi";
		
		
		MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());

		
		client.setCallback(new MqttCallback() {
		
			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
			}
		
			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
				System.out.println("PUBLISH SUCCESSFULL");
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
		
		return client;
	}
	
	 
}
