import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Anmeldesystem {

    private static List<Student> anmeldungen = new ArrayList<>();
    private static List<Tutor> tutoren = new ArrayList<>();
    private static List<Gruppe> gruppen = new ArrayList<>();

    private static int tutors_min_available = 5;
    private static int min_students_per_group = 4;
    private static int max_students_per_group = 6;
    private static int max_students_total;
    private static boolean accept_registration = true;


    public static void main(String[] args){
        if(args.length == 3){ // Manuelles Setup
            tutors_min_available = Integer.valueOf(args[0]);
            min_students_per_group = Integer.valueOf(args[1]);
            max_students_per_group = Integer.valueOf(args[2]);
        }

        max_students_total = tutors_min_available*max_students_per_group;

        System.out.println("Willkommen im Anmeldesystem der Übungsgruppen. Es sind Gruppen von min. " + min_students_per_group + " und max. " + max_students_per_group + " Studenten vorgesehen.");
        System.out.println("Freie Plätze: " + anmeldungen.size() + " / " + max_students_total);
        System.out.println("Anmeldung in diesem Format:");
        System.out.println("Vorname Nachname");

        Scanner s = new Scanner(System.in);
        while(s.hasNext()){
            String in = s.nextLine();
            if(in.startsWith("#")){ // Abfrage ob Befehl oder Anmeldung
                in = in.replaceAll("#", "");
                switch (in){
                    case "close": zuordnen();
                                    break;

                    case "get": print();
                        break;
                }
            } else {
                String[] arg = in.split(" ");
                if(arg.length == 2){
                    if(accept_registration){
                        if(anmeldungen.size() < max_students_total){
                            anmeldungen.add(new Student(arg[0], arg[1]));
                            System.out.println("Die Anmeldung zur Gruppe war erfolgreich. Die Zuteilung erfahren Sie demnächst von Ihrem Dozenten.");
                            System.out.println("");
                            System.out.println("");
                            System.out.println("Willkommen im Anmeldesystem der Übungsgruppen. Es sind Gruppen von min. " + min_students_per_group + " und max. " + max_students_per_group + " Studenten vorgesehen.");
                            System.out.println("Freie Plätze: " + anmeldungen.size() + " / " + max_students_total);
                        } else {
                            System.out.println("ERROR: Die maximale Anzahl an Studenten ist bereits erreicht.");
                        }
                    } else {
                        System.out.println("ERROR: Die Anmeldung ist bereits geschlossen.");
                    }
                }

                if(arg.length == 3 && arg[2].equalsIgnoreCase("-t")){
                    if(accept_registration){
                        tutoren.add(new Tutor(arg[0], arg[1]));
                        System.out.println("Die Anmeldung zur Gruppe als Tutor war erfolgreich. Die Zuteilung erfahren Sie demnächst von dem zuständigen Dozenten.");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("Willkommen im Anmeldesystem der Übungsgruppen. Es sind Gruppen von min. " + min_students_per_group + " und max. " + max_students_per_group + " Studenten vorgesehen.");
                        System.out.println("Freie Plätze: " + anmeldungen.size() + " / " + max_students_total);
                    } else {
                        System.out.println("ERROR: Die Anmeldung ist bereits geschlossen.");
                    }
                }
            }
        }
        s.close();
    }

    private static void zuordnen(){
        if(tutoren.size() < tutors_min_available){ // Check if there are enough tut's registered
            System.out.println("Es sind noch nicht genügend Tutoren angemeldet.");
            return;
        }

        accept_registration = false; // Set Registration to closed

        int groups = groupsNeeded(); // Get Count of needed Groups
        int currentgroup = 0;
        for(int i=0; i<groups; i++){
            gruppen.add(new Gruppe()); // Create Groups
        }

        for(Student student : anmeldungen){ // run trough all registrations
            gruppen.get(currentgroup).addTeilnehmer(student);

            if(currentgroup == groups-1) {
                currentgroup = 0;
            } else {
                currentgroup++;
            }
        }
        currentgroup = 0;
        for(Tutor tutor : tutoren){ // run through all tut's
            gruppen.get(currentgroup).addTutor(tutor);

            if(currentgroup == groups-1) {
                currentgroup = 0;
            } else {
                currentgroup++;
            }
        }
    }

    /*

    Print List of assignment in this format:
    groupname:
     - Max Mustermann (tutor)
     - Anja Musterfrau

     */
    private static void print(){
        int i = 1;
        for(Gruppe gruppe : gruppen){
            System.out.println("Gruppe" + i + ":");

            for(Tutor tutor : gruppe.getTutoren()){
                System.out.println(" - " + tutor.getVorname() + " " + tutor.getNachname() + "(Tutor)");
            }

            for(Student student : gruppe.getTeilnehmer()){
                System.out.println(" - " + student.getVorname() + " " + student.getNachname());
            }
            i++;
        }
    }

    private static int groupsNeeded(){
        int users = anmeldungen.size(); // Anzahl Studenten
        int r = users % min_students_per_group; // Anzahl Reststudenten bei Teilung durch max Gruppengröße
        int groups = (users-r) / min_students_per_group; // Anzahl Gruppen ohne die Reststudenten

        if(r != 0){ // Überprüfe ob noch Reststudenten vorhanden sind
            groups++;
        }

        return groups;
    }

}
