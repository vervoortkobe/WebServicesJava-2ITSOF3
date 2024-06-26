package edu.ap.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.ap.spring.service.*;
import edu.ap.spring.transaction.Transaction;
import jakarta.annotation.PostConstruct;

@Controller
public class MainController {

 	@Autowired
  	private BlockChain bChain;
  	@Autowired
  	private Wallet coinbase, walletA, walletB;
  	private Transaction genesisTransaction;

  	@PostConstruct
  	private void init() {
		bChain.setSecurity();
		coinbase.generateKeyPair();
		walletA.generateKeyPair();
		walletB.generateKeyPair();

		// create genesis transaction, which sends 100 coins to walletA:
		genesisTransaction = new Transaction(coinbase.getPublicKey(), walletA.getPublicKey(), 100f);
		genesisTransaction.generateSignature(coinbase.getPrivateKey());	 // manually sign the genesis transaction	
		genesisTransaction.transactionId = "0"; // manually set the transaction id
						
		// creating and Mining Genesis block
		Block genesis = new Block();
		genesis.setPreviousHash("0");
		genesis.addTransaction(genesisTransaction, bChain);
		bChain.addBlock(genesis);
  	}

  	@GetMapping(value="/")
   	public String index() {
	   return "redirect:/home";
   	}

	@GetMapping(value="/home")
   	public String home() {
	   return "home";
   	}

  	@GetMapping(value="/balance/{wallet}")
   	public String getBalance(@PathVariable String wallet,
                             Model model) {
     	
	  	model.addAttribute("wallet", wallet);

	  	if(wallet.equalsIgnoreCase("walletA")) {
	  		model.addAttribute("balance", walletA.getBalance());
	  	}
	  	else if(wallet.equalsIgnoreCase("walletB")) {
			model.addAttribute("balance", walletB.getBalance());
	  	}
	  	else {
			model.addAttribute("balance", 0f);
	  	}
      
    	return "balance";
  	}

  	@GetMapping(value="/transaction")
  	public String getForm() {
	  return "transaction";
  	}

  	@PostMapping(value="/transaction")
  	public String transaction(@RequestParam String wallet1, 
                              @RequestParam String wallet2,
                              @RequestParam float amount) {
	
		try {
			if(wallet1.equalsIgnoreCase("walletA") && wallet2.equalsIgnoreCase("walletB")) {
				walletA.sendFunds(walletB.getPublicKey(), amount);
			}
			else if(wallet1.equalsIgnoreCase("walletB") && wallet2.equalsIgnoreCase("walletA")) {
				walletB.sendFunds(walletA.getPublicKey(), amount);
			}
		}
		catch(Exception e) {}
		
      return "redirect:/balance/" + wallet1;
    }

	@GetMapping(value="/alltransactions")
	public @ResponseBody String alltransactions() {
		return this.bChain.toJSON();
	}

	@GetMapping(value="/valid")
	public @ResponseBody String testValidity() {
		return "Valid : " + this.bChain.isValid();
	}
}
