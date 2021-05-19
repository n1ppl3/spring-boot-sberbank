package ru.pipan.boot2;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	private ApplicationContext ctx;

	@Test
	public void startUpTest() {

	}

	@Test
	public void vaultTest() {
		Environment env = ctx.getEnvironment();
		Boolean isVaultEnabled = env.getProperty("spring.cloud.vault.enabled", Boolean.class);
		if (Boolean.TRUE.equals(isVaultEnabled)) {
			// vault kv put secret/fakebank bank-key=bank-value
			VaultTemplate vaultTemplate = ctx.getBean(VaultTemplate.class);
			VaultKeyValueOperations vaultKeyValueOperations = vaultTemplate.opsForKeyValue("secret", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2);
			VaultResponse vaultResponse = vaultKeyValueOperations.get("fakebank");
			assertEquals("bank-value", vaultResponse.getData().get("bank-key"));
			assertEquals("bank-value", env.getProperty("bank-key"));
		}
	}

}
