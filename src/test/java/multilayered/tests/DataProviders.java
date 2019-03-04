package multilayered.tests;

import com.tngtech.java.junit.dataprovider.DataProvider;
import multilayered.data.Customer;

public class DataProviders {

    @DataProvider
    public static Object[][] validCustomers() {
        return new Object[][]{
                {Customer.newEntity()
                        .withFirstname("Adam").withLastname("Smith").withPhone("+0123456789")
                        .withAddress("Hidden Place").withPostcode("12345").withCity("New City")
                        .withCountry("US").withZone("KS")
                        .withEmail("adam" + System.currentTimeMillis() + "@smith.me")
                        .withPassword("qwerty").build()},
                {Customer.newEntity()
                        .withFirstname("John").withLastname("Connor").withPhone("+0000000001")
                        .withAddress("Los Angeles").withPostcode("00000").withCity("Terminator")
                        .withCountry("US").withZone("KS")
                        .withEmail("terminator" + System.currentTimeMillis() + "@smith.me")
                        .withPassword("qwerty").build()}
                /* ... */
        };
    }
}