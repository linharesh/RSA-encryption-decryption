package UserInterface;

import ApplicationModel.Decryptor;
import ApplicationModel.Encryptor;
import ApplicationModel.RSAKeys;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Class responsable for all user interaction through the console
 *
 * @author Henrique Linhares ; Raphael Quintanilha ; Filipe Coimbra; Pablo Curty
 */
public class ConsoleInterface {

    private BufferedReader inputConsole;

    /**
     * Constructor
     */
    public ConsoleInterface() {
        this.inputConsole = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Launch the console interface. Gets all the information with the user,
     * such as if he wants to encrypt or decrypt, the keys and the paths to the files
     *
     */
    public void launchConsoleInterface() throws IOException {
        System.out.println("Hello! Welcome to the RSA Encrypter / Decrypter");
        int encriptOrDecript = this.chooseEncryptOrDecrypt();
        RSAKeys keys = this.readKeys();
        String inputFilePath = this.chooseINFile() ; 
        String outputFilePath = this.chooseOUTFile() ;
        
        if (encriptOrDecript == 1){//Encript
            Encryptor encryptor = new Encryptor();
            encryptor.encript(inputFilePath, outputFilePath, keys);
        } else {//Decript
            Decryptor decryptor = new Decryptor();
            decryptor.decript(inputFilePath, outputFilePath, keys);
        }
    }

    /**
     * Write the param message in the console
     *
     * @param S the message that will be displayed in console
     */
    public static void displayMessage(String S) {
        System.out.println(S);
    }

    /**
     * Ask for the user if he wats to encrypt or decrypt a file
     *
     * @return Encode -> returns 1 ; Decode -> returns 2
     * @throws IOException Can throw IO exception
     */
    private int chooseEncryptOrDecrypt() throws IOException {
        int read;
        do {
            System.out.println("1 - Encrypt File");
            System.out.println("2 - Decrypt File");
            String readStr = inputConsole.readLine();
            read = Integer.parseInt(readStr);
        } while (read != 1 && read != 2);
        return read;
    }

    /**
     * Asks the user the path to the INPUT file, and check if the path is
     * correct and if the file exists
     *
     * @return the correct path to the input file
     * @throws IOException Can throw IO exception
     */
    private String chooseINFile() throws IOException {
        File file;
        Boolean fileExists = false;
        String readStr;
        do {
            System.out.println("Enter the path to the INPUT file");
            readStr = inputConsole.readLine();
            file = new File(readStr);
            if (file.exists() && !file.isDirectory()) {
                fileExists = true;
            } else if (!file.exists()) {
                ConsoleInterface.displayMessage("File don't exist");
                fileExists = false;
            }
        } while (fileExists == false);
        return readStr;
    }

    /**
     * Asks the user the path to the OUTPUT file, and check if the path is
     * correct and if the file dont exists
     *
     * @return the correct path to the output file
     * @throws IOException Can throw IO exception
     */
    private String chooseOUTFile() throws IOException {
        File file;
        Boolean pathIsCorrect;
        String readStr;
        do {
            System.out.println("Enter the path to the OUTPUT file");
            readStr = inputConsole.readLine();
            file = new File(readStr);
            if (!file.exists()) {
                if (!file.isDirectory()) {
                    pathIsCorrect = true;
                } else {
                    pathIsCorrect = false;
                }
            } else {
                pathIsCorrect = false;
                ConsoleInterface.displayMessage("File already exists");
            }
        } while (!pathIsCorrect);
        return readStr;
    }

    /**
     * Asks the user the keys to the encrypt / decrypt process
     *
     * @return An array with both keys
     * @throws IOException Can throw IO exception
     */
    private RSAKeys readKeys() throws IOException {
        int nKey;
        int edKey;
        String readStr;

        do {
            System.out.println("Enter the key (N)");
            readStr = inputConsole.readLine();
        } while (!nKeyValidator(readStr));
        nKey = Integer.parseInt(readStr);

        do {
            System.out.println("Enter the key (D or E)");
            readStr = inputConsole.readLine();
        } while (!isNumeric(readStr));
        edKey = Integer.parseInt(readStr);
        
        return new RSAKeys(nKey, edKey);
    }

    /** Validates the N key
     * 
     * @param readStr The string containing the key that will be validated
     * @return if its a number and its between 255 and 65536, return true. Otherwise, return false
     */
    private boolean nKeyValidator(String readStr) {
        int key = Integer.parseInt(readStr);
        if (isNumeric(readStr)) {
            return RSAKeys.nKeyValidator(key);
        }
        return false;
    }

    /** Checks if a string is a number
     * 
     * @param str The string that will be checked
     * @return is a number == true
     *         not a number == false
     */
    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

}
