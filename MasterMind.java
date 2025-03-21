import java.util.Scanner;
import java.util.Random;

public class MasterMind {
    public static void main(String[] args) {
        boolean supportsAnsi = supportsAnsi();

        final String RESET = supportsAnsi ? "\u001B" : "";
        final String GREEN = supportsAnsi ? "\u001B" : "";
        final String YELLOW = supportsAnsi ? "\u001B" : "";
        final String RED = supportsAnsi ? "\u001B" : "";
        final String CYAN = supportsAnsi ? "\u001B" : "";

        System.out.println(CYAN + "🎮 Witaj w Grze MasterMind! 🎮" + RESET);
        System.out.println("Zgadnij " + GREEN + "4-cyfrowy kod" + RESET + " składający się z liczb od " + YELLOW + "1" + RESET + " do " + YELLOW + "6" + RESET + ".");
        
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        int codeLength = 4;
        int maxDigit = 6;
        int[] secretCode = new int[codeLength];
        int[] userCode = new int[codeLength];
        boolean guessed = false;

        for (int i = 0; i < codeLength; i++) {
            secretCode[i] = random.nextInt(maxDigit) + 1;
        }

        while (!guessed) {
            System.out.print(CYAN + "\n🔢 Wybierz swoje liczby: " + RESET);
            String guess = scanner.nextLine();

            try {
                if (guess.length() != codeLength) {
                    throw new NumberFormatException();
                }

                for (int i = 0; i < codeLength; i++) {
                    userCode[i] = Character.getNumericValue(guess.charAt(i));
                    if (userCode[i] < 1 || userCode[i] > maxDigit) {
                        throw new NumberFormatException();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "❗ Wprowadź liczby od 1 do " + maxDigit + "." + RESET);
                continue;
            }

            int identicalButNotInPlace = 0;
            int identicalAndInPlace = 0;
            boolean[] countedInUserCode = new boolean[codeLength];
            boolean[] countedInSecretCode = new boolean[codeLength];

            for (int i = 0; i < codeLength; i++) {
                if (userCode[i] == secretCode[i]) {
                    identicalAndInPlace++;
                    countedInUserCode[i] = true;
                    countedInSecretCode[i] = true;
                }
            }

            for (int i = 0; i < codeLength; i++) {
                if (!countedInUserCode[i]) {
                    for (int j = 0; j < codeLength; j++) {
                        if (!countedInSecretCode[j] && userCode[i] == secretCode[j]) {
                            identicalButNotInPlace++;
                            countedInUserCode[i] = true;
                            countedInSecretCode[j] = true;
                        }
                    }
                }
            }

            if (identicalAndInPlace == codeLength) {
                System.out.println(GREEN + "🎉 Gratulacje, odgadłeś kod! 🎉" + RESET);
                guessed = true;
            } else {
                System.out.println(GREEN + "✔ Poprawne cyfry na poprawnej pozycji: " + identicalAndInPlace + RESET);
                System.out.println(YELLOW + "➖ Poprawne cyfry na niepoprawnych pozycjach: " + identicalButNotInPlace + RESET);
            }
        }

        scanner.close();
    }

    private static boolean supportsAnsi() {
        String os = System.getProperty("os.name").toLowerCase();
        return !(os.contains("win")) || System.console() != null;
    }
}
