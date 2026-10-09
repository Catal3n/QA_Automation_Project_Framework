package Data;

// enum = o clasă cu un număr FIX de valori.
// Fiecare valoare de mai jos e un utilizator de test al aplicației.
// Nimeni nu poate crea alți utilizatori cu "new Users(...)".
public enum Users {

    // Valorile enum-ului. Fiecare apelează constructorul de mai jos cu username și parolă.
    // Se despart prin virgulă, iar lista se încheie cu punct și virgulă.
    STANDARD("standard_user", "standard123"),
    ADMIN("admin_user", "$Admin08102026"),
    LOCKED("locked_user", "locked123"),
    INVALID("invalid_user", "wrong123");

    // Fiecare utilizator are două informații, care nu se mai schimbă după creare (final).
    private final String username;
    private final String password;

    // Constructorul unui enum e întotdeauna privat: îl folosesc doar valorile de mai sus.
    Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getteri: testele pot CITI datele, dar nu le pot modifica.
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}