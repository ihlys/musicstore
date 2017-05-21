package com.ihordev.repository;

import com.ihordev.config.AppProfiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.math.BigInteger;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles(AppProfiles.REPOSITORY_TESTS)
public class SimpleTest {

    @Autowired
    private EntityManager em;

    @Test
    public void procedureTest() {
        StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("NEXT_PRIME");
        storedProcedureQuery.registerStoredProcedureParameter("value", String.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("value", "11");
        storedProcedureQuery.execute();
        Object output = storedProcedureQuery.getSingleResult();
        System.out.println(output);
    }

    public static boolean isPrime(int value) {
        return new BigInteger(String.valueOf(value)).isProbablePrime(100);
    }

}
