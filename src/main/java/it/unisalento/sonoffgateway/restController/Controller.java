package it.unisalento.sonoffgateway.restController;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	String broker = "tcp://192.168.1.100:1883";
	String clientId = "raspberrypi";
	
	String cmdTopic = "cmnd/tasmota_8231A8/POWER1";
	String reqToipic = "cmnd/tasmota_8231A8/Power1";
	String statTopic = "stat/tasmota_8231A8/POWER1";
		
	String status = new String();
		
	@RequestMapping(value="changeStatus/{status}", method = RequestMethod.POST)
	public ResponseEntity<Boolean> changeStatus(@PathVariable("status") String status) throws Exception{
		/*
		 * try { MqttClient client = new MqttClient(broker, clientId, new
		 * MemoryPersistence());
		 * 
		 * client.setCallback(new MqttCallback() {
		 * 
		 * @Override public void messageArrived(String topic, MqttMessage message)
		 * throws Exception { }
		 * 
		 * @Override public void deliveryComplete(IMqttDeliveryToken token) {
		 * System.out.println("PUBLISH SUCCESSFULL"); }
		 * 
		 * @Override public void connectionLost(Throwable cause) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * });
		 * 
		 * MqttConnectOptions opt = new MqttConnectOptions();
		 * 
		 * opt.setCleanSession(true);
		 * 
		 * System.out.println("CONNECTIONG TO BROKER " + broker);
		 * 
		 * client.connect(opt);
		 * 
		 * System.out.println("CONNECT SUCCESSED");
		 * 
		 * MqttMessage message = new MqttMessage(status.getBytes());
		 * 
		 * client.publish(cmdTopic, message); //BLOCKING
		 * 
		 * client.disconnect();
		 * 
		 * System.out.println("CLIENT DISCONNESSO");
		 * 
		 * return new ResponseEntity<Boolean>(HttpStatus.OK);
		 * 
		 * }catch (Exception e) { throw e; }
		 */
	}
	

	
	@RequestMapping(value="getStatus", method = RequestMethod.GET)
	public String getStatus() throws Exception{
		return "Ok";
	}
//		try {
//			status = "";
//			MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
//			
//			client.setCallback(new MqttCallback() {
//				
//				@Override
//				public void messageArrived(String topic, MqttMessage message) throws Exception {
//				}
//				
//				@Override
//				public void deliveryComplete(IMqttDeliveryToken token) {
//					System.out.println("PUBLISH SUCCESSFULL");
//				}
//				
//				@Override
//				public void connectionLost(Throwable cause) {
//					// TODO Auto-generated method stub
//					
//				}
//			});	
//			
//			MqttConnectOptions opt = new MqttConnectOptions();
//			
//			opt.setCleanSession(true);
//			
//			System.out.println("CONNECTIONG TO BROKER " + broker);
//			
//			client.connect(opt);
//			
//			System.out.println("CONNECT SUCCESSED");
//
//			client.subscribe(statTopic, new IMqttMessageListener() {
//				
//				@Override
//				public void messageArrived(String topic, MqttMessage message) throws Exception {
//					String m = new String(message.getPayload(), StandardCharsets.UTF_8);
//					System.out.println("MESSAGE ARRIVED: " + m);
//					status = m;
//					System.out.println("CLOSING CLIENT CONNECTION..");
//					client.disconnect();					
//				}
//			});
//			
//			
//			MqttMessage message = new MqttMessage();
//			
//			client.publish(reqToipic, message);	//BLOCKING
//			
//			while(client.isConnected()) {
//				
//			}
//
//			return status;
//		}catch (Exception e) {
//			throw e;
//			}
//		
//	}
	
}
