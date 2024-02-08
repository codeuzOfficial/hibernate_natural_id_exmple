package dasturlash.uz;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
//        saveAndGetStudent();
//        getStudentByNaturalId();
        saveAndGetSubject();
    }

    public static void saveAndGetStudent() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        StudentEntity student = new StudentEntity();
        student.setName("Ali");
        student.setSurname("Aliyev");
        student.setPassportId("AB1234567");

        session.save(student); // save student1;
        t.commit();
        session.clear();
        // get student by naturalId
        Optional<StudentEntity> optional = session.bySimpleNaturalId(StudentEntity.class).loadOptional("AB1234567");
        if (optional.isPresent()) {
            System.out.println(optional.get());
        }

        session.close();
        factory.close();
    }

    public static void saveAndGetSubject() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        SubjectEntity student = new SubjectEntity();
        student.setTitle("Java");
        student.setLocalCode("AOO1");
        student.setGeneralCode("AAB");

        session.save(student); // save student1;
        t.commit();
        session.clear();

        // get subject by naturalId
        Optional<SubjectEntity> optional = session.byNaturalId(SubjectEntity.class)
                .using("localCode", "AOO1")
                .using("generalCode", "AAB")
                .loadOptional();

        if (optional.isPresent()) {
            System.out.println(optional.get());
        }

        session.close();
        factory.close();
    }

    public static void getStudentByNaturalId() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Optional<StudentEntity> optional = session.bySimpleNaturalId(StudentEntity.class).loadOptional("AB1234567");
        if (optional.isPresent()) {
            System.out.println(optional.get());
        }

//      session.byNaturalId(StudentEntity.class).using("AB1234567").loadOptional();

        t.commit();
        session.close();
        factory.close();
    }
}