import java.util.ArrayList;
import java.util.List;

public class Gruppe {

    private List<Student> teilnehmer;
    private List<Tutor> tutoren;

    public Gruppe() {
        teilnehmer = new ArrayList<>();
        tutoren = new ArrayList<>();
    }

    public Gruppe(ArrayList<Student> teilnehmer, ArrayList<Tutor> tutoren) {
        this.teilnehmer = new ArrayList<>();
        this.tutoren = new ArrayList<>();
    }

    public List<Student> getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(ArrayList<Student> teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public void addTeilnehmer(Student student) {
        teilnehmer.add(student);
    }

    public void removeTeilnehmer(Student student) {
        teilnehmer.remove(student);
    }

    public List<Tutor> getTutoren() {
        return tutoren;
    }

    public void setTutoren(ArrayList<Tutor> tutoren) {
        this.tutoren = tutoren;
    }

    public void addTutor(Tutor tutor) {
        tutoren.add(tutor);
    }

    public void removeTutor(Tutor tutor) {
        tutoren.remove(tutor);
    }
}
