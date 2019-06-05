package com.tufike.taxi.common.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Transaction{

	@SerializedName("amount")
	private float amount;

	@SerializedName("document_number")
	private String documentNumber;

	@SerializedName("operator_id")
	private int operatorId;

	@SerializedName("transaction_time")
	private Timestamp transactionTime;

	@SerializedName("details")
	private Object details;

	@SerializedName("id")
	private int id;

	@SerializedName("transaction_type")
	private String transactionType;

	@SerializedName("rider_id")
	private int riderId;

	public void setAmount(float amount){
		this.amount = amount;
	}

	public float getAmount(){
		return amount;
	}

	public void setDocumentNumber(String documentNumber){
		this.documentNumber = documentNumber;
	}

	public String getDocumentNumber(){
		return documentNumber;
	}

	public void setOperatorId(int operatorId){
		this.operatorId = operatorId;
	}

	public int getOperatorId(){
		return operatorId;
	}

	public void setTransactionTime(Timestamp transactionTime){
		this.transactionTime = transactionTime;
	}

	public Timestamp getTransactionTime(){
		return transactionTime;
	}

	public void setDetails(Object details){
		this.details = details;
	}

	public Object getDetails(){
		return details;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTransactionType(String transactionType){
		this.transactionType = transactionType;
	}

	public String getTransactionType(){
		return transactionType;
	}

	public void setRiderId(int riderId){
		this.riderId = riderId;
	}

	public int getRiderId(){
		return riderId;
	}

	public String getDay() {
		return String.valueOf(transactionTime.getDate());
	}

	public String getMonth() {
		String[] months = new String[]{"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
		return months[transactionTime.getMonth()];
	}
}