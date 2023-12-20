package secretsanta;

/**
 * Class for participants to be used in the SecretSanta class
 *
 * @author Elijah Bulluck
 *
 */
public class Participant {
    private String name;
    private String phoneNumber;

    public Participant(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
