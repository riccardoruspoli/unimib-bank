package it.unimib.bank.account;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class Random20CharsHexGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		while (sb.length() < 20) {
			sb.append(Integer.toHexString(r.nextInt()));
		}

		return sb.toString().substring(0, 20);
	}

}
