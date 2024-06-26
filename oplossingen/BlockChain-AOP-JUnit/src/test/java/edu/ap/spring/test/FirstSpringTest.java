package edu.ap.spring.test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mockito;
import edu.ap.spring.service.*;
import edu.ap.spring.transaction.Transaction;

@SpringBootTest
public class FirstSpringTest {

	@Autowired
	private BlockChain bChain;
	@Autowired
	private Wallet coinbase, walletA, walletB;
	private Wallet walletC = Mockito.mock(Wallet.class);
	private Transaction genesisTransaction;

	@BeforeEach
	public void init() {
		bChain.setSecurity();
		coinbase.generateKeyPair();
		walletA.generateKeyPair();
		walletB.generateKeyPair();

		// create genesis transaction, which sends 100 coins to walletA:
		genesisTransaction = new Transaction(coinbase.getPublicKey(), walletA.getPublicKey(), 100f);
		genesisTransaction.generateSignature(coinbase.getPrivateKey());	 // manually sign the genesis transaction	
		genesisTransaction.transactionId = "0"; // manually set the transaction id
						
		// creating and mining Genesis block
		Block genesisBlock = new Block();
		genesisBlock.setPreviousHash("0");
		genesisBlock.addTransaction(genesisTransaction, bChain);
		bChain.addBlock(genesisBlock);
	}

	@Test
	public void test1() {
			
		try {
			walletA.sendFunds(walletB.getPublicKey(), 40f);
			walletA.sendFunds(walletB.getPublicKey(), 10f);
		} 
		catch(Exception e) {}

		assertEquals(50f, walletA.getBalance(), 0);
		assertEquals(50f, walletB.getBalance(), 0);
	}

	@Test
	public void test2() {
			
		try {
			walletA.sendFunds(walletB.getPublicKey(), 10f);
		} 
		catch(Exception e) {}

		assertEquals(10f, walletB.getBalance(), 0);
	}

	@Test
	public void test3() {

		//configure mocking behaviour
		Mockito.when(walletC.getBalance()).thenReturn(50f);

		assertEquals(50f, walletC.getBalance(), 0);
	}
}
