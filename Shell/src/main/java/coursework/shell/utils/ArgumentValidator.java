package coursework.shell.utils;

public class ArgumentValidator {

    public static boolean validateArgsLength(String[] args, int expectedLength) {
        if (args.length < expectedLength) {
            System.out.println("Not enough arguments. Require: " + expectedLength);
            return false;
        }
        return true;
    }
}
