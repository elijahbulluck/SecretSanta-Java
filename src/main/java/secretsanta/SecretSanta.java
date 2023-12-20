package secretsanta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * Class for running the secret santa program. User enters their Twilio info
 * that sends a text to each person with the person they must gift in a way that
 * allows the user of the program to participate, which is something that I
 * didn't really see with other applications.
 *
 * @author Elijah Bulluck
 *
 */
public class SecretSanta {

    public static String ACCOUNT_SID;
    public static String AUTH_TOKEN;
    public static String TWILIO_PHONE_NUM;

    private static void getTwilioCredentials() {
        //Ask the user for Twilio info required to send SMS messages
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Twilio Account SID: ");
        ACCOUNT_SID = scanner.nextLine();

        System.out.print("Enter your Twilio Authorization Token: ");
        AUTH_TOKEN = scanner.nextLine();

        System.out.print("Enter your Twilio phone number: ");
        TWILIO_PHONE_NUM = scanner.nextLine();
        scanner.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Ask user for Twilio info
        getTwilioCredentials();
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Create list of participants
        List<Participant> participants = new ArrayList<>();

        //The user must enter the name and then the phone number for all participants
        System.out.println(
                "Enter the name and phone number for each participant. When you are finished, type 'done'.");
        while (true) {
            System.out.print("Enter participant name (or 'done'): ");
            String name = scanner.nextLine();
            //end loop if the user types in done
            if (name.toLowerCase().equals("done")) {
                break;
            }
            //if a name is entered, prompt the user to enter the persons phone number
            System.out.print("Enter " + name + "'s phone number: ");
            String phoneNumber = scanner.nextLine();
            //add the participant to the list
            participants.add(new Participant(name, phoneNumber));
        }

        // Shuffle participants (for now, looking for a better way for more randomness)
        Collections.shuffle(participants);

        // Assign gifters and receivers and send SMS
        for (int i = 0; i < participants.size(); i++) {
            Participant gifter = participants.get(i);
            Participant receiver = participants
                    .get((i + 1) % participants.size());

            //message that will be sent to each person
            String message = "Merry Christmas " + gifter.getName()
                    + ", your Secret Santa is " + receiver.getName() + "! 🎅";

            // Send SMS
            Message.creator(new PhoneNumber(gifter.getPhoneNumber()),
                    new PhoneNumber(TWILIO_PHONE_NUM), message).create();

            // Confirm that the message was sent
            System.out
                    .println("Text has been sent to " + gifter.getName() + ".");
        }
        //close scanner and destroy Twilio to prevent leaks
        scanner.close();
        Twilio.destroy();
    }
}