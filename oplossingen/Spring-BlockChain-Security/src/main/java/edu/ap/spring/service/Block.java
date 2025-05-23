package edu.ap.spring.service;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import edu.ap.spring.transaction.Transaction;

public class Block {
	
	public String hash;
	public String previousHash; 
	public String merkleRoot;
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>(); // our data will be a simple message.
	public long timeStamp; // as number of milliseconds since 1/1/1970.
	public int nonce;
	
	public Block() {
		this.setTimeStamp();
		this.calculateHash();
	}
	
	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}
	
	public void setTimeStamp() {
		this.timeStamp = new Date().getTime();
	}

	public ArrayList<Transaction> getTransactions() {
		return this.transactions;
	}
	
	// calculate new hash based on blocks contents
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				merkleRoot
				);
		this.hash = calculatedhash;
		
		return calculatedhash;
	}
	
	// increases nonce value until hash target is reached.
	public void mineBlock(int difficulty) {
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = StringUtil.getDificultyString(difficulty); // create a string with difficulty * "0" 
		while(!hash.substring(0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
	}
	
	// add transactions to this block
	public boolean addTransaction(Transaction transaction, BlockChain bChain) {
		// process transaction and check if valid, unless block is genesis block then ignore.
		if(transaction == null) return false;		
		if((previousHash != "0")) {
			if((transaction.processTransaction(bChain) != true)) {
				System.out.println("# Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);

		return true;
	}

	public void clearTransactions() {
		this.transactions.clear();
	}

	public String toJSON() {
		JSONObject blockObj = new JSONObject();
		try {
			blockObj.put("hash", this.hash);
			blockObj.put("previousHash", this.previousHash);
			// only one transaction per block
			blockObj.put("transactionId", this.getTransactions().get(0).transactionId);
			blockObj.put("sender", this.getTransactions().get(0).getSender());
			blockObj.put("recipient", this.getTransactions().get(0).getRecipient());
			blockObj.put("value", this.getTransactions().get(0).getValue());
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}

		return blockObj.toString();
	}
}
