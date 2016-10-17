public class happyNumberTest {
    public static void main(String[] args) {
        happyNumber hn = new happyNumber();

        System.out.print("Happy Numbers between 9,001 and 10,000: ");
        for (int i = 1; i < 10001; i++) {
            hn.test(i);
        }
        System.out.println("\nRandomly generated number, >30 digits.");
        while (hn.getHappyStatus() == false) {
            hn.startLargeTest();
            hn.test(hn.generate());
        }
        System.out.println("\nLarge pre-generated unhappy number: 47767571321471647576382011386513");
        hn.startUnhappyNumber();
        hn.test("47767571321471647576382011386513");

        System.out.println("\nFalse inputs that should not work:");
        System.out.println("Number primatives:");
        System.out.printf("Testing -18174 (negative integer).\n");
        hn.test(-18174);
        System.out.printf("Testing 0 (non-positive integer).\n");
        hn.test(0);
        System.out.printf("Testing 4.2 (non-integer).\n");
        hn.test(4.2);
        System.out.println("\nString inputs:" );
        System.out.printf("Testing 000000000000000000000000000000 (invalid large input).\n");
        hn.test("000000000000000000000000000000");
        System.out.printf("Testing abcd (invalid input).\n");
        hn.test("abcd");
        System.out.printf("Testing \"\" (empty input).\n");
        hn.test("");
    }
}