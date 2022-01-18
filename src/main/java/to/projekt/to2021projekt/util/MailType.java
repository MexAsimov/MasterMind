package to.projekt.to2021projekt.util;

public enum MailType {
    RegisterNotification, NewRecordNotification;

    public String toString() {
        if (this == RegisterNotification) {
            return "<b>Witaj. </b><br> Założyłes nowe konto w aplikacji MasterMind! Baw sie dobrze.";
        } else if (this == NewRecordNotification) {
            return "<b>Witaj. </b><br> Ktos wyprzedzil cie w rankingu! Popraw swój wynik.";
        }
        return "";
    }
}
