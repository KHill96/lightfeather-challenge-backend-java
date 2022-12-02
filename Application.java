import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.StringBuilder;

public class Application{

    public static boolean validPhoneNumber(String numberInput){
        // Regex for 10 digits
        Pattern phoneNumberPattern = Pattern.compile("^\\d{10}$");
        // Match with input string
        Matcher phoneNumberMatcher = phoneNumberPattern.matcher(numberInput);
        // Return whether we have a match
        return phoneNumberMatcher.matches();
    }

    public static boolean validEmail(String emailInput){
        // Regex for email address
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9-]+.[a-zA-Z.]{2,}");
        // Match with input string
        Matcher emailMatcher = emailPattern.matcher(emailInput);
        // Return whether we have a match
        return emailMatcher.matches();
    }

    public static void main(String[] args) throws Exception{
        // Variables to be used
        Scanner sc = new Scanner(System.in);
        String request = "";
        String [] data = {}, itemsToPrint = {};
        StringBuilder builder = new StringBuilder();
        String firstName, lastName, email, phoneNumber, supervisor;

        while(true){
            // Prompt
            System.out.print("Request (GET/POST/EXIT): ");
            // Handle for different case letters
            request = sc.nextLine().toUpperCase();
            
            // GET Case
            if (request.equals("GET")){
                // Get data from API
                String url = "https://o3m5qixdng.execute-api.us-east-1.amazonaws.com/api/managers";
                URL apiLink = new URL(url);
                URLConnection connection = apiLink.openConnection();
                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine = input.readLine();
                
                // Remove Brackets
                inputLine = inputLine.replace('[','\0').replace(']','\0');
                // If I split at every comma I'll end up splitting the entries themselves
                // Need to split by every }, instance
                data = inputLine.split("},");

                // For String in data
                for(String item : data){
                    // Replace Braces and quotes
                    item = item.replace('{', '\0').replace('}','\0').replaceAll("\"", "\0");
                    // Split the entry by comma
                    itemsToPrint = item.split(",");
                    for (String content: itemsToPrint){
                        // Build the string with the data we're looking for
                        if (content.contains("jurisdiction")){
                            // Append the jurisdiction
                            builder.append(content.substring(content.indexOf(":") + 1) + " - ");
                        }
                        else if (content.contains("lastName")){
                            // Replace placeholder with the last name
                            builder.replace(builder.indexOf("ph"),builder.indexOf("ph") + 2, content.substring(content.indexOf(":") + 1) +", ");
                            // builder.repla
                        }
                        else if (content.contains("firstName")){
                            // Append with ph for placeholder
                            builder.append("ph" + content.substring(content.indexOf(":") + 1));
                        }
                    }
                    // Output string
                    System.out.println(builder.toString());
                    // Clear string builder
                    builder.setLength(0);
                }
                
            }
            
            // POST Case
            else if (request.equals("POST")){
                // Input with scanner
                System.out.print("First Name: ");
                firstName = sc.nextLine();
                System.out.print("Last Name: ");
                lastName = sc.nextLine();
                System.out.print("E-Mail (Optional): ");
                email = sc.nextLine();
                System.out.print("Phone Number (Optional): ");
                phoneNumber = sc.nextLine();
                System.out.print("Supervisor: ");
                supervisor = sc.nextLine();

                // Verify that required data isn't blank
                if (firstName.isBlank() || lastName.isBlank() || supervisor.isBlank()){
                    // Error
                    System.out.println("Entry invalid: Required Data Missing");
                }

                else{
                    //  Divider
                    System.out.println("------------------------------------------------");
                    // Name Out[put]
                    System.out.println("Name: " + lastName + ", " + firstName);
                    // If the email is valid
                    if (validEmail(email)){
                        // Print entry
                        System.out.println("Email: " + email);
                    }
                    // If the phone number is valid
                    if (validPhoneNumber(phoneNumber)){
                        // Print entry
                        System.out.println("Phone Number: " + phoneNumber);
                    }
                    // Supervisor output
                    System.out.println("Supervisor: " + supervisor);
                }
                
            }

            // EXIT CASE
            else if (request.equals("EXIT")){
                // Break Out of loop
                break;
            }

            // Divider for readability
            System.out.println("------------------------------------------------");
        }
        // Close the scanner
        sc.close();
        // End the program
        return;
    }
}