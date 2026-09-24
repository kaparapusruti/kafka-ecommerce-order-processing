package com.kafkaEcomerce.order.response;


public class OrderResponse {
	
	 private long oderId;
	 private String status;
	 
	 
	 public long getOderId() {
		 return oderId;
	 }
	 public void setOderId(long oderId) {
		 this.oderId = oderId;
	 }
	 public String getStatus() {
		 return status;
	 }
	 public void setStatus(String status) {
		 this.status = status;
	 }
	 
	 

}
