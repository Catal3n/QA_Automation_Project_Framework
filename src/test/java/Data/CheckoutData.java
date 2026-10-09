package Data;

// Datele pe care un client le completează la checkout, grupate într-un singur obiect.
// "record" = o clasă făcută special pentru a ține date. Java generează AUTOMAT:
//   - câmpurile (private final),
//   - constructorul cu toate cele 7 valori,
//   - metodele de citire: firstName(), lastName(), ... (fără "get" în față).
public record CheckoutData(
        String firstName,
        String lastName,
        String address,
        String cardNumber,
        String expiry,
        String cvv,
        String nameOnCard
) {

    // Un set de date valid, gata de folosit în teste (ca Users.STANDARD).
    public static final CheckoutData VALID = new CheckoutData(
            "Ion",
            "Popescu",
            "Strada Exemplu 10, Timișoara",
            "4242 4242 4242 4242", // card de TEST, sugerat chiar de site
            "12/30",
            "123",
            "Ion Popescu"
    );
}
