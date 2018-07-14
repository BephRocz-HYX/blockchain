package com.ppblock.blockchain.pow;

import com.ppblock.blockchain.Application;
import com.ppblock.blockchain.account.Account;
import com.ppblock.blockchain.account.Personal;
import com.ppblock.blockchain.core.Block;
import com.ppblock.blockchain.core.BlockBody;
import com.ppblock.blockchain.core.BlockHeader;
import com.ppblock.blockchain.core.Transaction;
import com.ppblock.blockchain.crypto.Hash;
import com.ppblock.blockchain.mine.pow.PowResult;
import com.ppblock.blockchain.mine.pow.ProofOfWork;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * 工作量证明测试
 * @author yangjian
 * @since 18-4-11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PowTest {

	static Logger logger = LoggerFactory.getLogger(PowTest.class);

	@Autowired
	private Personal personal;

	@Test
	public void run() throws Exception {

		BlockHeader header = new BlockHeader(1, null);
		BlockBody body = new BlockBody();
		Account account = personal.newAccount();
		Transaction transaction = new Transaction(null, account.getAddress(), BigDecimal.valueOf(50));
		transaction.setData("Mining Reward");
		//transaction.setPublicKey(account.getPublicKey());
		transaction.setTxHash(Hash.sha3(transaction.toString()));
		//transaction.setSign(Sign.sign(account.getPrivateKey(), transaction.toString()));
		body.addTransaction(transaction);

		Block block = new Block(header, body);
		ProofOfWork proofOfWork = ProofOfWork.newProofOfWork(block);
		PowResult result = proofOfWork.run();
		logger.info("Pow result, {}", result);

	}

}
