public class Utente {
    private String nome;
    private String cognome;
    private String email;
    private String role; //Va settata poi come studente, insegnate 
}

public Utente(String nome, String cognome, String email, String role) {
    this.nome = nome;
    this.cognome = cognome;
    this.email = email;
    this.role = role;
}

public boolean isInsegnante() {
    return "insegnante".equalsIgnoreCase(role); //IgnoreCase perchè altrimenti so già che esplode tutto tempo 0
}

public boolean isStudente() {
    return "studente".equalsIgnoreCase(role);
}

public String getNome() {
    return nome;
}

public String getCognome() {
    return cognome;
}
public String getEmail() {
    return email;
}

public String getRole() {
    return role;
}

public void setNome(String nome) {
    this.nome = nome;
}
public void setCognome(String cognome) {
    this.cognome = cognome;
}  
public void setEmail(String email) {
    this.email = email;
}
public void setRole(String role) {
    this.role = role;
}

